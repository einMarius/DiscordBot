package me.marius.listeners;

import me.marius.main.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class ReactionsListener extends ListenerAdapter {

    private HashMap<Member, Long> cooldown = new HashMap<>();
    private int cooldowntime = 2*60;

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {

        if(!e.getMember().getUser().getName().equalsIgnoreCase("Baumbalabunga")) {
            if(!e.getChannel().getName().equalsIgnoreCase("role-selection") || e.getChannel().getName().equalsIgnoreCase("umfragen") ||
                    e.getChannel().getName().equalsIgnoreCase("news")){

                if (cooldown.containsKey(e.getMember())) {
                    long secondsleft = ((cooldown.get(e.getMember()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
                    if (secondsleft > 0) {
                        System.out.println(e.getMember().getUser().getName() + " hat eine Reaktion hinzugefügt, obwohl der Cooldown für ihn noch aktiviert ist");
                        Main.plugin.getMySQL().setPunkte(e.getMember().getId(), e.getMember().getUser().getName(), 0, 0, 1);
                    } else if (secondsleft < 0) {
                        Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 0, 1);
                        cooldown.put(e.getMember(), System.currentTimeMillis());
                    }

                } else {
                    //MySQL
                    if (!Main.plugin.getMySQL().userIsExisting(e.getMember().getUser().getId())) {
                        Main.plugin.getMySQL().createNewPlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 0, 1, 0);
                        cooldown.put(e.getMember(), System.currentTimeMillis());
                    } else {
                        Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 0, 1);
                        cooldown.put(e.getMember(), System.currentTimeMillis());
                    }
                }
            }
        }
    }
}
