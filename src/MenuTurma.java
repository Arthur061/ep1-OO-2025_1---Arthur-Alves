import java.io.IOException;

public class MenuTurma implements MenuOptions{
    @Override
    public void executar() throws IOException {
        System.out.println("Você escolheu Disciplinas e Turmas.");
        DisciplinasTurmas escolha = new DisciplinasTurmas();
        escolha.Serviços();
    }
}
