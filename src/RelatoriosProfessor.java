import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RelatoriosProfessor extends AvaliacoesFrequencia {
    // RELATORIO PROFESSOR
    public void relatorioProfessor(String nomeProfessor) throws IOException {
        AvaliacoesFrequencia avaliar = new AvaliacoesFrequencia();
       List<String> professor = avaliar.buscarDisciplinasQueRetornamLista("turmas.txt", null, nomeProfessor);
                
        if (professor.isEmpty()) {
            System.out.println("Nenhuma turma encontrada para o professor " + nomeProfessor);
            return;
        }
    
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DO PROFESSOR: ").append(nomeProfessor.toUpperCase()).append("\n\n");
        relatorio.append("Total de disciplinas: ").append(professor.size()).append("\n\n");
    
        int cont = 1;

        for (String bloco : professor) {
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
                }
            }
            relatorio.append("\nAVALIAÇÃO DO PROFESSOR:\n");
            relatorio.append("O professor demonstra excelente didática, conseguindo transmitir os conteúdos de forma clara e objetiva.\n");
            relatorio.append("Apresenta comprometimento com o aprendizado dos alunos, estando sempre disponível para esclarecimentos e dúvidas.\n");
            relatorio.append("Utiliza metodologias variadas que facilitam a compreensão e o interesse dos estudantes.\n");
            relatorio.append("-------------------------------------------------\n\n");

        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatorioProfessor.txt"))) {
            writer.write(relatorio.toString());
        } catch (IOException e) {
            System.out.println("Erro ao escrever o relatório: " + e.getMessage());
            return;
        }
        System.out.println(relatorio.toString());
    }
}
