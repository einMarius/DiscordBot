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

public class AddRolesCommand implements ServerCommand {

    private int amount = 1;
    private EmbedBuilder builder;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (m.hasPermission(Permission.MESSAGE_MANAGE)) {
            if (channel.getName().equalsIgnoreCase("role-selection") || channel.getName().equalsIgnoreCase("test")) {
                if (args.length == 1) {

                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    builder = new EmbedBuilder();
                    builder.setTitle("⚠ **Role-Selection** ⚠");
                    builder.setDescription("**Reagiere mit dem jeweiligen Emoji, um bestimmte \nBenachrichtigungen zu erhalten. **");
                    builder.addField(">>> News-Role", "`Reagiere mit ❗ um in der Zukunft News-Benachrichtigungen zu erhalten` ", false);
                    builder.addField(">>> Registration-Role", "`Reagiere mit ❕ um in der Zukunft Registration-Benachrichtigungen zu erhalten`", false);
                    builder.addField(">>> Umfrage-Role", "`Reagiere mit ✅ um in der Zukunft Umfrage-Benachrichtigungen zu erhalten`", false);
                    builder.setThumbnail(m.getGuild().getIconUrl());
                    builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                    builder.setColor(Color.GREEN);

                    channel.sendMessage(builder.build()).queue(embedMessage -> {

                        embedMessage.addReaction("❗").queue();
                        embedMessage.addReaction("❕").queue();
                        embedMessage.addReaction("✅").queue();

                    });

                    builder.clear();
                } else {
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();
                    channel.sendMessage("Benutze: #addroles").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5,
                        TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").queue();
    }
}
