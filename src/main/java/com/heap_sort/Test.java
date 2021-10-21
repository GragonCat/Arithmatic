package com.heap_sort;

public class Test {
    public static void main(String[] args) {
//        int[] nums1 = new int[]{1};
//        int[] nums2 = new int[]{1};
//        Sort sort = new Sort();
//        double result =  sort.findMedianSortedArrays(nums1,nums2);
//        System.out.println(result);


    }
}

class Sort{
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length == 0 && nums2.length == 0){
            return 0;
        }
        int[] arr = new int[nums1.length+nums2.length];
        int nums1Index=0;
        int nums2Index=0;
        int arrIndex=0;
        double result = 0;
        if(nums1.length == 0){
            arr = nums2;
            if(arr.length % 2 == 1){
                result = (double)arr[arr.length/2];
            }else{
                result = ((double)arr[arr.length/2 - 1] + (double)arr[arr.length/2])/2;
            }
        }
        if(nums2.length == 0){
            arr = nums1;
            if(arr.length % 2 == 1){
                result = (double)arr[arr.length/2];
            }else{
                result = ((double)arr[arr.length/2 - 1] + (double)arr[arr.length/2])/2;
            }
        }
        while(nums1Index < nums1.length && nums2Index < nums2.length){
            if(nums1[nums1Index] < nums2[nums2Index]){
                arr[arrIndex] = nums1[nums1Index];
                nums1Index++;
            }else{
                arr[arrIndex] = nums2[nums2Index];
                nums2Index++;
            }
            arrIndex++;
        }

        if(nums1Index < nums1.length){
            while(nums1Index <= nums1.length-1 ){
                arr[arrIndex] = nums1[nums1Index];
                arrIndex++;
                nums1Index++;
            }
        }
        if(nums2Index < nums2.length){
            while(nums2Index <= nums2.length-1){
                arr[arrIndex] = nums2[nums2Index];
                arrIndex++;
                nums2Index++;
            }
        }

        for(int s : arr){
            System.out.println(s);
        }

        if(arr.length % 2 == 1){
            result = (double)arr[arr.length/2];
        }else{
            result = ((double)arr[arr.length/2 - 1] + (double)arr[arr.length/2])/2;
        }
        return result;
    }
}