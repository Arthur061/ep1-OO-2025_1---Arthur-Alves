import java.io.*;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.nio.file.StandardOpenOption;
 import java.util.ArrayList;
 import java.util.List;

public class DadosTXT  extends Aluno{ 
    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso, boolean condicao) { //CADASTRO ALUNO
        String dadosAluno = String.join("\n", 
        "Aluno especial: " + (condicao ? "SIM" : "NÃO"),
        "Matrícula: " + matricula,
        "Nome: " + nome,
        "Curso: " + curso,
        "---------------------");

         try {
            Files.write(Paths.get(caminhoArquivo), (dadosAluno + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    
    }
    public static boolean verificarSeDadoExiste(Integer matricula) { //Verificação da matricula repetida
        File arquivo = new File("alunos.txt");

        if (!arquivo.exists()) {
            return false; // Arquivo não existe no .txt
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Matrícula:")) {
                    String matriculaNoArquivo = linha.replace("Matrícula:", "").trim();
                    if (matriculaNoArquivo.equals(String.valueOf(matricula))) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return false;
    }
    public static List<String> getNomesAlunos(String caminhoArquivo) { // Saida dos nomes de todos alunos
        List<String> nomes = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
        
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Nome: ")) {  // Verifica se a linha começa com "Nome: "
                nomes.add(linha.substring(6).trim());  // Adiciona o nome na lista
             }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return nomes;
    }
}
