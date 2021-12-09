package com.leetcode.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 30. 串联所有单词的子串
 * 给定一个字符串 s 和一些 长度相同 的单词 words 。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 *
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符 ，但不需要考虑 words 中单词串联的顺序。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 * 示例 2：
 *
 * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 输出：[]
 * 示例 3：
 *
 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 输出：[6,9,12]
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 104
 * s 由小写英文字母组成
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * words[i] 由小写英文字母组成
 *
 *
 */
public class No30_FindSubString {
    public static List<Integer> findSubstring(String s, String[] words) {
        int wordLen = words[0].length();
        int wordsLen = wordLen*words.length;
        int sLen = s.length();
        List<Integer> result = new ArrayList();
        if(sLen < wordsLen){
            return result;
        }

        Map<String,String> record = new HashMap<>();
        //遍历每个单词，查找单词在字符串s中出现的位置
        for(int i = 0;i<words.length;i++){
            int start = s.indexOf(words[i],0);
            //记录使用过的单词，防止出现重复结果
            if(record.containsKey(words[i])){
                continue;
            }else{
                record.put(words[i],words[i]);
            }
            // 单词可能在字符串中出现多次，需要循环遍历
            while(start >= 0){
                //利用hashmap 记录所有单词,方便对比字符串是否连续
                Map<String,Integer> map = new HashMap<>();
                for(int j = 0;j<words.length;j++){
                    if(map.get(words[j]) == null){
                        map.put(words[j],1);
                    }else{
                        map.put(words[j],map.get(words[j])+1);
                    }
                }

                //记录剩余的单词数，所有单词都匹配了（count为0）才算完全匹配
                int count = words.length - 1;
                int index = start;
                //如果没有查找到 或者 s字符串剩余长度不足
                if(index < 0 || (sLen-index)< wordsLen){
                    start = s.indexOf(words[i],start+1);
                    continue;
                }
                // 查找到了字符串，记录减1
                map.put(words[i],map.get(words[i])-1);
                //下一个子串的索引
                index += wordLen;
                //往后遍历字符串
                for(;index < sLen && count > 0;index += wordLen){
                    if(index >= sLen){
                        break;
                    }
                    if(count <= 0){
                        break;
                    }
                    String str = s.substring(index,(index + wordLen));
                    if(!map.containsKey(str) || map.get(str) <= 0){
                        break;
                    }
                    map.put(str,map.get(str)-1);
                    count--;
                }
                if(count == 0)  result.add(start);
                start = s.indexOf(words[i],start+1);

            }
        }

        return result;
    }

    public static void main(String[] args) {

        String a = "a";
        String[] b = new String[]{"a"};
        List<Integer> substring = findSubstring(a, b);
        substring.stream().forEach(System.out::println);
    }

}
