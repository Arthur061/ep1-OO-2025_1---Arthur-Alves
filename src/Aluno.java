import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Aluno extends MenuPrincipal {
    private Integer matricula; private String nome; private String curso;private Integer opcao; private Integer tipoAluno;
    public boolean condicao; String caminhoArquivo = "alunos.txt"; public String dadoAluno;
    buscarDados buscarInfo = new buscarDados();
    FrontSysout front = new FrontSysout();
    MenuOptions menu = new MenuAluno();

    int diaAula1 = 0; int diaAula2 = 0; int diaAula3 = 0;

    Scanner sc = new Scanner(System.in);
    public void aluno() throws IOException {
        front.MenuAluno();
        opcao = sc.nextInt();

        ValidarLetrasNum verificador = new ValidarLetrasNum();

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

                        int matricula = verificador.verificarMatricula(-1);

                        EditarDados editarNome = new EditarDados();
                        editarNome.EditarNome(matricula);

                    }

                    case 3 -> { // EDITAR CURSO
                        System.out.println("\nVamos editar o curso!\n");

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
                
                int mat = verificador.verificarMatricula(-1);

                buscarInfo.BuscarDados("alunos.txt", String.valueOf(mat), null);
            }
            case 6 -> {
                System.out.println("Voltando ao menu principal...");
                super.menu();
            }
            default -> {System.err.println("Opção invalida");
            menu.executar();}
        } 
    } 

    //CADASTRAR ALUNO
    public void cadastrarAluno() throws IOException {
        ValidarLetrasNum verificador = new ValidarLetrasNum();
        System.err.println("Vamos seguir com o cadastro");
        int matricula = verificador.verificarMatricula(-1);
        System.out.println("Matrícula validada: " + matricula);

        boolean existe = DadosAlunosTXT.verificarSeDadoExiste(matricula);
        while (existe) {
            System.out.println("O aluno já está cadastrado.");
            System.err.print("Matricula: ");
            matricula = sc.nextInt();
            sc.nextLine();
            existe = DadosAlunosTXT.verificarSeDadoExiste(matricula); 
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

        int numReprovacao = ValidarLetrasNum.lerInteiro("Quantas matérias você já reprovou: ");
        List<String> materiasReprovadas = new ArrayList<>();

        if (numReprovacao > 0) {
            for (int i = 0; i < numReprovacao; i++) {
                String nomeMateria = ValidarLetrasNum.lerTextoValido("Nome da matéria " + (i + 1) + ": ");
                String mencao = ValidarLetrasNum.lerTextoValido("Menção da matéria " + nomeMateria.toUpperCase() + ": ");
                String materiaFormatada = nomeMateria.toUpperCase() + " (" + mencao.toUpperCase() + ")";
                materiasReprovadas.add(materiaFormatada);
            }
        } else {
            System.out.println("Nenhuma matéria reprovada.");
        }

        String materiasReprovadasStr = materiasReprovadas.isEmpty() ? "NENHUMA" : String.join(", ", materiasReprovadas);

        int semestreAtual = ValidarLetrasNum.lerInteiro("Qual seu semestre atual: ");

        List<String> listaMaterias = new ArrayList<>();
        List<String> listaMencoes = new ArrayList<>();
        List<String> listaMateriasComMencao = new ArrayList<>();

        for (int semestre = 1; semestre <= semestreAtual; semestre++) {
            if (semestre == semestreAtual) {
                break;
            }
            int qtdMaterias = ValidarLetrasNum.lerInteiro("Quantas matérias você finalizou no semestre " + semestre + ": ");
            String sala = null;

            List<String> professores = new ArrayList<>();
            List<String> modos = new ArrayList<>();
            List<String> salas = new ArrayList<>();
            List<String> cargasHorarias = new ArrayList<>();

            for (int i = 1; i <= qtdMaterias; i++) {
                String materiaFinalizada = ValidarLetrasNum.lerTextoValido("Digite o nome da matéria " + i + " do semestre " + semestre + ": ");
                String mencaoFinal = ValidarLetrasNum.lerTextoValido("Menção final de " + materiaFinalizada.toUpperCase() + ": ");

                String modoMateriaFinalizada = ValidarLetrasNum.lerTextoValido("ONLINE OU PRESENCIAL: ");
                if (modoMateriaFinalizada.toUpperCase().equals("ONLINE")) {
                    sala = "REMOTO";
                } else {
                    sala = ValidarLetrasNum.lerSalaValida("Qual era a sala da materia " + materiaFinalizada.toUpperCase() + ": ");
                }

                String nomeProfessor = ValidarLetrasNum.lerTextoValido("Nome professor: ");
                int cargaHoraria = ValidarLetrasNum.lerInteiro("Carga horaria da materia " + materiaFinalizada.toUpperCase() + ": ");

                listaMaterias.add(materiaFinalizada.toUpperCase());
                listaMencoes.add(mencaoFinal.toUpperCase());
                listaMateriasComMencao.add(materiaFinalizada.toUpperCase() + " (" + mencaoFinal.toUpperCase() + ")");

                professores.add(nomeProfessor.toUpperCase());
                modos.add(modoMateriaFinalizada.toUpperCase());
                salas.add(sala);
                cargasHorarias.add(cargaHoraria + "h");
            }
            String materiasFinalizadasStr = String.join(", ", listaMateriasComMencao);
            StringBuilder dadosMaterias = new StringBuilder();
            for (int i = 0; i < listaMaterias.size(); i++) {
                dadosMaterias.append("DADOS ").append(listaMaterias.get(i)).append(": PROFESSOR ")
                            .append(professores.get(i)).append(", ")
                            .append(modos.get(i)).append(" - SALA ").append(salas.get(i))
                            .append(" - CARGA HORARIA ").append(cargasHorarias.get(i))
                            .append("\n");
            }

            gerarBoletim gerar = new gerarBoletim();
            gerar.criarRelatorioAluno("boletimAlunos.txt", semestre, nome, matricula, curso, materiasFinalizadasStr,
                materiasReprovadasStr, dadosMaterias.toString());

        }

       String materias = String.join(", ", listaMaterias);
       String mencoes = String.join(", ", listaMencoes);

        switch (this.tipoAluno) {
            case 1 -> { 
                System.out.println("Você estará sendo cadastrado no sistema como um aluno especial!");}
        
            case 2 -> System.out.println("Você estará sendo cadastrado no sistema como um aluno normal!"); 
            }

            List<String> materiaDoing = new ArrayList<>();
            List<String> nomesProfessores = new ArrayList<>();
            List<String> turnoProfessores = new ArrayList<>();
            List<String> horaioAulas = new ArrayList<>();
            List<String> metodoAvaliacao = new ArrayList<>();

            List<Integer> diasSemana = new ArrayList<>();

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

                    front.turnoAula();
                    String turnoProfessor = ValidarLetrasNum.lerTextoValido("Qual o turno: ");

                    int horarioInicio = ValidarLetrasNum.lerInteiro("Horario de inicio: ");
                    int horarioF = ValidarLetrasNum.lerInteiro("Horario de termino: ");
                    front.diasAula();

                    diaAula1 = ValidarLetrasNum.lerInteiro("Dia da 1º aula: ");
                    while (true) { 
                        diaAula2 = ValidarLetrasNum.lerInteiro("Dia da 2º (Caso não tenha, digite 0): ");
                        if (diaAula2 == 0) {
                            break;
                        } else {
                            diaAula3 = ValidarLetrasNum.lerInteiro("Dia da 3º (Caso não tenha, digite 0): ");
                            break;
                        }
                    }
                    diasSemana.addAll(Arrays.asList(diaAula1, diaAula2, diaAula3));

                    TurnoHorarioAula format = new TurnoHorarioAula();
                    String horarioFormatado;
                    horarioFormatado = format.horarioAulaFormatado(diasSemana, turnoProfessor.toUpperCase(), horarioInicio, horarioF);


                    front.metodoAva();
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
                    horaioAulas.add(horarioFormatado);
                    materiaDoing.add(materiasCursando.toUpperCase());
                    diasSemana.clear();
                }
                doing = String.join(", ", materiaDoing);
                nomeProfs = String.join(",", nomesProfessores);
                turnoProfs = String.join(",", turnoProfessores);
                horarioProfs = String.join(",", horaioAulas);
                metodoProfs = String.join(",", metodoAvaliacao);
            }
            DadosAlunosTXT.salvarEmTxt("alunos.txt", String.valueOf(matricula), nome, curso, condicao, materias, doing, nomeProfs,
            turnoProfs, horarioProfs, metodoProfs, String.valueOf(semestreAtual), mencoes, materiasReprovadasStr);
            System.out.println("CADASTRO FINALIZADO!");
            aluno();
    }

    // LISTAR ALUNOS
    public void ListarAlunos () throws IOException {
        List<String> nomesAlunos = DadosAlunosTXT.getNomesAlunos(caminhoArquivo);
        if (nomesAlunos.isEmpty()) {
            System.out.println("Lista atualmente está vazia :("); menu.executar();
        } else {System.out.println( "-- Lista de todos os alunos cadastrados no sistema --");
            System.out.println();
            for (int i = 0; i < nomesAlunos.size(); i++) {
                System.out.println(i + " : "+ nomesAlunos.get(i));}    
        }
        System.out.println();
        System.out.println("Voltando para menu Aluno...");
        menu.executar();
    }

    // EDITAR MATRICULA CHECK
    public void EditarMatricula () throws IOException {
        ValidarLetrasNum verificador = new ValidarLetrasNum();
        System.out.println("Vamos editar a matricula!\n");

        // Buscar dados do guerreiro
        System.out.print("Digite seu nome completo: ");
        dadoAluno = sc.nextLine().toUpperCase().trim();
        while (dadoAluno.isEmpty()) {
            System.out.print("Nome inválido. Digite novamente: ");
            dadoAluno = sc.nextLine().toUpperCase().trim();
        }

        System.out.println("Procurando pelo nome " + dadoAluno.toUpperCase() + " na lista...");
        buscarInfo.BuscarDados("alunos.txt",dadoAluno, null);

        System.out.print("Digite a nova matricula para "+buscarInfo.getNomeVelho()+", a atual é "+buscarInfo.getMatriculaVelha()+": \n");

        int novaMatricula = verificador.verificarMatricula(-1);
                        
        EditarDados edicaoMatricula = new EditarDados();
        edicaoMatricula.editarDados(buscarInfo.getMatriculaVelha(), "MATRICULA", Long.toString(novaMatricula));
        System.out.println("Matricula edita! Voltando para menu de aluno.");
        menu.executar();
    }
}