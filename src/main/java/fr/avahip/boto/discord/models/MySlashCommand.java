package fr.avahip.boto.discord.models;

import fr.avahip.boto.event.DiscordEvent;
import lombok.Data;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.Locale;

@Data
public class MySlashCommand {
    private SlashCommand command;
    private CommandData data;
    private ApplicationEvent event;

    public MySlashCommand(SlashCommand command, String description, Collection<OptionData> options) {
        this.command = command;
        data = Commands.slash(command.name().toLowerCase(Locale.ROOT), description).addOptions(options);
    }

    public DiscordEvent getDiscordEvent(Object source, SlashCommandInteractionEvent event) {
        return new DiscordEvent(source, event);
    }
}
