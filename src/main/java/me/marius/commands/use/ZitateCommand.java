package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.collections4.map.HashedMap;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ZitateCommand implements ServerCommand {

    private int amount = 1;
    private boolean Zitateisrunning;

    private Map<Member, Long> cooldown = new HashedMap<>();

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (channel.getName().equalsIgnoreCase("zitate")) {

            if (args.length > 2) {

                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();

                Zitateisrunning = !Zitateisrunning;

                if (Zitateisrunning) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            while (Zitateisrunning) {

                                try {
                                    Thread.sleep(100);
                                }catch(InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                }

                                String args[] = message.getContentRaw().split(" ");

                                String lehrer = args[1];
                                String zitat = "";
                                for (int i = 2; i < args.length; i++)
                                    zitat = String.valueOf(zitat) + " " + args[i];

                                if(cooldown.containsKey(m)) {
                                    if (cooldown.get(m) > System.currentTimeMillis()) {
                                        System.out.println(m.getUser().getName() + " hat den Zitate-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                        EmbedBuilder info = new EmbedBuilder();
                                        info.setTitle("Zitat");
                                        info.setDescription(lehrer + " sagte: " + zitat + "");
                                        info.setFooter("Zitat erstellt von " + m.getUser().getName(), m.getUser().getAvatarUrl());
                                        info.setColor(Color.RED);

                                        channel.sendMessage(info.build()).queue();
                                        info.clear();

                                        Zitateisrunning = false;
                                    }
                                } else {

                                    EmbedBuilder info = new EmbedBuilder();
                                    info.setTitle("Zitat");
                                    info.setDescription(lehrer + " sagte: " + zitat + "");
                                    info.setFooter("Zitat erstellt von " + m.getUser().getName(), m.getUser().getAvatarUrl());
                                    info.setColor(Color.RED);

                                    channel.sendMessage(info.build()).queue();
                                    info.clear();

                                    //MySQL
                                    if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                        Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 5, 0, 0, 0);
                                        cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                                    } else {
                                        Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 5, 0, 0);
                                        cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                                    }

                                    Zitateisrunning = false;
                                }
                            }
                        }
                    }.start();
                }



                return;
            } else
                channel.purgeMessages(Utils.get(channel, amount));
            channel.sendMessage("Benutze: #zitat <Person> <Zitat>").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        } else {
            amount = 1;
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendMessage("Der Command funktioniert nur im #zitate Channel!").complete().delete().queueAfter(5,
                    TimeUnit.SECONDS);
        }
    }
}
