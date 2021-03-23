package me.marius.commands.use;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import me.marius.commands.types.ServerCommand;
import me.marius.listeners.CommandListener;
import me.marius.listeners.GuildMemberJoin;
import me.marius.listeners.GuildMemberLeave;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

public class OnlineOfflineCommand implements ServerCommand {

    private int amount;
    private String emoji = "❗";

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {

            if (args.length == 2) {

                if (args[1].equalsIgnoreCase("restart")) {

                    int amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    EmbedBuilder info = new EmbedBuilder();
                    info.setTitle(emoji + " **Information** " + emoji);
                    info.setAuthor(m.getNickname());
                    info.setDescription("Der Bot wurde erfolgreich restarted!");
                    info.setFooter(m.getUser().getName() + " hat den Bot restarted!", m.getUser().getAvatarUrl());
                    info.setColor(Color.green);

                    channel.sendMessage(info.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                    info.clear();

                    Main.shardManager.setStatus(OnlineStatus.OFFLINE);
                    Main.shardManager.shutdown();
                    System.out.print("Bot wird neugestartet...");

                    @SuppressWarnings("deprecation")
                    DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
                    builder.setToken(Main.token);
                    builder.setActivity(Activity.playing("mit den Usern."));
                    builder.setStatus(OnlineStatus.ONLINE);

                    // --------------REGISTER---------------
                    builder.addEventListeners(new CommandListener());
                    builder.addEventListeners(new GuildMemberJoin());
                    builder.addEventListeners(new GuildMemberLeave());
                    // --------------REGISTER---------------

                    try {
                        Main.shardManager = builder.build();
                    } catch (LoginException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Bot gestartet...");

                } else if (args[1].equalsIgnoreCase("exit")) {

                    int amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    EmbedBuilder info = new EmbedBuilder();
                    info.setTitle(emoji + " **Information** " + emoji);
                    info.setAuthor(m.getNickname());
                    info.setDescription("Der Bot ist nun offline!");
                    info.setFooter(m.getUser().getName() + " hat den Bot gestoppt!", m.getUser().getAvatarUrl());
                    info.setColor(Color.RED);

                    channel.sendMessage(info.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                    info.clear();

                    Main.shardManager.setStatus(OnlineStatus.OFFLINE);
                    Main.shardManager.shutdown();
                    System.out.print("Bot gestoppt...");

                } else {
                    amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendMessage("Benutze: #bot <exit>").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                amount = 1;
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendMessage("Benutze: #bot <exit>").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").queue();
    }
}
