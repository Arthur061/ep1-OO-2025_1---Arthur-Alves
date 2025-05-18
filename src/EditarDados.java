import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 import java.nio.charset.StandardCharsets;
 import java.nio.file.Files;
 import java.nio.file.Path;
import java.nio.file.Paths;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;

public class EditarDados extends DadosAlunosTXT {
    buscarDados buscarInfo = new buscarDados();

    //VERIFICA O .TXT ATRAS DOS DADOS DO ALUNO
    public void editarDados(String parametroBuscar, String campo, String novoValor) {
    try {
        Path arquivo = Paths.get("alunos.txt");

        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        List<String> novasLinhas = new ArrayList<>();

        StringBuilder blocoAtual = new StringBuilder();
        boolean alunoEncontrado = false;
        boolean campoAlterado = false;

        for (String linha : linhas) {
            if (linha.startsWith("---------------------")) {
                if (blocoAtual.length() > 0) { 
                    ResultadoProcessamento resultado = processarBloco(blocoAtual, parametroBuscar, campo, novoValor); // MANDA PARA O ABATE
                    alunoEncontrado = alunoEncontrado || resultado.alunoEncontrado;
                    campoAlterado = campoAlterado || resultado.campoAlterado;
                    novasLinhas.addAll(resultado.novasLinhas);
                }
                    novasLinhas.add(linha);
                    blocoAtual.setLength(0); 
            } else {
                blocoAtual.append(linha).append("\n");
            }
        }

        if (alunoEncontrado) { //HORA DO VAMO VER 
            Files.write(arquivo, novasLinhas, StandardCharsets.UTF_8);
            System.out.println(campoAlterado 
                ? " Dados atualizados com sucesso!" 
                : " Campo não encontrado para o aluno.");
                System.out.println("\n Dados atuais do aluno: ");
                buscarInfo.BuscarDados("alunos.txt",novoValor, null);

        } else {
            System.out.println(" Aluno '" + parametroBuscar + "' não encontrado.");
        }

    } catch (IOException e) {
        System.out.println(" Erro ao editar: " + e.getMessage());
    }
}

    //CLASSE QUE RESULTA NO DADO EDITADO
    class ResultadoProcessamento { 
        boolean alunoEncontrado;
        boolean campoAlterado;
        List<String> novasLinhas;

        ResultadoProcessamento(boolean alunoEncontrado, boolean campoAlterado, List<String> novasLinhas) {
            this.alunoEncontrado = alunoEncontrado;
            this.campoAlterado = campoAlterado;
            this.novasLinhas = novasLinhas;

        }
    }

    // ONDE A MAGICA ACONTECE
    public ResultadoProcessamento processarBloco(StringBuilder bloco, String parametroBuscar, String campo, String novoValor) {

        String blocoStr = bloco.toString();
        String[] linhasBloco = blocoStr.split("\n");
        List<String> resultadoLinhas = new ArrayList<>();

        boolean registroEncontrado = false;
        boolean campoAlterado = false;
    
        //BUSCANDO REGISTRO DO STUDENT BRO
        for (String linha : linhasBloco) {
            if (linha.trim().toUpperCase().startsWith("MATRICULA:")) {
                String valor = linha.substring(linha.indexOf(":") + 1).trim();
                if (parametroBuscar.equalsIgnoreCase(valor)) {
                    registroEncontrado = true;
                    break;
                }
            } else if (linha.trim().toUpperCase().startsWith("NOME:")) {
                String valor = linha.substring(linha.indexOf(":") + 1).trim();
                if (parametroBuscar.equalsIgnoreCase(valor)) {
                    registroEncontrado = true;
                    break;
                }
                
            }
            
        }
        // EDIÇÃOOO
        if (registroEncontrado) {// Se for o aluno, alterar dado selecionado
            for (String linha : linhasBloco) {
                // Verifica se os campo existem (nome, curso, ...)
                if (linha.trim().toUpperCase().startsWith(campo.toUpperCase()+": ")) {
                    String prefixo = linha.substring(0, linha.indexOf(":") + 1);

                    resultadoLinhas.add(prefixo +" " + novoValor);
                    campoAlterado = true;
                } else {
                    resultadoLinhas.add(linha);
                }
            }
        } else {// Mantém o bloco original se aluno não foi encontrado
            resultadoLinhas.addAll(Arrays.asList(linhasBloco));
            }
            return new ResultadoProcessamento(registroEncontrado, campoAlterado, resultadoLinhas);
            
        }


