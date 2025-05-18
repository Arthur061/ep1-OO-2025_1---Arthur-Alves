public class MenuAvaliacao implements MenuOptions {
    @Override
    public void executar() {
        System.out.println("Você escolheu Avaliação e Frequência.");
        AvaliacoesFrequencia avaliacao = new AvaliacoesFrequencia();
        avaliacao.sistemaNotas();
    }

}
