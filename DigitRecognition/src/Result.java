public class Result {
    int label;
    double confidance;
    public Result(int r,double c) {
        label = r;
        confidance = c;
    }
    @Override
    public String toString(){
        return "Digit is " + label + " with " + (100*confidance) + "% confidence";
    }
}
