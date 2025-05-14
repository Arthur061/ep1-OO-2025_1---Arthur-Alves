import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aluno extends Menu {
    private Integer matricula; private String nome; private String curso;private Integer opcao; private Integer tipoAluno;
    public boolean condicao; String caminhoArquivo = "alunos.txt"; public String dadoAluno;

    Scanner sc = new Scanner(System.in);
    public void aluno() {
        IntroduçãoFront front = new IntroduçãoFront();
        front.MenuAluno();
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

                front.EdicaoAluno();
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
        tipoAluno = ValidarLetrasNum.lerInteiro("Sua escolha: ");
        if (tipoAluno == 1) {
            condicao = true;
        } else if (tipoAluno == 2) {
            condicao = false;
        }
        
    

        List<String> listaMaterias = new ArrayList<>();
        String materias = "";
        int qtdMaterias = ValidarLetrasNum.lerInteiro("Quantas materias você ja finalizou: ");
        if (qtdMaterias == 0) {
            listaMaterias.clear();
        } else {
            for (int i = 1; i <= qtdMaterias; i++) {
                String materiaFinalizada = ValidarLetrasNum.lerTextoValido("Digite o nome da matéria " + i + ": ");
                listaMaterias.add(materiaFinalizada.toUpperCase());
            }
            materias = String.join(", ", listaMaterias);
        }

        switch (this.tipoAluno) {
            case 1 -> {
                System.out.println("Aluno cadastrado!"); 
                System.out.println("Você agora está cadastrado no sistema como um aluno especial!");}
        
            case 2 -> System.out.println("Aluno cadastrado!"); 
            }

            List<String> materiaDoing = new ArrayList<>();
            List<String> nomesProfessores = new ArrayList<>();
            List<String> turnoProfessores = new ArrayList<>();
            List<String> horaioAulas = new ArrayList<>();
            List<String> metodoAvaliacao = new ArrayList<>();
            String doing = ""; String nomeProfs = ""; String turnoProfs = ""; String horarioProfs = ""; String metodoProfs = "";

            int qtdMateriasFazendo = ValidarLetrasNum.lerInteiro("Quantas materias você está cursando: ");
            while(true) {
                if (qtdMateriasFazendo > 2 && condicao) {
                    System.out.println("Você está cadastrado como aluno especial. Maximo de duas materias cursando.");
                    qtdMateriasFazendo = ValidarLetrasNum.lerInteiro("Quantas materias você está cursando: ");
                } else if (qtdMateriasFazendo > 5 && !condicao) {
                    System.out.println("Maximo de cinco materias cursando.");
                    qtdMateriasFazendo = ValidarLetrasNum.lerInteiro("Quantas materias você está cursando: ");
                } else {
                    break;
                }
            }
            if (qtdMateriasFazendo == 0) {
                materiaDoing.clear();
            } else {
                for (int m = 1; m <= qtdMateriasFazendo; m ++) {
                    String materiasCursando = ValidarLetrasNum.lerTextoValido("Digite o nome da matéria " + m +": ");
                    String nomeProfessor = ValidarLetrasNum.lerTextoValido("Digite o nome do professor de "+materiasCursando+":");
                    String turnoProfessor = ValidarLetrasNum.lerTextoValido("Qual o turno (MANHÂ, TARDE OU NOITE): ");
                    int horarioInicio = ValidarLetrasNum.lerInteiro("Horario de inicio: ");
                    int horarioF = ValidarLetrasNum.lerInteiro("Horario de termino: ");


                    IntroduçãoFront avaliacao = new IntroduçãoFront();
                    avaliacao.metodoAva();
                    String metodoAva = "";
                    int escolhaAva = ValidarLetrasNum.lerInteiro("");
                    OUTER:
                    while (true) {
                        switch (escolhaAva) {
                            case 1 -> {
                                metodoAva = "(P1 + P2 + P3 + L + S) / 5";
                                break OUTER;
                            }
                            case 2 -> {
                                metodoAva = "(P1 + P2 * 2 + P3 * 3 + L + S) / 8";
                                break OUTER;
                            }
                            default -> { System.out.println("Escolha invalida!");
                            escolhaAva = ValidarLetrasNum.lerInteiro("Escolha 1 ou 2: ");
                            }
                        }
                    }

                    metodoAvaliacao.add(metodoAva);
                    nomesProfessores.add(nomeProfessor.toUpperCase());
                    turnoProfessores.add(turnoProfessor.toUpperCase());
                    horaioAulas.add(horarioInicio+"h até "+horarioF+"h");
                    materiaDoing.add(materiasCursando.toUpperCase());
                }
                doing = String.join(", ", materiaDoing);
                nomeProfs = String.join(",", nomesProfessores);
                turnoProfs = String.join(",", turnoProfessores);
                horarioProfs = String.join(",", horaioAulas);
                metodoProfs = String.join(",", metodoAvaliacao);
            }
            DadosAlunosTXT.salvarEmTxt("alunos.txt", String.valueOf(matricula), nome, curso, condicao, materias, doing, nomeProfs, turnoProfs, horarioProfs, metodoProfs);
            aluno();
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
        buscarMatricula.BuscarDados("alunos.txt",dadoAluno, null);

        // Validação da nova matrícula

        System.out.print("Digite a nova matricula para "+buscarMatricula.getNomeVelho()+", a atual é "+buscarMatricula.getMatriculaVelha()+": \n");

        VerificadorMatricula verificador = new VerificadorMatricula();
        int novaMatricula = verificador.verificarMatricula(-1);
                        
        EditarDados edicaoMatricula = new EditarDados();
        edicaoMatricula.editarDados(buscarMatricula.getMatriculaVelha(), "MATRICULA", Long.toString(novaMatricula));
                    
    
}
}