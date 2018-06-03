import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1,3,5,7,9};
        int[] arr2 = new int[]{3,6,7,8,9,10,11,12};

        List<Integer> res = new ArrayList<>();
        int arr1Index = 0;
        int arr2Index = 0;
        
        while(true){
          if(arr1Index == arr1.length || arr2Index == arr2.length){
            break;
          }

          if(arr1[arr1Index] == arr2[arr2Index]){
            res.add(arr1[arr1Index]);
            arr1Index++;
            arr2Index++;
            continue;
          }

          if(arr1[arr1Index] > arr2[arr2Index]){
            arr2Index++;
          } else {
            arr1Index++;
          }
        }

        System.out.println(res);
    }
}
