package me.marius.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import me.marius.main.Main;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {

    private Guild guild;
    private AudioPlayer player;

    public MusicController(Guild guild) {
        this.guild = guild;
        this.player = Main.audioplayermanager.createPlayer();

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
        this.player.setVolume(20);

    }

    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

}
