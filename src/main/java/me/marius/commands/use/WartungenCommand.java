package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class WartungenCommand implements ServerCommand {


    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (m.hasPermission(Permission.ADMINISTRATOR)) {
            if (!(channel.getIdLong() == 812007747087368202L) || !(channel.getIdLong() == 816270150859882496L) || !(channel.getIdLong() == 811948730142949436L) || !(channel.getIdLong() == 735904029606936598L)
                    || !(channel.getIdLong() == 673615226955890710L) || !(channel.getIdLong() == 736308928438206464L) || !(channel.getIdLong() == 723621796083138631L) || !(channel.getIdLong() == 825103970270707743L)
                    || !(channel.getIdLong() == 799597621224275988L) || !(channel.getIdLong() == 799597621224275988L)) {
                if (args.length == 1) {
                    channel.purgeMessages(Utils.get(channel, 1));
                    channel.sendTyping().queue();

                    if(Main.plugin.getMySQL().getWartungen() == false) {

                        EmbedBuilder info = new EmbedBuilder();
                        info.setTitle("➥ **Wartungen**");
                        info.setAuthor(m.getNickname());
                        info.setThumbnail(m.getGuild().getIconUrl());
                        info.setDescription("Der Bot wird jetzt geupdatet!");
                        info.setFooter(m.getUser().getName() + " hat die Wartungen für den Bot deaktiviert", m.getUser().getAvatarUrl());
                        info.addField(">>> Information", "Die Commands sind jetzt wieder nutzbar!", false);
                        info.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
                        info.setColor(Color.red);

                        Main.plugin.getMySQL().setWartungen();

                        channel.sendMessage(info.build()).queue();
                        info.clear();

                    } else if(Main.plugin.getMySQL().getWartungen() == true){

                        EmbedBuilder info = new EmbedBuilder();
                        info.setTitle("➥ **Wartungen**");
                        info.setAuthor(m.getNickname());
                        info.setThumbnail(m.getGuild().getIconUrl());
                        info.setDescription("Der Bot wird nicht länger geupdatet!");
                        info.setFooter(m.getUser().getName() + " hat die Wartungen für den Bot aktiviert", m.getUser().getAvatarUrl());
                        info.addField(">>> Information", "Die Commands sind jetzt nicht mehr nutzbar!", false);
                        info.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
                        info.setColor(Color.green);

                        Main.plugin.getMySQL().setWartungen();

                        channel.sendMessage(info.build()).queue();
                        info.clear();
                    }
                } else
                    channel.sendMessage("Benutze #Wartungen").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            } else
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        } else
            channel.sendMessage("Dafür hast du keine Rechte du Arschloch!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
