package me.marius.music;

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler{

    private final AudioPlayer audioplayer;
    private AudioFrame lastframe;

    public AudioPlayerSendHandler(AudioPlayer audioplayer) {
        this.audioplayer = audioplayer;
    }

    @Override
    public boolean canProvide() {
        lastframe = audioplayer.provide();
        return lastframe != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastframe.getData());
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
