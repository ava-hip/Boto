package fr.avahip.boto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BotoApplication {

    @Value("${discord.token}")
    private String DISCORD_KEY;

    public static void main(String[] args) {
        SpringApplication.run(BotoApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void test() {
    }
}
