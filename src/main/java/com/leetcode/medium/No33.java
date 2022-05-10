package com.leetcode.medium;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class No33 {
    public static int search(int[] nums, int target) {
        int len = nums.length;
        if(len == 1) return target == nums[0]?0:-1;
        int right = len-1;
        return search2(nums,target,0,right);
    }
    public static int binSearch(int nums[],int target,int left,int right){
        int mid = (right+left) / 2;
        if(target == nums[mid]){
            return mid;
        }
        if(left <= right ){
            return -1;
        }
        if(nums[left] <= target && target < nums[mid]){
            return binSearch(nums,target,left,mid-1);
        }else if(nums[mid] < target && target <= nums[right]){
            return binSearch(nums,target,mid+1,right);
        }else{
            return -1;
        }

    }
    public static int search2(int nums[],int target,int left,int right){
        int mid = (right+left) / 2;
        if(target == nums[mid]){
            return mid;
        }
        if(left <= right ){
            return -1;
        }
        if(nums[left] < nums[mid] && nums[mid+1] < nums[right]){
            if(nums[left] <= target && target < nums[mid]){
                return binSearch(nums,target,left,mid-1);
            }else if(nums[mid+1] <= target && target <= nums[right]){
                return binSearch(nums,target,mid+1,right);
            }else{
                return -1;
            }
        }else if(nums[left] < nums[mid]){
            if(nums[left] <= target && target < nums[mid]){
                return binSearch(nums,target,left,mid-1);
            }else{
                return search2(nums,target,mid+1,right);
            }
        }else{
            if(nums[mid] < target && target <= nums[right]){
                return binSearch(nums,target,mid+1,right);
            }else{
                return search2(nums,target,left,mid-1);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2};
        int target = 0;
        String a ="a";
        LinkedList<Character> link = new LinkedList<>();
        Deque deque = new ArrayDeque();
        char s = 's';
        a += s;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("").append("").append("").toString();
        System.out.println(a);
    }
}
