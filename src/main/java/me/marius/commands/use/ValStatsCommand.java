package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ValStatsCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (channel.getIdLong() == 825103970270707743L) {
            if(args.length == 2){
                m.getUser().openPrivateChannel().queue(privateChannel -> {

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("◽ **WAIT** ◽");
                    builder.setDescription("**Aktuell noch in Arbeit...**");
                    builder.addField(">>> Information: ", "An den Valorant Stats wird derzeit noch gearbeitet!", false);
                    builder.setThumbnail(m.getUser().getAvatarUrl());
                    builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                    builder.setColor(Color.RED);

                    privateChannel.sendMessage(builder.build()).queue();
                    builder.clear();

                });
            } else {
                channel.purgeMessages(Utils.get(channel, 1));
                channel.sendTyping().queue();
                channel.sendMessage("Um deine Valorant Stats einzusehen musst du #valstats <Name> schreiben").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, 1));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
