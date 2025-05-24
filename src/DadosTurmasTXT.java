import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DadosTurmasTXT {

    private static final Set<String> listaDePreRequisitos = new HashSet<>();
    public Set<String> getListaDePreRequisitos() {
        return listaDePreRequisitos;
    }

    // CADASTRAR TURMA NOVA
     public static void turmasTXT(String caminhoArquivo, String nomeProf, String nomeDisciplina, int codigo, int cargaH, 
     String preReq, int capacidade, String avaliacao, String turno, String sala, String horarioFormatado, String modo)
      {
        String dadosTurma = String.join("\n", 
        "---------------------",
        "NOME PROFESSOR: " + nomeProf,
        "NOME DA DISCIPLINA: " + nomeDisciplina,
        "CODIGO: " + codigo,
        "CARGA HORARIA: " + cargaH+"h",
        "PRÉ-REQUISITO: "+ preReq,
        "CAPACIDADE: 0/"+ capacidade,
        "AVALIAÇÃO: "+ avaliacao,
        "TURNO: "+ turno,
        "MODO: "+ modo,
        "SALA: "+ sala,
        "HORARIO: "+ horarioFormatado,
        "ALUNOS MATRICULADOS: ",
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

     
    // GARANTIR QUE HAJA CAPACIDADE
    public boolean atualizarCapacidade(String caminhoArquivo, String nomeDisciplina) {
        try {
            File arquivo = new File(caminhoArquivo);
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            StringBuilder novoConteudo = new StringBuilder();

            String linha;
            boolean dentroBloco = false;
            boolean encontrouDisciplina = false;
            boolean atualizouCapacidade = false;
            StringBuilder blocoAtual = new StringBuilder();
            
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("---------------------")) {
                    if (dentroBloco) {
                        novoConteudo.append(blocoAtual.toString());
                    }

                    blocoAtual.setLength(0);
                    blocoAtual.append(linha).append("\n");
                    dentroBloco = true;
                    encontrouDisciplina = false;
                } else if (dentroBloco) {
                    blocoAtual.append(linha).append("\n");

                    if (linha.toUpperCase().startsWith("NOME DA DISCIPLINA:") &&
                        linha.toUpperCase().contains(nomeDisciplina.toUpperCase())) {
                        encontrouDisciplina = true;
                    }

                    if (encontrouDisciplina && linha.toUpperCase().startsWith("CAPACIDADE:")) {
                        String capacidadeStr = linha.substring(linha.indexOf(":") + 1).trim();
                        
                        String[] partes = capacidadeStr.split("/");
                        int ocupadas = Integer.parseInt(partes[0].trim());
                        int capacidadeMax = Integer.parseInt(partes[1].trim());
                    
                        if (ocupadas < capacidadeMax) {
                            ocupadas += 1;
                            atualizouCapacidade = true;

                            String novaLinha = "CAPACIDADE: " + ocupadas + "/" + capacidadeMax;
                            int startIdx = blocoAtual.lastIndexOf(linha);
                            blocoAtual.replace(startIdx, startIdx + linha.length(), novaLinha);
                        } else {
                            br.close();
                            return false;
                        }
                    }
                    
                } else {
                    novoConteudo.append(linha).append("\n");
                }
            }

            if (blocoAtual.length() > 0) {
                novoConteudo.append(blocoAtual.toString());
            }

            br.close();

            if (atualizouCapacidade) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
                bw.write(novoConteudo.toString());
                bw.close();
                System.out.println("Capacidade da disciplina '" + nomeDisciplina + "' atualizada.");
            }

            return atualizouCapacidade;

        } catch (IOException e) {
            System.out.println("Erro ao processar o arquivo de turmas: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR DISCIPLINA CHECK
    public static void buscarDisciplina(String caminhoArquivo, String nomeDisciplinaBuscada, String nomeProfessorBuscado) throws IOException {
        int contador = 0;
        int reqCont = 0;
        boolean encontrada = false;

        listaDePreRequisitos.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            DisciplinasTurmas disciplina = new DisciplinasTurmas();
            String linha;
            List<String> bloco = new ArrayList<>();
    
            while ((linha = br.readLine()) != null) {
                if (linha.contains("---------------------")) {
                    if (!bloco.isEmpty()) {

                        for (String l : bloco) {
                            if (l.toUpperCase().contains("NOME DA DISCIPLINA: " + nomeDisciplinaBuscada.toUpperCase())) {
                                encontrada = true;
                                contador++;

                                for (String info : bloco) {
                                    System.out.println(info);
                                    if (info.toUpperCase().contains("PRÉ-REQUISITO: ")) {
                                        String preRequisito = info.substring(info.indexOf(":") + 1).trim();
                                        if (listaDePreRequisitos.add(preRequisito)) { 
                                            reqCont++;
                                            System.out.println("Pré-requisito detectado: " + preRequisito);
                                        }
                                    }
                                }
                                System.out.println("---------------------");
                                break;
                            }
                        }
                        bloco.clear();
                    }
                } else {
                    bloco.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    
        if (contador > 0) {
            System.out.println("Há " + contador + " matéria(s) disponível(is) com esse nome.");
        } else {
            System.out.println("Disciplina não encontrada.");
            MenuTurma voltaMenu = new MenuTurma();
            voltaMenu.executar();
        }
    }
}

