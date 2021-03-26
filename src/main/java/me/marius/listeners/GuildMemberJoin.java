package me.marius.listeners;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter {

    private String emoji = "🎉";
    String[] messages = {

            "Yaaay! %member% hat den Discord-Server betreten.",
            "Oh nein! Was will denn %member% hier?",
            "Herzlich Willkommen, %member%.",
            "Wir haben dich erwartet, %member%.",
            "Woooooooosh. %member% ist gelandet!",
            "Ein wildes %member% ist aufgetaucht!",
            "Wanninger hat ein wildes %member% gespottet!",
            "Baumbalabunga Gaming wurde von %member% gejoosiet!"

    };

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){

        Random rand = new Random();
        int number = rand.nextInt(messages.length);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(emoji + " " + messages[number].replace("%member%", e.getMember().getAsMention()) + " " + emoji);
        builder.setColor(0x66d8ff);

        e.getGuild().getDefaultChannel().sendMessage(builder.build()).queue();

        e.getMember().getGuild().addRoleToMember(e.getMember(), e.getJDA().getRoleById("824983261197500440")).queue();

    }
}
