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
    
    // LISTAS E GETTERS PARA MATRICULA
    private final List<String> materiasFinalizadas = new ArrayList<>();
    private final List<String> materiasFazendo = new ArrayList<>();
    private final List<String> turnosList = new ArrayList<>();
    private final List<String> avaliacaoList = new ArrayList<>();
    private final List<String> horariosList = new ArrayList<>();
    private final List<String> nomesProfessores = new ArrayList<>();
    private final List<String> matriculadosList = new ArrayList<>();

    public String getMatriculaVelha() {return matriculaVelha;}
    public String getNomeVelho() {return nomeVelho;}
    public String getSemestreAtual() {return semestreAtual;}
    public String getCursoVelho () {return cursoVelho;}
    public String getCondicao () {return condicao;}
    public String getNomeProf() {return professor;}

    public List<String> getNomesProfs () {return nomesProfessores;}
    public List<String> getmatriculadosList() {return matriculadosList;}
    public List<String> getTurnosList() {return turnosList;}
    public List<String> getAvaliacaoList() {return avaliacaoList;}
    public List<String> getHorariosList() {return horariosList;}
    public List<String> getMateriasCursando () {return materiasFazendo;}
    public List<String> getMateriasFinalizadas() {return materiasFinalizadas;}



    //CADASTRO ALUNO
    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso, boolean condicao, String materiasDone,
     String materiasCursando, String nomeProf, String turnoProf, String horarioProf, String tipoAva, String semestre, String mencoes) {
        String dadosAluno = String.join("\n", 
        "---------------------",
        "ALUNO ESPECIAL: " + (condicao ? "SIM" : "NÃO"),
        "MATRICULA: " + matricula,
        "NOME: " + nome,
        "SEMESTRE: " + semestre,
        "CURSO: " + curso,
        "MATERIAS FINALIZADAS: " + materiasDone,
        "MENÇÕES FINAIS: "+ mencoes,
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

    //Buscar Dados
    public void BuscarDados(String caminhoArquivo, String dado, String dadoExtra) {
        
        materiasFazendo.clear();
        materiasFinalizadas.clear();
        materiasCursando = null;
        matriculaVelha = null;
        nomeVelho = null;
        cursoVelho = null;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) { 
            if("alunos.txt".equals(caminhoArquivo)) {
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
                                } 
                                else if (l.trim().toUpperCase().startsWith("NOME:")) {
                                    nomeVelho = l.substring(l.indexOf(":") + 1).trim();
                                } 
                                else if (l.trim().toUpperCase().startsWith("CURSO:")) {
                                    cursoVelho = l.substring(l.indexOf(":") + 1).trim();
                                }
                                else if (l.trim().toUpperCase().startsWith("ALUNO ESPECIAL:")) {
                                    condicao = l.substring(l.indexOf(":") + 1).trim();
                                }
                                else if (l.trim().toUpperCase().startsWith("SEMESTRE: ")) {
                                    semestreAtual = l.substring(l.indexOf(":")+1).trim();
                                }
                                else if (l.trim().toUpperCase().startsWith("MATERIAS CURSANDO:")) {
                                    materiasCursando = l.substring(l.indexOf(":") + 1).trim();
                                    String[] cursandoArray = materiasCursando.split(",");
                                    for(String materia : cursandoArray) {
                                        materiasFazendo.add(materia.trim());
                                    }
                                }
                                else if (l.toUpperCase().startsWith("MATERIAS FINALIZADAS:")) {
                                    String materias = l.substring(l.indexOf(":") + 1).trim();
                                    String[] materiasArray = materias.split(",");
                                    for (String materia : materiasArray) {
                                        materiasFinalizadas.add(materia.trim());
                                    }
                                }

                                if (matriculaVelha != null && nomeVelho != null && cursoVelho != null && condicao != null && !materiasFinalizadas.isEmpty() && !materiasFazendo.isEmpty()) { // TCHAU LOOP AMEM
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
            } 
            else if ("turmas.txt".equals(caminhoArquivo)) {
                avaliacaoList.clear();
                turnosList.clear();
                horariosList.clear();
                nomesProfessores.clear();
                matriculadosList.clear();

                String turno = null;
                String nomeProf = null;
                String horario = null;
                String avaliacao = null;
                String matriculados = null;

                String linha;
                StringBuilder blocoProf = new StringBuilder();
                boolean encontrou = false;

                //não atualiza a lista de alunos na turma.

                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("---------------------")) {
                        if (blocoProf.toString().toLowerCase().contains(dado.toLowerCase())) {
                            encontrou = true;
                            System.out.println("\nProfessor encontrado:\n" + blocoProf.toString());
                                
                            String[] linhas = blocoProf.toString().split("\n");
                            for (String l : linhas) {
                                if (l.trim().toUpperCase().startsWith("TURNO:")) {
                                    turno = l.substring(l.indexOf(":") + 1).trim();
                                    String[] turnoArray = turno.split(",");
                                    for(String t : turnoArray) {
                                        turnosList.add(t.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("NOME PROFESSOR:")) {
                                    professor = l.substring(l.indexOf(":") + 1).trim();
                                    String[] nomeArray = professor.split(",");
                                    for(String t : nomeArray) {
                                        nomesProfessores.add(t.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("HORARIO:")) {
                                    horario = l.substring(l.indexOf(":") + 1).trim();
                                    String[] horariosArray = horario.split(",");
                                    for(String hora : horariosArray) {
                                        horariosList.add(hora.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("ALUNOS MATRICULADOS:")) {
                                    matriculados = l.substring(l.indexOf(":") + 1).trim();
                                    String[] matriculadosArray = matriculados.split(",");
                                    for(String mat : matriculadosArray) {
                                        matriculadosList.add(mat.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("AVALIAÇÃO:")) {
                                    avaliacao = l.substring(l.indexOf(":") + 1).trim();
                                    String[] avaliacosArray = avaliacao.split(",");
                                    for(String ava : avaliacosArray) {
                                        avaliacaoList.add(ava.trim());
                                    }
                                }
                                if (turno != null && horario != null && avaliacao != null && matriculados != null) {
                                }
                            }
                            break;
                        }
                        blocoProf.setLength(0); 
                    } else {
                        blocoProf.append(linha).append("\n");
                    }
                }
                if(!encontrou){
                    System.out.println("Professor não encontrado.");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
} 

