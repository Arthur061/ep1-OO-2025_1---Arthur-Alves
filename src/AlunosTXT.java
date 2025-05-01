import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AlunosTXT {
    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.write("Matrícula: " + matricula);
            writer.newLine();
            writer.write("Nome: " + nome);
            writer.newLine();
            writer.write("Curso: " + curso);
            writer.newLine();
            writer.write("---------------------");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }
}
