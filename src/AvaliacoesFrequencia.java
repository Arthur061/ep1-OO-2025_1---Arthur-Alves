
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AvaliacoesFrequencia extends Menu {
    MenuOptions voltando = new MenuAvaliacao();
    IntroduçãoFront menu = new IntroduçãoFront();
    DadosAlunosTXT buscar = new DadosAlunosTXT();
    
    public void sistemaNotas () {
        menu.MenuAvaliacoes();
        int escolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");

        switch (escolha) {
            case 1 -> {
                // RELATORIOS
            }
            case 2 -> {
                // LANÇAMENTOS
                menu.lancamentoAvaliacoes();
                OUTER:
                while (true) {
                    int lancamentoescolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");
                    switch (lancamentoescolha) {
                        case 1 -> {
                            System.out.println("Vamos para a aba de notas!");
                            lancamentosNotas();
                            break OUTER;
                        }
                        case 2 -> {
                            System.out.println("Vamos para a aba de frequência!");
                            break OUTER;
                        }
                        case 3 -> {
                            voltando.executar();
                            break OUTER;
                        }
                        default -> System.out.println("Opção invalida! Tente novamente.");
                    }
                }
                
            }
            case 3 -> {
                // BOLETINS
            }
            case 4 -> {
                super.menu();
            }
            default -> {
                System.out.println("Opção invalida. Tente novamente!");
                voltando.executar();
            }
        }
    }

    // LNACAMENTO DE NOTAS
    public void lancamentosNotas() {
        int ava = 0;
        double notaP1; double listas;
        double notaP2; double seminario;
        double notaP3; double notaFinal = 0;

        System.out.println("Você esta na aba de notas!");
        VerificadorMatricula buscarMat = new VerificadorMatricula();
        int matricula = buscarMat.verificarMatricula(-1);

        buscar.BuscarDados("alunos.txt", String.valueOf(matricula), null);
        String nomeAluno = buscar.getNomeVelho();

        String professor = ValidarLetrasNum.lerTextoValido("Olá "+buscar.getNomeVelho()+", qual professor você deseja fazer o lançamento de nota: ");
        buscar.BuscarDados("turmas.txt", professor.toUpperCase(), "AVALIAÇÃO:");

        List<String> metodoAva = buscar.getAvaliacaoList();
        String metodoStr = String.join(", ", metodoAva);
        List<String> materia = buscar.getmateriaProf();
        String materiaStr = String.join(", ", materia);

        List<String> metodos = Arrays.asList(
            "(P1 + P2 * 2 + P3 * 3 + L + S) / 8",
            "(P1 + P2 + P3 + L + S) / 5");

        if (metodos.get(0).equals(metodoStr)) {ava = 1;}
        else if (metodos.get(1).equals(metodoStr)) { ava = 2;}
        else {System.out.println("Método de avaliação desconhecido."); lancamentosNotas();}

        switch (ava) {
            case 1 -> {
                notaP1 = ValidarLetrasNum.lerDouble("Nota P1: ");
                notaP2 = ValidarLetrasNum.lerDouble("Nota P2: ");
                notaP2 *= 2;
                notaP3 = ValidarLetrasNum.lerDouble("Nota P3: ");
                notaP3 *= 3;
                listas = ValidarLetrasNum.lerDouble("Nota Listas: ");
                seminario = ValidarLetrasNum.lerDouble("Nota Seminario: ");
                notaFinal = (notaP1 + notaP2 + notaP3 + listas + seminario)/8;
                System.out.printf("Sua nota final é %.2f\n", notaFinal);
                //gravarAvaliacao(nomeAluno, String.valueOf(matricula), professor, materiaStr, 0, notaFinal);
            }
            case 2 -> {
                notaP1 = ValidarLetrasNum.lerDouble("Nota P1: ");
                notaP2 = ValidarLetrasNum.lerDouble("Nota P2: ");
                notaP3 = ValidarLetrasNum.lerDouble("Nota P3: ");
                listas = ValidarLetrasNum.lerDouble("Nota Listas: ");
                seminario = ValidarLetrasNum.lerDouble("Nota Seminario: ");
                notaFinal = (notaP1 + notaP2 + notaP3 + listas + seminario)/5;
                System.out.printf("Sua nota final é %.2f\n", notaFinal);
            }
        }
        menu.migrarLacamentos();
        OUTER:
        while (true) {
            int migrar = ValidarLetrasNum.lerInteiro("Sua escolha: ");
            switch (migrar) {
                case 1 -> {
                    //CHAMAR CLASSE FREQUENCIA
                    break OUTER;
                }
                case 2 -> {
                    gravarAvaliacao(nomeAluno, String.valueOf(matricula), professor.toUpperCase(), materiaStr, 0, notaFinal);
                    break OUTER;
                }
                default -> System.out.println("Opção invalida! Tente novamente.");
            }
        }
    }

    // GRAVAR DESEMPENHO DO ALUNO
    public static void gravarAvaliacao(String nomeAluno, String matricula, String nomeProfessor, String materia, int frequencia, double notaFinal) {
        String nomeArquivo = "avaliacoesNotas.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            String desempenhoAluno = String.join("\n",
            "---------------------",
            "NOME ALUNO: "+ nomeAluno,
            "MATRICULA: "+ matricula,
            "NOME PROFESSOR: "+ nomeProfessor,
            "NOME DISCIPLINA: "+ materia,
            "FREQUÊNCIA: "+ frequencia,
            "NOTA FINAL: "+ notaFinal,
            "---------------------"
            );

            writer.write(desempenhoAluno);
            writer.newLine();
            System.out.println("Desempenho gravado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }
}

