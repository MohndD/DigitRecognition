import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MNIST {
    private static ArrayList<MNISTImage> images;
    private static String csvPath = "./img/train.csv";

    public static void LoadMNIST() throws IOException {
        if (images != null) return;
        System.err.println("Loading MNIST");
        BufferedReader br = new BufferedReader(new FileReader(csvPath));
        br.readLine();
        images = new ArrayList<>();
        while (br.ready()) {
            String [] line = br.readLine().split(",");
            DigitImage di = new DigitImage(line);
            di.trim();
            int label = Integer.parseInt(line[0].trim());
            images.add(new MNISTImage(label,di));
        }
        System.err.println("Done");
    }
    public static Result KNN (DigitImage di,int k) {
        di.trim();
        for (int i = 0;i < images.size();i++){
            DigitImage curDigit = images.get(i).getImage();
            long dist = curDigit.getEuclideanDistance(di);
            images.get(i).setDist(dist);
        }
        Collections.sort(images);
        int ctr = 0;
        int [] freq = new int[10];
        for (int i = 0;i < k && i < images.size();i++) {
            ctr++;
            freq[images.get(i).getLabel()]++;
        }
        int choice = 0;
        for (int d = 0;d < 10;d++)
            if (freq[d] > freq[choice])
                choice = d;
        return new Result(choice,freq[choice]/(double)ctr);
    }
}

class MNISTImage implements Comparable<MNISTImage>{
    private DigitImage image;
    private int label;
    private long dist;

    public MNISTImage(int lab,DigitImage im) {
        label = lab;
        image = im;
        dist = 0;
    }

    public DigitImage getImage(){
        return image;
    }
    public int getLabel(){
        return label;
    }
    public void setDist(long v) {
        dist = v;
    }
    @Override
    public int compareTo(MNISTImage O) {
        if (dist < O.dist) return -1;
        if (dist == O.dist) return 0;
        return 1;
    }
}
