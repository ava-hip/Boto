package fr.avahip.boto.discord.event;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class OtherEvent extends DiscordEvent{

    @Getter
    private final boolean accept;

    public OtherEvent(Object source, SlashCommandInteractionEvent event, boolean accept) {
        super(source, event);
        this.accept = accept;
    }
}
