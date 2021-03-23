package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements ServerCommand {

    private int amount;
    private boolean Clearrunnging;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        if (m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {

            String args[] = message.getContentDisplay().split(" ");

            if (args.length == 2) {
                try {

                    int clearamount = Integer.parseInt(args[1]);

                    Clearrunnging = !Clearrunnging;

                    if (Clearrunnging) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();

                                while (Clearrunnging) {

                                    try {
                                        Thread.sleep(100);
                                    }catch(InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                    }

                                    String args[] = message.getContentDisplay().split(" ");
                                    OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);

                                    List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(args[1]) + 1)
                                            .complete();
                                    messages.removeIf(m -> m.getTimeCreated().isBefore(twoWeeksAgo));

                                    channel.deleteMessages(messages).complete();
                                    channel.sendTyping().queue();

                                    Clearrunnging = false;

                                }
                            }
                        }.start();
                    }

                    new Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {

                            EmbedBuilder info = new EmbedBuilder();
                            info.setTitle("**Information**");
                            info.setDescription(clearamount + " Nachrichten gelöscht.");
                            info.setFooter("Nachrichten von " + m.getUser().getName() + " gelöscht.",
                                    m.getUser().getAvatarUrl());
                            info.setColor(Color.RED);
                            channel.sendMessage(info.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                            info.clear();
                        }
                    }, 500);

                } catch (NumberFormatException e) {
                    amount = 1;
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendMessage("Du musst eine Zahl angeben!").complete().delete().queueAfter(5,
                            TimeUnit.SECONDS);
                }

            } else {

                amount = 1;
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendMessage("Benutze: #clear AMOUNT").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").queue();
    }
}
