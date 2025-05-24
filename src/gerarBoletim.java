import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class gerarBoletim {
    public void criarRelatorioAluno(String caminhoArquivo, int semestre, String nomeAluno, int matriculaAluno, String cursoAluno,
                                    String materiasFinalizadas, String materiasReprovadas, String dadosMaterias) {
        String dadosBoletim = String.join("\n", 
            "---------------------",
            "SEMESTRE: " + semestre,
            "NOME ALUNO: " + nomeAluno,
            "MATRICULA: " + matriculaAluno,
            "CURSO: " + cursoAluno,
            "",
            "MATERIAS FINALIZADAS: " + materiasFinalizadas,
            "MATERIAS REPROVADAS: " + materiasReprovadas,
            "",
            "DADOS DAS MATERIAS:\n" + dadosMaterias,
            "---------------------"
        );
        try {
            Files.write(Paths.get(caminhoArquivo), (dadosBoletim + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }

    public void buscarNoBoletim(int matricula, int semestre, boolean mostrarCompleto) throws IOException {
        File arquivo = new File("boletimAlunos.txt");
        if (!arquivo.exists()) {
            System.out.println("Não há registros de boletim ate o momento.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean blocoEncontrado = false;
            StringBuilder bloco = new StringBuilder();

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("SEMESTRE: ")) {
                    bloco.setLength(0);
                    bloco.append(linha).append("\n");
                    blocoEncontrado = false;
                } else if (!linha.trim().isEmpty()) {
                    bloco.append(linha).append("\n");
                    if (linha.contains("MATRICULA: " + matricula) && bloco.toString().contains("SEMESTRE: " + semestre)) {
                        blocoEncontrado = true;
                    }
                }
                if (linha.equals("---------------------") && blocoEncontrado) {
                    System.out.println("\n===== DADOS ENCONTRADOS =====");
                    if (mostrarCompleto) {
                        System.out.println(bloco.toString());
                    } else {
                        String[] linhas = bloco.toString().split("\n");
                        for (String l : linhas) {
                            if (l.startsWith("DADOS DAS MATERIAS:")) break;
                            System.out.println(l);
                        }
                    }
                    return;
                }
            }
            System.out.println("Nenhum boletim encontrado com matrícula " + matricula + " e semestre " + semestre + ".");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        System.out.println("Voltando para menu inicial...");
    }
}
