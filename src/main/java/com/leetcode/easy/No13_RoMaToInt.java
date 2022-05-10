package com.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class No13_RoMaToInt {

    public static Map<Character,Integer> map = new HashMap<>();
    static{
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
    }

    public static void main(String[] args) {
        String s = "III";
        System.out.println(romanToInt(s));
    }
    public static int romanToInt(String s) {
        int length = s.length();
        int tempVal = 0;
        int result = 0;;
        for(int i = 0;i <length;i++){
            char c = s.charAt(i);
            if(tempVal < map.get(c)){
                result -= tempVal;
            }else{
                result += tempVal;
            }
            tempVal = map.get(c);
        }
        result+=tempVal;
        return result;
    }
}
