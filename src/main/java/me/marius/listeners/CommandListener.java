package me.marius.listeners;

import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e){

        String message = e.getMessage().getContentDisplay();

        if(e.isFromType(ChannelType.TEXT)){
            TextChannel channel = e.getTextChannel();

            if(message.startsWith("#")){

                if(Main.plugin.getMySQL().getWartungen() == false) {

                    String[] args = message.substring(1).split(" ");

                    if (args.length > 0) {

                        if (!Main.plugin.getCmdManager().perform(args[0], e.getMember(), channel, e.getMessage())) {

                            EmbedBuilder info = new EmbedBuilder();
                            info.setTitle("Information");
                            info.setDescription("Der Befehl ist nicht bekannt.");
                            info.setFooter("Created by Marius", e.getMember().getUser().getAvatarUrl());
                            info.setColor(Color.RED);

                            channel.sendTyping().queue();
                            channel.sendMessage(info.build()).complete().delete().queueAfter(6, TimeUnit.SECONDS);
                            info.clear();

                        }
                    }
                } else if(Main.plugin.getMySQL().getWartungen() == true) {

                    if(e.getMember().hasPermission(Permission.ADMINISTRATOR)){
                        String[] args = message.substring(1).split(" ");

                        if (args.length > 0) {

                            if (!Main.plugin.getCmdManager().perform(args[0], e.getMember(), channel, e.getMessage())) {

                                EmbedBuilder info = new EmbedBuilder();
                                info.setTitle("Information");
                                info.setDescription("Der Befehl ist nicht bekannt.");
                                info.setFooter("Created by Marius", e.getGuild().getIconUrl());
                                info.setColor(Color.RED);

                                channel.sendTyping().queue();
                                channel.sendMessage(info.build()).complete().delete().queueAfter(6, TimeUnit.SECONDS);
                                info.clear();

                            }
                        }

                    } else {

                        channel.purgeMessages(Utils.get(channel, 1));
                        channel.sendTyping().queue();

                        EmbedBuilder info = new EmbedBuilder();
                        info.setTitle("Information");
                        info.setDescription("Der Bot wird derzeit geupdatet!");
                        info.setThumbnail(e.getGuild().getIconUrl());
                        info.setFooter("Created by Marius", e.getGuild().getIconUrl());
                        info.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
                        info.setColor(Color.RED);

                        channel.sendTyping().queue();
                        channel.sendMessage(info.build()).complete().delete().queueAfter(6, TimeUnit.SECONDS);
                        info.clear();
                    }
                }
            }
        }
    }
}
