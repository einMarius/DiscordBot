package me.marius.commands.use;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CreateTeamsCommand implements ServerCommand {

    private int amount = 1;
    private boolean CreateTeamsrunning;
    public static Random rand = new Random();
    public static List<String> mitspieler = new ArrayList<>();
    public static List<String> agentlist = new ArrayList<>();
    public static List<String> team1 = new ArrayList<>();
    public static List<String> team2 = new ArrayList<>();
    private static String marius = "Marius";
    private static String nico = "Nico L.";

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        // #createteams

        String args[] = message.getContentDisplay().substring(12).split(" ");

        if (m.hasPermission(Permission.MESSAGE_MANAGE)) {

            if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                    && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                    && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                    && !channel.getName().equalsIgnoreCase("zitate")
                    && !channel.getName().equalsIgnoreCase("schulchat") && !channel.getName().equalsIgnoreCase("stats")) {

                if (args.length == 9) {

                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();

                    CreateTeamsrunning = !CreateTeamsrunning;

                    if (CreateTeamsrunning) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();

                                while (CreateTeamsrunning) {

                                    try {
                                        Thread.sleep(100);
                                    }catch(InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                    }

                                    String args[] = message.getContentDisplay().substring(12).split(" ");

                                    for (int i = 1; i < args.length; i++) {
                                        mitspieler.add(args[i]);
                                    }

                                    int INDEXn = rand.nextInt(mitspieler.size());
                                    String player1 = (String) (mitspieler.get(INDEXn));
                                    mitspieler.remove(player1);
                                    int INDEXn1 = rand.nextInt(mitspieler.size());
                                    String player2 = (String) (mitspieler.get(INDEXn1));
                                    mitspieler.remove(player2);
                                    int INDEXn2 = rand.nextInt(mitspieler.size());
                                    String player3 = (String) (mitspieler.get(INDEXn2));
                                    mitspieler.remove(player3);
                                    int INDEXn3 = rand.nextInt(mitspieler.size());
                                    String player4 = (String) (mitspieler.get(INDEXn3));
                                    mitspieler.remove(player4);

                                    int INDEXn4 = rand.nextInt(mitspieler.size());
                                    String player5 = (String) (mitspieler.get(INDEXn4));
                                    mitspieler.remove(player5);
                                    int INDEXn5 = rand.nextInt(mitspieler.size());
                                    String player6 = (String) (mitspieler.get(INDEXn5));
                                    mitspieler.remove(player6);
                                    int INDEXn6 = rand.nextInt(mitspieler.size());
                                    String player7 = (String) (mitspieler.get(INDEXn6));
                                    mitspieler.remove(player7);
                                    int INDEXn7 = rand.nextInt(mitspieler.size());
                                    String player8 = (String) (mitspieler.get(INDEXn7));
                                    mitspieler.remove(player8);

                                    team1.add(nico);
                                    team1.add(player1);
                                    team1.add(player2);
                                    team1.add(player3);
                                    team1.add(player4);

                                    team2.add(marius);
                                    team2.add(player5);
                                    team2.add(player6);
                                    team2.add(player7);
                                    team2.add(player8);

                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle("Team 1");
                                    builder.setDescription("Nico´s Team besteht aus: " + player1 + ", " + player2 + ", "
                                            + player3 + ", " + player4);
                                    builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                                    builder.setColor(0x133ad4);

                                    EmbedBuilder builder1 = new EmbedBuilder();
                                    builder1.setTitle("Team 2");
                                    builder1.setDescription("Marius´s Team besteht aus: " + player5 + ", " + player6
                                            + ", " + player7 + ", " + player8);
                                    builder1.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                                    builder1.setColor(0xd91818);

                                    channel.sendMessage(builder.build()).queue();
                                    channel.sendMessage(builder1.build()).queue();
                                    builder.clear();

                                    mitspieler.clear();
                                    team1.clear();
                                    team2.clear();

                                    CreateTeamsrunning = false;
                                }
                            }
                        }.start();
                    }

                } else {
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();
                    channel.sendMessage("Benutze: #createteams <1 2 3 4 5 6 7 8>").complete().delete().queueAfter(5,
                            TimeUnit.SECONDS);
                }
            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5,
                        TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
