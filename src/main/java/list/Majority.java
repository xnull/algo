package list;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/majority-element/description/
 */
class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();

        for (int num : nums) {
            if (counts.keySet().contains(num)) {
                counts.put(num, counts.get(num) + 1);
            } else {
                counts.put(num, 1);
            }
        }


        int count = 1;
        int num = nums[0];

        for (int currNum : counts.keySet()) {
            if (counts.get(currNum) > count) {
                count = counts.get(currNum);
                num = currNum;
            }
        }

        return num;
    }
}
