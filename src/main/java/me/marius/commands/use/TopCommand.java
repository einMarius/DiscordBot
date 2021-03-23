package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.collections4.map.HashedMap;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TopCommand implements ServerCommand {

    private int amount = 1;
    private boolean isrunningTopCommand;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                && !channel.getName().equalsIgnoreCase("zitate")) {

            if (args.length == 1) {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();

                isrunningTopCommand = !isrunningTopCommand;
                new Thread(() -> {
                    while(isrunningTopCommand){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("⚠ **Die Top 10** ⚠");
                            builder.setDescription("**Das ist die aktuelle Top 10**");
                            for(int i = 0; i < Main.plugin.getMySQL().getRanking().size(); i++){
                                int place = i + 1;
                                builder.addField(">>> Platz " + place, Main.shardManager.getUserById(Main.plugin.getMySQL().getRanking().get(i)).getName() + " mit "
                                        + Main.plugin.getMySQL().getPoints(Main.plugin.getMySQL().getRanking().get(i)) + " Punkten", false);
                            }
                            builder.setThumbnail(m.getGuild().getIconUrl());
                            builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                            builder.setColor(Color.GREEN);

                            channel.sendMessage(builder.build()).queue();
                            builder.clear();

                            isrunningTopCommand = false;

                        }catch(NullPointerException ex){
                            channel.sendMessage("Es exisitert momentan keine Top 10!").queue();
                        }
                    }
                }).start();

            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Benutze #top").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
