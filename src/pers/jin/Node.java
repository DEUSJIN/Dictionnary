package pers.jin;

public class Node {
    Node[] words;
    String Chinese;
    public Node(){
        words = new Node[26];
    }
    public boolean containsKey(char ch){
        return words[ch-'a']!=null;
    }
    public void put(char ch,Node w){
        words[ch-'a'] = w;
    }
    public Node get(char ch){
        return words[ch-'a'];
    }
}
