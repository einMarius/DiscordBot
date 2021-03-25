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

public class PhoebeCommand implements ServerCommand {

    private int amount = 1;
    private boolean isRunningPhoebe;

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

                isRunningPhoebe = !isRunningPhoebe;
                new Thread(() -> {
                    while(isRunningPhoebe){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(cooldown.containsKey(m)) {
                            if (cooldown.get(m) > System.currentTimeMillis()) {
                                System.out.println(m.getUser().getName() + " hat den Phoebe-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                EmbedBuilder info = new EmbedBuilder();
                                info.setTitle(" **LATEIN** ");
                                info.setDescription("**Cum** sint crura tibi similent quae cornua lunae, \nin rhytio poteras, Phoebe, lavare pedes.");
                                info.addField(">>> Übersetzung für Dumme: ", "Weil du Unterschenkel hast, die den Hörnern des Mondes ähneln, konntest du Phoebus deine Füße in einem Trinkhorn waschen.", false);
                                info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl());
                                info.setColor(0xe3be7f);

                                channel.sendMessage(info.build()).queue();
                                info.clear();

                                isRunningPhoebe = false;
                            }
                        } else {

                            EmbedBuilder info = new EmbedBuilder();
                            info.setTitle(" **LATEIN** ");
                            info.setDescription("**Cum** sint crura tibi similent quae cornua lunae, \nin rhytio poteras, Phoebe, lavare pedes.");
                            info.addField(">>> Übersetzung für Dumme: ", "Weil du Unterschenkel hast, die den Hörnern des Mondes ähneln, konntest du Phoebus deine Füße in einem Trinkhorn waschen.", false);
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

                            isRunningPhoebe = false;
                        }

                    }
                }).start();

            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #Phoebe schreiben").complete().delete()
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
