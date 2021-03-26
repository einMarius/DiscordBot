package me.marius.commands.use;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import me.marius.music.AudioLoadResult;
import me.marius.music.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand {

    private int amount = 1;
    private static ArrayList<MusicController> controllerlist = new ArrayList<>();

    private boolean isrunningStart;
    private boolean isrunningStopp;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String[] args = message.getContentDisplay().split(" ");
        if (channel.getName().equalsIgnoreCase("musik")) {
            if (args.length == 2) {
                GuildVoiceState state;
                if ((state = m.getVoiceState()) != null) {
                    VoiceChannel vc;
                    if ((vc = state.getChannel()) != null) {

                        MusicController controller = Main.plugin.getPlayerManager().getController(vc.getGuild().getIdLong());
                        AudioPlayer player = controller.getPlayer();
                        AudioPlayerManager apm = Main.audioplayermanager;
                        AudioManager manager = vc.getGuild().getAudioManager();
                        manager.openAudioConnection(vc);

                        if (args[1].equalsIgnoreCase("start")) {
                            if (!controllerlist.contains(controller)) {

                                isrunningStart = !isrunningStart;

                                new Thread(() -> {
                                    while (isrunningStart) {

                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            System.out.println(
                                                    "[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                        }

                                        channel.purgeMessages(Utils.get(channel, amount));
                                        channel.sendTyping().queue();

                                        StringBuilder strBuilder = new StringBuilder();
                                        for (int i = 1; i < args.length; i++)
                                            strBuilder.append(args[i] + " ");

                                        String url = "https://www.youtube.com/watch?v=sDwmitvFBvg&ab_channel=TheVibeGuide";

                                        apm.loadItem(url, new AudioLoadResult(controller, url));
                                        controllerlist.add(controller);

                                        EmbedBuilder builder = new EmbedBuilder();
                                        builder.setTitle("⚠ **MAXIMALER BOOOOOST** ⚠");
                                        builder.setDescription("**OMG, DER MAXIMAL BOOOOOOOST GEHT LOOOOOOOOOOOS**");
                                        builder.addField(">>> Now playing", "`VIZE x Tokio Hotel - White Lies` ",
                                                false);
                                        builder.setThumbnail(m.getGuild().getIconUrl());
                                        builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                                        builder.setColor(Color.GREEN);

                                        channel.sendMessage(builder.build()).queue();

                                        isrunningStart = false;
                                    }
                                }).start();

                                //MySQL
                                if(!Main.plugin.getMySQL().userIsExisting(m.getUser().getId())) {
                                    Main.plugin.getMySQL().createNewPlayer(m.getUser().getId(), m.getUser().getName(), 3, 0, 0, 0);
                                } else {
                                    Main.plugin.getMySQL().setPunkte(m.getUser().getId(), m.getUser().getName(), 3, 0, 0);
                                }

                            } else {
                                channel.purgeMessages(Utils.get(channel, amount));
                                channel.sendTyping().queue();

                                channel.purgeMessages(Utils.get(channel, amount));
                                channel.sendTyping().queue();
                                channel.sendMessage("Ihr bekommt doch schon den kompletten BOOOOOOOOOOOST!").complete()
                                        .delete().queueAfter(5, TimeUnit.SECONDS);
                                return;
                            }
                        } else if (args[1].equalsIgnoreCase("stop")) {
                            if (controllerlist.contains(controller)) {

                                isrunningStopp = !isrunningStopp;

                                new Thread(() -> {
                                    while (isrunningStopp) {

                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            System.out.println(
                                                    "[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                                        }

                                        channel.purgeMessages(Utils.get(channel, amount));
                                        channel.sendTyping().queue();

                                        player.stopTrack();
                                        manager.closeAudioConnection();
                                        controllerlist.remove(controller);

                                        EmbedBuilder builder = new EmbedBuilder();
                                        builder.setTitle("⚠ **MAXIMALER BOOOOOST** ⚠");
                                        builder.setDescription("**Der maximale Boost ist leider vorbei**");
                                        builder.addField(">>> Played", "`VIZE x Tokio Hotel - White Lies` ", false);
                                        builder.setThumbnail(m.getGuild().getIconUrl());
                                        builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                                        builder.setColor(Color.RED);

                                        channel.sendMessage(builder.build()).queue();

                                        isrunningStopp = false;
                                    }
                                }).start();
                            } else {
                                channel.purgeMessages(Utils.get(channel, amount));
                                channel.sendTyping().queue();

                                channel.purgeMessages(Utils.get(channel, amount));
                                channel.sendTyping().queue();
                                channel.sendMessage(
                                        "Du solltest dich vorher boosten, bevor du was stoppen wilst du KEK!")
                                        .complete().delete().queueAfter(5, TimeUnit.SECONDS);
                                return;
                            }
                        } else {
                            channel.purgeMessages(Utils.get(channel, amount));
                            channel.sendTyping().queue();
                            channel.sendMessage("Benutze #Boost <start/stop>").complete().delete().queueAfter(5,
                                    TimeUnit.SECONDS);
                        }
                    } else {
                        channel.purgeMessages(Utils.get(channel, amount));
                        channel.sendTyping().queue();
                        channel.sendMessage("Du Kek willst dich boosten, bist aber nicht mal in nem Channel!")
                                .complete().delete().queueAfter(5, TimeUnit.SECONDS);
                    }
                } else {
                    channel.purgeMessages(Utils.get(channel, amount));
                    channel.sendTyping().queue();
                    channel.sendMessage("Du Kek willst dich boosten, bist aber nicht mal in nem Channel!").complete()
                            .delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Benutze #Boost <start/stop>").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5,
                    TimeUnit.SECONDS);
        }

    }

}
