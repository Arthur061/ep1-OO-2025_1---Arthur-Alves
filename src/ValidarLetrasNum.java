import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidarLetrasNum {
    private static final Scanner sc = new Scanner(System.in);

    public static class Validador {
        public static boolean ehTextoValido(String texto) {
            return texto.matches("[a-zA-ZÀ-ÿ\\s]+");
        }
    }
    

    // Leitura e validação de texto
    public static String lerTextoValido(String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = sc.nextLine().trim();
            if (Validador.ehTextoValido(entrada)) {
                return entrada;
            } else {
                System.out.println("Entrada inválida. Digite apenas letras.");
            }
        }
    }

    // Leitura e validação de número inteiro
    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite apenas números inteiros.");
                sc.next(); 
            }
        }
    }

    //VALIDAR SALA DE AULA
    public static String lerSalaValida(String mensagem) {
        Scanner sc = new Scanner(System.in);
        String sala;
        String regex = "^(I(10|[1-9])|S(10|[1-9]))$";
        
        while (true) {
            System.out.print(mensagem);
            sala = sc.nextLine().trim();
            if (Pattern.matches(regex, sala)) {
                break; 
            } else {
                System.out.println("Sala não encontrada. O formato válido é I1-I10 ou S1-S10.");
            }
        }
        return sala;
    }

    // LER DOUBLE
    public static double lerDouble (String mensagem) {
        while (true) { 
            try {
                System.out.print(mensagem);
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número decimal, como 7.5 ou 8.0.");
                sc.next();
            }
        }
    }

}
