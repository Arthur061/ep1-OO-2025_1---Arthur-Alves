import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RelatorioDisciplina {
    // RELATORIO POR DISCIPLINA
    public void relatorioDisciplina(String nomedisciplina) throws IOException {
        AvaliacoesFrequencia avaliar = new AvaliacoesFrequencia();
        List<String> disciplinas = avaliar.buscarDisciplinasQueRetornamLista("turmas.txt", nomedisciplina, null);
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma turma encontrada de " + nomedisciplina+" encontrada...");
            return;
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DA DISCIPLINA: ").append(nomedisciplina.toUpperCase()).append("\n\n");
        relatorio.append("Total de disciplinas: ").append(disciplinas.size()).append("\n\n");
    
        int cont = 1;

        for (String bloco : disciplinas) {
            relatorio.append("DISCIPLINA ").append(cont++).append(":\n");
            String[] linhas = bloco.split("\n");
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
                } else if(linha.toUpperCase().startsWith("NOME PROFESSOR:")) {
                    relatorio.append("Professor ofertando: ").append(linha.substring(linha.indexOf(":") + 1).trim()).append("\n");
                }
            }
            relatorio.append("\nAVALIAÇÃO DA DISCIPLINA:\n");
            relatorio.append("A disciplina demonstra uma estrutura bem organizada, com conteúdos atualizados e alinhados às necessidades do curso.\n");
            relatorio.append("O plano de ensino está bem definido, contemplando os principais tópicos exigidos pela ementa.\n");
            relatorio.append("A metodologia adotada favorece o aprendizado dos alunos, utilizando recursos didáticos variados e estratégias de avaliação coerentes.\n");
            relatorio.append("A avaliação é bem distribuída ao longo do semestre, promovendo o acompanhamento contínuo do desempenho dos estudantes.\n");
            relatorio.append("-------------------------------------------------\n\n");

        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatorioDisciplina.txt"))) {
            writer.write(relatorio.toString());
        } catch (IOException e) {
            System.out.println("Erro ao escrever o relatório: " + e.getMessage());
            return;
        }
        System.out.println(relatorio.toString());
    }
}
