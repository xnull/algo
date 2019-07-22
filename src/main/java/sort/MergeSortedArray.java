package sort;

import java.lang.*;
import java.util.PriorityQueue;

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(nums2.length == 0){
            return;
        }
        
        int nums1Counter = 0;
        int nums2Counter = 0;
        int resultCounter = 0;
        
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        if(m >0){
          queue.add(nums1[nums1Counter]);
          nums1Counter++;
        }
        if(n>0){
         queue.add(nums2[nums2Counter]);
         nums2Counter++;
        }
        
        while(!queue.isEmpty()){
            int curr = queue.poll();
            
            nums1[resultCounter] = curr;
            resultCounter++;
            
            if(nums1Counter < m){
                if(curr == nums1[nums1Counter-1]){
                  queue.add(nums1[nums1Counter]);
                  nums1Counter++;
                }
            }
            
            if(nums2Counter < n){
                if(curr == nums1[nums2Counter-1]){
                  queue.add(nums2[nums2Counter]);
                  nums2Counter++;
                }
            }
        }
    }
}
