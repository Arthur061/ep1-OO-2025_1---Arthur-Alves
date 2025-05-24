import java.io.*;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
 import java.util.List;

public class DadosAlunosTXT  extends Aluno{
    //CADASTRO ALUNO
    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso, boolean condicao, String materiasDone,
     String materiasCursando, String nomeProf, String turnoProf, String horarioProf, String tipoAva, String semestre, String mencoes, String materiasFall) {
        String dadosAluno = String.join("\n", 
        "---------------------",
        "ALUNO ESPECIAL: " + (condicao ? "SIM" : "NÃO"),
        "MATRICULA: " + matricula,
        "NOME: " + nome,
        "SEMESTRE: " + semestre,
        "CURSO: " + curso,
        "MATERIAS FINALIZADAS: " + materiasDone,
        "MENÇÕES FINAIS: "+ mencoes,
        "MATERIAS REPROVADAS: " +materiasFall,
        "",
        "MATERIAS CURSANDO: " + materiasCursando,
        "NOME PROFESSOR: "+ nomeProf,
        "TURNO: " + turnoProf,
        "HORARIO: "+horarioProf,
        "TIPO AVALIAÇÃO: "+tipoAva,
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
            return false;
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
                if (linha.startsWith("NOME: ")) {  
                nomes.add(linha.substring(6).trim());  
             }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return nomes;
    }
} 

