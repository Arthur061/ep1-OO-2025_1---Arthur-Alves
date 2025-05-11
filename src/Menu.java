import java.util.HashMap;
 import java.util.InputMismatchException;
import java.util.Map;
 import java.util.Scanner;

public class Menu {

    //Chamada do menu()
    public void menu(){
        Scanner sc = new Scanner(System.in);
        IntroduçãoFront Menu = new IntroduçãoFront();
        Menu.MenuInicial();
        
        int opcao = 0;
        boolean Entrada = false;

        // Restrição para letras e números fora do intervalo válido [1, 4]
        while(!Entrada) {
            System.out.print("Sua escolha: ");

            try {
                opcao = sc.nextInt();
                
                if (opcao >= 1 && opcao <= 4) {
                    Entrada = true; 
                } else {
                    System.out.println("Opção inválida! Por favor, escolha uma opção de 1 a 4.");
                }
            } catch (InputMismatchException e) { 
                System.out.println("Isso não é um número válido. Tente novamente.");
                sc.next(); 
            }
        }

        Map<Integer, MenuOptions> opcoes = new HashMap<>();
        opcoes.put(1, new MenuAluno());
        opcoes.put(2, new MenuTurma());
        opcoes.put(3, new MenuAvaliacao());
        opcoes.put(4, new MenuSair());

        MenuOptions acao = opcoes.get(opcao);
        if (acao != null) {
            acao.executar();
        }

        sc.close();

    }
}
