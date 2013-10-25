
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reneealves
 */
public class Altera {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        FileReader f = new FileReader ("optNorm.txt");
        
        Scanner sc = new Scanner (f);
        
        DecimalFormat df = new DecimalFormat ("0.###");
        
        int i = 0;
        
        while (sc.hasNext()){
            
            if (i == 64){
                i = 0;
                System.out.println();
            }
            
            double a = Double.parseDouble(sc.next());
            String result = df.format(a);
            System.out.print(result + "\t");
            i++;
        }
    }
}
