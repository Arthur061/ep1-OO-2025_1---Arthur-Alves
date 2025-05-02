import java.util.List;
import java.util.Scanner;

public class Aluno extends Menu {
    private Integer matricula; 
    private String nome; 
    private String curso;
    private Integer opcao; 
    private Integer tipoAluno;
    public boolean condicao;

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
            case 1 -> { // CADASTRAR ALUNOS 
                System.err.println("Vamos seguir com o cadastro");
                    System.err.print("Matricula: ");
                    matricula = sc.nextInt();
                    sc.nextLine();
                    boolean existe = DadosTXT.verificarSeDadoExiste(matricula);
                    while (existe) {
                        System.out.println("O aluno já está cadastrado.");
                        System.err.print("Matricula: ");
                        matricula = sc.nextInt();
                        sc.nextLine();
                        existe = DadosTXT.verificarSeDadoExiste(matricula); // atualiza o valor da matricula pae
                    }
                System.err.print("Nome completo: ");
                nome = sc.nextLine();
                System.err.print("Qual curso você está cursando: ");
                curso = sc.nextLine();
                System.out.println("");

                System.out.println("");
                System.out.println("Qual sua condição de aluno? ");
                System.out.println("1- Sou aluno especial");
                System.out.println("2- Sou aluno normal");
                System.out.print("Sua escolha: ");
                tipoAluno = sc.nextInt();
                if(tipoAluno == 1) {condicao = true;}
                switch (this.tipoAluno) {
                    case 1 -> { 
                        System.out.println("Ok, vamos prosseguir com o sistema para alunos especiais!");
                        AlunoEspecial especial = new AlunoEspecial();
                        especial.executarSistema();}
                    case 2 -> {
                    System.out.println("Aluno cadastrado!"); 
                    System.out.println("Voltando para menu aluno");
                    Aluno Voltando = new Aluno();
                    Voltando.aluno();}
                }
                DadosTXT.salvarEmTxt("alunos.txt", String.valueOf(matricula), nome, curso, condicao);} 
            case 2 -> { // LISTAR ALUNOS
                String caminhoArquivo = "alunos.txt";
                List<String> nomesAlunos = DadosTXT.getNomesAlunos(caminhoArquivo);
                if (nomesAlunos.isEmpty()) {
                    System.out.println("Lista atualmente está vazia :(");
                } else {System.out.println( "-- Lista de todos os alunos cadastrados no sistema --");
                        for (int i = 0; i < nomesAlunos.size(); i++) {
                            System.out.println(i + " : "+ nomesAlunos.get(i));}    
                }
            }
        } 
    }   
    }

