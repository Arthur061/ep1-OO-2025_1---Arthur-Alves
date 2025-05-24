import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class buscarDados {
    Scanner sc = new Scanner(System.in);
    StringBuilder novoConteudo = new StringBuilder();
    StringBuilder blocoAluno = new StringBuilder();
    boolean encontrou = false; String linha; String dado;

    //VARIAVEIS ALUNO
    private String matriculaVelha; private String nomeVelho; private String cursoVelho; private String condicao; private String materiasCursando;
    private String semestreAtual; String professor; private String materiasFall = null; private String mencao; private String horariosCursando;

    // VARIAVEIS PROFESSOR
    private String turno = null; private String nomeProf = null; private String horario = null;
    private String avaliacao = null; private String matriculados = null; private String materia = null; private String cargaH = null;

    //GETTERS ALUNO
    public String getMatriculaVelha() {return matriculaVelha;}
    public String getNomeVelho() {return nomeVelho;}
    public String getSemestreAtual() {return semestreAtual;}
    public String getCursoVelho () {return cursoVelho;}
    public String getCondicao () {return condicao;}

    public List<String> getMateriasCursando () {return materiasFazendo;}
    public List<String> getMateriasFinalizadas() {return materiasFinalizadas;}
    public List<String> getHorariosAluno() {return horariosAlunoList;}
    public List<String> getMateriasreprovadas() {return materiasReprovadasList;}
    public List<String> getTurnosAluno() {return turnosAluno;}
    public List<String> getNomeProfessores() {return NomeProfessores;}
    public List<String> getAvaliacoesAluno() {return avaliacoesAluno;}

    // LISTAS ALUNO
    private final List<String> materiasFinalizadas = new ArrayList<>();
    private final List<String> materiasFazendo = new ArrayList<>();
    private final List<String> materiasReprovadasList = new ArrayList<>();
    private final List<String> horariosAlunoList = new ArrayList<>();
    private final List<String> turnosAluno = new ArrayList<>(); 
    private final List<String> NomeProfessores = new ArrayList<>();
    private final List<String> avaliacoesAluno = new ArrayList<>();

    //LISTAS PROFESSOR
    private final List<String> turnosList = new ArrayList<>();
    private final List<String> avaliacaoList = new ArrayList<>();
    private final List<String> horariosList = new ArrayList<>();
    private final List<String> nomesProfessores = new ArrayList<>();
    private final List<String> matriculadosList = new ArrayList<>();
    private final List<String> materiaList = new ArrayList<>();
    private final List<String> cargaHorariaList = new ArrayList<>();
    private final List<String> mencaoFinaList = new ArrayList<>();
    private final List<String> modoList = new ArrayList<>();
    private final List<String> salaList = new ArrayList<>();

    // GETTERS PROFESSOR
    public String getNomeProf() {return professor;}

    public List<String> getMencao() {return mencaoFinaList;}
    public List<String> getCargaH() {return cargaHorariaList;}
    public List<String> getmateriaProf() {return materiaList;}
    public List<String> getNomesProfs () {return nomesProfessores;}
    public List<String> getmatriculadosList() {return matriculadosList;}
    public List<String> getTurnosList() {return turnosList;}
    public List<String> getAvaliacaoList() {return avaliacaoList;}
    public List<String> getHorariosList() {return horariosList;}
    public List<String> getModoProf() {return modoList;}
    public List<String> getSalaprof() {return salaList;}

    public void BuscarDados(String caminhoArquivo, String dado, String dadoExtra) {
        
        materiasFazendo.clear();
        materiasFinalizadas.clear();
        materiasReprovadasList.clear();
        materiasCursando = null;
        matriculaVelha = null;
        nomeVelho = null;
        cursoVelho = null;
        mencao = null;
        horariosCursando = null;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) { 
            if("alunos.txt".equals(caminhoArquivo) || "boletimAlunos.txt".equals(caminhoArquivo)) {
                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("---------------------")) {
                        if (blocoAluno.toString().toLowerCase().contains(dado.toLowerCase())) {
                            encontrou = true;
                            System.out.println("\nAluno encontrado:\n" + blocoAluno.toString());
                                
                            // ARMAZENA OS DADOS PARA FUTURAS ALTERAÇÕES
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
                                else if (l.trim().toUpperCase().startsWith("HORARIO:")) {
                                    horariosCursando = l.substring(l.indexOf(":") + 1).trim();
                                    String[] horariosArray = horariosCursando.split(",");
                                    for (String horario : horariosArray) {
                                        horariosAlunoList.add(horario.trim());
                                    }
                                }

                                else if (l.toUpperCase().startsWith("MATERIAS FINALIZADAS:")) {
                                    String materias = l.substring(l.indexOf(":") + 1).trim();
                                    String[] materiasArray = materias.split(",");
                                    for (String materia : materiasArray) {
                                        materiasFinalizadas.add(materia.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("MENÇÕES FINAIS:")) {
                                    mencao = l.substring(l.indexOf(":") + 1).trim();
                                    String[] mencaoArray = mencao.split(",");
                                    for(String m : mencaoArray) {
                                        mencaoFinaList.add(m.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("MATERIAS REPROVADAS:")) {
                                    materiasFall = l.substring(l.indexOf(":") + 1).trim();
                                    String[] materiasFallArray = materiasFall.split(",");
                                    for (String mFall : materiasFallArray) {
                                        materiasReprovadasList.add(mFall.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("NOME PROFESSOR:")) {
                                    String[] professores = l.substring(l.indexOf(":") + 1).split(",");
                                    for (String p : professores) {
                                        NomeProfessores.add(p.trim());
                                    }
                                } else if (l.trim().toUpperCase().startsWith("TURNO:")) {
                                    String[] turnos = l.substring(l.indexOf(":") + 1).split(",");
                                    for (String t : turnos) {
                                        turnosAluno.add(t.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("TIPO AVALIAÇÃO:")) {
                                    String[] avaliacoes = l.substring(l.indexOf(":") + 1).split(",");
                                    for (String a : avaliacoes) {
                                        avaliacoesAluno.add(a.trim());
                                    }
                                }
                                if (matriculaVelha != null && nomeVelho != null && cursoVelho != null && condicao != null && !materiasFinalizadas.isEmpty() && !materiasFazendo.isEmpty() &&
                                 mencaoFinaList.isEmpty()) { // TCHAU LOOP AMEM
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
                    System.out.println("Aluno não encontrado.");
                }
            } 
            else if ("turmas.txt".equals(caminhoArquivo)) {
                avaliacaoList.clear();
                turnosList.clear();
                horariosList.clear();
                nomesProfessores.clear();
                matriculadosList.clear();
                mencaoFinaList.clear();

                String linha;
                StringBuilder blocoProf = new StringBuilder();
                boolean encontrou = false;

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
                                if (l.trim().toUpperCase().startsWith("NOME DA DISCIPLINA:")) {
                                    materia = l.substring(l.indexOf(":") + 1).trim();
                                    String[] materiaArray = materia.split(",");
                                    for(String t : materiaArray) {
                                        materiaList.add(t.trim());
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
                                else if (l.trim().toUpperCase().startsWith("CARGA HORARIA:")) {
                                    cargaH = l.substring(l.indexOf(":") + 1).trim();
                                    String [] cargaArray = cargaH.split(",");
                                    for (String carga : cargaArray) {
                                        cargaHorariaList.add(carga.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("AVALIAÇÃO:")) {
                                    avaliacao = l.substring(l.indexOf(":") + 1).trim();
                                    String[] avaliacosArray = avaliacao.split(",");
                                    for(String ava : avaliacosArray) {
                                        avaliacaoList.add(ava.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("MODO:")) {
                                    String modo = l.substring(l.indexOf(":") + 1).trim();
                                    String[] modosArray = modo.split(",");
                                    for (String m : modosArray) {
                                        modoList.add(m.trim());
                                    }
                                }
                                else if (l.trim().toUpperCase().startsWith("SALA:")) {
                                    String sala = l.substring(l.indexOf(":") + 1).trim();
                                    String[] salasArray = sala.split(",");
                                    for (String s : salasArray) {
                                        salaList.add(s.trim());
                                    }
                                }
                                if (turno != null && horario != null && avaliacao != null && matriculados != null && mencao != null) {
                                break;}
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
