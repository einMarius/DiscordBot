package me.marius.main;

import java.util.*;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class Utils {

    private static int amount = 1;

    public static Random rand = new Random();
    public static List<String> mitspieler = new ArrayList<>();
    public static List<String> agentlist = new ArrayList<>();
    private static String breach = "Breach";
    private static String brimstone = "Brimstone";
    private static String cypher = "Cypher";
    private static String jett = "Jett";
    private static String killjoy = "Killjoy";
    private static String sage = "Sage";
    private static String sky = "Sky";
    private static String sova = "Sova";
    private static String omen = "Omen";
    private static String phoenix = "Phoenix";
    private static String raze = "Raze";
    private static String reyna = "Reyna";
    private static String viper = "Viper";
    private static String yoru = "Yoru";

    public static List<String> team1 = new ArrayList<>();
    public static List<String> team2 = new ArrayList<>();

    public static void createRandomAgents(Message message, TextChannel channel, Member m) {

        if (!team1.isEmpty()) {

            agentlist.add(breach);
            agentlist.add(brimstone);
            agentlist.add(cypher);
            agentlist.add(jett);
            agentlist.add(killjoy);
            agentlist.add(sage);
            agentlist.add(sky);
            agentlist.add(sova);
            agentlist.add(omen);
            agentlist.add(phoenix);
            agentlist.add(raze);
            agentlist.add(reyna);
            agentlist.add(viper);
            agentlist.add(yoru);

            int INDEXa = rand.nextInt(agentlist.size());
            String player1 = team1.get(0);
            String agent1 = (String) (agentlist.get(INDEXa));
            agentlist.remove(agent1);
            //
            int INDEXa1 = rand.nextInt(agentlist.size());
            String player2 = team1.get(1);
            String agent2 = (String) (agentlist.get(INDEXa1));
            agentlist.remove(agent2);
            //
            int INDEXa2 = rand.nextInt(agentlist.size());
            String player3 = team1.get(2);
            String agent3 = (String) (agentlist.get(INDEXa2));
            agentlist.remove(agent3);
            //
            int INDEXa3 = rand.nextInt(agentlist.size());
            String player4 = team1.get(3);
            String agent4 = (String) (agentlist.get(INDEXa3));
            mitspieler.remove(player4);
            agentlist.remove(agent4);
            //
            int INDEXa4 = rand.nextInt(agentlist.size());
            String player5 = team1.get(4);
            String agent5 = (String) (agentlist.get(INDEXa4));
            agentlist.remove(agent5);

            agentlist.add(agent1);
            agentlist.add(agent2);
            agentlist.add(agent3);
            agentlist.add(agent4);
            agentlist.add(agent5);

            int INDEXa5 = rand.nextInt(agentlist.size());
            String player6 = team2.get(0);
            String agent6 = (String) (agentlist.get(INDEXa5));
            agentlist.remove(agent6);
            //
            int INDEXa6 = rand.nextInt(agentlist.size());
            String player7 = team2.get(1);
            String agent7 = (String) (agentlist.get(INDEXa6));
            agentlist.remove(agent7);
            //
            int INDEXa7 = rand.nextInt(agentlist.size());
            String player8 = team2.get(2);
            String agent8 = (String) (agentlist.get(INDEXa7));
            agentlist.remove(agent8);
            //
            int INDEXa8 = rand.nextInt(agentlist.size());
            String player9 = team2.get(3);
            String agent9 = (String) (agentlist.get(INDEXa8));
            agentlist.remove(agent9);
            //
            int INDEXa9 = rand.nextInt(agentlist.size());
            String player10 = team2.get(4);
            String agent10 = (String) (agentlist.get(INDEXa9));
            agentlist.remove(agent10);

            agentlist.add(agent6);
            agentlist.add(agent7);
            agentlist.add(agent8);
            agentlist.add(agent9);
            agentlist.add(agent10);

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Team 1");
            builder.setDescription(player1 + " (" + agent1 + ")" + ", " + player2 + " (" + agent2 + ")" + ", " + player3
                    + " (" + agent3 + ")" + ", " + player4 + " (" + agent4 + ")" + ", " + player5 + " (" + agent5
                    + ")");
            builder.setFooter("Agenten von Nico´s Team", m.getGuild().getIconUrl());
            builder.setColor(0x133ad4);

            EmbedBuilder builder1 = new EmbedBuilder();
            builder1.setTitle("Team 2");
            builder1.setDescription(player6 + " (" + agent6 + ")" + ", " + player7 + " (" + agent7 + ")" + ", "
                    + player8 + " (" + agent8 + ")" + ", " + player9 + " (" + agent9 + ")" + ", " + player10 + " ("
                    + agent10 + ")");
            builder1.setFooter("Agenten von Marius´s Team", m.getGuild().getIconUrl());
            builder1.setColor(0xd91818);

            channel.sendMessage(builder.build()).queue();
            channel.sendMessage(builder1.build()).queue();
            builder.clear();
            builder1.clear();

            team1.clear();
            team2.clear();

        } else {
            channel.purgeMessages(get(channel, amount));
            channel.sendTyping().queue();

            channel.sendMessage("Du musst zuvor verschieden Teams generieren lassen!").complete().delete().queueAfter(5,
                    TimeUnit.SECONDS);
        }
    }

    public static List<Message> get(MessageChannel channel, int amount) {
        List<Message> messages = new ArrayList<>();
        int i = 1;

        for (Message message : channel.getIterableHistory().cache(false)) {
            if (!message.isPinned()) {
                messages.add(message);
            }
            if (--i <= 0)
                break;
        }
        return messages;
    }
}
