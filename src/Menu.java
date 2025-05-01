import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void menu(){ //Chamada do menu()
        Scanner sc = new Scanner(System.in);
        IntroduçãoFront Menu = new IntroduçãoFront();
        Menu.MenuInicial();
        
        int opcao = 0;
        boolean Entrada = false;

        while(!Entrada) { // Restrição para letras e números fora do intervalo válido [1, 4]
            System.out.print("Sua escolha: ");

            try {
                opcao = sc.nextInt();
                
                if (opcao >= 1 && opcao <= 4) {
                    Entrada = true; 
                } else {
                    System.out.println("Opção inválida! Por favor, escolha uma opção de 1 a 4.");
                }
            } catch (InputMismatchException e) { // Execução do while para entradas diferente de número.
                System.out.println("Isso não é um número válido. Tente novamente.");
                sc.next(); 
            }
        }

        switch (opcao) {// Redirecionando para a escolha
            case 1 -> {System.out.println("Você escolheu Alunos.");
                Aluno OpcaoAluno = new Aluno();
                OpcaoAluno.aluno();}
            case 2 -> System.out.println("Você escolheu Disciplinas e Turmas.");
            case 3 -> System.out.println("Você escolheu Avaliação e Frequencia.");
            case 4 -> System.out.println("Já vai sair... É uma pena! Ate mais :)");
            default -> {
            }
        }

        sc.close();

    }
}
