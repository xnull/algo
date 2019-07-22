package tree;

import java.lang.*;
import java.util.*;
import java.math.*;
import java.util.AbstractMap.SimpleEntry;

public class BstWithCounting {
    public static void main(String[] a) {
      Bst bst = new Bst();
      bst.insert(10);
      bst.insert(6);
      bst.insert(4);
      bst.insert(8);
      bst.insert(14);
      bst.insert(18);
      bst.insert(12);
      bst.insert(25);
      
      //bst.printBfs();
      
      TreePrinter.print(bst.head);
    }
}

class Node {
  int value;
  int duplications;
  
  int countLeft;
  int countRight;
  
  Node left;
  Node right;
  
  Node(int value){
    this.value = value;
  }
  
  public String toString(){
    return "{v:" + value + ",l:" + countLeft + ",r:" + countRight + "}";
  }
}

class Bst {
  Node head;
  
  void insert(int value){
    if(head == null){
      head = new Node(value);
      return;
    }
    
    Node curr = head;
    
    while(true){
      if(value < curr.value){
        curr.countLeft += 1;
        
        if(curr.left == null){
          curr.left = new Node(value);
          break;
        } else {
          curr = curr.left;
          continue;
        }
      }
      
      if(value > curr.value){
        curr.countRight += 1;
        
        if(curr.right == null){
          curr.right = new Node(value);
          break;
        } else {
          curr = curr.right;
          continue;
        }
      }
      
      //if val equals curr node
      break;
    }
  }
  
  void printBfs(){
    Queue<Node> queue = new LinkedList<>();
    queue.add(head);
    
    while(!queue.isEmpty()){
      Node curr = queue.poll();
      
      System.out.println(curr);
      
      if(curr.left != null){
        queue.add(curr.left);
      }
      
      if(curr.right != null){
        queue.add(curr.right);
      }
    }
  }
}

class TreePrinter {
  
  static void print(Node root){
      int depth = depth(root);
      
      Queue<Node> queue = new LinkedList<>();
      queue.add(root);
      
      int level = 0;
      while(!queue.isEmpty()){
        
        int size = queue.size();
        
        for(int i = 0; i < size; i++){
          Node curr = queue.poll();
          
          int offset = (int) Math.pow(2, (depth-level)) + (depth-level)*8;
          System.out.print(spaces(offset) + curr);
          
          if(curr.left != null){
            queue.add(curr.left);
          }
          
          if(curr.right != null){
            queue.add(curr.right);
          }
        }
        
        level++;
        System.out.println("");
      }
  }
  
  static String spaces(int n) {
      StringBuilder res = new StringBuilder();
      for(int i=0; i< n; i++){
        res.append(' ');
      }
       return res.toString();
    }
    
    static int depth(Node tree){
      Queue<Node> queue = new LinkedList<>();
      queue.add(tree);
      
      int depth = 0;
      while(!queue.isEmpty()){
        depth++;
        
        int size = queue.size();
        
        for(int i = 0; i < size; i++){
          Node curr = queue.poll();
          
          if(curr.left != null){
            queue.add(curr.left);
          }
          
          if(curr.right != null){
            queue.add(curr.right);
          }
        }
      }
      
      return depth;
    }
}
