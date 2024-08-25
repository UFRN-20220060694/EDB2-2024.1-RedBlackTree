package imd.edb2.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import imd.edb2.dataStructure.redBlackTree.Color;
import imd.edb2.dataStructure.redBlackTree.Node;

public class ArvoreBalanceada {
   private Node root;

   public ArvoreBalanceada() {
      this.root = new Node();
   }

   public void inserirReserva(Reserva reserva) {
      if (this.root.isNull()) {
         this.root = new Node(reserva, Color.BLACK);
         root.setDad(new Node());
      } else {
         this.root.add(reserva);

         var node = root.search(reserva.getId());

         if (node != null) {
            addFix(node);
         }
      }

      // System.out.println(String.format("Reserva inserida com sucesso: %s",
      // reserva.toString()));
      return;
   }

   public void list() {
      this.root.list();
   }

   private void addFix(Node z) {
      Node y;
      while (z.getDad().getColor() == Color.RED) {
         if (z.getDad() == z.getDad().getDad().getLeft()) {
            y = z.getDad().getDad().getRight();
            // Caso 1
            if (y.getColor() == Color.RED) {

               z.getDad().setColor(Color.BLACK);

               y.setColor(Color.BLACK);

               z.getDad().getDad().setColor(Color.RED);

               z = z.getDad().getDad();
            } else {
               if (z == z.getDad().getRight()) {
                  z = z.getDad();
                  this.rotateLeft(z);
               }
               z.getDad().setColor(Color.BLACK);
               z.getDad().getDad().setColor(Color.RED);
               this.rotateRight(z.getDad().getDad());
            }
         } else {
            y = z.getDad().getDad().getLeft();
            if (y.getColor() == Color.RED) {
               y.setColor(Color.BLACK);
               z.getDad().setColor(Color.BLACK);
               z.getDad().getDad().setColor(Color.RED);
               z = z.getDad().getDad();
            } else {
               if (z == z.getDad().getLeft()) {
                  z = z.getDad();
                  this.rotateRight(z);
               }
               // caso 3
               z.getDad().setColor(Color.BLACK);
               z.getDad().getDad().setColor(Color.RED);
               this.rotateLeft(z.getDad().getDad());
            }
         }
      }
      this.root.setColor(Color.BLACK);
   }

   private void deleteFix(Node n) {
      Node x;

      while (n != root && n.getColor() == Color.BLACK) {
         if (n == n.getDad().getLeft()) {
            x = n.getDad().getRight();

            if (x.getColor() == Color.RED) { // caso 1
               x.setColor(Color.BLACK);
               n.getDad().setColor(Color.RED);
               this.rotateLeft(n.getDad());
               x = n.getDad().getRight();
            }
            if (x.getLeft().getColor() == Color.BLACK && x.getRight().getColor() == Color.BLACK) {
               x.setColor(Color.RED);
               n = n.getDad();
            } else {
               if (x.getRight().getColor() == Color.BLACK) {
                  x.getLeft().setColor(Color.BLACK);
                  x.setColor(Color.RED);
                  this.rotateRight(x);
                  x = n.getDad().getRight();
               }
               x.setColor(n.getDad().getColor());
               n.getDad().setColor(Color.BLACK);
               x.getRight().setColor(Color.BLACK);
               this.rotateLeft(n.getDad());
               n = this.root;
            }
         } else {
            x = n.getDad().getLeft();

            if (x.getColor() == Color.RED) {
               x.setColor(Color.BLACK);
               n.getDad().setColor(Color.RED);
               this.rotateRight(n.getDad());
               x = n.getDad().getLeft();
            }
            if (x.getLeft().getColor() == Color.BLACK && x.getRight().getColor() == Color.BLACK) {
               x.setColor(Color.RED);
               n = n.getDad();
            } else {
               if (x.getLeft().getColor() == Color.BLACK) {
                  x.getRight().setColor(Color.BLACK);
                  x.setColor(Color.RED);
                  this.rotateLeft(x);
                  x = n.getDad().getLeft();
               }
               x.setColor(n.getDad().getColor());
               n.getDad().setColor(Color.BLACK);
               x.getLeft().setColor(Color.BLACK);
               this.rotateRight(n.getDad());
               n = this.root;
            }
         }
      }
      n.setColor(Color.BLACK);
   }

