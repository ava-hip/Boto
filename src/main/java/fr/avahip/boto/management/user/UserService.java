package fr.avahip.boto.management.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    @EventListener(MessageReceivedEvent.class)
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            User user = repository.findById(event.getAuthor().getId())
                    .orElseGet(() -> repository.save(new User(event.getAuthor().getId())));
            user.setXp(user.getXp() + 1);
            repository.save(user);
            if(event.getMember().getRoles().stream().anyMatch(role -> role.getName().equals("FLOPESQUE"))) {
                event.getMessage().delete().queue();
            }
            if (event.getMessage().getContentRaw().contains("FLOPESQUE")) {
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("FLOPESQUE", false).get(0)).queue();
            }
        }
    }
}
