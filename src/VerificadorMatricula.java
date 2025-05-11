import java.util.Scanner;

public class VerificadorMatricula {

    public int verificarMatricula(int matricula) { // VERIFICAR PROCEDÊNCIA DA MATRÍCULA
        Scanner sc = new Scanner(System.in);
        String matriculaStr;

        // Teste de fidelidade da matrícula que o bendito digitou
        while (matricula == -1 || String.valueOf(matricula).length() != 9) {
            System.out.print("Digite a matrícula: ");
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

        return matricula;
    } 
}
