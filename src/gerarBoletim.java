import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class gerarBoletim {
    public void criarRelatorioAluno(String caminhoArquivo, int semestre, String nomeAluno, int matriculaAluno, String cursoAluno,
                                    String materiasFinalizadas, String materiasReprovadas, String dadosMaterias) {
        String dadosBoletim = String.join("\n", 
            "- - - - - - - - - - - - - -",
            "SEMESTRE: " + semestre,
            "NOME ALUNO: " + nomeAluno,
            "MATRICULA: " + matriculaAluno,
            "CURSO: " + cursoAluno,
            "",
            "MATERIAS FINALIZADAS: " + materiasFinalizadas,
            "MATERIAS REPROVADAS: " + materiasReprovadas,
            "",
            "DADOS DAS MATERIAS:\n" + dadosMaterias,
            "- - - - - - - - - - - - - -"
        );
        try {
            Files.write(Paths.get(caminhoArquivo), (dadosBoletim + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }
}