   private void rotateLeft(Node x) {
      Node y = x.getRight();
      x.setRight(y.getLeft());

      if (!y.getLeft().isNull()) {
         y.getLeft().setDad(x);
      }
      y.setDad(x.getDad());

      if (x.getDad().isNull()) {
         this.root = y;
      } else if (x == x.getDad().getLeft()) {
         x.getDad().setLeft(y);
      } else {
         x.getDad().setRight(y);
      }
      y.setLeft(x);
      x.setDad(y);
   }

   private void rotateRight(Node x) {
      Node y = x.getLeft();

      x.setLeft(y.getRight());

      if (!y.getRight().isNull()) {
         y.getRight().setDad(x);
      }
      y.setDad(x.getDad());

      if (x.getDad().isNull()) {
         this.root = y;
      } else if (x == x.getDad().getLeft()) {
         x.getDad().setLeft(y);
      } else {
         x.getDad().setRight(y);
      }

      y.setRight(x);
      x.setDad(y);
   }

   public void removerReserva(int id) {
      if (root.isLeaf()) {
         root = new Node();
      }
      var result = this.root.remove(id, this.root) != null;
      if (result) {
         this.deleteFix(root);
         System.out.println(String.format("Reserva removida com sucesso: %d", id));
      }
      return;
   }

   public void verificarBalanceamento() {
      String success = "Árvore balanceada com sucesso";
      String error = "Árvore desbalanceada";

      if (root.isNull()) {
         System.out.println(success);
         return;
      }

      if (root.getColor() == Color.RED) {
         System.out.println(error);
         return;
      }
      if (verificarBalanceamento(root, 0, -1)) {
         System.out.println(success);
         return;
      }

      System.out.println(error);
   }

   private boolean verificarBalanceamento(Node node, int blackCount, int pathBlackCount) {
      if (node == null) {
         if (pathBlackCount == -1) {
            pathBlackCount = blackCount;
         } else if (blackCount != pathBlackCount) {
            return false;
         }
         return true;
      }

      if (node.getColor() == Color.BLACK) {
         blackCount++;
      }

      if (node.getColor() == Color.RED) {
         if ((!node.getLeft().isNull() && node.getLeft().getColor() == Color.RED) || 
         (!node.getRight().isNull() && node.getRight().getColor() == Color.RED)) {
            return false;
         }
      }

      return verificarBalanceamento(node.getLeft(), blackCount, pathBlackCount) && verificarBalanceamento(node.getRight(), blackCount, pathBlackCount);
   }

   public void buscarReserva(int id) {
      var reserva = root.search(id);

      if (reserva == null) {
         System.out.println(String.format("Reserva não encontrada: ID = %d", id));
         return;
      }

      System.out.println(String.format("Reserva encontrada: %s", reserva.toString()));
   }

   public void imprimirEmPreOrdem(){
      this.root.printPreOrdered();
   }

   public List<Reserva> listarReservasPorVoo(String voo) {
      var reservas = new ArrayList<Reserva>();

      reservas = (ArrayList<Reserva>) root.listByFlight(reservas, voo);

      if (reservas.size() > 0) {
         reservas.sort(Comparator.comparing(Reserva::getDataDaReserva));

         System.out.println(String.format("Reservas para o voo %s:", voo));
         int count = 1;
         for (Reserva reserva : reservas) {
            System.out.println(String.format("%d. %s", count, reserva.toString()));
            count++;
         }
      } else {
         System.out.println(String.format("Nenhum reserva encontrada para o voo %s:", voo));
      }

      return reservas;
   }
}
