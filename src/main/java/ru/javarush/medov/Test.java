package ru.javarush.medov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.javarush.medov.config.AppConfig;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
