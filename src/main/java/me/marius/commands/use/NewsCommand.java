package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class NewsCommand implements ServerCommand {

    private boolean Newsisrunning;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        if (m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {

            if (channel.getName().equalsIgnoreCase("news") || channel.getName().equalsIgnoreCase("test")) {

                String args[] = message.getContentDisplay().split(" ");

                if (args.length >= 2) {

                    int amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    Newsisrunning = !Newsisrunning;

                    if (Newsisrunning) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();

                                while (Newsisrunning) {

                                    try {
                                        Thread.sleep(100);
                                    }catch(InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                    }

                                    String args[] = message.getContentRaw().split(" ");

                                    Calendar cal = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");

                                    String news = "";
                                    for (int i = 1; i < args.length; i++)
                                        news = String.valueOf(news) + " " + args[i];

                                    EmbedBuilder info = new EmbedBuilder();
                                    info.setTitle("🎉 **Neuigkeiten** 🎉");
                                    info.setAuthor(m.getNickname());
                                    info.setThumbnail(m.getGuild().getIconUrl());
                                    info.addField("Erwähung", "<@&816243134090444812>", false);
                                    info.setDescription(news);
                                    info.setFooter(
                                            m.getUser().getName() + " hat eine Neuigkeit gepostet",
                                            m.getUser().getAvatarUrl());
                                    info.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
                                    info.setColor(Color.green);

                                    channel.sendMessage("||<@&816243134090444812>|| \n").embed(info.build()).queue();
                                    info.clear();

                                    Newsisrunning = false;
                                }
                            }
                        }.start();
                    }

                    return;

                } else {
                    int amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendMessage("Benutze: #new NEUIGKEITEN").complete().delete().queueAfter(5,
                            TimeUnit.SECONDS);
                }
            } else {
                int amount = 1;
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendMessage("Dies funktioniert nur im #news Channel").complete().delete().queueAfter(5,
                        TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
