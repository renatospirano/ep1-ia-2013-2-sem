public class Teste {
    
    CamadaSensorial teste[];
    CamadaSensorial treino[];
    CamadaSensorial validacao[];
    Rede rede;
    int saida;

    public Resultados holdout(CamadaSensorial[] base, Rede rede2, float
            teta, int maximoEpocas, 
            boolean funcao) {
        Resultados r = new Resultados();
        
        double r1 = base.length * 0.2;
        int tst = (int) r1;
        
        double r2 = base.length * 0.6;
        int trn = (int ) r2;
        
        int valid = tst;

        validacao = new CamadaSensorial [valid];
        teste = new CamadaSensorial[tst];
        treino = new CamadaSensorial[trn];
        
        try {
            CamadaSensorial[] aux = embaralhaConjunto(base);
           
            // atribuição de valores ao conjunto de treino

            for (int i = 0; i < treino.length; i++) {
                treino[i] = aux[i];
            }
            
            // atribuição de valores ao conjunto de teste
           
            int j = treino.length;
            for (int i = 0; i < teste.length; i++, j++) {
                teste[i] = aux[j];
            }
            
            // atribuição de valores ao conjunto de validação
            
            int a = treino.length + teste.length; 
            for (int i = 0; i < validacao.length; i ++, a ++){
                validacao[i] = aux[a];
            }
            
            // ******************************************************

            rede = rede2;
            //Perceptron p = new Perceptron(treino, teste, teta, maximoEpocas, funcao);
            Perceptron p = new Perceptron(treino, validacao, teste, teta, maximoEpocas, funcao);
            double nErroTreino = 0;
            nErroTreino = p.executa(rede, nErroTreino);

            double escondida[] = new double[rede.escondida.length];
            double saida[] = new double[rede.saida.length];
            int matriz_confusao[][] = new int[saida.length][saida.length];
            int k = 0;
            double nErroTeste = 0;
            double nErroValidacao = 0;

            while (k < teste.length) {
                double escondida_in = 0;
                double saida_in = 0;
                //         double aux3 = 0;
                for (int i = 0; i < escondida.length; i++) {
                    for (j = 0; j < teste[0].neuroniosSensoriais.length; j++) {
                        
                        escondida_in = escondida_in + teste[k].neuroniosSensoriais[j] * rede.pesoV[escondida.length * j + i];

                    }
                    escondida_in = escondida_in + rede.biasV[i];
                    escondida[i] = 1 / (1 + (1 / Math.pow(2.71, escondida_in)));
                    escondida_in = 0;
                }

                for (int i = 0; i < saida.length; i++) {
                    for (j = 0; j < escondida.length; j++) {
                        saida_in = saida_in + escondida[j] * rede.pesoW[saida.length * j + i];

                    }
                    saida_in = saida_in + rede.biasW[i];
                    saida[i] = 1 / (1 + (1 / Math.pow(2.71, saida_in)));
                    saida_in = 0;
                }

                //System.out.println(saida[0] + " " + saida[1] + " " + saida[2] + "  " + flor[k].classe + " " + k);
               
                boolean erro = false;
              
                for (int i = 0; i < saida.length; i++) {
                    
                    if (saida[i] >= 0.8) {
                        matriz_confusao[teste[k].resposta][i]++;
                        if (teste[k].resposta != i)
                            erro = true;
                    }
                    else {
                        if (teste[k].resposta == i)
                            erro = true;
                    }
                        
                }

                if (erro == true) {
                    nErroTeste++;
                } 
                
                
             /*   double max = -1000000;
                int iMax = -1;
                
                for (int i = 0; i < saida.length; i++) {
                    if (saida[i] > max) {
                        max = saida[i];
                        iMax = i;
                    }
                }
                
                if (iMax != teste[k].resposta) {
                    nErroTeste ++;
                }
                matriz_confusao[teste[k].resposta][iMax]++; */

                k++;
             }
            
            k = 0;
            while (k < validacao.length) {
                
                double escondida_in = 0;
                double saida_in = 0;
                //         double aux3 = 0;
                for (int i = 0; i < escondida.length; i++) {
                    for (j = 0; j < validacao[0].neuroniosSensoriais.length; j++) {
                        
                        escondida_in = escondida_in + validacao[k].neuroniosSensoriais[j] * rede.pesoV[escondida.length * j + i];

                    }
                    escondida_in = escondida_in + rede.biasV[i];
                    escondida[i] = 1 / (1 + (1 / Math.pow(2.71, escondida_in)));
                    escondida_in = 0;
                }

                for (int i = 0; i < saida.length; i++) {
                    for (j = 0; j < escondida.length; j++) {
                        saida_in = saida_in + escondida[j] * rede.pesoW[saida.length * j + i];

                    }
                    saida_in = saida_in + rede.biasW[i];
                    saida[i] = 1 / (1 + (1 / Math.pow(2.71, saida_in)));
                    saida_in = 0;
                }

                //System.out.println(saida[0] + " " + saida[1] + " " + saida[2] + "  " + flor[k].classe + " " + k);
               
                boolean erro = false;
              
                for (int i = 0; i < saida.length; i++) {
                    
                    if (saida[i] >= 0.8) {
                        matriz_confusao[validacao[k].resposta][i]++;
                        if (validacao[k].resposta != i)
                            erro = true;
                    }
                    else {
                        if (validacao[k].resposta == i)
                            erro = true;
                    }
                        
                }

                if (erro == true) {
                    nErroValidacao++;
                } 
                
                
             /*   double max = -1000000;
                int iMax = -1;
                
                for (int i = 0; i < saida.length; i++) {
                    if (saida[i] > max) {
                        max = saida[i];
                        iMax = i;
                    }
                }
                
                if (iMax != teste[k].resposta) {
                    nErroTeste ++;
                }
                matriz_confusao[teste[k].resposta][iMax]++; */

                k++;
             }
            
             System.out.println("Erro Teste: " + nErroTeste);
             System.out.println("Erro Validacao: " + nErroValidacao);
             System.out.println("Erro Treino: " + nErroTreino);
            r.erroValidacao = nErroValidacao;
            r.erroTeste = nErroTeste;
            r.erroTreino = nErroTreino;
            r.matriz = matriz_confusao;
            r.rede = rede;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return r;
    }

    public CamadaSensorial[] embaralhaConjunto(CamadaSensorial[] base) {
        int tamanho = base.length;
        CamadaSensorial aux1[] = new CamadaSensorial[tamanho];
        for (int i = 0; i < tamanho; i++) {
            aux1[i] = null;
        }
        for (int i = 0; i < tamanho; i++) {
            double temp2 = Math.random() * tamanho;
            int temp = (int) temp2;
            if (base[temp] != null) {
                aux1[i] = base[temp];
                base[temp] = null;
                //teste[i] = aux1[i];
            } else {
                i--;
            }
        }
        return aux1;

    }
}
