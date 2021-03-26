package me.marius.commands.use;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.collections4.map.HashedMap;

public class HesternoCommand implements ServerCommand {

    private int amount = 1;
    private boolean Hesternoisrunning;

    private Map<String, Long> cooldown = new HashedMap<>();

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

                Hesternoisrunning = !Hesternoisrunning;

                if (Hesternoisrunning) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            while (Hesternoisrunning) {

                                try {
                                    Thread.sleep(100);
                                }catch(InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                }

                                if(cooldown.containsKey(m.getUser().getName())) {
                                    if (cooldown.get(m.getUser().getName()) > System.currentTimeMillis()) {
                                        System.out.println(m.getUser().getName() + " hat den Hesterno-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                        EmbedBuilder info = new EmbedBuilder();
                                        info.setTitle(" **LATEIN** ");
                                        info.setDescription(
                                                "**Hesterno** fetere mero qui credit Acerram, \nfallitur: in lucem semper Acerra bibit.");
                                        info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss",
                                                m.getUser().getAvatarUrl());
                                        info.setColor(0xe3be7f);

                                        channel.sendMessage(info.build()).queue();
                                        info.clear();

                                        Hesternoisrunning = false;
                                    }
                                } else {

                                    EmbedBuilder info = new EmbedBuilder();
                                    info.setTitle(" **LATEIN** ");
                                    info.setDescription(
                                            "**Hesterno** fetere mero qui credit Acerram, \nfallitur: in lucem semper Acerra bibit.");
                                    info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss",
                                            m.getUser().getAvatarUrl());
                                    info.setColor(0xe3be7f);

                                    channel.sendMessage(info.build()).queue();
                                    info.clear();

                                    //MySQL
                                    if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                        Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                        cooldown.put(m.getUser().getName(), System.currentTimeMillis() + (10 * 60 * 1000));
                                    } else {
                                        Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0);
                                        cooldown.put(m.getUser().getName(), System.currentTimeMillis() + (10 * 60 * 1000));
                                    }

                                    Hesternoisrunning = false;
                                }
                            }
                        }
                    }.start();
                }

            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #hesterno schreiben").complete().delete()
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
