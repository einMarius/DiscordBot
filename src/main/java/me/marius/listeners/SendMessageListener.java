package me.marius.listeners;

import me.marius.main.LevelRoles;
import me.marius.main.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class SendMessageListener extends ListenerAdapter {

    private HashMap<Member, Long> cooldown = new HashMap<>();
    private int cooldowntime = 2*60;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(e.isFromType(ChannelType.TEXT)) {
            if(!e.getMember().getUser().getName().equalsIgnoreCase("Baumbalabunga")) {
                if(!e.getMessage().getContentDisplay().startsWith("#")) {
                    if(!e.getMember().getUser().getId().equalsIgnoreCase("235088799074484224") || e.getMember().getUser().getId().equalsIgnoreCase("252128902418268161")) {
                        if (cooldown.containsKey(e.getMember())) {
                            long secondsleft = ((cooldown.get(e.getMember()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
                            if (secondsleft > 0) {
                                System.out.println(e.getMember().getUser().getName() + " hat eine Nachricht geschrieben, obwohl der Cooldown für ihn noch aktiviert ist");
                                Main.plugin.getMySQL().setPunkte(e.getMember().getId(), e.getMember().getUser().getName(), 0, 1, 0);
                            } else if (secondsleft < 0) {
                                //RANK 5
                                if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 10000){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 4
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 1000){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 3
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 500){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 2
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 100){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 1
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 50){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //UNRANKED
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) < 50) {
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                }

                                cooldown.put(e.getMember(), System.currentTimeMillis());
                                LevelRoles.addRoles(e.getMember());
                            }

                        } else {
                            //MySQL
                            if (!Main.plugin.getMySQL().userIsExisting(e.getMember().getUser().getId())) {
                                Main.plugin.getMySQL().createNewPlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0, 0);
                                e.getMember().getGuild().addRoleToMember(e.getMember().getId(), e.getMember().getJDA().getRoleById("824983261197500440")).queue();
                                cooldown.put(e.getMember(), System.currentTimeMillis());
                            } else {
                                //RANK 5
                                if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 10000){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 4
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 1000){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 3
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 500){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 2
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 100){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //RANK 1
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) >= 50){
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                    //UNRANKED
                                } else if(Main.plugin.getMySQL().getPunkte(e.getMember().getId()) < 50) {
                                    Main.plugin.getMySQL().setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 1, 0);
                                }

                                cooldown.put(e.getMember(), System.currentTimeMillis());
                                LevelRoles.addRoles(e.getMember());
                            }
                        }
                    }
                }
            }
        }
    }
}
