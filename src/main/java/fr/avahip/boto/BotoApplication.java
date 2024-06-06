package fr.avahip.boto;

import fr.avahip.boto.discord.event.DiscordEvent;
import fr.avahip.boto.discord.event.PingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class BotoApplication {

    @Value("${discord.token}")
    private String DISCORD_KEY;

    public static void main(String[] args) {
        SpringApplication.run(BotoApplication.class, args);
    }

    @EventListener(PingEvent.class)
    public void reactToPingEvent(DiscordEvent discordEvent) {discordEvent.getEvent().reply("Hello").queue();}

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}
