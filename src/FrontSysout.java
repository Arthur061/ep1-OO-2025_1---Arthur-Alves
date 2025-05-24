

public class FrontSysout {
    public void MenuInicial() {
        System.out.println("---- - -- - -- - -- - -- - -- - ----");
        System.out.println("    BEM-VINDO AO SISTEMA DA FCTE    ");
        System.out.println("---- - -- - -- - -- - -- - -- - ----");
        System.out.println("\nQual opção você deseja acessar?\n");
        System.out.println("1- ALUNOS");
        System.out.println("2- DISCIPLINAS E TURMAS");
        System.out.println("3- AVALIAÇÃO E FREQUENCIA");
        System.out.println("4- SAIR\n");
    }
    
    public void MenuAluno() {
        System.out.println("\n=== MENU DO ALUNO ===\n");
        System.out.println("Qual opção você deseja acessar?\n");
        System.out.println("1- CADASTRAR ALUNO");
        System.out.println("2- LISTAR ALUNOS");
        System.out.println("3- EDITAR ALUNO");
        System.out.println("4- MATRICULA E TRANCAMENTO");
        System.out.println("5- MINHAS INFORMAÇÕES");
        System.err.println("6- VOLTAR PARA MENU INICIAL\n");
        System.out.print("Sua escolha: ");
    }

    public void EdicaoAluno() {
        System.err.println("\nQual informação voce deseja editar?\n");
        System.out.println("1- MATRICULA");
        System.out.println("2- NOME");
        System.out.println("3- CURSO");
        System.out.println("4- VOLTAR MENU ALUNO\n");
        System.out.print("Sua escolha: ");
    }

    public void MenuDisciplinasTurmas () {
        System.out.println("\n === MENU DE SERVIÇOS ===\n");
        System.out.println("Qual opção você deseja acessar?\n");
        System.out.println("1- TRANCAMENTO");
        System.out.println("2- MATRICULA");
        System.out.println("3- CADASTRO DE DISCIPLINA");
        System.out.println("4- LISTAR TURMAS DISPONIVEIS");
        System.out.println("5- VOLTAR MENU INICIAL\n");
        System.out.print("Sua escolha: ");
    }

    public void menuTrancamento() {

        System.out.println("\n Antes de tudo, me deixa saber qual o seu objetivo ");
        System.out.println("1- TRANCAR SEMESTRE");
        System.out.println("2- TRANCAR DISCIPLINA");
        System.out.println("3- DESTRANCAR SEMESTRE");
        System.out.println("4- VOLTAR MENU DE SERVIÇOS");
    }

    public void CadastroDisciplina () {
        System.out.println("A matéria possui pré-requisitos?");
        System.out.println("1- SIM");
        System.out.println("2- NÃO");
        System.out.print("Sua escolha: ");
    
    }

    public void metodoAva () {
        System.out.println("Qual o metodo de avaliação?\n");
        System.out.println("Escolha uma das opções a seguir:");
        System.out.println("1- (P1 + P2 + P3 + L + S) / 5");
        System.out.println("2- (P1 + P2 * 2 + P3 * 3 + L + S) / 8");
        System.out.print("Sua escolha: ");
    }

    public void modoAula () {
        System.out.println("Qual o modo de aula?");
        System.out.println("1- PRESENCIAL");
        System.out.println("2- REMOTO");
    }

    public void TurnoDisciplina () {
        System.out.println("Qual o turno da materia?");
        System.out.println("1- MANHÃ");
        System.out.println("2- TARDE");
        System.out.println("3- NOITE");
    }
    public void MenuAvaliacoes () {
        System.out.println("\n=== MENU DE AVALIAÇÕES ===\n");
        System.out.println("Qual opção você deseja acessar?");
        System.out.println("1- RELATORIOS");
        System.out.println("2- LANÇAMENTOS");
        System.out.println("3- BOLETINS");
        System.out.println("4- VOLTAR MENU PRINCIPAL");
    }
    public void lancamentoAvaliacoes () {
        System.out.println("\n Estamos na aba de lançamentos!\n Oque você deseja?\n");
        System.out.println("1- NOTAS / FREQUÊNCIA");
        System.out.println("2- VOLTAR MENU");
    }
    public void menuRelatorios() {
        System.out.println("\n Estamos na aba de relatorios!\n Oque você deseja?\n");
        System.out.println("1- RELATORIO POR TURMA");
        System.out.println("2- RELATORIO POR DISCIPLINA");
        System.out.println("3- RELATORIO POR PROFESSOR");
        System.out.println("4- VOLTAR MENU");
    } 
    public void diasAula() {
        System.out.println("\n DIAS DE AULA NA UNB \n");
        System.out.println("2- SEGUNDA-FEIRA");
        System.out.println("3- TERÇA-FEIRA");
        System.out.println("4- QUARTA-FEIRA");
        System.out.println("5- QUINTA-FEIRA");
        System.out.println("6- SEXTA-FEIRA");
        System.out.println("7- SÁBADO\n");
    }
    public void turnoAula() {
        System.out.println("QUAL O TURNO DA MATERIA");
        System.out.println("M- MANHÃ");
        System.out.println("T- TARDE");
        System.out.println("N- NOITE");
    }

    public void selecaoBoletim() {
        System.out.println("QUAL TIPO DE BOLETIM VOCÊ DESEJA\n");
        System.out.println("1- BOLETIM COMPLETO");
        System.out.println("2- BOLETIM SEM OS DADOS DO PROF");
        System.out.println("3- VOLTAR MENU");
    }
}
