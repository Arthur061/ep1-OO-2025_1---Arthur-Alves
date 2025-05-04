public class IntroduçãoFront {
    public void MenuInicial() {
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
    }
    public void MenuAluno() {
        System.out.println("");
        System.out.println("=== MENU DO ALUNO ===");
        System.out.println("");
        System.out.println("1- CADASTRAR ALUNO");
        System.out.println("2- LISTAR ALUNOS");
        System.out.println("3- EDITAR ALUNO");
        System.err.println("4- VOLTAR PARA MENU");
        System.out.print("Sua escolha: ");
    }

    public void EdicaoAluno() {
        System.out.println("");
        System.err.println("Qual informação voce deseja editar?");
                System.out.println("");
                System.out.println("1- MATRICULA");
                System.out.println("2- NOME");
                System.out.println("3- CURSO");
                System.out.println("4- VOLTAR");
                System.out.print("Sua escolha: ");
    }
}
