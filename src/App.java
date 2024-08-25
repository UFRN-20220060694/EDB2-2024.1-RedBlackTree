import imd.edb2.domain.Reserva;
import imd.edb2.domain.ArvoreBalanceada;

public class App {
    public static void main(String[] args) throws Exception {
        ArvoreBalanceada sistema = new ArvoreBalanceada();

        Reserva reserva1 = new Reserva(1, "Alice Silva", "V001", "2024-08-20T15:30:00");
        Reserva reserva2 = new Reserva(2, "João Pereira", "V001", "2024-08-20T14:00:00");
        Reserva reserva3 = new Reserva(3, "Maria Costa", "V001", "2024-08-20T15:00:00");
        Reserva reserva4 = new Reserva(4, "Carlos Souza", "V002", "2024-08-21T09:00:00");
        Reserva reserva5 = new Reserva(5, "Beatriz Lima", "V002", "2024-08-21T10:30:00");
        Reserva reserva6 = new Reserva(6, "Fernanda Oliveira", "V001", "2024-08-20T16:00:00");
        Reserva reserva7 = new Reserva(7, "Pedro Santos", "V001", "2024-08-20T16:30:00");

        sistema.inserirReserva(reserva1);
        sistema.inserirReserva(reserva2);
        sistema.inserirReserva(reserva3);
        sistema.inserirReserva(reserva4);
        sistema.inserirReserva(reserva5);
        sistema.inserirReserva(reserva6);
        sistema.inserirReserva(reserva7);

        sistema.list();
        System.out.println("\n\nFim listagem");
        
        sistema.removerReserva(2);
        sistema.removerReserva(3);
        sistema.removerReserva(7);

        sistema.verificarBalanceamento();

        sistema.imprimirEmPreOrdem();
        var reservas = sistema.listarReservasPorVoo("V001");
    }
}
