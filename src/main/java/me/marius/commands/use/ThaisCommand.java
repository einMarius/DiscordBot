package me.marius.commands.use;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.marius.commands.types.ServerCommand;
import me.marius.main.LevelRoles;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.collections4.map.HashedMap;

public class ThaisCommand implements ServerCommand {

    private int amount = 1;
    private boolean Thaisrunning;

    private Map<Member, Long> cooldown = new HashedMap<>();

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                && !channel.getName().equalsIgnoreCase("zitate")) {

            if(args.length == 1) {

                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping();

                Thaisrunning = !Thaisrunning;

                if (Thaisrunning) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            while (Thaisrunning) {

                                try {
                                    Thread.sleep(100);
                                }catch(InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                }

                                if(cooldown.containsKey(m)){
                                    if(cooldown.get(m) > System.currentTimeMillis()){
                                        System.out.println(m.getUser().getName() + " hat den Thais-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                        EmbedBuilder info = new EmbedBuilder();
                                        info.setTitle(" **LATEIN** ");
                                        info.setDescription(
                                                "**Thais** habet nigros, niveos Laecania dentes. \nQuae ratio est? Emptos haec habet, illa suos.");
                                        info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss",
                                                m.getUser().getAvatarUrl());
                                        info.setColor(0xe3be7f);

                                        channel.sendMessage(info.build()).queue();
                                        info.clear();

                                        Thaisrunning = false;
                                    }
                                } else {

                                    EmbedBuilder info = new EmbedBuilder();
                                    info.setTitle(" **LATEIN** ");
                                    info.setDescription(
                                            "**Thais** habet nigros, niveos Laecania dentes. \nQuae ratio est? Emptos haec habet, illa suos.");
                                    info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss",
                                            m.getUser().getAvatarUrl());
                                    info.setColor(0xe3be7f);

                                    channel.sendMessage(info.build()).queue();
                                    info.clear();

                                    //MySQL
                                    if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                        Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                        cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                                    } else {
                                        Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0);
                                        cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                                        System.out.println("Debug 1");
                                        LevelRoles.addRoles(m);
                                    }

                                    Thaisrunning = false;
                                }
                            }
                        }
                    }.start();
                }



            }else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #thais schreiben").complete().delete().queueAfter(5,
                        TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
