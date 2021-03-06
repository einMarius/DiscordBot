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

public class OdetAmoCommand implements ServerCommand {

    private int amount = 1;
    private boolean OdetAmoisrunning;

    private Map<Member, Long> cooldown = new HashedMap<>();

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                && !channel.getName().equalsIgnoreCase("zitate") && !channel.getName().equalsIgnoreCase("stats")) {

            if (args.length == 2) {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();

                OdetAmoisrunning = !OdetAmoisrunning;

                if (OdetAmoisrunning) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            while (OdetAmoisrunning) {

                                try {
                                    Thread.sleep(100);
                                }catch(InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                }

                                if(cooldown.containsKey(m)) {
                                    if (cooldown.get(m) > System.currentTimeMillis()) {
                                        System.out.println(m.getUser().getName() + " hat den OdetAmo-Befehl ausgef??hrt, obwohl der Cooldown f??r ihn noch aktiviert ist");

                                        EmbedBuilder info = new EmbedBuilder();
                                        info.setTitle(" **LATEIN** ");
                                        info.setDescription("**Odi** et amo. Quare id faciam, fortasse requiris. \nNescio, sed fieri sentio et excrucior.");
                                        info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss",
                                                m.getUser().getAvatarUrl());
                                        info.setColor(0xe3be7f);

                                        channel.sendMessage(info.build()).queue();
                                        info.clear();

                                        OdetAmoisrunning = false;
                                    }
                                } else {

                                    EmbedBuilder info = new EmbedBuilder();
                                    info.setTitle(" **LATEIN** ");
                                    info.setDescription("**Odi** et amo. Quare id faciam, fortasse requiris. \nNescio, sed fieri sentio et excrucior.");
                                    info.setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss",
                                            m.getUser().getAvatarUrl());
                                    info.setColor(0xe3be7f);

                                    channel.sendMessage(info.build()).queue();
                                    info.clear();

                                    //MySQL
                                    if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                        Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                        m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById("824983261197500440")).queue();
                                        cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));

                                    } else {
                                        //RANK 5
                                        if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 10000){
                                            Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                            //RANK 4
                                        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 1000){
                                            Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                            //RANK 3
                                        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 500){
                                            Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                            //RANK 2
                                        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 100){
                                            Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                            //RANK 1
                                        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) >= 50){
                                            Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                            //UNRANKED
                                        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) < 50) {
                                            Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 1, 0, 0, 0, 0);
                                        }

                                        cooldown.put(m, System.currentTimeMillis() + (10 * 60 * 1000));
                                        LevelRoles.addRoles(m);
                                    }

                                    OdetAmoisrunning = false;
                                }
                            }
                        }
                    }.start();
                }

            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("F??r den puren Latein-Genuss musst du **#odet amo** schreiben").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        }else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze f??r den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
