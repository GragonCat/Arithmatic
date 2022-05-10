package com.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {


        public int[] twoSum(int[] nums, int target) {
            Map<Integer,Integer> map = new HashMap<>();
            int size = nums.length;
            for(int i = 0;i < size;i++){
                int temp = nums[i];
                if(map.containsKey(Integer.valueOf(target-temp))){
                    int index = map.get(Integer.valueOf(target-temp));
                    return new int[]{i,index};
                }else{
                    map.put(Integer.valueOf(temp),Integer.valueOf(i));
                }
            }
        return new int[]{};
    }


    public static void main(String[] args) {
        System.out.println(13%10);
        System.out.println(3/10);

        System.out.println(3/2);
        System.out.println(5/2);
    }


}
