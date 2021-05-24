package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.LevelRoles;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.collections4.map.HashedMap;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PPCommand implements ServerCommand {

    String[] colours =  new String[] {
            "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff", "45ffc4", "459fff", "4a2bfc", "000000", "b55400", "faef52", "93fc38", "76ff00"
    };

    String[] lenght = new String[]{
            "-2mm", "5km", "Hat keinen, hat schon vergeblich danach gesucht", "2938472938745923895km", "14cm", "5mm", "234m", "-932498237498372489", "Kann man nicht messen", "24, was?"
    };

    private boolean isRunningPP;
    private Map<Member, Long> cooldown = new HashedMap<>();

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (!(channel.getIdLong() == 812007747087368202L) || !(channel.getIdLong() == 816270150859882496L) || !(channel.getIdLong() == 811948730142949436L)
                || !(channel.getIdLong() == 735904029606936598L)
                || !(channel.getIdLong() == 736308928438206464L) || !(channel.getIdLong() == 723621796083138631L) || !(channel.getIdLong() == 825103970270707743L)
                || !(channel.getIdLong() == 640912830870192140L) || !(channel.getIdLong() == 799597621224275988L)) {

            if(args.length >= 2){

                channel.purgeMessages(Utils.get(channel, 1));
                channel.sendTyping().queue();

                Member targett = message.getMentionedMembers().get(0);

                if(!(targett.getIdLong() == 811985115306655774L) && !(targett.getIdLong() == 235088799074484224L)) {
                    if(!(targett.getIdLong() == 252128902418268161L)) {

                        isRunningPP = !isRunningPP;
                        new Thread(() -> {
                            while (isRunningPP) {

                                if(cooldown.containsKey(m)) {
                                    if (cooldown.get(m) > System.currentTimeMillis()) {
                                        System.out.println(m.getUser().getName() + " hat den PP-Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist");

                                        EmbedBuilder builder = new EmbedBuilder();
                                        builder.setTitle("◽ **PP von " + targett.getUser().getName() + "** ◽");
                                        builder.setDescription("**Hier siehst du die Originallänge**");
                                        //LÄNGE
                                        Random random = new Random();
                                        int i2 = random.nextInt(lenght.length);
                                        String longus = lenght[i2];
                                        builder.addField(">>> Pipilänge", "Die Länge beträgt: `" + longus + "`!", false);
                                        builder.setThumbnail(targett.getUser().getAvatarUrl());
                                        builder.setFooter(m.getUser().getName() + " wollte die wahre PP-Länge von " + targett.getUser().getAsMention(), m.getGuild().getIconUrl());

                                        builder.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
                                        //COLOUR
                                        Random rand = new Random();
                                        int i = rand.nextInt(colours.length);
                                        String colour = colours[i];
                                        builder.setColor(Color.decode("0x" + colour));

                                        channel.sendMessage(builder.build()).queue();
                                        builder.clear();

                                        try {
                                            Thread.sleep(250);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        isRunningPP = false;

                                    }
                                } else {

                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle("◽ **PP von " + targett.getUser().getName() + "** ◽");
                                    builder.setDescription("**Hier siehst du die Originallänge**");
                                    //LÄNGE
                                    Random random = new Random();
                                    int i2 = random.nextInt(lenght.length);
                                    String longus = lenght[i2];
                                    builder.addField(">>> Pipilänge", "Die Länge beträgt: `" + longus + "`!", false);
                                    builder.setThumbnail(targett.getUser().getAvatarUrl());
                                    builder.setFooter(m.getUser().getName() + " wollte die wahre PP-Länge von " + targett.getUser().getName(), m.getGuild().getIconUrl());

                                    builder.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
                                    //COLOUR
                                    Random rand = new Random();
                                    int i = rand.nextInt(colours.length);
                                    String colour = colours[i];
                                    builder.setColor(Color.decode("0x" + colour));

                                    channel.sendMessage(builder.build()).queue();
                                    builder.clear();

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

                                    try {
                                        Thread.sleep(250);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    isRunningPP = false;
                                }
                            }
                        }).start();
                    }else {
                        channel.purgeMessages(Utils.get(channel, 1));
                        channel.sendTyping().queue();
                        channel.sendMessage("Der PP ist zu lang für eine Nachricht!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                    }
                }else {
                    channel.purgeMessages(Utils.get(channel, 1));
                    channel.sendTyping().queue();
                    channel.sendMessage("Der PP ist nich verfügbar!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            }else {
                channel.purgeMessages(Utils.get(channel, 1));
                channel.sendTyping().queue();
                channel.sendMessage("Für die wahre Schwanzlänge von jemandem musst du #pp <@Name> schreiben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, 1));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht für diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
