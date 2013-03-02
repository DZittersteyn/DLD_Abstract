package afstudeerproject;

public class PercPrettyPrinter {

    Progress p; 

    public PercPrettyPrinter(String desc, int max) {
        p = new Progress(desc, max);
    }

    public void print(int current) {
        p.setProgress(current);
    }
    public void close(){
        p.dispose();
    }
}
