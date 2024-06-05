package fr.avahip.boto.discord.listeners;

import fr.avahip.boto.discord.models.MySlashCommand;
import fr.avahip.boto.discord.models.SlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class Boto extends ListenerAdapter {
    private final JDA jda;
    private final ApplicationEventPublisher publisher;

    public Boto(@Value("${discord.token}") String DISCORD_KEY, ApplicationEventPublisher publisher) {
        this.publisher = publisher;
        this.jda = JDABuilder.createDefault(DISCORD_KEY).setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT
                )
                .setChunkingFilter(ChunkingFilter.ALL)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.listening("Deftones"))
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(mesCommandes.stream().map(MySlashCommand::getData).toList()).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        mesCommandes.stream()
                .filter(mySlashCommand -> mySlashCommand.getCommand().name().equalsIgnoreCase(command))
                .findFirst()
                .ifPresent(mySlashCommand -> publisher.publishEvent(mySlashCommand.getDiscordEvent(this, event)));
    }

    List<MySlashCommand> mesCommandes = List.of(
            new MySlashCommand(SlashCommand.ping,
                    "ping",
                    List.of(new OptionData(OptionType.STRING, "text", "pong lol", true)))
    );
}
