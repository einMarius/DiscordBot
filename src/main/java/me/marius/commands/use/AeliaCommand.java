package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.LevelRoles;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AeliaCommand implements ServerCommand {

    private int amount = 1;
    private boolean isRunningAelia;

    private HashMap<Member, Long> cooldown = new HashMap<>();

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                && !channel.getName().equalsIgnoreCase("zitate") && !channel.getName().equalsIgnoreCase("stats")) {

            if (args.length == 1) {

                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();

                isRunningAelia = !isRunningAelia;
                new Thread(() -> {
                    while(isRunningAelia){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(cooldown.containsKey(m)) {
                            if (cooldown.get(m) > System.currentTimeMillis()) {
                                System.out.println(m.getUser().getName() + " hat den Aelia-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                EmbedBuilder info = new EmbedBuilder();
                                info.setTitle(" **LATEIN** ");
                                info.setDescription("**Si** memini, fuerant tibi quattuor, Aelia, dentes: \nExpulit una duos tussis et una duos. \nIam secura potes totis tussire diebus: \nNil istic quod agat tertia tussis habet.");
                                info.addField(">>> Übersetzung für Dumme: ", "Wenn ich mich erinnere hattest du 4 Zähne gehabt Aelia: Ein Husten hat zwei herausgeschleudert und noch einer zwei. Schon kannst du die ganzen Tage lang sorglos husten: Ein dritter Husten hat nichts was er dort treiben könnte.", false);
                                info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl());
                                info.setColor(0xe3be7f);

                                channel.sendMessage(info.build()).queue();
                                info.clear();

                                isRunningAelia = false;
                            }
                        } else {

                            EmbedBuilder info = new EmbedBuilder();
                            info.setTitle(" **LATEIN** ");
                            info.setDescription("**Si** memini, fuerant tibi quattuor, Aelia, dentes: \nExpulit una duos tussis et una duos. \nIam secura potes totis tussire diebus: \nNil istic quod agat tertia tussis habet.");
                            info.addField(">>> Übersetzung für Dumme: ", "Wenn ich mich erinnere hattest du 4 Zähne gehabt Aelia: Ein Husten hat zwei herausgeschleudert und noch einer zwei. Schon kannst du die ganzen Tage lang sorglos husten: Ein dritter Husten hat nichts was er dort treiben könnte.", false);
                            info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl());
                            info.setColor(0xe3be7f);

                            channel.sendMessage(info.build()).queue();
                            info.clear();

                            //MySQL
                            if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById("824983261197500440")).queue();
                                cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));

                            } else {
                                //RANK 5
                                if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 10000){
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                    //RANK 4
                                } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 1000){
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                    //RANK 3
                                } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 500){
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                    //RANK 2
                                } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 100){
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                    //RANK 1
                                } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 50){
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                    //UNRANKED
                                } else if(Main.plugin.getMySQL().getPunkte(m.getId()) < 50) {
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0);
                                }

                                cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                                LevelRoles.addRoles(m);
                            }

                            isRunningAelia = false;
                        }

                    }
                }).start();

            }else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #Aelia schreiben").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        }else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
