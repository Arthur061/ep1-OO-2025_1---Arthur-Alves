import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DisciplinasTurmas extends Aluno  {
    private static Scanner sc = new Scanner(System.in);
    buscarDados buscarInfo = new buscarDados();
    MenuOptions voltando = new MenuTurma();
    String horarioFormat = "";

    private static String preRequisito; private static int reqCont;
    public int getReqCont () {
        return reqCont;
    }
     public String getPreRequisito() {
        return preRequisito;
    }

    private static final Set<String> listaDePreRequisitos = new HashSet<>();
    public Set<String> getListaDePreRequisitos() {
        return listaDePreRequisitos;
    }

    public void Serviços () throws IOException {
        FrontSysout Menu = new FrontSysout();
        Menu.MenuDisciplinasTurmas();
        int escolha = sc.nextInt();
        
        switch (escolha) {

            case 1 -> {
                System.out.println("Ok, vamos para a parte de trancamento/destrancamento!");
                Trancamento();
            }

            // APARETENMENTE CHECK
            case 2 -> {
                System.out.println("Ok, vamos para a parte de matriculas!");
                matricularDisciplina();
            }

            // CADASTRAR DISCIPLINAS CHECK 
            case 3 -> {
                System.out.println("Ok, vamos cadastrar uma nova disciplina!");
                cadastrarDisciplina();
            }

            // LISTAR TODAS TURMAS DISPONIVEIS CHECK
            case 4 -> {
                System.out.println("Ok, vamos listar as turmas disponiveis.");
                String caminho = "turmas.txt"; 
                listar(caminho);
                voltando.executar();
            }

            // VOLTAR MENU INICAL CHECK
            case 5 -> {
                System.out.println("Bye bye... Voltando para menu inicial.");
                super.menu();
            } 
            default -> {
                System.out.println("Opção invalida!");
                voltando.executar();
            }
        }
    }

    // TRANCAMENTO / DESTRANCAMENTO
    public void Trancamento() throws IOException {
        FrontSysout opcoes = new FrontSysout();
        opcoes.menuTrancamento();
        EditarDados novosDados = new EditarDados();

        int opcao = 0;
        boolean Entrada = false;
        ValidarLetrasNum verificandomat = new ValidarLetrasNum();

        int escolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");

        switch (escolha) {
            case 1 -> {
                // TRANCAR SEMESTRE
                System.out.println("Vamos trancar o semestre então.");
                System.out.println("Antes de começar o processo preciso que você me diga sua matricula.");
                int matricula = ValidarLetrasNum.verificarMatricula(-1);
                
                novosDados.trancarSemestre(matricula);
            }
            case 2 -> {
                // TRANCAR DISCIPLINA
                System.out.println("Vamos trancar a disciplina desejada.");
                System.out.println("Antes de começar o processo preciso que você me diga sua matricula.");
                int matricula = ValidarLetrasNum.verificarMatricula(-1);
                    
                buscarInfo.BuscarDados("alunos.txt", String.valueOf(matricula), null);
                String materiaTrancar = ValidarLetrasNum.lerTextoValido("Qual materia você vai querer trancar: ");
                EditarDados retirar = new EditarDados();
    
                try {
                    System.out.println(materiaTrancar);
                    retirar.trancarDisciplina(materiaTrancar, matricula);
                } catch (IOException e) {
                    System.out.println("Erro ao tentar trancar disciplina: " + e.getMessage());
                    voltando.executar();
                }
                break;
            }
            case 3 -> {
                //DESTRANCAR SEMESTRE
                System.out.println("Vamos destrancar o seu semestre então.");
                System.out.println("Antes de começar o processo preciso que você me diga sua matricula.");
                int matricula = ValidarLetrasNum.verificarMatricula(-1);
                novosDados.destrancarSemestre(matricula);
            }
            case 4 -> {
                System.out.println("Voltando para menu...");
                voltando.executar();
            }
            default -> {
                System.out.println("Opção invalida!");
                Trancamento();
            }
        }
    }

    // SE MATRICULAR EM DISCIPLINAS
    public void matricularDisciplina() throws IOException {
        String profEscolhido = "";
        String materia; int matricula;
        int numDisciplinas;
        
        System.out.println("\nPartiu fazer as matriculas então!\n ");
        materia = ValidarLetrasNum.lerTextoValido("Primeiro, me deixe saber qual matéria você quer se matricular: ");

        System.out.println("\nMatéria escolhida: " + materia.toUpperCase()+"\n"); // DEBUG PQ SLK NÃO DA
 
        boolean vazio = estaVazio(caminhoArquivo); 
        if (vazio) {
            System.out.println("Eita! parece que ainda não há turmas cadastradas...");
            voltando.executar();
        }
        DadosTurmasTXT.buscarDisciplina("turmas.txt", materia, null);

        System.out.println("A materia "+materia.toUpperCase()+" tem "+getReqCont()
        +" pre-requisito(s)");

        // PRE REQUISITOS DA MATERIA
        System.out.println("\nLista de pré-requisitos da disciplina:");
        Set<String> lista = this.getListaDePreRequisitos();
        for (String req : lista) {
            System.out.println("- " + req);
        }

        matricula = ValidarLetrasNum.lerInteiro("Me fala qual a sua matricula, por favor: ");
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula), null);

        if (buscarInfo.getSemestreAtual().startsWith("TRANCADO")) {
            System.out.println("Sua matricula atualmente está trancada! Volte após destranca-la.");
            voltando.executar();
        }

        // GUARDAR MATERIAS FEITAS PELO GUERREIRO
        List<String> materias = buscarInfo.getMateriasFinalizadas();
        if (materias == null  || materias.isEmpty()) {
            System.out.println("Não há materia finalizadas ainda.");
        }else {
            System.out.println("Parece que você tem " + materias.size() + " matéria(s) finalizada(s). Elas são:");
            for (String i : materias) {
                System.out.println("- " + i);
            }
        }
        String materiasDone = String.join(", ", materias);

        // VERIFICAR PRE REQUISITO
        boolean possuiTodos = true;
        for (String req : lista ) {
            if (req.trim().equalsIgnoreCase("NÃO POSSUI")) {
                System.out.println("Disciplina não possui pré-requisito. Matrícula liberada.");
                break;
            }
            if (!materias.contains(req.toUpperCase())) {
                possuiTodos = false;
                System.out.println("Você NÃO possui o pre-requisito: " + req);
                System.out.println("Não será possivel se matricula em "+materia);
                voltando.executar();
            } else {
                System.out.println("Você possui o pre-requisito: " + req);
            }
        }
        if (possuiTodos) {
            System.out.println(" Todos os pre-requisitos foram cumpridos. Você pode se matricular!");
            profEscolhido = ValidarLetrasNum.lerTextoValido("Qual professor você escolhe para a materia de "+materia.toUpperCase()+":");
        }
        
        buscarInfo.BuscarDados("turmas.txt", profEscolhido, materia);
        List<String> horarioProfessor = buscarInfo.getHorariosList(); 
        List<String> horariosAluno = buscarInfo.getHorariosAluno();
        boolean conflito = false;
        buscarInfo.BuscarDados("alunos.txt", String.valueOf(matricula), null);

        String nomeEstudante = buscarInfo.getNomeVelho();
        String curso = buscarInfo.getCursoVelho();
        String dadoMatricula = buscarInfo.getMatriculaVelha();
        String nome = buscarInfo.getNomeVelho();
        String condicao = buscarInfo.getCondicao();
        List<String> materiasCursando = buscarInfo.getMateriasCursando();
        List<String> alunosMatriculados = buscarInfo.getmatriculadosList();
        String ParametroCursando = String.valueOf(materiasCursando);

        System.out.println("===== DEBUG DOS DADOS DO ESTUDANTE =====");
        System.out.println("Nome do Estudante (nomeEstudante): " + buscarInfo.getNomeVelho());
        System.out.println("Curso: " + buscarInfo.getCursoVelho());
        System.out.println("Matrícula: " + buscarInfo.getMatriculaVelha());
        System.out.println("Nome (repetido): " + buscarInfo.getNomeVelho());
        System.out.println("Condição: " + buscarInfo.getCondicao());

        System.out.println("Matérias Cursando (List): " + materiasCursando);
        System.out.println("Matérias Cursando (String): " + String.valueOf(materiasCursando));

        System.out.println("Alunos Matriculados: " + alunosMatriculados);
        System.out.println("===== FIM DO DEBUG =====");

        
        // CASO TENHA CONFLITO DE HORARIO
        for (String horario : horarioProfessor) {
            if (horariosAluno.contains(horario)) {
                conflito = true;
                break;
            }
        }
        if (conflito) {
            System.out.println("Há um conflito de horário! Não será possível se matricular nessa turma.");
            System.out.println("Voltando para o menu.");
            voltando.executar();
        }
        
        if (alunosMatriculados.isEmpty()) {
            System.out.println("A lista de alunos está vazia. Verifique se a linha foi lida corretamente.");
        } else {
            System.out.println("Alunos: " + alunosMatriculados);
        }

        // CONDIÇÃO PARA FAZER AS MATRICULAS
        System.out.println(condicao);
        boolean condicaoAluno;
        switch(condicao) {
            case "SIM" -> {
                System.out.println("Sua condição atual é de estudante especial. Vamos prosseguir com a matricula!");
                condicaoAluno = true;
                if (materiasCursando.size() == 2) {
                    System.out.println("Você já está cursando o número maximo de materia!");
                    voltando.executar();
                }
                while (condicaoAluno) {
                    numDisciplinas = ValidarLetrasNum.lerInteiro("Quantas materias você quer matricular: ");
                    if (numDisciplinas > 2) {
                        System.out.println("Você pode se matricular em no maximo 2 disciplinas!");
                        System.out.println("Você atualmente está matriculado em "+materiasCursando.size()+" materia(s).");
                    } else if ( numDisciplinas == 1) {
                        System.out.println("Você foi matriculado em "+materia+". Parabens!");
                        break;
                    }
                }
            }
            case "NÃO" -> {
                System.out.println("Vamos prosseguir com a matricula normalmente!");
                condicaoAluno = false;
                if(materiasCursando.size() == 5) {
                    System.out.println("Você já está cursando o número maximo de materia!");
                    voltando.executar();
                }
                while (!condicaoAluno) {
                    numDisciplinas = ValidarLetrasNum.lerInteiro("Quantas materias você quer matricular: ");
                    if (numDisciplinas > 5) {
                        System.out.println("Você pode se matricular em no maximo 5 disciplinas!");
                        System.out.println("Você atualmente está matriculado em "+materiasCursando.size()+" materia(s).");
                    } else if (numDisciplinas == 1 ) {
                        System.out.println("Você foi matriculado em "+materia+". Parabens!");
                        break;
                    }
                }
            }
        }
        alunosMatriculados.add(nomeEstudante);
    
        EditarDados editarTurmas = new EditarDados();
        editarTurmas.adicionarNovasInfosAoAluno("alunos.txt", nomeEstudante.toUpperCase(), materia.toUpperCase(), profEscolhido.toUpperCase(),
        buscarInfo.getTurnosList(), buscarInfo.getHorariosList(), buscarInfo.getAvaliacaoList(), alunosMatriculados);

        addAlunoNaTurma adicionar = new addAlunoNaTurma();
        adicionar.adicionarAlunoNaTurma("turmas.txt", materia, nomeEstudante.toUpperCase());
        System.out.println("Processo de matricula finalizado! Voltando para o menu...");
        voltando.executar();
    }

    // CADASTRAR NOVAS DISCIPLINAS
    public void cadastrarDisciplina () throws IOException {

        FrontSysout front = new FrontSysout();

        String nomeProf; String nomeDisciplina; Integer codigo; Integer cargaH; Integer escolha; String preReq;
        int maxAlunos; int avaliacao; String ava; String modo; String sala; int horarioInicio; int horarioFinal; String turno;

        System.out.println("\nVamos cadastrar uma disciplina então!\n");

        nomeProf = ValidarLetrasNum.lerTextoValido("Nome do professor: ");
        nomeDisciplina = ValidarLetrasNum.lerTextoValido("Nome da disciplina: ");
        codigo = ValidarLetrasNum.lerInteiro("Código da disciplina: ");
        cargaH = ValidarLetrasNum.lerInteiro("Carga horária (horas): ");
        maxAlunos = ValidarLetrasNum.lerInteiro("Capacidade máxima de alunos: ");

        // MODO E SALA DE AULA
        OUTER: 
        while (true) {
            front.modoAula();

            int condicaoAula = ValidarLetrasNum.lerInteiro("Sua escolha:");
            switch (condicaoAula) {
                case 1 -> {
                    modo = "PRESENCIAL";
                    sala = ValidarLetrasNum.lerSalaValida("Digite o número da sala (PADRÃO FGA): ");
                    break OUTER;
                }
                case 2 -> {
                    modo = "REMOTO";
                    sala = "ONLINE";
                    break OUTER;
                }
                default -> System.out.println("Escolha inválida. Digite 1 para PRESENCIAL ou 2 para REMOTO.");
            }
        }

        // HORARIO
        while (true) {
            front.TurnoDisciplina();

            int turnoEscolhido = ValidarLetrasNum.lerInteiro("Sua escolha: ");
            switch (turnoEscolhido) {
                case 1 -> turno = "M";
                case 2 -> turno = "T";
                case 3 -> turno = "N";
                default -> {
                    System.out.println("Escolha inválida. Digite 1, 2 ou 3.");
                    continue;
                }
            }
        
            
            horarioInicio = ValidarLetrasNum.lerInteiro("Horario de inicio: ");
            horarioFinal = ValidarLetrasNum.lerInteiro("Horario de termino: ");
            int dia01 = 0; int dia02 = 0; int dia03 = 0;
            front.diasAula();
            switch (cargaH) {
                case 30 -> dia01 = ValidarLetrasNum.lerInteiro("Qual o dia de aula: ");
                case 60 -> {
                    dia01 = ValidarLetrasNum.lerInteiro("Qual o 1º dia de aula: ");
                    dia02 = ValidarLetrasNum.lerInteiro("Qual o 2º dia de aula: ");
                }
                case 90 -> {
                    dia01 = ValidarLetrasNum.lerInteiro("Qual o 1º dia de aula: ");
                    dia02 = ValidarLetrasNum.lerInteiro("Qual o 2º dia de aula: ");
                    dia03 = ValidarLetrasNum.lerInteiro("Qual o 3º dia de aula: ");
                }
                default -> {
                }
            }
            List<Integer> diasSemana = new ArrayList<>(List.of(dia01, dia02, dia03));

            if (horarioFinal <= horarioInicio) {
                System.out.println("Horário de término deve ser maior que o horário de início.");
                continue;
            }
            TurnoHorarioAula horarioF = new TurnoHorarioAula();
            horarioFormat = horarioF.horarioAulaFormatado(diasSemana, turno, horarioInicio, horarioFinal);
            break;
        }

        //METODO DE AVALIAÇÃO
        front.metodoAva();
        ava = null;
        while (true) { 
            avaliacao = sc.nextInt();
            if (avaliacao != 1 && avaliacao != 2){
            System.out.println("Numero invalido. Tente novamente. ");
            System.out.print("Nova escolha:");
            avaliacao = sc.nextInt();}
            else if (avaliacao == 1){
                ava = " (P1 + P2 + P3 + L + S) / 5";
                break;
            } else if (avaliacao == 2) {
                ava = "(P1 + P2 * 2 + P3 * 3 + L + S) / 8";
                break;
            }
        }
    

        // PRE REQUISITOS
        front.CadastroDisciplina();
        escolha = sc.nextInt();

        preReq = null;
        while (true) {
            try {
                if (escolha != 1 && escolha != 2) {
                    System.out.println("Escolha inválida. Digite 1 ou 2.");
                    System.out.print("Sua escolha: ");
                    escolha = sc.nextInt();
                } else {
                    if (escolha == 2){
                        preReq = "NÃO POSSUI";
                    } else if (escolha == 1) {
                        System.out.print("Quantos pré-requisitos essa disciplina possui? ");
                        int quantidadePreReq = sc.nextInt();
                        sc.nextLine(); // limpar o buffer

                        List<String> listaPreReq = new ArrayList<>();

                        for (int i = 0; i < quantidadePreReq; i++) {
                            System.out.print("Digite o nome do pré-requisito " + (i + 1) + ": ");
                            String nomePreReq = sc.nextLine().trim();
                            listaPreReq.add(nomePreReq);
                        }
                        preReq = String.join(", ", listaPreReq);

                    }
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite apenas números (1 ou 2).");
                sc.next();
            }
            
        }


        DadosTurmasTXT.turmasTXT("turmas.txt", nomeProf.toUpperCase(), nomeDisciplina.toUpperCase(), codigo, cargaH, preReq.toUpperCase(),
         maxAlunos, ava, turno.toUpperCase(), sala.toUpperCase(), horarioFormat, modo.toUpperCase());

         voltando.executar();
        
    }

    // LISTAR TODAS AS TURMAS
    public static void listar(String caminhoArquivo) {
        boolean vazio = estaVazio(caminhoArquivo); 

        if (vazio) {
            System.out.println("O arquivo está vazio.");
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    if (!linha.trim().isEmpty()) {
                        System.out.println(linha);
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            }
        }
    }


    // VERIFICAR SE O ARQUIVO ESTA VAZIO
    public static boolean estaVazio(String caminhoArquivo) {
        boolean arquivoVazio = true; 

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    arquivoVazio = false;
                    break; 
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return true; 
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return true; 
        }
        return arquivoVazio;
    }
}


