
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {


    public static void main(String[] args) throws IOException {
// testing code
//        MNIST.LoadMNIST();
//        File directory = new File("./img/test");
//        Instant begin = Instant.now();
//        int good = 0,bad = 0;
//        for (File f : directory.listFiles()) {
//            try {
//                DigitImage di = new DigitImage(f.getPath());
//                int expected = f.getName().charAt(0) - '0';
//                Result calc = MNIST.KNN(di,10);
//                boolean correct = expected == calc.label;
//                if (correct) good++;
//                else bad++;
////                System.out.println(correct + " " + f.getName() + " " + calc);
//            }
//            catch (IOException ioe) {
//                System.err.println("couldn't read image " + f.getName());
//            }
//        }
//        Instant cur = Instant.now();
//        System.err.println(good*100.0/(good + bad));
//        System.err.println(Duration.between(begin,cur).toMillis()/(good + bad));

        MNIST.LoadMNIST();

        Form f = new Form();
    }
}
