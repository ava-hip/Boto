package fr.avahip.boto.discord.models;

import fr.avahip.boto.discord.event.DiscordEvent;
import fr.avahip.boto.discord.event.PingEvent;
import lombok.Data;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collection;
import java.util.Locale;
import java.util.function.BiFunction;

@Data
public class MySlashCommand {
    private SlashCommand command;
    private CommandData data;
    private BiFunction<Object, SlashCommandInteractionEvent, DiscordEvent> discordEvent;

    public MySlashCommand(SlashCommand command, String description, Collection<OptionData> options, BiFunction<Object, SlashCommandInteractionEvent, DiscordEvent> discordEvent) {
        this.command = command;
        data = Commands.slash(command.name().toLowerCase(Locale.ROOT), description).addOptions(options);
        this.discordEvent = discordEvent;
    }

    public DiscordEvent getDiscordEvent(Object source, SlashCommandInteractionEvent event) {
        return new PingEvent(source, event);
    }
}
