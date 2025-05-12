import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DadosTurmasTXT {

    // CADASTRAR TURMA NOVA
     public static void turmasTXT(String caminhoArquivo, String nomeProf, String nomeDisciplina, int codigo, int cargaH, 
     String preReq, int capacidade, String avaliacao, String turno, String sala, int horarioI, int horarioF, String modo)
      {
        String dadosTurma = String.join("\n", 
        "---------------------",
        "NOME PROFESSOR: " + nomeProf,
        "NOME DA DISCIPLINA: " + nomeDisciplina,
        "CODIGO: " + codigo,
        "CARGA HORARIA: " + cargaH+"h",
        "PRÉ-REQUISITO: "+ preReq,
        "CAPACIDADE: "+ capacidade,
        "AVALIAÇÃO: "+ avaliacao,
        "TURNO DA DISCIPLINA: "+ turno,
        "MODO: "+ modo,
        "SALA: "+ sala,
        "HORARIO: "+ horarioI+"h até "+horarioF+"h",
        "---------------------");

        try {
            Files.write(
                Paths.get(caminhoArquivo),
                (dadosTurma + "\n").getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            System.out.println("Turma salva com sucesso em " + caminhoArquivo);
            System.out.println("\nTurma cadastrada:\n");
            System.out.println(dadosTurma);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
     }

    // MATRICULAR ALUNO
    public static void MatriculaTXT (String caminhoArquivo, String nome, int matricula, String curso, String condicao,
    String disciplina, String professor, String turno, int horario, String avaliacao, String materiasDone, int alunosMatriculados, int capacidade) {
        String dadosMatricula = String.join("\n",
        "---------------------",
        "NOME ALUNO: " + nome,
        "MATRICULA: " + matricula,
        "CURSO: " + curso,
        "ALUNO ESPECIAL: " + condicao,
        "MATERIAS FINALIZADAS: "+ materiasDone,
        "",
        "NOME DISCIPLINA: "+ disciplina,
        "CAPACIDADE MAXIMA: "+ capacidade,
        "NOME PROFESSOR: " + professor,
        "TURNO: "+ turno,
        "HORARIO: "+ horario,
        "TIPO AVALIAÇÃO: "+ avaliacao,
        "---------------------");
        try {
            Files.write(
                Paths.get(caminhoArquivo),
                (dadosMatricula + "\n").getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            System.out.println("Turma salva com sucesso em " + caminhoArquivo);
            System.out.println("\nTurma cadastrada:\n");
            System.out.println(dadosMatricula);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }

    }
     
}

