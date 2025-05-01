import java.util.Scanner;

public class Aluno extends Menu {
    private Integer matricula; 
    private String nome; 
    private String curso;
    private Integer opcao; 
    private Integer tipoAluno;

    public void aluno() {
        Scanner sc = new Scanner(System.in);
        IntroduçãoFront menuAluno = new IntroduçãoFront();
        menuAluno.MenuAluno();
        opcao = sc.nextInt();

        switch (this.opcao) {
            case 1 -> System.out.println("Cadastro de aluno em andamento...");
            case 2 -> System.out.println("Listando alunos...");
            case 3 -> {
                System.out.println("Voltando ao menu principal...");
                super.menu();  // Voltar para classe pai
            }
            default -> System.out.println("Opção inválida.");
        }

        switch (this.opcao) {
            case 1 -> {
                System.err.println("Vamos seguir com o cadastro");
                System.err.print("Matricula: ");
                matricula = sc.nextInt();
                sc.nextLine();
                System.err.print("Nome completo: ");
                nome = sc.nextLine();
                System.err.print("Qual curso você está cursando: ");
                curso = sc.nextLine();
                System.out.println("");
                System.out.println("Sua matricula é " + matricula + ", Nome " + nome + " e seu curso é " + curso);
                AlunosTXT.salvarEmTxt("alunos.txt", String.valueOf(matricula), nome, curso);}
        }

        System.out.println("");
        System.out.println("Qual sua condição de aluno? ");
        System.out.println("1- Sou aluno especial");
        System.out.println("2- Sou aluno normal");
        System.out.print("Sua escolha: ");
        tipoAluno = sc.nextInt();
        switch (this.tipoAluno) {
            case 1 -> {
                System.out.println("Ok, vamos prosseguir com o sistema para alunos especiais!"); 
                AlunoEspecial especial = new AlunoEspecial();
                especial.Sistemaesepcial();
            }
            case 2 -> {
                System.out.println("Ok, vamos prosseguir com o sistema para aluno normal");
            }
        }
    }
}
