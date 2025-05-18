import java.io.IOException;

public class MenuAluno implements MenuOptions{
    @Override
    public void executar() throws IOException {
        System.out.println("\nVocê está no menu para aluno\n");
        Aluno aluno = new Aluno();
        aluno.aluno();
    }
}
