import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LancarNotas {
    buscarDados buscar = new buscarDados();

    // LANCAMENTO DE NOTAS
    public void lancamentosNotasFrequencia() throws IOException {
        int ava = 0;
        double notaP1; double listas;
        double notaP2; double seminario;
        double notaP3; double notaFinal = 0;
        String situacao = null;
        String mencaoStr = null;

        System.out.println("Você esta na aba de notas!");
        int matricula = ValidarLetrasNum.verificarMatricula(-1);

        buscar.BuscarDados("alunos.txt", String.valueOf(matricula), null);
        String nomeAluno = buscar.getNomeVelho();

        /// testeeeee
        List<String> mencoesFinais = buscar.getMencao();
        System.out.println("menções: "+mencoesFinais);

        String professor = ValidarLetrasNum.lerTextoValido("Olá "+buscar.getNomeVelho()+", qual professor você deseja fazer o lançamento de nota: ");
        buscar.BuscarDados("turmas.txt", professor.toUpperCase(), "AVALIAÇÃO:");

        List<String> metodoAva = buscar.getAvaliacaoList();
        String metodoStr = String.join(", ", metodoAva);
        List<String> materia = buscar.getmateriaProf();
        String materiaStr = String.join(", ", materia);
        List<String> cargaH = buscar.getCargaH();
        String cargaHstr = String.join(",", cargaH);

        List<String> metodos = Arrays.asList(
            "(P1 + P2 * 2 + P3 * 3 + L + S) / 8",
            "(P1 + P2 + P3 + L + S) / 5");

        if (metodos.get(0).equals(metodoStr)) {ava = 1;}
        else if (metodos.get(1).equals(metodoStr)) { ava = 2;}
        else {System.out.println("Método de avaliação desconhecido."); lancamentosNotasFrequencia();}

        switch (ava) {
            case 1 -> {
                notaP1 = ValidarLetrasNum.lerDouble("Nota P1: ");
                notaP2 = ValidarLetrasNum.lerDouble("Nota P2: ");
                notaP2 *= 2;
                notaP3 = ValidarLetrasNum.lerDouble("Nota P3: ");
                notaP3 *= 3;
                listas = ValidarLetrasNum.lerDouble("Nota Listas: ");
                seminario = ValidarLetrasNum.lerDouble("Nota Seminario: ");
                notaFinal = (notaP1 + notaP2 + notaP3 + listas + seminario)/8;
                System.out.printf("Sua nota final é %.2f\n", notaFinal);
            }
            case 2 -> {
                notaP1 = ValidarLetrasNum.lerDouble("Nota P1: ");
                notaP2 = ValidarLetrasNum.lerDouble("Nota P2: ");
                notaP3 = ValidarLetrasNum.lerDouble("Nota P3: ");
                listas = ValidarLetrasNum.lerDouble("Nota Listas: ");
                seminario = ValidarLetrasNum.lerDouble("Nota Seminario: ");
                notaFinal = (notaP1 + notaP2 + notaP3 + listas + seminario)/5;
                System.out.printf("Sua nota final é %.2f\n", notaFinal);
            }
        }
        System.out.println("A materia "+materiaStr.toUpperCase() +" tem uma carga horaria de "+cargaHstr);

        int cargaHoraria = Integer.parseInt(cargaHstr.replace("h", "").trim());

        int totalDias = cargaHoraria / 2; // TOTAL DE DIAS DE AULA (CADA AULA TEM 2H)
        double presencaHoras = cargaHoraria * 0.75; // PRESENÇA EM HORAS
        int presencaDias = (int) Math.ceil(presencaHoras / 2.0); // PRESENÇA EM DIAS (ARREDONDADO PRA CIMA)
        int maxFaltas = totalDias - presencaDias;

        
        mencaoStr = Avaliador.obterMencao(notaFinal);
        int faltas = ValidarLetrasNum.lerInteiro("Quantas faltas você tem na materia de "+materiaStr+": ");
        if (faltas > maxFaltas) {
            System.out.println("Infelizmente você está reprovado por falta.");
            situacao = "REPROVADO POR FALTA";
            mencaoStr = "SR";
            processarReprovacao(String.valueOf(matricula), materiaStr, mencaoStr);
        } else if (notaFinal < 5) {
            System.out.println("Você não tem a nota mínima para ser aprovado...");
            situacao = "REPROVADO POR NOTA";
            processarReprovacao(String.valueOf(matricula), materiaStr, mencaoStr);
        } else {
            System.out.println("Parabéns! Você foi aprovado :)");
            situacao = "APROVADO";
            try {
                processarAprovacao(String.valueOf(matricula), materiaStr, mencaoStr);
            } catch (IOException e) {
                System.out.println("Erro ao processar aprovação disciplina: " + e.getMessage());
            }
        }
        
    System.out.println("Menção final: " + mencaoStr);

    gravarAvaliacao(nomeAluno, String.valueOf(matricula), professor, materiaStr, situacao, mencaoStr);
                    
    }

    // GRAVAR DESEMPENHO DO ALUNO
    public static void gravarAvaliacao(String nomeAluno, String matricula, String nomeProfessor, String materia, String situacao, String notaFinal) {
        String nomeArquivo = "avaliacoesNotas.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            String desempenhoAluno = String.join("\n",
            "---------------------",
            "NOME ALUNO: "+ nomeAluno,
            "MATRICULA: "+ matricula,
            "NOME PROFESSOR: "+ nomeProfessor,
            "NOME DISCIPLINA: "+ materia,
            "SITUAÇÃO: "+ situacao,
            "MENÇÃO FINAL: "+ notaFinal,
            "---------------------"
            );

            writer.write(desempenhoAluno);
            writer.newLine();
            System.out.println("Desempenho gravado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }

    // DAR MENÇÃO
    public class Avaliador {

        public static String obterMencao(double notaFinal) {
            if (notaFinal >= 9.0 && notaFinal <= 10.0) {
                return "SS";
            } else if (notaFinal >= 7.0) {
                return "MS";
            } else if (notaFinal >= 5.0) {
                return "MM";
            } else if (notaFinal >= 3.0) {
                return "MI";
            } else if (notaFinal >= 0.1) {
                return "II";
            } else {
                return "SR";
            }
        }
    }

    // ALUNO APROVADO
    public void processarAprovacao(String matricula, String materiaAprovada, String mencaoFinal) throws IOException {
        Path caminho = Paths.get("alunos.txt");
        buscar.BuscarDados("alunos.txt", matricula, null);

        List<String> materiasCursando = buscar.getMateriasCursando();
        List<String> professores = buscar.getNomesProfs();
        List<String> turnos = buscar.getTurnosList();
        List<String> horarios = buscar.getHorariosList();
        List<String> avaliacoes = buscar.getAvaliacaoList();
        List<String> materiasFinalizadas = buscar.getMateriasFinalizadas();
        List<String> mencoesFinais = buscar.getMencao();

        List<String> linhas = Files.readAllLines(caminho);

        int indice = -1;
        for (int i = 0; i < materiasCursando.size(); i++) {
            if (materiasCursando.get(i).equalsIgnoreCase(materiaAprovada.trim())) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            System.out.println("Matéria não encontrada entre as matérias cursando.");
            return;
        }

        materiasFinalizadas.add(materiasCursando.get(indice));
        mencoesFinais.add(mencaoFinal.trim());

        materiasCursando.remove(indice);
        professores.remove(indice);
        turnos.remove(indice);
        horarios.remove(indice);
        avaliacoes.remove(indice);
    
        // FAZ ADD AS INFO
        boolean alunoEncontrado = false;
        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            
            if (linha.startsWith("MATRICULA:")) {
                alunoEncontrado = linha.contains(matricula);
            }
            if (alunoEncontrado) {
                if (linha.startsWith("MATERIAS CURSANDO:")) {
                    linhas.set(i, "MATERIAS CURSANDO: " + String.join(", ", materiasCursando));

                } else if (linha.startsWith("MATERIAS FINALIZADAS:")) {
                    linhas.set(i, "MATERIAS FINALIZADAS: " + String.join(", ", materiasFinalizadas));

                } else if (linha.startsWith("MENÇÕES FINAIS:")) {
                    linhas.set(i, "MENÇÕES FINAIS: " + String.join(", ", mencoesFinais));

                } else if (linha.startsWith("NOME PROFESSOR:")) {
                    linhas.set(i, "NOME PROFESSOR: " + String.join(", ", professores));
                    
                } else if (linha.startsWith("TURNO:")) {
                    linhas.set(i, "TURNO: " + String.join(", ", turnos));

                } else if (linha.startsWith("HORARIO:")) {
                    linhas.set(i, "HORARIO: " + String.join(", ", horarios));

                } else if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    linhas.set(i, "TIPO AVALIAÇÃO: " + String.join(", ", avaliacoes));
                }
                if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    break; 
                }
            }
        }
        Files.write(caminho, linhas, StandardCharsets.UTF_8);
        System.out.println("Dados atualizados para a matrícula: " + matricula);
    }

    // SE O CAMARADA REPROVOU
    public void processarReprovacao(String matricula, String materiaReprovada, String mencaoFinal) throws IOException {
        String formatado = materiaReprovada.toUpperCase() + " (" + mencaoFinal + ")";
        
        buscar.BuscarDados("alunos.txt", String.valueOf(matricula), null);

        List<String> materiasReprovadas = buscar.getMateriasreprovadas();
        List<String> materia = buscar.getMateriasCursando();
        List<String> professor = buscar.getNomesProfs();
        List<String> turno = buscar.getTurnosList();
        List<String> horario = buscar.getHorariosList();
        List<String> avaliacao = buscar.getAvaliacaoList();

        System.out.println("Item a ser adicionado em materias Reprovadas: " + formatado); // check
        materiasReprovadas.add(formatado);

        List<String> linhas = Files.readAllLines(Paths.get("alunos.txt"));

        int indice = -1;
        for (int i = 0; i < professor.size(); i++) {
            System.out.println("Comparando: '" + materia.get(i).trim() + "' com '" + materiaReprovada.trim().toUpperCase() + "'");
            if (materia.get(i).trim().equalsIgnoreCase(materiaReprovada.trim())) {
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
            System.out.println(" Materia '" + materiaReprovada.toUpperCase() + "' não encontrada entre as matérias cursando.");
            return;
        }

        boolean alunoEncontrado = false;
        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            
            if (linha.startsWith("MATRICULA:")) {
                alunoEncontrado = linha.contains(matricula);
            }
            if (alunoEncontrado) {
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
                else if (linha.startsWith("MATERIAS REPROVADAS:")) {
                    linhas.set(i, "MATERIAS REPROVADAS: " + String.join(", ", materiasReprovadas));
                }
                if (linha.startsWith("TIPO AVALIAÇÃO:")) {
                    break; 
                }
            }
        }
        Files.write(Paths.get("alunos.txt"), linhas);
        System.out.println("Processo da disciplina " + materiaReprovada.toUpperCase() + " foi finalizado");
    }
}
