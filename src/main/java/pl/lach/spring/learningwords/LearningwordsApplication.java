package pl.lach.spring.learningwords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.lach.spring.learningwords.controller.LinguController;

import java.util.Scanner;

@SpringBootApplication
public class LearningwordsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearningwordsApplication.class, args);

        LinguController controller = context.getBean(LinguController.class);
        controller.mainLoop();
    }


    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}
