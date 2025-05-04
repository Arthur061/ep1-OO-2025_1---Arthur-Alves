import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 import java.nio.file.StandardOpenOption;
 import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadosTXT  extends Aluno{ 
    StringBuilder novoConteudo = new StringBuilder();
    StringBuilder blocoAluno = new StringBuilder();
    boolean encontrou = false;
    String linha; String dado;

    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso, boolean condicao) { //CADASTRO ALUNO
        String dadosAluno = String.join("\n", 
        "Aluno especial: " + (condicao ? "SIM" : "NÃO"),
        "Matricula: " + matricula,
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
    public void BuscarDados(String dado) { //Buscar Dados

        try (BufferedReader br = new BufferedReader(new FileReader("alunos.txt"))) { 
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("---------------------")) {
                    if (blocoAluno.toString().toLowerCase().contains(dado.toLowerCase())) {
                        encontrou = true;
                        System.out.println();
                        System.out.println("Aluno encontrado:");
                        System.out.println();
                        System.out.println(blocoAluno.toString());
                        System.out.println();
                        return;
                    }
                    blocoAluno.setLength(0); 
                } else {
                    blocoAluno.append(linha).append("\n");
                }
            }
            if(!encontrou){
                System.out.println("Aluno não encontrado.");}

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

}
    public void editarDados(String nomeAluno, String campo, String novoValor) {
    try {
        Path arquivo = Paths.get("alunos.txt");
        
        if (!Files.exists(arquivo)) {
            System.out.println("Arquivo de alunos não encontrado!");
            return;
        }

        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        List<String> novasLinhas = new ArrayList<>();
        boolean alunoEncontrado = false;
        boolean campoAlterado = false;
        StringBuilder blocoAtual = new StringBuilder();

        for (String linha : linhas) {
            if (linha.startsWith("---------------------")) {
                processarBloco(blocoAtual, novasLinhas, nomeAluno, campo, novoValor);
                novasLinhas.add(linha);
                blocoAtual.setLength(0);
            } else {
                blocoAtual.append(linha).append("\n");
            }
        }

        // Processar último bloco se existir
        if (blocoAtual.length() > 0) {
            processarBloco(blocoAtual, novasLinhas, nomeAluno, campo, novoValor);
        }

        if (alunoEncontrado) {
            Files.write(arquivo, novasLinhas, StandardCharsets.UTF_8);
            System.out.println(campoAlterado 
                ? " Dados atualizados com sucesso!" 
                : " Campo não encontrado para o aluno.");
        } else {
            System.out.println(" Aluno '" + nomeAluno + "' não encontrado.");
        }

    } catch (IOException e) {
        System.out.println(" Erro ao editar: " + e.getMessage());
    }
}

private void processarBloco(StringBuilder bloco, List<String> output, 
                          String nomeAluno, String campo, String novoValor) {
    String blocoStr = bloco.toString();
    String[] linhasBloco = blocoStr.split("\n");
    
    // Verifica se é o aluno correto
    boolean ehAlunoProcurado = false;
    for (String linha : linhasBloco) {
        if (linha.toLowerCase().startsWith("nome:")) {
            String nomeNoArquivo = linha.substring(linha.indexOf(":") + 1).trim();
            if (nomeNoArquivo.equalsIgnoreCase(nomeAluno)) {
                ehAlunoProcurado = true;
                boolean alunoEncontrado = true;
                break;
            }
        }
    }

    if (ehAlunoProcurado) {
        // Processa cada linha do bloco
        for (String linha : linhasBloco) {
            if (linha.toLowerCase().startsWith(campo.toLowerCase() + ":")) {
                output.add(campo + ": " + novoValor);
                boolean campoAlterado = true;
            } else {
                output.add(linha);
            }
        }
    } else {
        // Mantém o bloco original
        output.addAll(Arrays.asList(linhasBloco));
    }
}
}


