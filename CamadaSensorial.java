public class CamadaSensorial {
    
    double neuroniosSensoriais[]; // armazena os atributos
    int resposta; // armazena a resposta do HandWritten Digits
    int resp; // armazena a resposta do WDBC M = 0 , B = 1
    
    // Tamanho do vetor é definido na criação do objeto CamadaSensorial
    CamadaSensorial(int tamanho) {
        
        neuroniosSensoriais = new double[tamanho];
    }

}
