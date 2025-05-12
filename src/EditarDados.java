import java.io.IOException;
 import java.nio.charset.StandardCharsets;
 import java.nio.file.Files;
 import java.nio.file.Path;
import java.nio.file.Paths;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;

public class EditarDados extends DadosAlunosTXT {

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
                if (blocoAtual.length() > 0) { // caso exisat bloco (----)
                    ResultadoProcessamento resultado = processarBloco(blocoAtual, parametroBuscar, campo, novoValor); // MANDA PARA O ABATE
                    alunoEncontrado = alunoEncontrado || resultado.alunoEncontrado;
                    campoAlterado = campoAlterado || resultado.campoAlterado;
                    novasLinhas.addAll(resultado.novasLinhas); // NÃO EXCLOI TODO O .TXT ;-;
                }
                    novasLinhas.add(linha); // Partir para prox bloco
                    blocoAtual.setLength(0); // add separador
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
                BuscarDados(novoValor);

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
        DadosAlunosTXT buscarNome = new DadosAlunosTXT();
        buscarNome.BuscarDados(String.valueOf(matricula));

        switch (i) {
            // EDITAR NOME
            case 1 ->EditarNome(matricula);

            // EDITAR CURSO
            case 2 -> EditarCurso(matricula);
            }
        
        
    }

    // EDITAR NOME
    public void EditarNome (int matricula) {
        DadosAlunosTXT buscarNome = new DadosAlunosTXT();
        buscarNome.BuscarDados(String.valueOf(matricula));

        System.out.println("Procurando pela matricula " + matricula + " na lista...");
        buscarNome.BuscarDados(String.valueOf(matricula));
        String novoNome;
        System.out.println("O nome atual vinculado a sua matricula é "+buscarNome.getNomeVelho());
        novoNome = ValidarLetrasNum.lerTextoValido("Novo nome: ");

        editarDados(buscarNome.getMatriculaVelha(), "NOME", novoNome.toUpperCase());

    }
    
    // EDITAR CURSO
    public void EditarCurso (int matricula) {
        DadosAlunosTXT buscarCurso = new DadosAlunosTXT();
        buscarCurso.BuscarDados(String.valueOf(matricula));

        System.out.println("Procurando pela matricula " + matricula + " na lista...");
        buscarCurso.BuscarDados(String.valueOf(matricula));

        String novoCurso;
        String cursoVelho = buscarCurso.getCursoVelho();
        System.out.println("O curso atual vinculado à sua matrícula é " + cursoVelho);
        novoCurso = ValidarLetrasNum.lerTextoValido("Digite seu novo curso: ");
        editarDados(buscarCurso.getMatriculaVelha(), "CURSO", novoCurso.toUpperCase());

    }

}
