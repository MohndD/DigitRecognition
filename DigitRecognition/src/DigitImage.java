import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class DigitImage {
    private static final int Width = 64,Height = 64;
    private static final int DisplayWidth = 400,DisplayHeight = 400;
    private BufferedImage image;

    public DigitImage(){
        image = new BufferedImage(Width,Height,BufferedImage.TYPE_BYTE_GRAY);
    }
    public DigitImage (String path) throws IOException {
        File ImageFile = new File(path);
        image = ImageIO.read(ImageFile);
        image = ImageUtils.rescale(image,Width,Height);
    }
    public DigitImage (String [] MNISTRecord) throws IOException {
        int []  grid = new int[28*28];
        for (int i = 0;i < 28*28;i++)
            grid[i] = Integer.parseInt(MNISTRecord[i+1].trim());
        BufferedImage im = new BufferedImage(28,28,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = im.getRaster();
        raster.setPixels(0,0,28,28,grid);
        image = ImageUtils.rescale(im,Width,Height);
    }
    public Image getScaledImage(){
        return ImageUtils.rescale(image,DisplayWidth,DisplayHeight);
    }

    public void trim(){
        image = ImageUtils.trim(image);
        image = ImageUtils.rescale(image,Width,Height);
    }
    public void showImage() {
        JFrame frame = new JFrame();
        Image scaledImage = image.getScaledInstance(DisplayWidth,DisplayHeight,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public int [] [] getBitmap(){
        return ImageUtils.getBitmap(image);
    }
    public long getEuclideanDistance(DigitImage O) {
        long dist = 0;
        int [] [] A = getBitmap();
        int [] [] B = O.getBitmap();
        for (int i = 0;i < Height;i++)
            for (int j = 0;j < Width;j++){
                int diff = A[i][j] - B[i][j];
                dist += diff*diff;
            }
        return dist;
    }
    public void drawLine(Point start,Point end) {
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(6));

        int x1 = start.x*Width/DisplayWidth,y1 = start.y*Height/DisplayHeight;
        int x2 = end.x*Width/DisplayWidth,y2 = end.y*Height/DisplayHeight;
        x1 = clip(x1,0,Width);
        x2 = clip(x2,0,Width);
        y1 = clip(y1,0,Height);
        y2 = clip(y2,0,Height);
        g.drawLine(x1,y1,x2,y2);
    }
    public int clip(int x,int l,int r) {
        if (x < l) return l;
        if (x > r) return r;
        return x;
    }
}
