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
    private String matriculaVelha; private String nomeVelho; private String cursoVelho; private String condicao; private String materiasCursando;
    private String semestreAtual; String professor;

    String materiasFall = null;
    String turno = null;
    String nomeProf = null;
    String horario = null;
    String avaliacao = null;
    String matriculados = null;
    String materia = null;
    String cargaH = null;

    
    // LISTAS E GETTERS PARA MATRICULA
    private final List<String> materiasFinalizadas = new ArrayList<>();
    private final List<String> materiasFazendo = new ArrayList<>();
    private final List<String> turnosList = new ArrayList<>();
    private final List<String> avaliacaoList = new ArrayList<>();
    private final List<String> horariosList = new ArrayList<>();
    private final List<String> nomesProfessores = new ArrayList<>();
    private final List<String> matriculadosList = new ArrayList<>();
    private final List<String> materiaList = new ArrayList<>();
    private final List<String> cargaHorariaList = new ArrayList<>();
    private final List<String> mencaoFinaList = new ArrayList<>();
    private final List<String> materiasReprovadasList = new ArrayList<>();

    public String getMatriculaVelha() {return matriculaVelha;}
    public String getNomeVelho() {return nomeVelho;}
    public String getSemestreAtual() {return semestreAtual;}
    public String getCursoVelho () {return cursoVelho;}
    public String getCondicao () {return condicao;}
    public String getNomeProf() {return professor;}

    public List<String> getMateriasreprovadas() {return materiasReprovadasList;}
    public List<String> getMencao() {return mencaoFinaList;}
    public List<String> getCargaH() {return cargaHorariaList;}
    public List<String> getmateriaProf() {return materiaList;}
    public List<String> getNomesProfs () {return nomesProfessores;}
    public List<String> getmatriculadosList() {return matriculadosList;}
    public List<String> getTurnosList() {return turnosList;}
    public List<String> getAvaliacaoList() {return avaliacaoList;}
    public List<String> getHorariosList() {return horariosList;}
    public List<String> getMateriasCursando () {return materiasFazendo;}
    public List<String> getMateriasFinalizadas() {return materiasFinalizadas;}



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
        "TURNO: "+turnoProf,
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

    
} 

