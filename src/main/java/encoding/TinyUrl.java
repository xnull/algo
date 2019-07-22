package encoding;

import java.lang.*;
import java.util.*;
import java.math.*;
import java.util.AbstractMap.SimpleEntry;

public class TinyUrl {
    public static void main(String[] arr) {
      //String code="abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXY0123456789";
      String code="abcd";
      //int i = 6;
      for(int i = 0; i< 25; i++){
        System.out.println(i + ", " +  toStr(generate(code, i), code));
        //System.out.println(i + ", " +  generate(code, i));
      }

      for(int i = 0; i< 25; i++){
        //System.out.println(i + ", " +  toStr(generate(code, i), code));
        System.out.println(i + ", " +  generate(code, i));
      }
    }

    //3 - aa
    //4 ab
    //5 ac
    //6 aaa

    static List<Integer> generate(String code, int counter){
      int base = code.length();

      List<Integer> result = new ArrayList<>();
     
      while(counter >= 0){
          int index = (counter % base);        
          char currChar = code.charAt(index);
          result.add(index); 
        
          counter /= base;

          if(counter == 0) {
            break;
          }
      }

      return result;
    }

    static String toStr(List<Integer> indexes, String code){
      StringBuilder result = new StringBuilder();

      for(int i = indexes.size()-1;i>=0;i--){
        result.append(code.charAt(indexes.get(i)));
      }

      return result.toString();
    }
}
