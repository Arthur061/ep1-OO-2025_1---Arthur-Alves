import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

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
    public static void MatriculaTXT (String caminhoArquivo, String nome, String matricula, String curso, String condicao, String materiasDone,
    String disciplina, int capacidade, String professor, String turno, String horario, String avaliacao) {
        String dadosMatricula = String.join("\n",
        "---------------------",
        "NOME ALUNO: " + nome,
        "MATRICULA: " + matricula,
        "CURSO: " + curso,
        "ALUNO ESPECIAL: " + condicao,
        "MATERIAS FINALIZADAS: "+ materiasDone,
        "",
        "DISCIPLINAS CURSANDO: "+ disciplina,
        "CAPACIDADE: 0/"+ capacidade,
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
     
    // GARANTIR QUE HAJA CAPACIDADE
    public static boolean atualizarCapacidade(String caminhoArquivo, String nomeDisciplina) {
    try {
        List<String> linhas = Files.readAllLines(Paths.get(caminhoArquivo));
        for (int i = 0; i < linhas.size(); i++) {
            if (linhas.get(i).equalsIgnoreCase("NOME DA DISCIPLINA: " + nomeDisciplina)) {
                // Avança até encontrar a linha de capacidade (geralmente 2 linhas depois)
                for (int j = i; j < linhas.size(); j++) {
                    if (linhas.get(j).startsWith("CAPACIDADE:")) {
                        String[] partes = linhas.get(j).substring(11).split("/");
                        int atuais = Integer.parseInt(partes[0].trim());
                        int max = Integer.parseInt(partes[1].trim());

                        if (atuais >= max) {
                            System.out.println("Não há vagas disponíveis nesta disciplina.");
                            return false;
                        }

                        atuais++; // Incrementa matriculados
                        linhas.set(j, "CAPACIDADE: " + atuais + "/" + max);

                        // Salva o arquivo atualizado
                        Files.write(Paths.get(caminhoArquivo), linhas);
                        System.out.println("Matrícula realizada com sucesso! (" + atuais + "/" + max + " alunos)");
                        return true;
                    }
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao atualizar capacidade: " + e.getMessage());
    }

    System.out.println("Disciplina não encontrada.");
    return false;
}

}

