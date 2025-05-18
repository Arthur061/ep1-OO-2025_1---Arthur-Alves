import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LancarNotas {
    buscarDados buscar = new buscarDados();
    AvaliacoesFrequencia avaliar = new AvaliacoesFrequencia();

    // LNACAMENTO DE NOTAS
    public void lancamentosNotasFrequencia() throws IOException {
        int ava = 0;
        double notaP1; double listas;
        double notaP2; double seminario;
        double notaP3; double notaFinal = 0;
        String situacao = null;
        String mencaoStr = null;

        System.out.println("Você esta na aba de notas!");
        ValidarLetrasNum buscarMat = new ValidarLetrasNum();
        int matricula = buscarMat.verificarMatricula(-1);

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
            avaliar.processarReprovacao(String.valueOf(matricula), materiaStr, mencaoStr);
        } else if (notaFinal < 5) {
            System.out.println("Você não tem a nota mínima para ser aprovado...");
            situacao = "REPROVADO POR NOTA";
            avaliar.processarReprovacao(String.valueOf(matricula), materiaStr, mencaoStr);
        } else {
            System.out.println("Parabéns! Você foi aprovado :)");
            situacao = "APROVADO";
            try {
                avaliar.processarAprovacao(String.valueOf(matricula), materiaStr, mencaoStr);
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
}
