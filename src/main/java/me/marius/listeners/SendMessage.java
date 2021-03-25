package me.marius.listeners;

import me.marius.main.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class SendMessage extends ListenerAdapter {

    private HashMap<Member, Long> cooldown = new HashMap<>();
    private int cooldowntime = 2*60;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        TextChannel channel = e.getTextChannel();

        if(e.isFromType(ChannelType.TEXT)) {
            if(!e.getMember().getUser().getName().equalsIgnoreCase("Baumbalabunga")) {
                if(!e.getMessage().getContentDisplay().startsWith("#")) {

                    if (cooldown.containsKey(e.getMember())) {
                        long secondsleft = ((cooldown.get(e.getMember()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
                        if (secondsleft > 0) {
                            System.out.println(e.getMember().getUser().getName() + " hat eine Nachricht geschrieben, obwohl der Cooldown für ihn noch aktiviert ist");
                        } else if (secondsleft < 0) {

                            //MySQL
                            if (!Main.plugin.getMySQL().userIsExisting(e.getMember().getUser().getId())) {
                                Main.plugin.getMySQL().createNewPlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1);
                                cooldown.put(e.getMember(), System.currentTimeMillis());
                            } else {
                                Main.plugin.getMySQL().updatePlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1);
                                cooldown.put(e.getMember(), System.currentTimeMillis());
                            }
                        }

                    } else {
                        //MySQL
                        if (!Main.plugin.getMySQL().userIsExisting(e.getMember().getUser().getId())) {
                            Main.plugin.getMySQL().createNewPlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1);
                            cooldown.put(e.getMember(), System.currentTimeMillis());
                        } else {
                            Main.plugin.getMySQL().updatePlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1);
                            cooldown.put(e.getMember(), System.currentTimeMillis());
                        }
                    }
                }
            }
        }
    }
}
