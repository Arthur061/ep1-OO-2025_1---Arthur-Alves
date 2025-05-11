import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DisciplinasTurmas extends Aluno  {
    Scanner sc = new Scanner(System.in);

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
            }

            case 3 -> {
                System.out.println("Ok, vamos cadastrar uma nova disciplina!");
                cadastrarDisciplina();
            }

            case 4 -> {
                System.out.println("Ok, vamos listar as turmas disponiveis.");
            }

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
                buscarInfo.BuscarDados(String.valueOf(matriculaEstudante));
                String nomeEstudante = buscarInfo.getNomeVelho();



            }
            case 2 -> {
                System.out.println("Vamos trancar a discplina então.");
            }
        }

    }

    public void cadastrarDisciplina () {

        String nomeProf; String nomeDisciplina; Integer codigo; Integer cargaH; Integer escolha; String preReq;

        System.out.println("\nVamos cadastrar uma disciplina então!\n");
        System.out.print("Nome do professor: ");
        nomeProf = sc.nextLine();
        sc.nextLine();
        System.out.print("Nome da disciplina: ");
        nomeDisciplina = sc.nextLine();
        System.out.print("Código da disciplina: ");
        codigo = sc.nextInt();
        System.out.print("Carga horária (horas): ");
        cargaH = sc.nextInt();

        IntroduçãoFront req = new IntroduçãoFront();
        req.CadastroDisciplina();
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
                sc.next(); // limpa o buffer
            }
            
        }
        DadosTurmasTXT.turmasTXT("turmas.txt", nomeProf, nomeDisciplina, codigo, cargaH, preReq);
        
    }
}
