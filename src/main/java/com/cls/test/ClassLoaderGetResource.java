package com.cls.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClassLoaderGetResource {

    static String getResource(String rsc) {
        String val = "";

        try {
            Class cls = ClassLoaderGetResource.class;

            // returns the ClassLoader object associated with this Class
            ClassLoader cLoader = cls.getClassLoader();

            // input stream
            InputStream i = cLoader.getResourceAsStream(rsc);
            BufferedReader r = new BufferedReader(new InputStreamReader(i));

            // reads each line
            String l;
            while((l = r.readLine()) != null) {
                val = val + l;
            }
            i.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return val;
    }

    public static void main(String[] args) {

        System.out.println("File1: " + getResource("file.txt"));
//        System.out.println("File2: " + getResource("test.txt"));
    }

}
