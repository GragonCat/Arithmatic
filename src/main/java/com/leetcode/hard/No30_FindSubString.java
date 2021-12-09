package com.leetcode.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 30. �������е��ʵ��Ӵ�
 * ����һ���ַ��� s ��һЩ ������ͬ �ĵ��� words ���ҳ� s ��ǡ�ÿ����� words �����е��ʴ����γɵ��Ӵ�����ʼλ�á�
 *
 * ע���Ӵ�Ҫ�� words �еĵ�����ȫƥ�䣬�м䲻���������ַ� ��������Ҫ���� words �е��ʴ�����˳��
 *
 *
 *
 * ʾ�� 1��
 *
 * ���룺s = "barfoothefoobarman", words = ["foo","bar"]
 * �����[0,9]
 * ���ͣ�
 * ������ 0 �� 9 ��ʼ���Ӵ��ֱ��� "barfoo" �� "foobar" ��
 * �����˳����Ҫ, [9,0] Ҳ����Ч�𰸡�
 * ʾ�� 2��
 *
 * ���룺s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * �����[]
 * ʾ�� 3��
 *
 * ���룺s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * �����[6,9,12]
 *
 *
 * ��ʾ��
 *
 * 1 <= s.length <= 104
 * s ��СдӢ����ĸ���
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * words[i] ��СдӢ����ĸ���
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
        //����ÿ�����ʣ����ҵ������ַ���s�г��ֵ�λ��
        for(int i = 0;i<words.length;i++){
            int start = s.indexOf(words[i],0);
            //��¼ʹ�ù��ĵ��ʣ���ֹ�����ظ����
            if(record.containsKey(words[i])){
                continue;
            }else{
                record.put(words[i],words[i]);
            }
            // ���ʿ������ַ����г��ֶ�Σ���Ҫѭ������
            while(start >= 0){
                //����hashmap ��¼���е���,����Ա��ַ����Ƿ�����
                Map<String,Integer> map = new HashMap<>();
                for(int j = 0;j<words.length;j++){
                    if(map.get(words[j]) == null){
                        map.put(words[j],1);
                    }else{
                        map.put(words[j],map.get(words[j])+1);
                    }
                }

                //��¼ʣ��ĵ����������е��ʶ�ƥ���ˣ�countΪ0��������ȫƥ��
                int count = words.length - 1;
                int index = start;
                //���û�в��ҵ� ���� s�ַ���ʣ�೤�Ȳ���
                if(index < 0 || (sLen-index)< wordsLen){
                    start = s.indexOf(words[i],start+1);
                    continue;
                }
                // ���ҵ����ַ�������¼��1
                map.put(words[i],map.get(words[i])-1);
                //��һ���Ӵ�������
                index += wordLen;
                //��������ַ���
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
