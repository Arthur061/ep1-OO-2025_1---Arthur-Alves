public class MenuTurma implements MenuOptions{
    @Override
    public void executar() {
        System.out.println("Você escolheu Disciplinas e Turmas.");
        DisciplinasTurmas escolha = new DisciplinasTurmas();
        escolha.Serviços();
    }
}
