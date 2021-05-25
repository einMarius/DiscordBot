package me.marius.listeners;

import me.marius.main.Main;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChannelJoinEvent extends ListenerAdapter {

    public static ArrayList<String> inChannel = new ArrayList<>();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){

        if(!e.getMember().getId().equalsIgnoreCase("811985115306655774") ||
                !e.getMember().getId().equalsIgnoreCase("235088799074484224") || !e.getMember().getId().equalsIgnoreCase("252128902418268161")) {

            inChannel.add(e.getMember().getUser().getId());

            if (inChannel.contains(e.getMember().getUser().getId())) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    int sekunden = 0;

                    @Override
                    public void run() {

                        sekunden++;

                        if(sekunden == 1){
                            System.out.println("1. Sekunde");
                        }

                        if (sekunden == 60) {

                            //nach 1 Minute hinzufügen!
                            if (!Main.plugin.getMySQL().userIsExisting(e.getMember().getId())) {
                                Main.plugin.getMySQL().createNewPlayer(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 0, 0, 0, 0, 1);
                                System.out.println(e.getMember().getUser().getName() + " hat +1 ChannelTime bekommen");
                            } else {
                                Main.plugin.getMySQL().setChannelTime(e.getMember().getId(), e.getMember().getUser().getName(), 1);
                                System.out.println(e.getMember().getUser().getName() + " hat +1 ChannelTime bekommen");
                            }
                        }
                    }
                }, 0);
            }
        }
    }
}
