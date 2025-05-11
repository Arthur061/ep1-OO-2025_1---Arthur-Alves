public class MenuAluno implements MenuOptions{
    @Override
    public void executar() {
        System.out.println("\nVocê está no menu para aluno\n");
        Aluno aluno = new Aluno();
        aluno.aluno();
    }
}
