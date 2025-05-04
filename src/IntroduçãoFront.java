public class IntroduçãoFront {
    public void MenuInicial() {
        System.out.println("---- - -- - -- - -- - -- - -- - ----");
        System.out.println("    BEM-VINDO AO SISTEMA DA FCTE    ");
        System.out.println("---- - -- - -- - -- - -- - -- - ----");
        System.out.println("\nEscolha a opção que deseja usar: \n");
        System.out.println("1- Alunos");
        System.out.println("2- Disciplinas e Turmas");
        System.out.println("3- Avaliação e Frequencia");
        System.out.println("4- SAIR\n");
    }
    
    public void MenuAluno() {
        System.out.println("\n=== MENU DO ALUNO ===\n");
        System.out.println("1- CADASTRAR ALUNO");
        System.out.println("2- LISTAR ALUNOS");
        System.out.println("3- EDITAR ALUNO");
        System.err.println("4- VOLTAR PARA MENU");
        System.out.print("Sua escolha: ");
    }

    public void EdicaoAluno() {
        System.err.println("\nQual informação voce deseja editar?\n");
        System.out.println("1- MATRICULA");
        System.out.println("2- NOME");
        System.out.println("3- CURSO");
        System.out.println("4- CONDIÇÃO DO ALUNO");
        System.out.println("5- VOLTAR MENU ALUNO");
        System.out.print("Sua escolha: ");
    }
}
