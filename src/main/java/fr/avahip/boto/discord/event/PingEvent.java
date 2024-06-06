package fr.avahip.boto.discord.event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingEvent extends DiscordEvent {
    public PingEvent(Object source, SlashCommandInteractionEvent event) {
        super(source, event);
    }
}
