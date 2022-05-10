package com.leetcode.medium;

import java.util.LinkedList;

public class No38 {
    public String countAndSay(int n) {
        return say(n);
    }
    public String say(int n){
        if(n == 1) return "1";
        LinkedList<Character> link = new LinkedList();
        String s = say(n-1);
        char[] c = s.toCharArray();
        StringBuilder res = new StringBuilder("");
        for(int i = c.length-1;i >= 0;i--){
            link.push(c[i]);
        }
        int charCount = 1;
        int loopCount = 0;
        char last = 'a';
        while(!link.isEmpty()){
            char a = link.pop();
            if(loopCount == 0){
                last = a;
            }else{
                if(last == a){
                    charCount++;
                }else{
                    res.append(charCount).append(last);
                    last = a;
                    charCount = 1;
                }
            }
            loopCount++;
        }
        return res.append(charCount).append(last).toString();
    }
}
