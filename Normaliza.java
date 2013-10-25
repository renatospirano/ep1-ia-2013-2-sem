
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reneealves
 */
public class Normaliza {
    
    public static double normalize (double a, double b, double min, double max, double number){
        
        double termo1 = number - min;
        double termo2 = b - a;
        double termo3 = max - min;
        
        double mult = termo1 * termo2;
        double div = mult / termo3;
        
        return a + div;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        FileReader f = new FileReader ("wSelect.txt");
        
        Scanner sc = new Scanner (f);
        sc.useDelimiter("[,\\s]+");
        
        double max = -1;
        double min = 2;
        
        int a = 0;
        int b = 1;
        
        ArrayList <Double> list = new ArrayList ();
        
        while (sc.hasNext()){
            
            list.add(Double.parseDouble(sc.next()));
        }
        
        for (int i = 0; i < list.size(); i ++){
            
            if (list.get(i) >= max){
                max = list.get(i);
            }
        }
        
        for (int i = 0; i < list.size(); i ++){
            
            if (list.get(i) <= min){
                min = list.get(i);
            }
        }
        
        System.out.println (list.size());
        System.out.println ("Max: " + max);
        System.out.println ("Min: " + min);
        
        System.out.println (normalize (1, 10, 92000, 64525000, 2214000));
    }
}
