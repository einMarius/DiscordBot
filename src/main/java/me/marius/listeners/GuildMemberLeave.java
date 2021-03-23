package me.marius.listeners;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@SuppressWarnings("deprecation")
public class GuildMemberLeave extends ListenerAdapter {

    private String emoji = "❔";
    private String emoji1 = "❓";
    String[] messages = {

            "Ohhhhh! %member% hat den Discord-Server verlassen.",
            "Oh nein! Wieso ist denn %member% gegangen?",
            "Auf Wiedersehen, %member%!",
            "Wir haben jemanden verloren, %member%!",
            "Woooooooosh. %member% ist weggeflogen!",
            "Ein wildes %member% ist verschwunden!",
            "%member% wurde von Wanningers Adlerblick erwischt!",
            "%member% wurde von Vannis Lostheit erwischt!.",
            "%member% wurde ge-Jommet!"

    };

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {

        Random rand = new Random();
        int number = rand.nextInt(messages.length);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(emoji + " " + messages[number].replace("%member%", e.getMember().getAsMention()) + " " + emoji1);
        builder.setColor(0xe3672d);

        e.getGuild().getDefaultChannel().sendMessage(builder.build()).queue();
    }
}
