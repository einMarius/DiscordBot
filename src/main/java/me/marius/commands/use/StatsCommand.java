package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StatsCommand implements ServerCommand {

    String[] colours =  new String[] {
            "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff"
    };

    private int amount = 1;

    private boolean isRunningOwnStats;
    private boolean isRunningOtherStats;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentRaw().split(" ");

        if (channel.getId().equalsIgnoreCase("825103970270707743")) {
            if(args.length == 1){

                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();

                channel.sendMessage("Searching for Stats...").complete().delete().queueAfter(500, TimeUnit.MILLISECONDS);

                isRunningOwnStats = !isRunningOwnStats;
                new Thread(() -> {
                    while(isRunningOwnStats){
                        try {
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("◽ **Stats von " + m.getUser().getName() + "** ◽");
                        builder.setDescription("**Hier siehst du die Stats**");
                        builder.addField(">>> Der Rank", "Rank: `" + Main.plugin.getMySQL().getRank(m.getUser().getId()) + "` ", false);
                        builder.addField(">>> Die Punkte", "Punkte: `" + Main.plugin.getMySQL().getPunkte(m.getUser().getId()) + "`", false);
                        builder.addField(">>> Die gesendeten Nachrichten", "Nachrichten: `" + Main.plugin.getMySQL().getNachrichten(m.getId()) + "`", false);
                        builder.addField(">>> Die hinzugefügten Reaktionen", "Reaktionen: `" + Main.plugin.getMySQL().getReaktionen(m.getId()) + "`", false);
                        builder.setThumbnail(m.getUser().getAvatarUrl());
                        builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                        Random rand = new Random();
                        int i = rand.nextInt(colours.length);

                        String colour = colours[i];

                        builder.setColor(Color.decode("0x" + colour));

                        channel.sendMessage(builder.build()).queue();
                        builder.clear();

                        isRunningOwnStats = false;
                    }
                }).start();

            } else if(args.length == 2){

                Member targett = message.getMentionedMembers().get(0);

                if(!targett.getId().equalsIgnoreCase("811985115306655774") && !targett.getId().equalsIgnoreCase("235088799074484224") && !targett.getId().equalsIgnoreCase("252128902418268161")) {

                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    channel.sendMessage("Searching for Stats...").complete().delete().queueAfter(500, TimeUnit.MILLISECONDS);

                    isRunningOtherStats = !isRunningOtherStats;
                    new Thread(() -> {
                        while (isRunningOtherStats) {
                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("◽ **Stats von " + targett.getUser().getName() + "** ◽");
                            builder.setDescription("**Hier siehst die Stats**");
                            builder.addField(">>> Der Rank", "Rank: `" + Main.plugin.getMySQL().getRank(targett.getUser().getId()) + "` ", false);
                            builder.addField(">>> Die Punkte", "Punkte: `" + Main.plugin.getMySQL().getPunkte(targett.getUser().getId()) + "`", false);
                            builder.addField(">>> Die gesendeten Nachrichten", "Nachrichten: `" + Main.plugin.getMySQL().getNachrichten(targett.getUser().getId()) + "`", false);
                            builder.addField(">>> Die hinzugefügten Reaktionen", "Reaktionen: `" + Main.plugin.getMySQL().getReaktionen(targett.getUser().getId()) + "`", false);
                            builder.setThumbnail(targett.getUser().getAvatarUrl());
                            builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                            //COLOUR
                            Random rand = new Random();
                            int i = rand.nextInt(colours.length);
                            String colour = colours[i];
                            builder.setColor(Color.decode("0x" + colour));

                            channel.sendMessage(builder.build()).queue();
                            builder.clear();

                            isRunningOtherStats = false;
                        }
                    }).start();
                }else {
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendMessage("Diese Stats sind verborgen :D").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            }else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendMessage("Benutze: #stats <Person>").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendMessage("Benutze für den Befehl nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
