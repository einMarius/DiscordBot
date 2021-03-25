package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SexteCommand implements ServerCommand {

    private int amount = 1;
    private boolean isRunningSexte;

    private HashMap<Member, Long> cooldown = new HashMap<>();

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

                isRunningSexte = !isRunningSexte;
                new Thread(() -> {
                    while(isRunningSexte){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(cooldown.containsKey(m)) {
                            if (cooldown.get(m) > System.currentTimeMillis()) {
                                System.out.println(m.getUser().getName() + " hat den Sexte-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                EmbedBuilder info = new EmbedBuilder();
                                info.setTitle(" **LATEIN** ");
                                info.setDescription("**Dicis** amore tui bellas ardere puellas, \nqui faciem sub aqua, Sexte, natantis habes.");
                                info.addField(">>> Übersetzung für Dumme: ", "Du sagst, dass die schönen Mädchen aus Liebe zu dir brennen, der du das Gesicht eines unter Wasser schwimmenden hast Sextus.", false);
                                info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl());
                                info.setColor(0xe3be7f);

                                channel.sendMessage(info.build()).queue();
                                info.clear();

                                isRunningSexte = false;
                            }
                        } else {

                            EmbedBuilder info = new EmbedBuilder();
                            info.setTitle(" **LATEIN** ");
                            info.setDescription("**Dicis** amore tui bellas ardere puellas, \nqui faciem sub aqua, Sexte, natantis habes.");
                            info.addField(">>> Übersetzung für Dumme: ", "Du sagst, dass die schönen Mädchen aus Liebe zu dir brennen, der du das Gesicht eines unter Wasser schwimmenden hast Sextus.", false);
                            info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl());
                            info.setColor(0xe3be7f);

                            channel.sendMessage(info.build()).queue();
                            info.clear();

                            //MySQL
                            if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 1);
                                cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                            } else {
                                Main.plugin.getMySQL().updatePlayer(m.getUser().getId(), m.getUser().getName(), 1);
                                cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                            }

                            isRunningSexte = false;
                        }

                    }
                }).start();

            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #Sexte schreiben").complete().delete()
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
