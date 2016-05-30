package com.xmlprocessor.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class ParserApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParserApplication.class, args);
        List<Path> files = FileSelector.getFiles("/Users/shailesh/tmp");
        System.out.println("Selected files : " + files.size());

    }

}





