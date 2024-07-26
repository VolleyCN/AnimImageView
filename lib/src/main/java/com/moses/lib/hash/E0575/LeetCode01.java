package com.moses.lib.hash.E0575;

import java.util.HashMap;

public class LeetCode01 {
    public static void main(String[] args) {
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = target - nums[i];
            if (map.containsKey(x)) {
                return new int[]{map.get(x), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }
}
