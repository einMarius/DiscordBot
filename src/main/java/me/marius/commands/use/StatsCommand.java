package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import me.marius.mysql.MySQL;
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

    private EmbedBuilder stats = new EmbedBuilder();

    private boolean isRunningStats;
    private boolean isRunningOtherStats;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentRaw().split(" ");

        if (channel.getId().equalsIgnoreCase("825103970270707743")) {
            if(args.length == 1) {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Searching for Stats...").complete().delete().queueAfter(500, TimeUnit.MILLISECONDS);


                isRunningStats = !isRunningStats;
                new Thread(() -> {
                    while (isRunningStats) {

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Random rand = new Random();
                        int i = rand.nextInt(colours.length);
                        String colour = colours[i];

                        stats = new EmbedBuilder()
                                .setTitle("◽ **Stats von " + m.getUser().getName() + "** ◽")
                                .setDescription("**Hier siehst du die Stats von " + m.getUser().getName() + "**")
                                .addField(">>> Der Rank", "Rank: `" + Main.plugin.getMySQL().getRank(m.getId()) + "`", false)
                                .addField(">>> Die Punkte", "Punkte: `" + Main.plugin.getMySQL().getPunkte(m.getUser().getId()) + "`", false)
                                .addField(">>> Die gesendeten Nachrichten", "Nachrichten: `" + Main.plugin.getMySQL().getNachrichten(m.getId()) + "`", false)
                                .addField(">>> Die hinzugefügten Reaktionen", "Reaktionen: `" + Main.plugin.getMySQL().getReaktionen(m.getId()) + "`", false)
                                .addField(">>> Die Anzahl der gejointen Channels", "Joined: `" + Main.plugin.getMySQL().getJoinedChannels(m.getId()) + "`", false)
                                .setThumbnail(m.getUser().getAvatarUrl())
                                .setFooter("Bot created by Marius", m.getGuild().getIconUrl())
                                .setColor(Color.decode("0x" + colour));

                        channel.sendMessage(stats.build()).queue();
                        stats.clear();

                        isRunningStats = false;
                    }
                }).start();
            } else if(args.length >= 2){

                Member targett = message.getMentionedMembers().get(0);

                if(!(targett.getIdLong() == 844166843731279912L) && !(targett.getIdLong() == 235088799074484224L) && !(targett.getIdLong() == 252128902418268161L) && !(targett.getIdLong() == 811985115306655774L)){

                    message.delete().queue();
                    channel.sendTyping().queue();
                    channel.sendMessage("Searching for Stats...").complete().delete().queueAfter(500, TimeUnit.MILLISECONDS);

                    isRunningOtherStats = !isRunningOtherStats;
                    new Thread(() -> {
                        while (isRunningOtherStats) {

                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Random rand = new Random();
                            int i = rand.nextInt(colours.length);
                            String colour = colours[i];

                            stats = new EmbedBuilder()
                                    .setTitle("◽ **Stats von " + targett.getUser().getName() + "** ◽")
                                    .setDescription("**Hier siehst du die Stats von " + targett.getUser().getName() + "**")
                                    .addField(">>> Der Rank", "Rank: `" + Main.plugin.getMySQL().getRank(targett.getId()) + "`", false)
                                    .addField(">>> Die Punkte", "Punkte: `" + Main.plugin.getMySQL().getPunkte(targett.getUser().getId()) + "`", false)
                                    .addField(">>> Die gesendeten Nachrichten", "Nachrichten: `" + Main.plugin.getMySQL().getNachrichten(targett.getId()) + "`", false)
                                    .addField(">>> Die hinzugefügten Reaktionen", "Reaktionen: `" + Main.plugin.getMySQL().getReaktionen(targett.getId()) + "`", false)
                                    .addField(">>> Die Anzahl der gejointen Channels", "Joined: `" + Main.plugin.getMySQL().getJoinedChannels(targett.getId()) + "`", false)
                                    .setThumbnail(targett.getUser().getAvatarUrl())
                                    .setFooter("Bot created by Marius", m.getGuild().getIconUrl())
                                    .setColor(Color.decode("0x" + colour));

                            channel.sendMessage(stats.build()).queue();
                            stats.clear();

                            isRunningOtherStats = false;
                        }
                    }).start();


                } else {
                    message.delete().queue();
                    channel.sendMessage("Keine Statistiken vorhanden!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                channel.sendMessage("Benutze #stats @User").complete().delete().queueAfter(5, TimeUnit.SECONDS);

            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendMessage("Benutze für den Befehl nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
