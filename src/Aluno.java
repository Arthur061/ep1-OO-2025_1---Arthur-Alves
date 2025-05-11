import java.util.List;
import java.util.Scanner;

public class Aluno extends Menu {
    private Integer matricula; private String nome; private String curso;private Integer opcao; private Integer tipoAluno;
    public boolean condicao; String caminhoArquivo = "alunos.txt"; public String dadoAluno;

    Scanner sc = new Scanner(System.in);
    public void aluno() {
        IntroduçãoFront menuAluno = new IntroduçãoFront();
        menuAluno.MenuAluno();
        opcao = sc.nextInt();

        // EXECUÇÃO DAS OPÇÕES 
        switch (this.opcao) {

            // CADASTRAR ALUNO
            case 1 -> {
                System.out.println("\nCadastro de aluno em andamento...\n");
                Aluno Cadastro = new Aluno();
                Cadastro.cadastrarAluno();
                }
            
            // LISTAR ALUNOS
            case 2 -> {
                System.out.println("\nListando alunos...\n");
                Aluno Listar = new Aluno();
                Listar.ListarAlunos();
            }
            
            // EDITAR ALUNO
            case 3 -> {

                List<String> nomesAlunos = DadosAlunosTXT.getNomesAlunos(caminhoArquivo);
                if (nomesAlunos.isEmpty()) {
                    System.out.println("Lista atualmente está vazia :(");
                    MenuOptions menu = new MenuAluno();
                    menu.executar();

                }else{
                        System.out.println("Vamos editar as informações desejadas!");
                    }

                IntroduçãoFront edicao = new IntroduçãoFront();
                edicao.EdicaoAluno();
                int opcaoEditar = sc.nextInt();
                sc.nextLine();

                switch (opcaoEditar) {

                    case 1 -> { // EDITAR MATRICULA
                        Aluno EditarM = new Aluno();
                        EditarM.EditarMatricula();
                    }

                    case 2 -> { // EDITAR NOME
                        System.out.println("\nVamos editar o nome!\n");

                        VerificadorMatricula verificador = new VerificadorMatricula();
                        int matricula = verificador.verificarMatricula(-1);

                        EditarDados editarNome = new EditarDados();
                        editarNome.EditarNome(matricula);

                    }

                    case 3 -> { // EDITAR CURSO
                        System.out.println("\nVamos editar o curso!\n");

                        VerificadorMatricula verificador = new VerificadorMatricula();
                        int matricula = verificador.verificarMatricula(-1);

                        EditarDados editarCurso = new EditarDados();
                        editarCurso.EditarCurso(matricula);
                        }
                    
                    case 4 -> {Aluno voltarAluno = new Aluno(); voltarAluno.aluno();}
                    default -> System.err.println("Opção invalida");
                }
            }
            
            case 4 -> {
                System.out.println("Encaminhando para aba de matriculas e trancamentos...");
                DisciplinasTurmas escolha = new DisciplinasTurmas();
                escolha.Serviços();
            }
            case 5 -> {
                System.out.println("Voltando ao menu principal...");
                super.menu();  // VOLTAR PARA CLASSE PAI
            }
            default -> {System.err.println("Opção invalida");
                Aluno voltarAluno = new Aluno(); voltarAluno.aluno();}
        } 
    }   
    //CADASTRAR ALUNO
    public void cadastrarAluno() {
        System.err.println("Vamos seguir com o cadastro");
        VerificadorMatricula verificador = new VerificadorMatricula();
        int matricula = verificador.verificarMatricula(-1);
        System.out.println("Matrícula validada: " + matricula);

        boolean existe = DadosAlunosTXT.verificarSeDadoExiste(matricula);
        while (existe) {
            System.out.println("O aluno já está cadastrado.");
            System.err.print("Matricula: ");
            matricula = sc.nextInt();
            sc.nextLine();
            existe = DadosAlunosTXT.verificarSeDadoExiste(matricula); // atualiza o valor da matricula pae
        }
        System.err.print("Nome completo: ");
        nome = sc.nextLine().toUpperCase();
        System.err.print("Qual curso você está cursando: ");
        curso = sc.nextLine().toUpperCase();

        System.out.println("\nQual sua condição de aluno? ");
        System.out.println("1- Sou aluno especial");
        System.out.println("2- Sou aluno normal");
        System.out.print("Sua escolha: ");
        tipoAluno = sc.nextInt();
        if(tipoAluno == 1) {condicao = true;}
        
        DadosAlunosTXT.salvarEmTxt("alunos.txt", String.valueOf(matricula), nome, curso, condicao);

        switch (this.tipoAluno) {
            case 1 -> {
                System.out.println("Aluno cadastrado!"); 
                System.out.println("Você agora está cadastrado no sistema como um aluno especial!");
                Aluno Voltando = new Aluno();
                Voltando.aluno();}
            case 2 -> {
                System.out.println("Aluno cadastrado!"); 
                System.out.println("Voltando para menu aluno");
                Aluno Voltando = new Aluno();
                Voltando.aluno();}
            }
    }

    // LISTAR ALUNOS
    public void ListarAlunos () {
        List<String> nomesAlunos = DadosAlunosTXT.getNomesAlunos(caminhoArquivo);
        if (nomesAlunos.isEmpty()) {
            System.out.println("Lista atualmente está vazia :("); Aluno voltarAluno = new Aluno(); voltarAluno.aluno();
        } else {System.out.println( "-- Lista de todos os alunos cadastrados no sistema --");
            System.out.println();
            for (int i = 0; i < nomesAlunos.size(); i++) {
                System.out.println(i + " : "+ nomesAlunos.get(i));}    
        }
        System.out.println();
        System.out.println("Voltando para menu Aluno...");
        Aluno voltarAluno = new Aluno(); voltarAluno.aluno();
    }

    // EDITAR MATRICULA CHECK
    public void EditarMatricula () {
        System.out.println("Vamos editar a matricula!\n");

        // Buscar dados do guerreiro
        System.out.print("Digite seu nome completo: ");
        dadoAluno = sc.nextLine().toUpperCase().trim();
        while (dadoAluno.isEmpty()) {
            System.out.print("Nome inválido. Digite novamente: ");
            dadoAluno = sc.nextLine().toUpperCase().trim();
        }

        System.out.println("Procurando pelo nome " + dadoAluno.toUpperCase() + " na lista...");
        DadosAlunosTXT buscarMatricula = new DadosAlunosTXT();
        buscarMatricula.BuscarDados(dadoAluno);

        // Validação da nova matrícula

        System.out.print("Digite a nova matricula para "+buscarMatricula.getNomeVelho()+", a atual é "+buscarMatricula.getMatriculaVelha()+": \n");

        VerificadorMatricula verificador = new VerificadorMatricula();
        int novaMatricula = verificador.verificarMatricula(-1);
                        
        EditarDados edicaoMatricula = new EditarDados();
        edicaoMatricula.editarDados(buscarMatricula.getMatriculaVelha(), "MATRICULA", Long.toString(novaMatricula));
                    
    
}
}