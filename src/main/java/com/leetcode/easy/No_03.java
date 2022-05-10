package com.leetcode.easy;

public class No_03 {

    public static int[]  findMedianSortedArrays(int[] nums1, int[] nums2) {
        // ArrayList res = new ArrayList();
        // if(nums1.length == 0){
        //     res = Array.asList(nums2);
        // }else if(nums1.length == 0){
        //     res = Array.asList(nums1);
        // }else{

        // ArrayList l1 = Array.asList(nums1);
        // ArrayList l2 = Array.asList(nums2);
        // Iteracter<Integer> i1 = l1.iteractor();
        // Iteracter<Integer> i2 = l2.iteractor();
        // while(i1.hasNex() && i2.hasNext()){
        //     int num1 = i1.next();
        //     int num2 = i2.next();



        // }
        int[] res = new int[nums1.length+nums2.length];
        if(nums1.length == 0){
            res = nums2;
        }else if(nums1.length == 0){
            res = nums1;
        }else{
            int i = 0;
            int j = 0;
            for(;i < nums1.length && j < nums2.length;){
                int num1 = nums1[i];
                int num2 = nums2[j];

                if(num1 < num2){
                    res[i+j] = num1;
                    i++;
                }else{
                    res[i+j] = num2;
                    j++;
                }

            }
            if(i == nums1.length){
                for(;j < nums2.length;j++){
                    res[i+j] = nums2[j];
                }
            }else{
                for(;i < nums1.length;i++){
                    res[i+j] = nums1[i];
                }
            }

        }
        return  res;
    }

    public static void main(String[] args) {
        String s1 = new String("Hydra");
        String s2 = s1.intern();
        System.out.println(s1 == s2);
        System.out.println(s1 == "Hydra");
        System.out.println(s2 == "Hydra");
    }
}
