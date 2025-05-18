
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AvaliacoesFrequencia extends MenuPrincipal {
    MenuOptions voltando = new MenuAvaliacao();
    FrontSysout menu = new FrontSysout();
    buscarDados buscar = new buscarDados();
    
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

    
    // ALUNO APROVADO
    public void processarAprovacao(String matricula, String materiaAprovada, String mencaoFinal) throws IOException {
        Path caminho = Paths.get("alunos.txt");
        buscar.BuscarDados("alunos.txt", matricula, null);

        List<String> materiasCursando = buscar.getMateriasCursando();
        List<String> professores = buscar.getNomesProfs();
        List<String> turnos = buscar.getTurnosList();
        List<String> horarios = buscar.getHorariosList();
        List<String> avaliacoes = buscar.getAvaliacaoList();
        List<String> materiasFinalizadas = buscar.getMateriasFinalizadas();
        List<String> mencoesFinais = buscar.getMencao();

        List<String> linhas = Files.readAllLines(caminho);

        int indice = -1;
        for (int i = 0; i < materiasCursando.size(); i++) {
            if (materiasCursando.get(i).equalsIgnoreCase(materiaAprovada.trim())) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            System.out.println("Matéria não encontrada entre as matérias cursando.");
            return;
        }

        materiasFinalizadas.add(materiasCursando.get(indice));
        mencoesFinais.add(mencaoFinal.trim());

        materiasCursando.remove(indice);
        professores.remove(indice);
        turnos.remove(indice);
        horarios.remove(indice);
        avaliacoes.remove(indice);
    
        // FAZ ADD AS INFO
        boolean alunoEncontrado = false;
        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            
            if (linha.startsWith("MATRICULA:")) {
                alunoEncontrado = linha.contains(matricula);
            }
            if (alunoEncontrado) {
                if (linha.startsWith("MATERIAS CURSANDO:")) {
                    linhas.set(i, "MATERIAS CURSANDO: " + String.join(", ", materiasCursando));

                } else if (linha.startsWith("MATERIAS FINALIZADAS:")) {
                    linhas.set(i, "MATERIAS FINALIZADAS: " + String.join(", ", materiasFinalizadas));

                } else if (linha.startsWith("MENÇÕES FINAIS:")) {
                    linhas.set(i, "MENÇÕES FINAIS: " + String.join(", ", mencoesFinais));

                } else if (linha.startsWith("NOME PROFESSOR:")) {
                    linhas.set(i, "NOME PROFESSOR: " + String.join(", ", professores));
                    
                } else if (linha.startsWith("TURNO:")) {
                    linhas.set(i, "TURNO: " + String.join(", ", turnos));

                } else if (linha.startsWith("HORARIO:")) {
                    linhas.set(i, "HORARIO: " + String.join(", ", horarios));

                } else if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    linhas.set(i, "TIPO AVALIAÇÃO: " + String.join(", ", avaliacoes));
                }
                if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    break; 
                }
            }
        }
        Files.write(caminho, linhas, StandardCharsets.UTF_8);
        System.out.println("Dados atualizados para a matrícula: " + matricula);
    }

    // SE O CAMARADA REPROVOU
    public void processarReprovacao(String matricula, String materiaReprovada, String mencaoFinal) throws IOException {
        String formatado = materiaReprovada.toUpperCase() + " (" + mencaoFinal + ")";
        
        buscar.BuscarDados("alunos.txt", String.valueOf(matricula), null);

        List<String> materiasReprovadas = buscar.getMateriasreprovadas();
        List<String> materia = buscar.getMateriasCursando();
        List<String> professor = buscar.getNomesProfs();
        List<String> turno = buscar.getTurnosList();
        List<String> horario = buscar.getHorariosList();
        List<String> avaliacao = buscar.getAvaliacaoList();

        System.out.println("Item a ser adicionado em materias Reprovadas: " + formatado); // check
        materiasReprovadas.add(formatado);

        List<String> linhas = Files.readAllLines(Paths.get("alunos.txt"));

        int indice = -1;
        for (int i = 0; i < professor.size(); i++) {
            System.out.println("Comparando: '" + materia.get(i).trim() + "' com '" + materiaReprovada.trim().toUpperCase() + "'");
            if (materia.get(i).trim().equalsIgnoreCase(materiaReprovada.trim())) {
                indice = i;
                materia.remove(indice);
                professor.remove(indice);
                turno.remove(indice);
                horario.remove(indice);
                avaliacao.remove(indice);
                break;
            }
        }

        if (indice == -1) {
            System.out.println(" Materia '" + materiaReprovada.toUpperCase() + "' não encontrada entre as matérias cursando.");
            return;
        }

        boolean alunoEncontrado = false;
        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            
            if (linha.startsWith("MATRICULA:")) {
                alunoEncontrado = linha.contains(matricula);
            }
            if (alunoEncontrado) {
                if (linha.startsWith("MATERIAS CURSANDO:")) {
                    linhas.set(i, "MATERIAS CURSANDO: " + String.join(", ", materia));

                } else if (linha.startsWith("NOME PROFESSOR:")) {
                    linhas.set(i, "NOME PROFESSOR: " + String.join(", ", professor));
                    
                } else if (linha.startsWith("TURNO:")) {
                    linhas.set(i, "TURNO: " + String.join(", ", turno));

                } else if (linha.startsWith("HORARIO:")) {
                    linhas.set(i, "HORARIO: " + String.join(", ", horario));

                } else if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    linhas.set(i, "TIPO AVALIAÇÃO: " + String.join(", ", avaliacao));
                }
                else if (linha.startsWith("MATERIAS REPROVADAS:")) {
                    linhas.set(i, "MATERIAS REPROVADAS: " + String.join(", ", materiasReprovadas));
                }
                if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    break; 
                }
            }
        }
        Files.write(Paths.get("alunos.txt"), linhas);
        System.out.println("Processo da disciplina " + materiaReprovada.toUpperCase() + " foi finalizado");
    }

    // MANDA PARA SUA DEVIDA ESCOLHA
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
            }
            case 2 -> {
                // RELATORIO POR DISCIPLINA
                String nomeDisciplina = ValidarLetrasNum.lerTextoValido("Qual disciplina você quer o relatorio: ");
                List<String> disciplinas = buscarDisciplinasQueRetornamLista("turmas.txt", nomeDisciplina, null);
                RelatorioDisciplina gerar = new RelatorioDisciplina();
                gerar.relatorioDisciplina(nomeDisciplina);
            }
            case 3 -> {
                // RELATORIO POR PROFESSOR
                String nomeProfessor = ValidarLetrasNum.lerTextoValido("Qual professor você quer o relatorio: ");
                List<String> professor = buscarDisciplinasQueRetornamLista("turmas.txt", null, nomeProfessor);
                System.out.println("Buscando relatorio do professor "+nomeProfessor.toUpperCase()+"...");
                RelatoriosProfessor gerar = new RelatoriosProfessor();
                gerar.relatorioProfessor(nomeProfessor);

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
    
            // Adiciona o último bloco, se necessário
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
    
    
}


