package com.sage.util;

import java.util.ArrayList;

public class Test {

    private static int temp = 0;

    public static void main(String[] args) {
        int j = 1;
        for (int i = 0; i < 7; i++) {
            if (j<3){
                j+=1;
                continue;
            }
            System.out.println(j);
        }
    }
    
    private static void prit(){
        ArrayList<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("44");
        list.add("55");
        System.out.println(list.get(temp));
        temp+=1;
    }
}
