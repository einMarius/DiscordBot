package me.marius.listeners;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Member;

public class ChannelLeaveEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e){

        ChannelJoinEvent.inChannel.remove(e.getMember().getUser().getId());
    }

}
