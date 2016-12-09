package org.libre.lingvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.TimeZone;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LibreLingvoApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(LibreLingvoApplication.class, args);
    }
}
