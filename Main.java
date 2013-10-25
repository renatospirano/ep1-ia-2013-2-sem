
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        try {
            
            //String file1 = "optN.txt";
            String file2 = "wSelectNorm.txt";
            
            FileReader fin = new FileReader (file2);
            
            
            /*
            if (args [0] == "0"){
                fin = new FileReader (file1);
            } else if (args[0] == "1"){
                fin = new FileReader (file2);
            } else {
                System.err.println ("Escolher entre 0 (Binário) e 1 (MultiClasse)");
            }
            * */


            //FileReader fin = new FileReader("optdigits.tra");
            Scanner sc = new Scanner(fin);
            
            sc.useDelimiter("[,\\s]+");
            int tBase = Integer.parseInt(sc.next()); // tamanho da base
            int tAtrib = Integer.parseInt(sc.next()); // número de atributos
            int saida = Integer.parseInt(sc.next()); // saída

            CamadaSensorial conjTreinamento[] = new CamadaSensorial[tBase]; // define vetor de treinamento do tamanho da base

            int i = 0;

            while (i < tBase) {
                conjTreinamento[i] = new CamadaSensorial(tAtrib);
                int j = 0;
                //System.out.print(i + " ");
                while (j < tAtrib) {

                    conjTreinamento[i].neuroniosSensoriais[j] = Double.parseDouble(sc.next());

                    j++;
                }
                conjTreinamento[i].resposta = sc.nextInt();
                //System.out.println(conjTreinamento[i].resposta);
                i++;
            }

            Scanner sc2 = new Scanner(System.in);

            System.out.println("Tamanho da camada escondida");

            int camadaEscondida = sc2.nextInt();

            System.out.println("Número de épocas");

            int maximoEpocas = sc2.nextInt();

            

            if (camadaEscondida == 0) {

                camadaEscondida = tAtrib * 3 / 4;
            }

            int pesoV = tAtrib * camadaEscondida;
            int biasV = camadaEscondida;

            int pesoW = camadaEscondida * saida;
            int biasW = saida;


            Teste t = new Teste();

            Rede rede = new Rede(pesoV, biasV, camadaEscondida, pesoW, biasW, saida);

            System.out.println("Digite:\n 0 - inicialização com zeros "
                    + " outro dígito - inicialização aleatória");

            int iniPesos = sc2.nextInt();

            if (iniPesos == 0) {
                rede.preencheRede(); //preenche rede somente com valor 0
            } else {
                rede.preencheRedeAleatorio(); // preenche rede com valores aleatórios
            }

            List<Resultados> resultados = new ArrayList<Resultados>();
            
            System.out.println("Taxa de aprendizagem");

            float teta = Float.parseFloat(sc2.next());
            
            System.out.println("Digite:\n 0 - taxa de aprendizagem fixa "
                    + " outro dígito - usar função de decréscimo");

            int usaFuncao = sc2.nextInt();
            boolean taxaAprendizagem;
            if (usaFuncao == 0) taxaAprendizagem = false;
            else
                taxaAprendizagem = true;
            
            i = 0;
            int n = 30;
            while (i < n) {
                CamadaSensorial[] aux = new CamadaSensorial[conjTreinamento.length];
                System.arraycopy(conjTreinamento, 0, aux, 0, conjTreinamento.length);
                resultados.add(t.holdout(aux, rede, teta, maximoEpocas, taxaAprendizagem));
                i++;
                System.out.println("i = " + i);
            }
            Resultados r = new Resultados();
            r.ordenaResultados(resultados);
            double[] erros = new double[n];
            double media = 0;
            for (i = 0; i < n; i++) {
                //System.out.println("Resultado [" + i +  "] " + resultados.get(i).erroTeste);
                erros[i] += resultados.get(i).erroTeste;
                media += erros[i];
                //System.out.println("Erros [" + i +  "] " + erros[i]);
            }

            media = media / n;
            Double desvioPadrao = new Double(0);

            for (i = 0; i < n; i++) {
                //System.out.println(resultados.get(i).erroTeste);
                desvioPadrao += (Math.pow((erros[i] - media), 2)) / n;
            }

            //System.out.println(desvioPadrao);

            desvioPadrao = Math.sqrt(desvioPadrao);
            //System.out.println(desvioPadrao);
            double min = erros[0];
            double max = erros[n - 1];
            double mediana = erros[n / 2];


            File arquivo;

            Calendar hoje = Calendar.getInstance();
            SimpleDateFormat formataDataHora = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH_mm_ss");


            arquivo = new File("log\\" + formataDataHora.format(hoje.getTime()) + ".txt");
            //FileWriter fos = new FileWriter(arquivo);
            PrintWriter p = new PrintWriter(new FileWriter(arquivo, true));
            p.print("Camada escondida = " + camadaEscondida + " número de"
                    + " épocas = " + maximoEpocas + " alfa = " + teta);
            p.println();
            if (iniPesos == 0)
                p.print("Inicialização dos pesos com 0");
            else
                p.print("Inicialização dos pesos aleatória");
            p.println();
            if (usaFuncao == 0) 
                p.print("Taxa de aprendizagem estática");
            else
                p.print("Taxa de aprendizagem dinâmica");
            p.println();
            String texto = "Média = " + media + " ";
            p.print(texto);
            texto = "Mediana = " + mediana + " ";
            p.print(texto);
            texto = "Mínimo = " + min + " ";
            p.print(texto);
            texto = "Máximo = " + max + " ";
            p.print(texto);
            texto = "Desvio padrão = " + desvioPadrao;
            p.println(texto);
            texto = "Matriz de confusão da mediana:";
            p.println(texto);
            i = 0;
            while (i < saida) {
                int j = 0;
                while (j < saida) {
                    p.print(resultados.get(n / 2).matriz[i][j] + " ");
                    j++;
                }
                i++;
                p.println();
            }
            texto = "Valor dos pesos da camada sensorial da mediana: ";
            p.println(texto);
            for (int j = 0; j < rede.pesoV.length; j++) {
                p.println(resultados.get(n/2).rede.pesoV[j]);
            }

            texto = "Valor dos pesos da camada escondida da mediana: ";
            p.println(texto);
            for (int j = 0; j < rede.pesoW.length; j++) {
                p.println(resultados.get(n/2).rede.pesoW[j]);
            }
            texto = "Valor dos bias da camada sensorial da mediana: ";
            p.println(texto);
            for (int j = 0; j < rede.biasV.length; j++) {
                p.println(resultados.get(n/2).rede.biasV[j]);
            }
            texto = "Valor dos bias da camada escondida da mediana: ";
            p.println(texto);
            for (int j = 0; j < rede.biasW.length; j++) {
                p.println(resultados.get(n/2).rede.biasW[j]);
            }
            p.println();
                                                                        
            
            texto = "Matriz de confusão do máximo:";
            p.println(texto);
            i = 0;
            while (i < saida) {
                int j = 0;
                while (j < saida) {
                    p.print(resultados.get(n - 1).matriz[i][j] + " ");
                    j++;
                }
                i++;
                p.println();
            }
            texto = "Valor dos pesos da camada sensorial do máximo: ";
            p.println(texto);
            for (int j = 0; j < rede.pesoV.length; j++) {
                p.println(resultados.get(n - 1).rede.pesoV[j]);
            }

            texto = "Valor dos pesos da camada escondida do máximo: ";
            p.println(texto);
            for (int j = 0; j < rede.pesoW.length; j++) {
                p.println(resultados.get(n - 1).rede.pesoW[j]);
            }
            texto = "Valor dos bias da camada sensorial do máximo: ";
            p.println(texto);
            for (int j = 0; j < rede.biasV.length; j++) {
                p.println(resultados.get(n - 1).rede.biasV[j]);
            }
            texto = "Valor dos bias da camada escondida do máximo: ";
            p.println(texto);
            for (int j = 0; j < rede.biasW.length; j++) {
                p.println(resultados.get(n - 1).rede.biasW[j]);
            }
            p.println();                                                
            
            texto = "Matriz de confusão do mínimo:";
            p.println(texto);
            i = 0;
            while (i < saida) {
                int j = 0;
                while (j < saida) {
                    p.print(resultados.get(0).matriz[i][j] + " ");
                    j++;
                }
                i++;
                p.println();
            }
            texto = "Valor dos pesos da camada sensorial do mínimo: ";
            p.println(texto);
            for (int j = 0; j < rede.pesoV.length; j++) {
                p.println(resultados.get(0).rede.pesoV[j]);
            }

            texto = "Valor dos pesos da camada escondida do mínimo: ";
            p.println(texto);
            for (int j = 0; j < rede.pesoW.length; j++) {
                p.println(resultados.get(0).rede.pesoW[j]);
            }
            texto = "Valor dos bias da camada sensorial do mínimo: ";
            p.println(texto);
            for (int j = 0; j < rede.biasV.length; j++) {
                p.println(resultados.get(0).rede.biasV[j]);
            }
            texto = "Valor dos bias da camada escondida do mínimo: ";
            p.println(texto);
            for (int j = 0; j < rede.biasW.length; j++) {
                p.println(resultados.get(0).rede.biasW[j]);
            }                        
            p.close();

            System.out.println(media + " " + mediana + " " + min + " "
                    + max + " " + desvioPadrao);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
