import java.io.*;
 import java.nio.charset.StandardCharsets;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.Scanner;

public class DadosTXT  extends Aluno{
    Scanner sc = new Scanner(System.in);
    StringBuilder novoConteudo = new StringBuilder();

    StringBuilder blocoAluno = new StringBuilder();
    boolean encontrou = false; String linha; String dado;
    //Variaveis para edição
    private String matriculaVelha; private String nomeVelho; private String cursoVelho; private String condicaoVelho;

    public static void salvarEmTxt(String caminhoArquivo, String matricula, String nome, String curso, boolean condicao) { //CADASTRO ALUNO
        String dadosAluno = String.join("\n", 
        "---------------------",
        "ALUNO ESPECIAL: " + (condicao ? "SIM" : "NÃO"),
        "MATRICULA: " + matricula,
        "NOME: " + nome,
        "CURSO: " + curso,
        "---------------------");

         try {
            Files.write(Paths.get(caminhoArquivo), (dadosAluno + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    
    }
    public static boolean verificarSeDadoExiste(Integer matricula) { //Verificação da matricula repetida
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
    public static List<String> getNomesAlunos(String caminhoArquivo) { // Saida dos nomes de todos alunos
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
    public void BuscarDados(String dado) { //Buscar Dados
        matriculaVelha = null;
        nomeVelho = null;
        cursoVelho = null;

        try (BufferedReader br = new BufferedReader(new FileReader("alunos.txt"))) { 
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
                            } else if (l.trim().toUpperCase().startsWith("NOME:")) {
                                nomeVelho = l.substring(l.indexOf(":") + 1).trim();
                            } else if (l.trim().toUpperCase().startsWith("CURSO:")) {
                                cursoVelho = l.substring(l.indexOf(":") + 1).trim();
                            } else if (l.trim().toUpperCase().startsWith("ALUNO ESPECIAL:")) {
                                condicaoVelho = l.substring(l.indexOf(":") + 1).trim();
                            }
                            if (matriculaVelha != null && nomeVelho != null && cursoVelho != null) { // TCHAU LOOP
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

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

}

    public String getMatriculaVelha() { //GET PARA EDITAR MATRICULA
        return matriculaVelha;
    }
    public String getNomeVelho() { // GET PARA EDITAR O NOME
        return nomeVelho;
    }
    public String getCursoVelho () { // GET PARA EDITAR O CURSO
        return cursoVelho;
    }
    public String getCondicaoVelho () {
        return condicaoVelho;
    }

    public void editarDados(String parametroBuscar, String campo, String novoValor) { //VERIFICA O .TXT ATRAS DOS DADOS DO ALUNO
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

    class ResultadoProcessamento { //Classe que resulta no dado editado do aluno
        boolean alunoEncontrado;
        boolean campoAlterado;
        List<String> novasLinhas;

        ResultadoProcessamento(boolean alunoEncontrado, boolean campoAlterado, List<String> novasLinhas) {
            this.alunoEncontrado = alunoEncontrado;
            this.campoAlterado = campoAlterado;
            this.novasLinhas = novasLinhas;

        }
    }
    public ResultadoProcessamento processarBloco(StringBuilder bloco, String parametroBuscar, String campo, String novoValor) {

        String blocoStr = bloco.toString();
        String[] linhasBloco = blocoStr.split("\n");
        List<String> resultadoLinhas = new ArrayList<>();

        boolean registroEncontrado = false;
        boolean campoAlterado = false;
    
        //BUSCANDO REGISTRO DO STUDENT BRO
        for (String linha : linhasBloco) {
            
            if (campo.equalsIgnoreCase("NOME") && linha.trim().toUpperCase().startsWith("MATRICULA:")) {
                String dadoNoArquivo = linha.substring(linha.indexOf(":") + 1).trim();

                if (dadoNoArquivo.equals(parametroBuscar.trim())) {
                    registroEncontrado = true;
                    break;
                }
            }else if (linha.trim().toUpperCase().startsWith(campo.toUpperCase() + ":")) {//SE O CAMPO FOR OUTRO
                String valorNoArquivo = linha.substring(linha.indexOf(":") + 1).trim();
                if (valorNoArquivo.equalsIgnoreCase(parametroBuscar.trim())) {
                    registroEncontrado = true;
                    break;
                }
            }
        }
        // EDICAOOO
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
   
    public int verificarMatricula(int i, int matricula) {//VERIFICAR PROCEDENCIA DA MATRICULA
        String matriculaStr;

        //Teste de fidelidade da matricula que o bendito digitou
        while (matricula == -1 || String.valueOf(matricula).length() != 9) {
            System.out.print("Digite sua matricula: ");
            matriculaStr = sc.nextLine().trim();
            try {
                matricula = Integer.parseInt(matriculaStr);
    
                if (String.valueOf(matricula).length() != 9) {
                    System.out.println("A matrícula deve conter exatamente 9 dígitos.");
                    matricula = -1; // força a repetição
                }
    
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite apenas números.");
                matricula = -1;
            }
        }
        DadosTXT buscarNome = new DadosTXT();
        buscarNome.BuscarDados(String.valueOf(matricula));
       switch (i) {
            case 1 -> {
                System.out.println("Procurando pela matricula " + matricula + " na lista...");
                buscarNome.BuscarDados(String.valueOf(matricula));

                String novoNome;
                while(true) {
                    System.out.println("O nome atual vinculado a sua matricula é "+buscarNome.getNomeVelho());
                    try {
                        System.out.print("\n Digite seu novo nome: ");
                        novoNome = sc.nextLine().trim();

                        if (!novoNome.matches("[a-zA-Z\\s]+")) { //Não pode faltar né
                            System.out.println("Apenas letras, por favor.");
                            continue;
                        }
                        break; //tchau loop
                    }
                    catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                DadosTXT editarNome = new DadosTXT();
                editarNome.editarDados(buscarNome.getMatriculaVelha(), "NOME", novoNome);
            }
            case 2 -> {
                System.out.println("Procurando pela matricula " + matricula + " na lista...");
                buscarNome.BuscarDados(String.valueOf(matricula));

                String novoCurso;
                while(true) {
                    System.out.println("O curso atual vinculado a sua matricula é "+buscarNome.getCursoVelho());
                    try {
                        System.out.print("\n Digite seu novo curso: ");
                        novoCurso = sc.nextLine().trim();

                        if (!novoCurso.matches("[a-zA-Z\\s]+")) { //Não pode faltar né
                            System.out.println("Apenas letras, por favor.");
                            continue;
                        }
                        break; //tchau loop
                    }
                    catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                DadosTXT editarNome = new DadosTXT();
                editarNome.editarDados(buscarNome.getMatriculaVelha(), "NOME", novoCurso);
            }
            case 3 -> {
                System.out.println("Procurando pela matricula " + matricula + " na lista...");
                buscarNome.BuscarDados(String.valueOf(matricula));

                String escolha = "";
                int novaCondicao;
                while(true) {
                    System.out.println("A condição atual vinculado a sua matricula é ALUNO ESPECIAL: "+buscarNome.getCondicaoVelho());
                    try {
                        System.out.println("\n Digite sua nova condição: ");
                        System.out.println("1- Sim, sou aluno esepcial.");
                        System.out.println("2- Não sou aluno especial.");
                        System.out.print("Sua escolha: ");
                        novaCondicao = sc.nextInt();
                        sc.nextLine(); 

                        if (novaCondicao != 1 && novaCondicao != 2) {
                            System.out.println("Digite apenas 1 ou 2.");
                            continue;
                        }
                        switch (novaCondicao) {
                            case 1 -> escolha = "SIM";
                            case 2 -> escolha = "NÃO";     
                        }
                        break; //tchau loop
                    } 
                    catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    
                }
                DadosTXT editarNome = new DadosTXT();
                editarNome.editarDados(buscarNome.getMatriculaVelha(), "NOME", escolha);
            }
            }
        return matricula;
    }
}

