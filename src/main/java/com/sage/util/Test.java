package com.sage.util;

import java.util.ArrayList;

public class Test {

    private static int temp = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            prit();
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
