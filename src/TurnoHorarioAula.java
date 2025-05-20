import java.util.List;

public class TurnoHorarioAula {

    public String horarioAulaFormatado(List<Integer> dias, String turno, int horarioI, int horarioF) {
        DiaSemana dia1 = new DiaSemana(dias.get(0));
        DiaSemana dia2 = new DiaSemana(dias.get(1));
        DiaSemana dia3 = new DiaSemana(dias.get(2));

        String horaFormatada = Horario.formatarHora(turno, horarioI, horarioF);

        if (("M".equals(turno) || "T".equals(turno)) && horarioI == 12 && horarioF == 14) {
            String turnoF1 = "M5";
            String turnoF2 = "T1";
            return dia1.getCodigo() + dia2.getCodigo() + turnoF2 + " " + dia1.getCodigo() + dia2.getCodigo() + turnoF1;
        }

        if ("0".equals(dia2.getCodigo()) && "0".equals(dia3.getCodigo())) {
            return dia1.getCodigo() + turno + horaFormatada;
        } else if (!"0".equals(dia2.getCodigo()) && "0".equals(dia3.getCodigo())) {
            return dia1.getCodigo() + dia2.getCodigo() + turno + horaFormatada;
        } else {
            return dia1.getCodigo() + dia2.getCodigo() + dia3.getCodigo() + turno + horaFormatada;
        }
    }

    static class DiaSemana {
        private final int valor;

        public DiaSemana(int valor) {
            this.valor = valor;
        }

        public String getCodigo() {
            return String.valueOf(valor);
        }
    }

    static class Horario {
        public static String formatarHora(String turno, int inicio, int fim) {
            return switch (turno) {
                case "M" -> (inicio == 8 && fim == 10) ? "12" :
                            (inicio == 10 && fim == 12) ? "34" :
                            null;
                case "T" -> (inicio == 14 && fim == 16) ? "23" :
                            (inicio == 16 && fim == 18) ? "45" :
                            null;
                case "N" -> (inicio == 19 && fim == 21) ? "12" :
                            (inicio == 21 && fim == 23) ? "34" :
                            null;
                default -> null;
            };
        }
    }
}
