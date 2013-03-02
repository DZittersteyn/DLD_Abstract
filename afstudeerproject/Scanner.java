package afstudeerproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Scanner {

    java.util.Scanner curstring;
    BufferedReader f;

    public Scanner(File file) {
        try {
            f = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            System.exit(1);
        }
        this.buffernext();
    }
    
    public int nextInt(){
        if(curstring==null){
            System.err.println("Read past EOF");
        }
        
        int result = curstring.nextInt();
        
        if(!curstring.hasNext()){
            buffernext();
        }
        
        return result;
    }
    public double nextDouble(){
        if(curstring==null){
            System.err.println("Read past EOF");
        }
        
        double result = curstring.nextDouble();
        
        if(!curstring.hasNext()){
            buffernext();
        }
        
        return result;
    }
    public String next(){
        if(curstring==null){
            System.err.println("Read past EOF");
        }
        
        String result = curstring.next();
        
        if(!curstring.hasNext()){
            buffernext();
        }
        
        return result;
    }
    
    
    
    public final void buffernext() {
        String s = null;
        try {
            s = f.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(s==null){
            curstring = null;
        }else{
            curstring = new java.util.Scanner(s);
        }
        
        
    }
}
