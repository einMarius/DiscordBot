package me.marius.commands.use;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class UmfrageCommand implements ServerCommand {

    private int amount;
    private boolean Umfrageisrunning;
    public String thumbsup = "✅";
    public String thumbsdown = "❌";

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {

            if (channel.getName().equalsIgnoreCase("umfragen")) {

                if (args.length >= 1) {

                    int amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    Umfrageisrunning = !Umfrageisrunning;

                    if (Umfrageisrunning) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();

                                while (Umfrageisrunning) {

                                    try {
                                        Thread.sleep(100);
                                    }catch(InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                    }

                                    String args[] = message.getContentDisplay().split(" ");

                                    Calendar cal = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");

                                    String umfrage = "";
                                    for (int i = 1; i < args.length; i++)
                                        umfrage = String.valueOf(umfrage) + " " + args[i];

                                    EmbedBuilder info = new EmbedBuilder();
                                    info.setTitle("🎆 **Umfrage** 🎆");
                                    info.setAuthor(m.getNickname());
                                    info.setThumbnail(m.getGuild().getIconUrl());
                                    info.setDescription(umfrage);
                                    info.setFooter(m.getUser().getName() + " hat am " + sdf.format(cal.getTime())
                                            + " eine Umfrage gestartet", m.getUser().getAvatarUrl());
                                    info.setColor(Color.CYAN);

                                    channel.sendMessage("||<@&816385581738885182>|| \n").embed(info.build())
                                            .queue(embedMessage -> {

                                                embedMessage.addReaction(thumbsup).queue();
                                                embedMessage.addReaction(thumbsdown).queue();

                                            });
                                    info.clear();

                                    Umfrageisrunning = false;
                                }
                            }
                        }.start();

                    }

                } else {
                    amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendMessage("Benutze #umfrage <Umfrage>").complete().delete().queueAfter(5,
                            TimeUnit.SECONDS);
                }
            } else {
                amount = 1;
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendMessage("Der Befehl funktioniert nur im #umfragen Channel!").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
