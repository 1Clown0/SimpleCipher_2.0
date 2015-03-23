package com.company;

import com.company.GUI.Form;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new Form();
        System.out.println(Keygen.MakeKey("pavel",1000,2));
    }
}
