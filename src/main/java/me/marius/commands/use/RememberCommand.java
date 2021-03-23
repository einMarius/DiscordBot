package me.marius.commands.use;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class RememberCommand implements ServerCommand {

    private int amount = 1;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");
        if (m.hasPermission(Permission.MESSAGE_MANAGE)) {
            if (channel.getName().equalsIgnoreCase("schulchat") || channel.getName().equalsIgnoreCase("test")) {
                if (args.length == 1) {

                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("**♻ Erinnerung ♻**");
                    builder.setDescription("**He Chefs, ich hab hier noch ne Erinnerung für euch:**");
                    builder.addField(">>> Registration",
                            "`Ihr Huen sollt mir ja nicht vergessen euch zu registrieren! \nSonst muss sich da Staufer noch mit euch elendem Fußvolk rumtreiben!`", false);
                    builder.addField("Erwähnung", "<@&816256434634358795>", false);
                    builder.setThumbnail(m.getGuild().getIconUrl());
                    builder.setFooter(m.getUser().getName() + " ist ein Ehrenmann und hat an das Registrieren erinnert",
                            m.getUser().getAvatarUrl());
                    builder.setColor(Color.GREEN);

                    //channel.sendMessage("||<@&816256434634358795>||").queue();
                    channel.sendMessage("||<@&816256434634358795>|| \n").embed(builder.build()).queue();
                    builder.clear();

                } else {
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();
                    channel.sendMessage("Benutze: #rememberRegistration!").complete().delete().queueAfter(5,
                            TimeUnit.SECONDS);
                }
            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5,
                        TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
