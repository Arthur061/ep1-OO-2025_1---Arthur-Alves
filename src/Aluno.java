import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Aluno extends Menu {
    private Integer matricula; private String nome; private String curso;private Integer opcao; private Integer tipoAluno;
    public boolean condicao; String caminhoArquivo = "alunos.txt"; public String dadoAluno;

    public void aluno() {
        Scanner sc = new Scanner(System.in);
        IntroduçãoFront menuAluno = new IntroduçãoFront();
        menuAluno.MenuAluno();
        opcao = sc.nextInt();

        switch (this.opcao) { // ESCOLHA DA OPÇÃO
            case 1 -> System.out.println("Cadastro de aluno em andamento...");

            case 2 -> System.out.println("Listando alunos...");

            case 3 -> { List<String> nomesAlunos = DadosTXT.getNomesAlunos(caminhoArquivo);
                if (nomesAlunos.isEmpty()) {
                    System.out.println("Lista atualmente está vazia :(");Aluno voltarAluno = new Aluno(); voltarAluno.aluno();}else{
                        System.out.println("Vamos editar as informações desejadas!");
                    }} 

            case 4 -> {
                System.out.println("Voltando ao menu principal...");
                super.menu();  // VOLTAR PARA CLASSE PAI
            }
            default -> System.out.println("Opção inválida.");
        }

        switch (this.opcao) { // EXECUÇÃO DAS OPÇÕES 
            case 1 -> { // CADASTRAR ALUNO
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
                List<String> nomesAlunos = DadosTXT.getNomesAlunos(caminhoArquivo);
                if (nomesAlunos.isEmpty()) {
                    System.out.println("Lista atualmente está vazia :("); Aluno voltarAluno = new Aluno(); voltarAluno.aluno();
                } else {System.out.println( "-- Lista de todos os alunos cadastrados no sistema --");
                        for (int i = 0; i < nomesAlunos.size(); i++) {
                            System.out.println(i + " : "+ nomesAlunos.get(i));}    
                }
            }
            
            case 3 -> { // EDITAR ALUNO
                IntroduçãoFront edicao = new IntroduçãoFront();
                edicao.EdicaoAluno();
                int opcaoEditar = sc.nextInt();
                sc.nextLine();
                switch (opcaoEditar) {
                    case 1 -> { // EDITAR MATRICULA
                        System.out.println("Vamos editar a matricula!");
                        System.out.println(); 
                        System.out.print("Digite seu nome completo: ");
                        dadoAluno = sc.nextLine().toLowerCase().trim();


                        System.out.println("Procurando pelo nome " + dadoAluno.toUpperCase() + " na lista...");
                        DadosTXT buscarNome = new DadosTXT();
                        buscarNome.BuscarDados(dadoAluno);
                        System.out.println("nome "+dadoAluno);

                        // Validação da nova matrícula
                        long novaMatricula;
                        while (true) {
                            System.out.print("Digite a nova matricula do aluno: ");
                            try {
                                novaMatricula = sc.nextLong();
                                // Valida se a matrícula tem pelo menos 9 digitos (matricula padrão unb)
                                if (Long.toString(novaMatricula).length() < 9) {
                                    System.out.println("Matrícula deve ter pelo menos 9 dígitos.");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, digite apenas números para a matrícula.");
                                sc.nextLine(); // Limpa o buffer
                            }
                        }
                        
                        System.out.println("nome2: "+dadoAluno);
                        DadosTXT edicaoMatricula = new DadosTXT();
                        edicaoMatricula.editarDados(dadoAluno, "Matrícula", Long.toString(novaMatricula));
                    }


                    case 2 -> { // EDITAR NOME
                        System.out.println("Vamos editar o nome!");
                        System.out.println("");
                        System.out.print("Digite sua matricula: ");
                        dadoAluno = sc.nextLine();
                        DadosTXT buscarMatricula = new DadosTXT();
                        buscarMatricula.BuscarDados(dadoAluno);}


                    case 3 -> { // EDITAR CURSO
                        System.out.println("Vamos editar o curso!");
                        System.out.println("");
                        System.out.print("Digite sua matricula ou nome: ");
                        dadoAluno = sc.nextLine();
                        DadosTXT buscarCurso = new DadosTXT();
                        buscarCurso.BuscarDados(dadoAluno);}

                    case 4 -> {Aluno voltarAluno = new Aluno(); voltarAluno.aluno();}
                    default -> System.err.println("Opção invalida");
                }
            }
            } 
    }   
}

