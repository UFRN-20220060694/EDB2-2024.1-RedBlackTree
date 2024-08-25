package imd.edb2.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva {
   private int id;
   private String nome;
   private String voo;
   private LocalDateTime dataDaReserva;

   public Reserva() {
      super();
      nome = "";
      voo = "";
      dataDaReserva = LocalDateTime.now();
   }

   public Reserva(int id, String nome, String voo, String dataDaReserva) {
      this.id = id;
      this.nome = nome;
      this.voo = voo;
      this.dataDaReserva = LocalDateTime.parse(dataDaReserva, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
   }

   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getNome() {
      return nome;
   }
   public void setNome(String nome) {
      this.nome = nome;
   }
   public String getVoo() {
      return voo;
   }
   public void setVoo(String voo) {
      this.voo = voo;
   }
   public LocalDateTime getDataDaReserva() {
      return dataDaReserva;
   }
   public void setDataDaReserva(LocalDateTime dataDaReserva) {
      this.dataDaReserva = dataDaReserva;
   }

   @Override
   public String toString() {
      var template = "ID = %d, Passageiro = %s, Voo = %s, Horario = %s";
      return String.format(template, getId(), getNome(), getVoo(), getDataDaReserva());
   }
}
