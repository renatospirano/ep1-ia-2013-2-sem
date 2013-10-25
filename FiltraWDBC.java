
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
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
public class FiltraWDBC {

    public static void main(String args[]) throws FileNotFoundException {

        FileReader fin = new FileReader("wdbc_data.txt");
        Scanner sc = new Scanner(fin);

        ArrayList<String> list = new ArrayList<String>();

        sc.useDelimiter("[,\\s]+");
        try {
            File arquivo = new File("wdbc_data_filtro.xls");
            PrintWriter p = new PrintWriter(new FileWriter(arquivo, true));
            
                int contador = 0;
            while (true) {
                char[] teste = sc.next().toCharArray();
                for (int k = 0; k < teste.length; k++) {

                    boolean resp = false;
                    
                        if (contador == 28) {
                            contador = 0;
                            System.out.println("PULANDO");
                        }
                    if (teste[k] == 'M' || teste[k] == 'B') {
                        break;
                    }
                    if (teste[k] == '.') {
                        resp = true;
                    }
                    if (resp) {
                        
                        String a = "";
                        for (int j = 0; j < teste.length; j++) {

                            a += teste[j];
                        }
                        System.out.print(a + "\t");
                        contador ++;
                        //list.add(a);
                        //p.print(a + "," + "\t");

                        //
                    }
                    /*
                    if (list.size() == 29) {
                        p.print("\n");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.print(list.get(i) + "\t");
                            p.print(list.get(i) + "\t");
                        }
                        list.clear();

                        System.out.println();
                    }
                    * */
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }


    }
}
