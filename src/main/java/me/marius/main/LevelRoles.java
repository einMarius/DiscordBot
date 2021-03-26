package me.marius.main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.util.Random;

public class LevelRoles {

    public static void addRoles(Member m){

        String[] colours =  new String[] {
                "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff"
        };

        if(Main.plugin.getMySQL().getPunkte(m.getId()) == 10000){

        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) == 1000){

        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) == 500){

        } else if(Main.plugin.getMySQL().getPunkte(m.getId()) == 100){

        } else if(Main.plugin.getMySQL().getPunkte(m.getId())+1 == 50){

            m.getGuild().removeRoleFromMember(m.getId(), m.getJDA().getRoleById("824983261197500440")).queue();
            m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById("824983198039015444")).queue();

            m.getUser().openPrivateChannel().queue(channel -> {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("◽ **Levelup** ◽");
                builder.setDescription("**Glückwunsch du bist jetzt Level 1**");
                builder.addField(">>> Neue Rolle: ", "Rolle: `" + m.getJDA().getRoleById("824977512639496222").getName() + "` ", false);
                builder.addField(">>> Neue Berechtigung", "Berechtigung: `Join Rechte oder so, Rechte noch nicht eingestellt loool`", false);
                builder.setThumbnail(m.getUser().getAvatarUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                Random rand = new Random();
                int i = rand.nextInt(colours.length);

                String colour = colours[i];

                builder.setColor(Color.decode("0x" + colour));

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            });

        }
    }

}
