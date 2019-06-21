package com.etc.mainboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MainbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainbootApplication.class, args);
        /*String password = "io.xjar";
        File plaintext = new File("/path/to/read/plaintext.jar");
        File encrypted = new File("/path/to/save/encrypted.jar");*/
        //XJar.encrypt(plaintext, encrypted, password);
    }

}
