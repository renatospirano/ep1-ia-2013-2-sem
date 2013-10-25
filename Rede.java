public class Rede {

    double pesoV[]; // vetor de pesos da camada de entrada
    double biasV[]; // vetor de bias da camada de entrada
    double pesoW[]; // vetor de pesos da camada escondida
    double biasW[]; // vetor de bias da camada escondida
    double escondida[]; // vetor da camada escondida
    double saida[]; // vetor de saida

    public Rede() {
    }

    // Construtor. Define o tamanho dos vetores na criação de um objeto Rede
    // e chama o método preencheRede que inicializa os pesos com zeros
    public Rede(int pesoV, int biasV, int escondida, int pesoW, int biasW,
            int saida) {
        this.pesoV = new double[pesoV];
        this.biasV = new double[biasV];
        this.pesoW = new double[pesoW];
        this.biasW = new double[biasW];
        this.escondida = new double[escondida];
        this.saida = new double[saida];
        preencheRede();
    }

    public void preencheRede() {
        for (int i = 0; i < pesoV.length; i++) {

            pesoV[i] = 0;
        }
        for (int i = 0; i < pesoW.length; i++) {
            pesoW[i] = 0;
        }
        for (int i = 0; i < biasV.length; i++) {
            biasV[i] = 0;
        }

        for (int i = 0; i < biasW.length; i++) {
            biasW[i] = 0;
        }
    }
    
    public void preencheRedeAleatorio() {
        for (int i = 0; i < pesoV.length; i++) {

            pesoV[i] = Math.random();
        }
        for (int i = 0; i < pesoW.length; i++) {
            pesoW[i] = Math.random();
        }
        for (int i = 0; i < biasV.length; i++) {
            biasV[i] = Math.random();
        }

        for (int i = 0; i < biasW.length; i++) {
            biasW[i] = Math.random();
        }
    }
    
}
