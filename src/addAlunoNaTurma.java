import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class addAlunoNaTurma {
    public void adicionarAlunoNaTurma(String caminhoTurmas, String nomeDisciplina, String nomeAluno) {
        try {
            File arquivo = new File(caminhoTurmas);
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            StringBuilder novoConteudo = new StringBuilder();

            String linha;
            boolean dentroBloco = false;
            boolean turmaAlvo = false;
            StringBuilder blocoAtual = new StringBuilder();

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("---------------------")) {
                    if (turmaAlvo) {
                        String blocoAtualizado = atualizarAlunosMatriculados(blocoAtual.toString(), nomeAluno);
                        novoConteudo.append(blocoAtualizado);
                    } else {
                        novoConteudo.append(blocoAtual.toString());
                    }
                    blocoAtual.setLength(0);
                    blocoAtual.append(linha).append("\n");
                    dentroBloco = true;
                    turmaAlvo = false;
                } else {
                    if (dentroBloco) {
                        blocoAtual.append(linha).append("\n");
                        if (linha.toUpperCase().contains("NOME DA DISCIPLINA:") && linha.toUpperCase().contains(nomeDisciplina.toUpperCase())) {
                            turmaAlvo = true;
                        }
                    } else {
                        novoConteudo.append(linha).append("\n");
                    }
                }
            }

            if (turmaAlvo) {
                String blocoAtualizado = atualizarAlunosMatriculados(blocoAtual.toString(), nomeAluno);
                novoConteudo.append(blocoAtualizado);
            } else {
                novoConteudo.append(blocoAtual.toString());
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
            bw.write(novoConteudo.toString());
            bw.close();

            System.out.println("Aluno adicionado à turma com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ATUALIZA A MATERIA DO PROFESSOR
    private String atualizarAlunosMatriculados(String bloco, String nomeAluno) {
        String[] linhas = bloco.split("\n");
        StringBuilder blocoFinal = new StringBuilder();

        for (String linha : linhas) {
            if (linha.toUpperCase().startsWith("ALUNOS MATRICULADOS:")) {
                String existentes = linha.substring(linha.indexOf(":") + 1).trim();
                if (!existentes.toUpperCase().contains(nomeAluno.toUpperCase())) {
                    existentes += ", " + nomeAluno;
                }
                blocoFinal.append("ALUNOS MATRICULADOS: ").append(existentes).append("\n");
            } else {
                blocoFinal.append(linha).append("\n");
            }
        }
        return blocoFinal.toString();
    }
}
