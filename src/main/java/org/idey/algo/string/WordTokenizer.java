package org.idey.algo.string;

import java.util.ArrayList;
import java.util.List;

public class WordTokenizer {
    public static List<String> getAllWords(String str){
        if(str==null){
            throw new IllegalArgumentException("Invalid String");
        }else{
            char[] array = str.toCharArray();
            String subString = "";
            boolean isQuoted = false;
            List<String> words = new ArrayList<>();
            for(char ch:array){
                if(ch!=' ' && ch!='"'){
                    subString+=ch;
                }else if(ch=='"'){
                    subString+=ch;
                    isQuoted = !isQuoted;
                }else{
                    if(isQuoted){
                        subString+=ch;
                    }else{
                        if(!("").equals(subString)){
                            words.add(subString);
                            subString="";
                            isQuoted = false;
                        }
                    }
                }
            }
            if(!("").equals(subString)){
                words.add(subString);
            }
            return words;
        }
    }

    public static void main(String[] args) {
        System.out.println(getAllWords("ABC  CDE \"XX YY     ZZ\" 1"));
    }
}
