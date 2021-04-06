package pers.jin;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author DEUSJIN
 * @date 2020/5/23
 */
public class Solution {
    /**
     * 字典
     */
    public  Node root;
    public Map<String,String> map = new HashMap<>();
    public Solution() throws IOException{
        root = new Node();
        FileReader fr = new FileReader("d:\\Dictionary.txt");
        int x = 0;
        int len = 0;
        char[] tmp = new char[50];
        while((len = fr.read())!=-1){
            if(len == 13){
                continue;
            }
            if(len == 10) {
                this.add(new String(tmp, 0, x));
                tmp = new char[50];
                x = 0;
                continue;
            }
            tmp[x++] = (char)len;
        }
    }
    public  void add(String s) {
        int idx = s.indexOf("   ")+3;
        if(idx == 2){
            return;
        }
        String word = s.substring(0,idx-3);
        String chinese = s.substring(idx);
        StringBuffer sb1 = new StringBuffer();
        for(int i = 0;i<chinese.length();i++){
            char c = chinese.charAt(i);
            if(isChinese(c)){
                sb1.append(c);
            }else{
                if(sb1.length()>0){
                    map.put(sb1.toString(),word);
                    sb1 = new StringBuffer();
                }
            }
        }
        if(sb1.length()>0){
            map.put(sb1.toString(),word);
        }
        Node tmp = this.root;
        for(int i = 0;i<word.length();i++){
            char c = word.charAt(i);
            if(c<'a'||c>'z'){
                continue;
            }
            if(!tmp.containsKey(c)){
                tmp.put(c,new Node());
            }
            tmp = tmp.get(c);
        }
        tmp.Chinese = chinese;
    }
    public String search(String word){
        Node node = root;
        word = word.toLowerCase();
        for(int i = 0;i<word.length();i++){
           char c = word.charAt(i);
            if(c<'a'||c>'z'){
                return null;
            }
            if(node.containsKey(c)){
                node = node.get(c);
            }else{
                System.out.println("未找到该单词");
                return "未找到该单词";
            }
        }
        return node.Chinese;
    }
    public String searchChinese(String chinese){
        return map.get(chinese);
    }
    public List<String> preSearch(String word){
        Node node = root;
        List<String> list = new ArrayList<>();
        if(word.length()==0){
            list.add("");
            return list;
        }
        for(int i = 0;i<word.length();i++){
            char c = word.charAt(i);
            if(c<'a'||c>'z'){
                list.add("");
                return list;
            }
            if(node.containsKey(c)){
                node = node.get(c);
            }else{
                list.add("");
                return list;
            }
        }
        int num = 0;
        if(node.Chinese!=null){
            list.add(word);
        }
        StringBuffer sb;
        for(int i = 0;i<26;i++){
            char c = (char) ('a'+i);
            sb = new StringBuffer(word);
            if(node.containsKey(c)){
                sb.append(c).append(this.findWord(node.get(c)));
                list.add(sb.toString());
            }
        }
        return list;
    }
    public StringBuffer findWord(Node node){
        StringBuffer sb =  new StringBuffer();
        for(int i = 0;i<26;i++){
            char c = (char)('a'+i);
            if(node.containsKey(c)){
                sb.append(c).append(this.findWord(node.get(c)));
                return sb;
            }
        }
        return sb;
    }
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
        ) {
            return true;
        }
        return false;
    }
    public static void main(String[] args)throws IOException {
        Frame f = new Frame();
    }
}
