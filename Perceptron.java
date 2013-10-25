public class Perceptron {

    // Classe responsável por realizar o treinamento da rede
    float teta = 0;
    int maximoEpocas = 0;
    CamadaSensorial treino[];
    CamadaSensorial validacao [];
    CamadaSensorial teste[];
    boolean funcao = false;
    int valorDecrescimo = 0;

    public Perceptron(CamadaSensorial treino[], CamadaSensorial [] validacao, CamadaSensorial[] teste, float teta,
            int maximoEpocas, boolean funcao) {
        this.funcao = funcao;
        this.teta = teta;
        this.maximoEpocas = maximoEpocas;
        this.treino = new CamadaSensorial[treino.length];
        this.validacao = new CamadaSensorial[validacao.length];
        this.teste = new CamadaSensorial[teste.length];
        System.arraycopy(treino, 0, this.treino, 0, treino.length);
        System.arraycopy(validacao, 0, this.validacao, 0, validacao.length);
        System.arraycopy(teste, 0, this.teste, 0, teste.length);
        valorDecrescimo = (int) (maximoEpocas / (teta * 10));

    }

    public double executa(Rede rede, double nErroTreino) {
        try {

            double correcao = 0;


            nErroTreino = 0;
            int nErroTreino2 = 1;

            double escondida[] = new double[rede.escondida.length];
            double saida[] = new double[rede.saida.length];


            int epocas = 0;
            while (epocas < maximoEpocas) { // laço responsável por rodar as épocas
                nErroTreino = 0;
                int k = 0;
                while (k < treino.length) { // laço responsável por rodar os dados
                    
                    //Dentro do segundo laço são realizadas as somatórias das multiplicações entre atributos e pesos e camada escondida 
                    //e pesos até que o valor é guardado em um vetor de saída.
                    
                    double escondida_in[] = new double [escondida.length]; // y_in da camada escondida
                    double saida_in[] = new double [saida.length]; // y_in da camada de saida
                    //         double aux3 = 0;
                    for (int i = 0; i < escondida.length; i++) {
                    
                        for (int j = 0; j < treino[0].neuroniosSensoriais.length; j++) {
                            escondida_in[i] = escondida_in[i] + treino[k].neuroniosSensoriais[j] * rede.pesoV[escondida.length * j + i];

                        }
                        escondida_in[i] = escondida_in[i] + rede.biasV[i];

                        rede.escondida[i] = 1 / (1 + (1 / Math.pow(2.71, escondida_in[i]))); // função de ativação Sigmóide

                    }

                    for (int i = 0; i < saida.length; i++) {
                        for (int j = 0; j < escondida.length; j++) {
                            saida_in[i] = saida_in[i] + rede.escondida[j] * rede.pesoW[saida.length * j + i];

                        }
                        saida_in[i] = saida_in[i] + rede.biasW[i];

                        rede.saida[i] = 1 / (1 + (1 / Math.pow(2.71, saida_in[i]))); // função de ativação Sigmóide
                    }
                    
                    //Com os valores de saída em mãos, é realizado o processo que verifica se houve ou não erro na classificação do dado. 
                    //O vetor de saída possui dez índices. Cada índice representa um dígito, o índice zero representa o dígito zero, o índice um o dígito zero e assim por diante. 
                    // Se o valor no índice for maior ou igual a 0.9 significa que a rede respondeu sim para o dígito correspondente 
                    // e se for menor ou igual a 0.1, significa que a rede respondeu não para o dígito correspondente. 
                    //Se a rede responder sim ou não para o dígito errado, então é feita uma chamada no método backpropagation().

                    int i = 0;
                    for (i = 0; i < rede.saida.length; i++) {
                        if (i == treino[k].resposta) {
                            if (rede.saida[i] < 0.9) {
                                double target[] = new double[rede.saida.length];
                                for (int w = 0; w < target.length; w++) {
                                    if (w == treino[k].resposta) {
                                        target[w] = 1.0;
                                    } else {
                                        target[w] = 0;
                                    }
                                }

                                backpropagation(rede, target, escondida_in, saida_in,
                                        k, correcao, epocas);
                                k++;
                                nErroTreino++;
                                nErroTreino2++;
                                break;
                            }
                        } else {
                            if (rede.saida[i] > 0.1) {
                                double target[] = new double[rede.saida.length];
                                for (int w = 0; w < target.length; w++) {
                                    if (w == treino[k].resposta) {
                                        target[w] = 1.0;
                                    } else {
                                        target[w] = 0;
                                    }
                                }

                                backpropagation(rede, target, escondida_in, saida_in,
                                        k, correcao, epocas);
                                k++;
                                nErroTreino++;
                                nErroTreino2++;
                                break;
                            }
                        }
                    }

                    if (i == rede.saida.length) {
                        k++;
                        nErroTreino2++;
                    }


                    treino = embaralhaConjunto(treino);


                }
                //System.out.println(epocas);
                epocas++;

                if (funcao == true) {
                    if (epocas % (valorDecrescimo) == 0) {

                        if (teta > 0.1) {
                            teta = teta - 0.1f;
                        }

                    }
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // for (this.i = 0; i < rede.pesoV.length; i++) {
        //   System.out.println(rede.pesoV[i]);
        //}
        return nErroTreino;
    }

    //return testaPerceptron(rede, nErroTreino);
    public CamadaSensorial[] embaralhaConjunto(CamadaSensorial[] base) {
        int tamanho = base.length;
        CamadaSensorial aux1[] = new CamadaSensorial[tamanho];
        for (int i = 0; i < tamanho; i++) {
            aux1[i] = null;
        }
        try {
            for (int i = 0; i < tamanho; i++) {
                double temp2 = Math.random() * tamanho;
                int temp = (int) temp2;
                if (base[temp] != null) {
                    aux1[i] = base[temp];
                    base[temp] = null;
                    //  teste[i] = aux1[i];
                } else {
                    i--;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return aux1;
    }

    public void backpropagation(Rede rede, double[] target, double[] escondida_in,
            double[] saida_in, int indice, double correcao, double epocas) {
        try {



            double deltaS[] = new double[rede.saida.length];
            double DeltaS[] = new double[rede.pesoW.length];
            double biasS[] = new double[rede.saida.length];

            double deltaE[] = new double[rede.escondida.length];
            double DeltaE[] = new double[rede.pesoV.length];
            double biasE[] = new double[rede.escondida.length];

            int j = 0;
            for (int i = 0; i < deltaS.length; i++) {
                deltaS[i] = (target[i] - rede.saida[i]) * (1 / (1 + (1 / Math.pow(2.71, saida_in[i]))))
                        * (1 - (1 / (1 + (1 / Math.pow(2.71, saida_in[i])))));
                biasS[i] = teta * deltaS[i];
                j = i;
                for (int k = 0; k < rede.escondida.length; k++) {
                    DeltaS[j] = teta * deltaS[i] * rede.escondida[k];
                    j = j + deltaS.length;
                }
            }


            j = 0;
            double deltaE_in[] = new double[deltaE.length];
            inicializaVetor(deltaE_in);
            for (int i = 0; i < deltaE.length; i++) {
                for (int k = 0; k < deltaS.length; k++, j++) {
                    deltaE_in[i] = +deltaS[k] * rede.pesoW[j];
                }
            }
            for (int i = 0; i < deltaE.length; i++) {
                deltaE[i] = deltaE_in[i] * (1 / (1 + (1 / Math.pow(2.71, escondida_in[i]))))
                        * (1 - (1 / (1 + (1 / Math.pow(2.71, escondida_in[i])))));
                biasE[i] = teta * deltaE[i];
            }

            for (int i = 0; i < deltaE.length; i++) {
                for (j = 0; j < treino[0].neuroniosSensoriais.length; j++) {
                    DeltaE[j + i * treino[0].neuroniosSensoriais.length] =
                            teta * deltaE[i] * treino[indice].neuroniosSensoriais[j];

                }
            }
            for (int i = 0; i < rede.pesoV.length; i++) {

                rede.pesoV[i] = rede.pesoV[i] + DeltaE[i];
            }
            for (int i = 0; i < rede.pesoW.length; i++) {
                rede.pesoW[i] = rede.pesoW[i] + DeltaS[i];
            }
            for (int i = 0; i < rede.biasV.length; i++) {
                rede.biasV[i] = rede.biasV[i] + biasE[i];
            }

            for (int i = 0; i < rede.biasW.length; i++) {
                rede.biasW[i] = rede.biasW[i] + biasS[i];
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void inicializaVetor(double[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = 0;
        }
    }
}
