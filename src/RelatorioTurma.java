import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RelatorioTurma {
    // RELATORIO TURMA
    public void relatorioTurma(String nomedisciplina, String nomeProfessor) throws IOException {
        AvaliacoesFrequencia avaliar = new AvaliacoesFrequencia();
        List<String> disciplinas = avaliar.buscarDisciplinasQueRetornamLista("turmas.txt", nomedisciplina, nomeProfessor);
        
        System.out.println("Turma: "+disciplinas);
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma turma encontrada de " + nomedisciplina + "...");
            return;
        }
        String blocoCorreto = null;
        

        for (String bloco : disciplinas) {
            String[] linhas = bloco.split("\n");
            for (String linha : linhas) {
                if (linha.toUpperCase().startsWith("NOME DA DISCIPLINA:")) {
                    String nomeNoArquivo = linha.substring(linha.indexOf(":") + 1).trim();
                    if (nomeNoArquivo.equalsIgnoreCase(nomedisciplina)) {
                        blocoCorreto = bloco;
                        break;
                    }
                }
            }
            if (blocoCorreto != null) break;
        }

        if (blocoCorreto == null) {
            System.out.println("Nenhuma turma com nome exato de " + nomedisciplina.toUpperCase() + " encontrada...");
            return;
        }

        System.out.println(blocoCorreto);

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DA DISCIPLINA ").append(nomedisciplina.toUpperCase()).append(" do professor ").append(nomeProfessor.toUpperCase()).append("\n\n");

        String[] linhas = blocoCorreto.split("\n");

        for (String linha : linhas) {
            if (linha.toUpperCase().startsWith("NOME DA DISCIPLINA:")) {
                relatorio.append("Nome: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("AVALIAÇÃO:")) {
                relatorio.append("Avaliação: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("ALUNOS MATRICULADOS:")) {
                relatorio.append("Alunos matriculados: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("PRÉ-REQUISITO:")) {
                relatorio.append("Pré-requisitos: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("MODO:")) {
                relatorio.append("Modo: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("SALA:")) {
                relatorio.append("Sala: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("CAPACIDADE:")) {
                relatorio.append("Capacidade: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            } else if (linha.toUpperCase().startsWith("NOME PROFESSOR:")) {
                relatorio.append("Professor ofertando: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
            }
        }
        relatorio.append("\nRELATÓRIO SOBRE A TURMA:\n");
        relatorio.append("A turma apresenta um bom nível de participação, com presença regular dos alunos e envolvimento durante as aulas.\n");
        relatorio.append("A maioria dos alunos demonstra interesse e comprometimento com as atividades propostas.\n");
        relatorio.append("O clima em sala é positivo, favorecendo o aprendizado e a troca de conhecimento entre os colegas.\n");
        relatorio.append("Alguns alunos se destacam pela proatividade e contribuições relevantes durante as aulas, o que contribui para o bom desempenho coletivo.\n");
        relatorio.append("-------------------------------------------------\n\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatorioTurma.txt"))) {
            writer.write(relatorio.toString());
        } catch (IOException e) {
            System.out.println("Erro ao escrever o relatório: " + e.getMessage());
            return;
        }
        System.out.println(relatorio.toString());
    }
}