    // SAIDAS PARA A EDIÇÃO
    public void dado(int i, int matricula) {
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula), null);

        switch (i) {
            // EDITAR NOME
            case 1 ->EditarNome(matricula);

            // EDITAR CURSO
            case 2 -> EditarCurso(matricula);
            }
        
        
    }

    // EDITAR NOME
    public void EditarNome (int matricula) {
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula), null);

        System.out.println("Procurando pela matricula " + matricula + " na lista...");
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula), null);
        String novoNome;
        System.out.println("O nome atual vinculado a sua matricula é "+buscarInfo.getNomeVelho());
        novoNome = ValidarLetrasNum.lerTextoValido("Novo nome: ");

        editarDados(buscarInfo.getMatriculaVelha(), "NOME", novoNome.toUpperCase());

    }
    
    // EDITAR CURSO
    public void EditarCurso (int matricula) {
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula),null);

        System.out.println("Procurando pela matricula " + matricula + " na lista...");
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula),null);

        String novoCurso;
        String cursoVelho = buscarInfo.getCursoVelho();
        System.out.println("O curso atual vinculado à sua matrícula é " + cursoVelho);
        novoCurso = ValidarLetrasNum.lerTextoValido("Digite seu novo curso: ");
        editarDados(buscarInfo.getMatriculaVelha(), "CURSO", novoCurso.toUpperCase());

    }

    // TRANCAR SEMESTRE
    public void trancarSemestre (int matricula) {
        System.out.println("Procurando pela matricula " + matricula + " na lista...");
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula),null);

        String semestreAtual = buscarInfo.getSemestreAtual();
        System.out.println("Você está atualmente no "+semestreAtual+"º semestre.");
        System.out.println("SEU SEMESTRE ESTÁ SENDO TRANCADO...");

        editarDados(String.valueOf(matricula), "SEMESTRE", "TRANCADO");
        editarDados(String.valueOf(matricula), "MATERIAS CURSANDO", "TRANCADO");
        editarDados(String.valueOf(matricula), "NOME PROFESSOR", "");
        editarDados(String.valueOf(matricula), "TURNO", "");
        editarDados(String.valueOf(matricula), "HORARIO", "");
        editarDados(String.valueOf(matricula), "TIPO AVALIAÇÃO", "");

        System.out.println("SUA MATRICULA ESTÁ ATUALMENTE TRANCADA!");
        buscarInfo.BuscarDados("alunos.txt", String.valueOf(matricula), null);
        
    }

    // DESTRANCAR SEMESTRE
    public void destrancarSemestre(int matricula) {
        System.out.println("Procurando pela matricula " + matricula + " na lista...");
        buscarInfo.BuscarDados("alunos.txt",String.valueOf(matricula),null);

        int semestreNovo = ValidarLetrasNum.lerInteiro("Qual o semestre que você trancou: ");

        editarDados(String.valueOf(matricula), "SEMESTRE", String.valueOf(semestreNovo));
        editarDados(String.valueOf(matricula), "MATERIAS CURSANDO", "");
        editarDados(String.valueOf(matricula), "NOME PROFESSOR", "");
        editarDados(String.valueOf(matricula), "TURNO", "");
        editarDados(String.valueOf(matricula), "HORARIO", "");
        editarDados(String.valueOf(matricula), "TIPO AVALIAÇÃO", "");

        buscarInfo.BuscarDados("alunos.txt", String.valueOf(matricula), null);
        System.out.println("Bem-vindo de volta! Curta seu novo semestre!");

    }

    // TRANCAR DISCIPLINA
    public void trancarDisciplina (String parametro, int matricula) throws IOException {
        
        buscarInfo.BuscarDados("alunos.txt", String.valueOf(matricula), null);

        List<String> materia = buscarInfo.getMateriasCursando();
        List<String> professor = buscarInfo.getNomesProfs();
        List<String> turno = buscarInfo.getTurnosList();
        List<String> horario = buscarInfo.getHorariosList();
        List<String> avaliacao = buscarInfo.getAvaliacaoList();


        List<String> linhas = Files.readAllLines(Paths.get("alunos.txt"));

        int indice = -1;
        for (int i = 0; i < professor.size(); i++) {
            System.out.println("Comparando: '" + materia.get(i).trim() + "' com '" + parametro.trim().toUpperCase() + "'");
            if (materia.get(i).trim().equalsIgnoreCase(parametro.trim())) {
                indice = i;
                materia.remove(indice);
                professor.remove(indice);
                turno.remove(indice);
                horario.remove(indice);
                avaliacao.remove(indice);
                break;
            }
        }

        if (indice == -1) {
            System.out.println(" Materia '" + parametro.toUpperCase() + "' não encontrada entre as matérias cursando.");
            return;
        }

        // REESCREVE OS DADOS SEM TUDO RELACIONADO AO PROF
        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            if (linha.startsWith("MATERIAS CURSANDO:")) {
                linhas.set(i, "MATERIAS CURSANDO: " + String.join(", ", materia));
            } else if (linha.startsWith("NOME PROFESSOR:")) {
                linhas.set(i, "NOME PROFESSOR: " + String.join(", ", professor));
            } else if (linha.startsWith("TURNO:")) {
                linhas.set(i, "TURNO: " + String.join(", ", turno));
            } else if (linha.startsWith("HORARIO:")) {
                linhas.set(i, "HORARIO: " + String.join(", ", horario));
            } else if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                linhas.set(i, "TIPO AVALIAÇÃO: " + String.join(", ", avaliacao));
            }
        }
        Files.write(Paths.get("alunos.txt"), linhas);
        System.out.println("Disciplina " + parametro.toUpperCase() + " foi trancada com sucesso.");
    }

    // CADASTRO DE NOVAS TURMAS
    public void adicionarNovasInfosAoAluno(String caminhoArquivo, String nomeAluno, String novaMateria, String novoProfessor,
                                       List<String> novosTurnos, List<String> novosHorarios, List<String> novasAvaliacoes, List<String> novosAlunos) {
        
        
        String caminhoTurmas = "turmas.txt";
        DadosTurmasTXT atualizador = new DadosTurmasTXT();                
        boolean returnAtualizador = atualizador.atualizarCapacidade(caminhoTurmas, novaMateria);                  
        if (!returnAtualizador) {
            System.out.println("Não há mais vagas disponíveis para a disciplina: " + novaMateria);
            return;
        }
                                        
        try {
            File arquivo = new File(caminhoArquivo);
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            StringBuilder novoConteudo = new StringBuilder();

            String linha;
            boolean dentroBloco = false;
            boolean alunoAlvo = false;
            StringBuilder blocoAtual = new StringBuilder();

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("---------------------")) {
                    if (alunoAlvo) {
                        String blocoAtualizado = acrescentarDadosNoBloco(blocoAtual.toString(), novaMateria, novoProfessor, novosTurnos, novosHorarios, novasAvaliacoes, novosAlunos);
                        novoConteudo.append(blocoAtualizado);
                    } else {
                        novoConteudo.append(blocoAtual.toString());
                    }
                    blocoAtual.setLength(0);
                    blocoAtual.append(linha).append("\n");
                    dentroBloco = true;
                    alunoAlvo = false;
                } else {
                    if (dentroBloco) {
                        blocoAtual.append(linha).append("\n");
                        if (linha.toUpperCase().contains("NOME:") && linha.toUpperCase().contains(nomeAluno.toUpperCase())) {
                            alunoAlvo = true;
                        }
                    } else {
                        novoConteudo.append(linha).append("\n");
                    }
                }
            }

            if (alunoAlvo) {
                String blocoAtualizado = acrescentarDadosNoBloco(blocoAtual.toString(), novaMateria, novoProfessor, novosTurnos, novosHorarios, novasAvaliacoes, novosAlunos);
                novoConteudo.append(blocoAtualizado);
            } else {
                novoConteudo.append(blocoAtual.toString());
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
            bw.write(novoConteudo.toString());
            bw.close();

            System.out.println("Novas informações adicionadas com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }  
    }

    private String acrescentarDadosNoBloco(String bloco, String novaMateria, String novoProfessor,
                                       List<String> novosTurnos, List<String> novosHorarios, List<String> novasAvaliacoes, List<String> novosAlunos) {
    String[] linhas = bloco.split("\n");
    String materias = "";
    String professores = "";
    String turnos = "";
    String horarios = "";
    String avaliacoes = "";
    String alunosMatriculados = "";

    StringBuilder blocoFinal = new StringBuilder();

    for (String linha : linhas) {
        if (linha.toUpperCase().startsWith("MATERIAS CURSANDO:")) {
            materias = linha.substring(linha.indexOf(":") + 1).trim();
            materias += ", " + novaMateria;
            blocoFinal.append("MATERIAS CURSANDO: ").append(materias).append("\n");
        }
         else if (linha.toUpperCase().startsWith("NOME PROFESSOR:")) {
            professores = linha.substring(linha.indexOf(":") + 1).trim();
            professores += ", " + novoProfessor;
            blocoFinal.append("NOME PROFESSOR: ").append(professores).append("\n");    
        }
         else if (linha.toUpperCase().startsWith("TURNO:")) {
            turnos = linha.substring(linha.indexOf(":") + 1).trim();
            turnos += ", " + String.join(", ", novosTurnos);
            blocoFinal.append("TURNO: ").append(turnos).append("\n");
        }
         else if (linha.toUpperCase().startsWith("HORARIO:")) {
            horarios = linha.substring(linha.indexOf(":") + 1).trim();
            horarios += ", " + String.join(", ", novosHorarios);
            blocoFinal.append("HORARIO: ").append(horarios).append("\n");
        }
         else if (linha.toUpperCase().startsWith("TIPO AVALIAÇÃO:")) {
            avaliacoes = linha.substring(linha.indexOf(":") + 1).trim();
            avaliacoes += ", " + String.join(", ", novasAvaliacoes);
            blocoFinal.append("TIPO AVALIAÇÃO: ").append(avaliacoes).append("\n");
        }
        else if (linha.toUpperCase().startsWith("ALUNOS MATRICULADOS:")) {
            alunosMatriculados = linha.substring(linha.indexOf(":") + 1).trim();
        
            if (!alunosMatriculados.isEmpty()) {
                alunosMatriculados += ", " + String.join(", ", novosAlunos);
            } else {
                alunosMatriculados = String.join(", ", novosAlunos);
            }
            blocoFinal.append("ALUNOS MATRICULADOS: ").append(alunosMatriculados).append("\n");
        }
             
        else {
            blocoFinal.append(linha).append("\n");
        }        
    }

    return blocoFinal.toString();
    }

}
