import java.io.IOException;

public class MenuAvaliacao implements MenuOptions {
    @Override
    public void executar() throws IOException {
        System.out.println("Você escolheu Avaliação e Frequência.");
        AvaliacoesFrequencia avaliacao = new AvaliacoesFrequencia();
        avaliacao.sistemaNotas();
    }

}
