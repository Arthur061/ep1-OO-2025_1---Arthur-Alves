
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacoesFrequencia extends MenuPrincipal {
    MenuOptions voltando = new MenuAvaliacao();
    FrontSysout menu = new FrontSysout();
   // buscarDados buscar = new buscarDados();
    gerarBoletim boletim = new gerarBoletim();
    
    public void sistemaNotas () throws IOException {
        menu.MenuAvaliacoes();
        int escolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");

        switch (escolha) {
            case 1 -> {
                // RELATORIOS
                relatorios();
            }
            case 2 -> {
                // LANÇAMENTOS
                menu.lancamentoAvaliacoes();
                OUTER:
                while (true) {
                    int lancamentoescolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");
                    switch (lancamentoescolha) {
                        case 1 -> {
                            // ACHO QUE TA PRONTO
                            System.out.println("Vamos lançar sua notas e frequência!\n");
                            LancarNotas lancamentos = new LancarNotas();
                            lancamentos.lancamentosNotasFrequencia();
                            break OUTER;
                        }
                        case 2 -> {
                            voltando.executar();
                            break OUTER;
                        }
                        default -> {System.out.println("Opção invalida! ");
                        voltando.executar();
                        }
                    }
                }
            }
            case 3 -> {
                boletimDesempenho();
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

    // ABA RELATORIOS
    public void relatorios() throws IOException {
        menu.menuRelatorios();
        int escolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");
        switch (escolha) {
            case 1 -> {
                // RELATORIO POR TURMA
                String nomeDisciplina = ValidarLetrasNum.lerTextoValido("Qual o nome da disciplina: ");
                String nomeProfessor = ValidarLetrasNum.lerTextoValido("Qual o nome do professor: ");
                List<String> turma = buscarDisciplinasQueRetornamLista("turmas.txt", nomeDisciplina, nomeProfessor);
                RelatorioTurma gerar = new RelatorioTurma();
                gerar.relatorioTurma(nomeDisciplina, nomeProfessor);
                voltando.executar();
            }
            case 2 -> {
                // RELATORIO POR DISCIPLINA
                String nomeDisciplina = ValidarLetrasNum.lerTextoValido("Qual disciplina você quer o relatorio: ");
                List<String> disciplinas = buscarDisciplinasQueRetornamLista("turmas.txt", nomeDisciplina, null);
                RelatorioDisciplina gerar = new RelatorioDisciplina();
                gerar.relatorioDisciplina(nomeDisciplina);
                voltando.executar();
            }
            case 3 -> {
                // RELATORIO POR PROFESSOR
                String nomeProfessor = ValidarLetrasNum.lerTextoValido("Qual professor você quer o relatorio: ");
                List<String> professor = buscarDisciplinasQueRetornamLista("turmas.txt", null, nomeProfessor);
                System.out.println("Buscando relatorio do professor "+nomeProfessor.toUpperCase()+"...");
                RelatoriosProfessor gerar = new RelatoriosProfessor();
                gerar.relatorioProfessor(nomeProfessor);
                voltando.executar();

            }
            case 4 -> {
                voltando.executar();
            }
            default -> {
                System.out.println("Opção inválida! Tente novamente.");
                relatorios();
            }
        }
        
    }

    // GUARDA INFOS
    public List<String> buscarDisciplinasQueRetornamLista(String caminhoArquivo, String nomeDisciplinaBuscada, String nomeProfessorBuscado) throws IOException {
        List<String> blocosEncontrados = new ArrayList<>();
        List<String> bloco = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("---------------------")) {
                    if (!bloco.isEmpty()) {
                        boolean encontrouDisciplina = false;
    
                        for (String l : bloco) {
                            if (nomeDisciplinaBuscada != null && l.toUpperCase().contains("NOME DA DISCIPLINA: " + nomeDisciplinaBuscada.toUpperCase())) {
                                encontrouDisciplina = true;
                            }
                            if (nomeProfessorBuscado != null && l.toUpperCase().contains("NOME PROFESSOR: " + nomeProfessorBuscado.toUpperCase())) {
                                encontrouDisciplina = true;
                            }
                        }
                        if (encontrouDisciplina) {
                            blocosEncontrados.add(String.join("\n", bloco));
                        }
                        bloco.clear();
                    }
                } else {
                    bloco.add(linha);
                }
            }
            if (!bloco.isEmpty()) {
                boolean encontrouDisciplina = false;
    
                for (String l : bloco) {
                    if (nomeDisciplinaBuscada != null && l.toUpperCase().contains("NOME DA DISCIPLINA: " + nomeDisciplinaBuscada.toUpperCase())) {
                        encontrouDisciplina = true;
                    }
                    if (nomeProfessorBuscado != null && l.toUpperCase().contains("NOME PROFESSOR: " + nomeProfessorBuscado.toUpperCase())) {
                        encontrouDisciplina = true;
                    }
                }
                if (encontrouDisciplina) {
                    blocosEncontrados.add(String.join("\n", bloco));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return blocosEncontrados;
    }

    // ABA DOS BOLETIM
    public void boletimDesempenho() throws IOException {
        int matriculaBusca;
        int semestreBusca;

        menu.selecaoBoletim();
        int escolha = ValidarLetrasNum.lerInteiro("Sua escolha: ");
        switch(escolha) {
            case 1 -> {
                // BOLETIM COMPLETO
                System.out.println("Vamos pegar seu boletim completo! Primeiro, me fale sua matricula.");
                matriculaBusca = ValidarLetrasNum.verificarMatricula(-1);
                semestreBusca = ValidarLetrasNum.lerInteiro("Qual semestre você quer o boletim: ");
                boletim.buscarNoBoletim(matriculaBusca, semestreBusca, true);

                System.out.println("Voltando para menu inicial...");
                voltando.executar();
            }
            case 2 -> {
                // BOLETIM SEM DADOS DO PROFESSOR
                System.out.println("Vamos pegar seu boletim parcial, sem os dados do professor! Primeiro, me fale sua matricula.");
                matriculaBusca = ValidarLetrasNum.verificarMatricula(-1);
                semestreBusca = ValidarLetrasNum.lerInteiro("Qual semestre você quer o boletim: ");
                boletim.buscarNoBoletim(matriculaBusca, semestreBusca, false);

                System.out.println("Voltando para menu inicial...");
                voltando.executar();
            }
            case 3 -> {
                voltando.executar();
            }
            default -> {
                System.out.println("Opção invalida!");
                boletimDesempenho();
            }
        }
    } 
}


