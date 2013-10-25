
import java.util.ArrayList;
import java.util.List;

public class Resultados {
    int [][] matriz; // matriz responsável por gerar a matriz de confusão
    double erroTreino; // controle de erro no treino
    double erroValidacao;
    double erroTeste; // controle de erro no teste  
    Rede rede; // guarda todos os pesos e bias

    public Resultados() {
    }

    // Ordena uma lista de resultados pelo número de erros
    public void ordenaResultados (List<Resultados> r) {
        List<Resultados> rAux = new ArrayList<Resultados>();
        Resultados rMin = new Resultados();
        double erroTeste = 0;
        double erroValidacao = 0;
        int iMin = 0;
        for (int i = 0; i < r.size(); i ++) {
          erroTeste = 10000000;
          erroValidacao = 10000000;
            for (int j = i; j < r.size(); j++) {
                if (r.get(j).erroTeste < erroTeste) {
                    erroTeste = r.get(j).erroTeste;
                    rMin = r.get(j);
                    r.remove(j);
                    r.add(i, rMin);
                }
                if (r.get(j).erroValidacao < erroValidacao) {
                    erroValidacao = r.get(j).erroValidacao;
                    rMin = r.get(j);
                    r.remove(j);
                    r.add(i, rMin);
                }
            }
        }
    }
}
