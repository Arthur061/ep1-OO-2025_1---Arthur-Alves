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

    public void Serviços () {
        IntroduçãoFront Menu = new IntroduçãoFront();
        Menu.MenuDisciplinasTurmas();
        int escolha = sc.nextInt();
        
        switch (escolha) {

            case 1 -> {
                System.out.println("Ok, vamos para a parte de trancamento!");
                Trancamento();
            }

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
            }

            // VOLTAR MENU INICAL CHECK
            case 5 -> {
                System.out.println("Bye bye... Voltando para menu inicial.");
                super.menu();
            } 


            default -> {}
        }
    }


    public void Trancamento() {
        IntroduçãoFront opcoes = new IntroduçãoFront();
        opcoes.menuTrancamento();

        int opcao = 0;
        boolean Entrada = false;

        // You know why broda
        while(!Entrada) {
            System.out.print("Sua escolha: ");

            try {
                opcao = sc.nextInt();
                
                if (opcao >= 1 && opcao <= 3) {
                    Entrada = true; 
                } else {
                    System.out.println("Opção inválida! Por favor, escolha uma opção de 1 a 3.");
                }
            } catch (InputMismatchException e) { 
                System.out.println("Isso não é um número válido. Tente novamente.");
                sc.next(); 
            }
        }

        switch (opcao) {
            case 1 -> {
                System.out.println("Vamos trancar o semestre então.");
                System.out.println("Antes de começar o processo preciso que você me diga sua matricula.");
                System.out.print("Matricula: ");

                int matriculaEstudante = sc.nextInt();
                DadosAlunosTXT buscarInfo = new DadosAlunosTXT();
                buscarInfo.BuscarDados("alunos.txt",String.valueOf(matriculaEstudante), null);
                String nomeEstudante = buscarInfo.getNomeVelho();



            }
            case 2 -> {
                System.out.println("Vamos trancar a discplina então.");
            }
        }

    }

    // SE MATRICULAR EM DISCIPLINAS
    public void matricularDisciplina() {
        String profEscolhido = "";
        String materia; int matricula;
        

        System.out.println("\nPartiu fazer as matriculas então!\n ");
        materia = ValidarLetrasNum.lerTextoValido("Primeiro, me deixe saber qual matéria você quer se matricular: ");

        System.out.println("\nMatéria escolhida: " + materia.toUpperCase()+"\n"); // DEBUG PQ SLK NÃO DA

        boolean vazio = estaVazio(caminhoArquivo); 
        if (vazio) {
            System.out.println("Eita! parece que ainda não há turmas cadastradas...");
            MenuTurma menuTurma = new MenuTurma();
            menuTurma.executar();
        }
        
        buscarDisciplina("turmas.txt", materia, null);

        System.out.println("A materia "+materia.toUpperCase()+" tem "+getReqCont()
        +" pre-requisito(s)");

        // PRE REQUISITOS DA MATERIA
        System.out.println("\nLista de pré-requisitos da disciplina:");
        Set<String> lista = this.getListaDePreRequisitos();
        for (String req : lista) {
            System.out.println("- " + req);
        }


        matricula = ValidarLetrasNum.lerInteiro("Me fala qual a sua matricula, por favor: ");
        DadosAlunosTXT buscarMatricula = new DadosAlunosTXT();
        buscarMatricula.BuscarDados("alunos.txt",String.valueOf(matricula), null);

        // GUARDAR MATERIAS FEITAS PELO GUERREIRO
        List<String> materias = buscarMatricula.getMateriasFinalizadas();
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
        for (String req : lista) {
            if (!materias.contains(req.toUpperCase())) {
                possuiTodos = false;
                System.out.println("Você NÃO possui o pre-requisito: " + req);
                System.out.println("Não será possivel se matricula em "+materia);
                MenuOptions voltando = new MenuTurma();
                voltando.executar();
            } else {
                System.out.println("Você possui o pre-requisito: " + req);
            }
        }
        if (possuiTodos) {
            System.out.println(" Todos os pre-requisitos foram cumpridos. Você pode se matricular!");
            profEscolhido = ValidarLetrasNum.lerTextoValido("Qual professor você escolhe para a materia de "+materia.toUpperCase()+":");
        }
        DadosAlunosTXT buscarprofessor = new DadosAlunosTXT();
        buscarprofessor.BuscarDados("turmas.txt", profEscolhido, materia);
        // GET PARA FAZER A MATRICULA NO TXT
        String nomeEstudante = buscarMatricula.getNomeVelho();
        String curso = buscarMatricula.getCursoVelho();
        String dadoMatricula = buscarMatricula.getMatriculaVelha();
        String nome = buscarMatricula.getNomeVelho();
        String condicao = buscarMatricula.getCondicao();
        List<String> materiasCursando = buscarMatricula.getMateriasCursando();
        String ParametroCursando = String.valueOf(materiasCursando);

        //String turnoMateria = String.valueOf(buscarprofessor.getTurnosList());
       // String horarioMateria = String.valueOf(buscarprofessor.getHorariosList());
       // String avaliacaoMateria = String.valueOf(buscarprofessor.getAvaliacaoList());
        System.out.println(buscarprofessor.getTurnosList()); System.out.println(buscarprofessor.getHorariosList()); System.out.println(buscarprofessor.getAvaliacaoList());


        // CONDIÇÃO PARA FAZER AS MATRICULAS
        boolean condicaoAluno;
        switch(condicao) {
            case "SIM" -> {
                System.out.println("Sua condição atual é de estudante especial. Vamos prosseguir com a matricula!");
                condicaoAluno = true;
                if (materiasCursando.size() == 2) {
                    System.out.println("Você já está cursando o número maximo de materia!");
                    MenuOptions voltando = new MenuTurma();
                    voltando.executar();
                }
                while (condicaoAluno) {
                    int numDisciplinas = ValidarLetrasNum.lerInteiro("Quantas materias você quer matricular: ");
                    if (numDisciplinas > 2) {
                        System.out.println("Você pode se matricular em no maximo 2 disciplinas!");
                        System.out.println("Você atualmente está matriculado em "+materiasCursando.size()+" materia(s).");
                    } else {
                        break;
                    }
                }
            }
            case "NÃO" -> {
                System.out.println("Vamos prosseguir com a matricula normalmente!");
                condicaoAluno = false;
                if(materiasCursando.size() == 5) {
                    System.out.println("Você já está cursando o número maximo de materia!");
                    MenuOptions voltando = new MenuTurma();
                    voltando.executar();
                }
                while (!condicaoAluno) {
                    int numDisciplinas = ValidarLetrasNum.lerInteiro("Quantas materias você quer matricular: ");
                    if (numDisciplinas > 5) {
                        System.out.println("Você pode se matricular em no maximo 5 disciplinas!");
                        System.out.println("Você atualmente está matriculado em "+materiasCursando.size()+" materia(s).");
                    } else {
                        break;
                    }
                }
            }
        }

        EditarDados editarTurmas = new EditarDados();
        editarTurmas.adicionarNovasInfosAoAluno("alunos.txt", nomeEstudante.toUpperCase(), materia.toUpperCase(), profEscolhido.toUpperCase(),
        buscarprofessor.getTurnosList(), buscarprofessor.getHorariosList(), buscarprofessor.getAvaliacaoList());
        



        
    }

    // CADASTRAR NOVAS DISCIPLINAS
    public void cadastrarDisciplina () {

        IntroduçãoFront front = new IntroduçãoFront();

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
                case 1 -> turno = "MANHÃ";
                case 2 -> turno = "TARDE";
                case 3 -> turno = "NOITE";
                default -> {
                    System.out.println("Escolha inválida. Digite 1, 2 ou 3.");
                    continue;
                }
            }
        
            
            horarioInicio = ValidarLetrasNum.lerInteiro("Horario de inicio: ");
            horarioFinal = ValidarLetrasNum.lerInteiro("Horario de termino: ");

            if (horarioFinal <= horarioInicio) {
                System.out.println("Horário de término deve ser maior que o horário de início.");
                continue;
            }

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


        DadosTurmasTXT.turmasTXT("turmas.txt", nomeProf.toUpperCase(), nomeDisciplina.toUpperCase(), codigo, cargaH, preReq.toUpperCase(), maxAlunos, ava, turno.toUpperCase(), sala.toUpperCase(), horarioInicio, horarioFinal, modo.toUpperCase());
        
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
                        System.out.println(linha); // SE DER BOM VAI SER AQ
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            }
        }
    }


    // VERIFICAR SE O ARQUIVO ESTA VAZIIII CHECK
    public static boolean estaVazio(String caminhoArquivo) {
        boolean arquivoVazio = true; 

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    arquivoVazio = false; // TA VAZIO NÃO PAE
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
    


    // BUSCAR DISCIPLINA CHECK
    public static void buscarDisciplina(String caminhoArquivo, String nomeDisciplinaBuscada, String nomeProfessorBuscado) {
        int contador = 0;
        reqCont = 0;
        boolean encontrada = false;

        listaDePreRequisitos.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            DisciplinasTurmas disciplina = new DisciplinasTurmas();
            String linha;
            List<String> bloco = new ArrayList<>();
    
            while ((linha = br.readLine()) != null) {
                if (linha.contains("---------------------")) {
                    if (!bloco.isEmpty()) {

                        for (String l : bloco) {
                            if (l.toUpperCase().contains("NOME DA DISCIPLINA: " + nomeDisciplinaBuscada.toUpperCase())) {
                                encontrada = true;
                                contador++;

                                for (String info : bloco) {
                                    System.out.println(info);
                                    if (info.toUpperCase().contains("PRÉ-REQUISITO: ")) {
                                        String preRequisito = info.substring(info.indexOf(":") + 1).trim();
                                        if (listaDePreRequisitos.add(preRequisito)) { 
                                            reqCont++;
                                            System.out.println("Pré-requisito detectado: " + preRequisito);
                                        }
                                    }
                                }
                                System.out.println("---------------------");
                                break;
                            }
                        }
                        bloco.clear();
                    }
                } else {
                    bloco.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    
        if (contador > 0) {
            System.out.println("Há " + contador + " matéria(s) disponível(is) com esse nome.");
        } else {
            System.out.println("Disciplina não encontrada.");
            MenuTurma voltaMenu = new MenuTurma();
            voltaMenu.executar();
        }
    }
    

}


