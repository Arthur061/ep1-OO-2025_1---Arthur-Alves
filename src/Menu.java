import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void menu(){ //Chamada do menu()
        Scanner sc = new Scanner(System.in);
        System.out.println("---- - -- - -- - -- - -- - -- - ----");
        System.out.println("    BEM-VINDO AO SISTEMA DA FCTE    ");
        System.out.println("---- - -- - -- - -- - -- - -- - ----");
        System.out.println("");
        System.out.println("Escolha a opção que deseja usar: ");
        System.out.println("");
        System.out.println("1- Alunos");
        System.out.println("2- Disciplinas e Turmas");
        System.out.println("3- Avaliação e Frequencia");
        System.out.println("");
        System.out.println("4- SAIR");
        System.out.println("");

        int opcao = 0;
        boolean Entrada = false;

        while(!Entrada) { //Restrição para tudo diferente das opções de 1 a 4
            System.out.print("Sua escolha: ");

            try {
                opcao = sc.nextInt();
                Entrada = true;
            } catch (InputMismatchException e) { // Execução do while
                System.out.println("Isso não é um número válido. Tente novamente.");
                sc.next();
            }
        }

        switch (opcao) {// Redirecionando para a escolha
            case 1 -> System.out.println("Você escolheu Alunos.");
            //Aluno OpcaoAluno = new Aluno();
            //OpcaoAluno.aluno();
            case 2 -> System.out.println("Você escolheu Disciplinas e Turmas.");
            case 3 -> System.out.println("Você escolheu Avaliação e Frequencia.");
            case 4 -> System.out.println("Já vai sair... É uma pena! Ate mais :)");
            default -> {
            }
        }

        sc.close();

    }
}
