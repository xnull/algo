package search;

import java.util.*;

class Main {
    public static void main(String[] args) {
        System.out.println("Start app");

        //int[] arr = new int[]{99, 11, 22, 54, 1, 0, 76, 3, -5};
        int[] arr = new int[]{1,2,3,4,5,6,7};

        int result = new QuickSelect().select(arr, 1);

        System.out.println(result);
        //System.out.println(Arrays.toString(arr));
    }
}

class QuickSelect {
    class Part {
        int low;
        int high;

        Part(int low, int high){
            this.low = low;
            this.high = high;
        }
    }

    int select(int[] arr, int kPosition){
        Queue<Part> queue = new LinkedList<>();
        queue.add(new Part(0, arr.length-1));

        int pos = Math.max(0, arr.length - kPosition -1);

        while(!queue.isEmpty()){
            Part currPart = queue.poll();

            if(currPart.low == currPart.high) {
                continue;
            }

            int index = partition(arr, currPart);

            if(index-1 == pos){
                return arr[index];
            }

            if(arr[index]-1 < pos){
                queue.add(new Part(index, currPart.high));
            }

            if(arr[index]-1 > pos){
                queue.add(new Part(currPart.low, index));
            }
        }

        return arr[0];
    }

    int partition(int[] arr, Part part){
        int pivot = arr[part.low];

        int left = part.low;
        int right = part.high;

        while(left <= right) {
            while(arr[left] < pivot){
                left++;
            }

            while(arr[right] > pivot) {
                right--;
            }

            if(left > right) {
                continue;
            }

            int tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }

        return left;
    }
}