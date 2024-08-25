package imd.edb2.dataStructure.redBlackTree;

import java.util.List;

import imd.edb2.domain.Reserva;

public class Node {
   private Node dad, left, right;
   private Reserva data;
   private boolean isNull;
   private Color color;

   public Node(Reserva reserva, Color color) {
      this.data = reserva;
      this.isNull = false;
      this.color = color;
      left = new Node();
      right = new Node();
   }

   public Node() {
      this.isNull = true;
      color = Color.BLACK;
   }

   public void add(Reserva reserva) {

      if (reserva.getId() == data.getId()) {
         return;
      }

      if (data.getId() > reserva.getId()) {
         if (left.isNull()) {
            left = new Node(reserva, Color.RED);
            left.setDad(this);
            return;
         } else {
            left.add(reserva);
         }
      } else {
         if (right.isNull()) {
            right = new Node(reserva, Color.RED);
            right.setDad(this);
            return;
         } else {
            right.add(reserva);
         }
      }
   }

   private void list(Node node) {
      if (node.getData() == null) {
         System.out.println("Your tree is empty!");
         return;
      }

      if (!node.getLeft().isNull()) {
         list(node.getLeft());
      }

      System.out.println(node.getData());

      if (!node.getRight().isNull()) {
         list(node.getRight());
      }
   }

   public void list() {
      list(this);
   }

   public void printPreOrdered(){
      printPreOrdered(this);
   }

   private void printPreOrdered(Node node){
      if (node.isNull()) {
         return;
     }

     System.out.println(node.getData());

     printPreOrdered(node.getLeft());

     printPreOrdered(node.getRight());
   }

   public List<Reserva> listByFlight(List<Reserva> reservas, String voo){
      return listByFlight(reservas, voo, this);
   }

   private List<Reserva> listByFlight(List<Reserva> reservas, String voo, Node node){
      if (node.getData() == null) {
         return reservas;
      }

      if (!node.getLeft().isNull()) {
         listByFlight(reservas, voo, node.getLeft());
      }

      if (node.getData().getVoo().equals(voo)) {
         reservas.add(node.getData());
      }

      if (!node.getRight().isNull()) {
         listByFlight(reservas, voo, node.getRight());
      }

      return reservas;
   }

   public Node search(int id) {
      return search(id, this);
   }

   public Node search(int id, Node node) {

      if (node.getData().getId() == id) {
         return node;
      } else if (node.getData().getId() > id) {
         if (node.getLeft().isNull()) {
            return null;
         }
         return search(id, node.getLeft());
      } else {
         if (node.getRight().isNull()) {
            return null;
         }
         return search(id, node.getRight());
      }
   }


   public Node remove(int id, Node node) {
      if (node == null) {
         return null;
      }

      if (node.getData().getId() > id) {
         node.setLeft(remove(id, node.getLeft()));
      } else if (node.getData().getId() < id) {
         node.setRight(remove(id, node.getRight()));
      } else {
         if (node.isLeaf()) {
            return new Node();
         }

         if (node.hasSingleSon()) {
            if (node.getLeft().isNull()) {
               return node.getRight();
            } else if (node.getRight().isNull()) {
               return node.getLeft();
            }
         }

         var successor = node.getRight().min();
         node.setData(successor.getData());
         Node removed = remove(successor.getData().getId(), node.getRight());
         node.setRight(removed);
      }

      return node;
   }

   private Node min() {
      if (this.getData() == null) {
         return new Node();
      }

      return min(this);
   }

   private Node min(Node node) {
      if (node.getLeft().isNull()) {
         return node;
      } else {
         return min(node.getLeft());
      }
   }

   public boolean hasSingleSon() {
      return getLeft().isNull() ^ getRight().isNull();
   }

   public boolean isLeaf() {
      return left.isNull() && right.isNull();
   }

   public Color getColor() {
      return color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public Node getDad() {
      return dad;
   }

   public void setDad(Node dad) {
      this.dad = dad;
   }

   public Node getLeft() {
      return left;
   }

   public void setLeft(Node left) {
      this.left = left;
   }

   public Node getRight() {
      return right;
   }

   public void setRight(Node right) {
      this.right = right;
   }

   public Reserva getData() {
      return data;
   }

   public void setData(Reserva data) {
      this.data = data;
   }

   public boolean isNull() {
      return isNull;
   }

   public void setNull(boolean isNull) {
      this.isNull = isNull;
   }
}
