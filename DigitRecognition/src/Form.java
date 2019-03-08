import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Form extends JFrame{
    private JButton drawDigitButton;
    private JButton loadImageButton;
    private JPanel ImagePanel;
    private JPanel MainPanel;
    private JLabel ImageLabel;
    private JLabel result;
    private static final JFileChooser jfc = new JFileChooser();
    private Point pointStart = null,pointEnd = null;
    private static DigitImage figure;
    private static boolean isDrawing = false;

    private void useCase1(){
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String path = jfc.getSelectedFile().getPath();
            try{
                DigitImage di = new DigitImage(path);
                ImageLabel.setIcon(new ImageIcon(di.getScaledImage()));
                ImageLabel.repaint();
                MainPanel.repaint();
                result.setText("processing");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Result r = MNIST.KNN(di,10);
                        result.setText(r.toString());
                    }
                });
            }
            catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, ioe.getMessage(), "InfoBox: " + "couldn't read image", JOptionPane.INFORMATION_MESSAGE);                    }

        }
    }
    private void useCase2(){
        //clear
        figure = new DigitImage();
        ImageLabel.setIcon(new ImageIcon(figure.getScaledImage()));
        ImageLabel.repaint();
        isDrawing = true;
    }
    public Form() {
        //create GUI
        this.setTitle("Digit Recognizer");
        ImageLabel.setIcon(new ImageIcon(new DigitImage().getScaledImage()));
        this.add(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);

        loadImageButton.addActionListener(new ActionListener() {
            //if pressed open file browser and classify chosen image
            @Override
            public void actionPerformed(ActionEvent e) {
                useCase1();
            }
        });

        drawDigitButton.addActionListener(new ActionListener() {
            //if pressed allow mouse to draw
            @Override
            public void actionPerformed(ActionEvent e) {
                useCase2();
                repaint();
            }
        });
        ImagePanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //get current mouse location relative to drawing space
                int x = e.getX() - ImagePanel.getX();
                int y = e.getY() - ImagePanel.getY();
                pointEnd = new Point(x,y);
                pointStart = pointEnd;
            }

            public void mouseReleased(MouseEvent e) {
                if (!isDrawing) return;
                pointStart = null;
                isDrawing = false;
                result.setText("processing");
                // open new thread to run KNN
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(MNIST.KNN(figure,10).toString());
                        result.repaint();
                    }
                });
            }
        });
        ImagePanel.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                //approximate a curve as a series of small line segments
                int x = e.getX() - ImagePanel.getX();
                int y = e.getY() - ImagePanel.getY();
                pointEnd = new Point(x,y);
                if (pointStart != null && isDrawing) {
                    figure.drawLine(pointStart,pointEnd);
                    ImageLabel.setIcon(new ImageIcon(figure.getScaledImage()));
                    ImageLabel.repaint();
                    repaint();
                }
                pointStart = pointEnd;
            }
        });

    }

    public void setData(Form data) {
        this.setLayeredPane(data.getLayeredPane());
    }

    public void getData(Form data) {

    }

    public boolean isModified(Form data) {
        return false;
    }
}
