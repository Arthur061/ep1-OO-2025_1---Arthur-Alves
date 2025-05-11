import java.io.*;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
 import java.util.List;
import java.util.Scanner;

public class DadosAlunosTXT  extends Aluno{
    Scanner sc = new Scanner(System.in);
    StringBuilder novoConteudo = new StringBuilder();

    StringBuilder blocoAluno = new StringBuilder();
    boolean encontrou = false; String linha; String dado;
    
    //Variaveis para edição
    private String matriculaVelha; private String nomeVelho; private String cursoVelho;

    //CADASTRO ALUNO
    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso, boolean condicao) {
        String dadosAluno = String.join("\n", 
        "---------------------",
        "ALUNO ESPECIAL: " + (condicao ? "SIM" : "NÃO"),
        "MATRICULA: " + matricula,
        "NOME: " + nome,
        "CURSO: " + curso,
        "---------------------");

         try {
            Files.write(Paths.get(caminhoArquivo), (dadosAluno + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    
    }

    //Verificação da matricula repetida
    public static boolean verificarSeDadoExiste(Integer matricula) {
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

    // Saida dos nomes de todos alunos
    public static List<String> getNomesAlunos(String caminhoArquivo) {
        List<String> nomes = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
        
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("NOME: ")) {  // Verifica se a linha começa com "Nome: "
                nomes.add(linha.substring(6).trim());  // Adiciona o nome na lista
             }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return nomes;
    }

    //Buscar Dados
    public void BuscarDados(String dado) {
        matriculaVelha = null;
        nomeVelho = null;
        cursoVelho = null;

        try (BufferedReader br = new BufferedReader(new FileReader("alunos.txt"))) { 
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("---------------------")) {
                    if (blocoAluno.toString().toLowerCase().contains(dado.toLowerCase())) {
                        encontrou = true;
                        System.out.println("\nAluno encontrado:\n" + blocoAluno.toString());
                        
                        // Armazena os dados para serem editados pae
                        String[] linhas = blocoAluno.toString().split("\n");
                        for (String l : linhas) {
                            if (l.trim().toUpperCase().startsWith("MATRICULA:")) {
                                matriculaVelha = l.substring(l.indexOf(":") + 1).trim();
                            } else if (l.trim().toUpperCase().startsWith("NOME:")) {
                                nomeVelho = l.substring(l.indexOf(":") + 1).trim();
                            } else if (l.trim().toUpperCase().startsWith("CURSO:")) {
                                cursoVelho = l.substring(l.indexOf(":") + 1).trim();
                            } if (matriculaVelha != null && nomeVelho != null && cursoVelho != null) { // TCHAU LOOP
                                break;
                            }
                            
                        }
                        
                        break;
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

    // GET PARA EDITAR MATRICULA
    public String getMatriculaVelha() {
        return matriculaVelha;
    }

    // GET PARA EDITAR O NOME
    public String getNomeVelho() {
        return nomeVelho;
    }

    // GET PARA EDITAR O CURSO
    public String getCursoVelho () {
        return cursoVelho;
    }
    
}

