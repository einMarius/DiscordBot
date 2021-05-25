package me.marius.main;

import me.marius.listeners.*;
import me.marius.music.PlayerManager;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class Main {

    public static Main plugin;
    public static ShardManager shardManager;
    public static String token = "ODExOTg1MTE1MzA2NjU1Nzc0.YC6KEQ.PwY-kja5bZ79UEwBS2C0s31Bzf0";
    public static AudioPlayerManager audioplayermanager;

    private PlayerManager playerManager;
    private MySQL mySQL;

    private CommandManager cmdManager;
    private Thread loop;

    public static void main(String[] args){

        try {
            new Main();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        plugin.mySQL.connect();
        plugin.mySQL.createTables();
    }

    //InChannelPoints
    private Thread points;
    public static ArrayList<Member> invoicechannel = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public Main() throws LoginException {

        plugin = this;
        mySQL = new MySQL();

        audioplayermanager = new DefaultAudioPlayerManager();
        playerManager = new PlayerManager();

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken(token);

        builder.setActivity(Activity.playing("mit den Usern."));
        builder.setStatus(OnlineStatus.ONLINE);

        // --------------REGISTER---------------
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new ReactionListener());
        builder.addEventListeners(new GuildMemberJoin());
        builder.addEventListeners(new GuildMemberLeave());
        builder.addEventListeners(new SendMessageListener());
        builder.addEventListeners(new ReactionsListener());
        builder.addEventListeners(new JoinLeaveListener(this));
        //builder.addEventListeners(new ChannelJoinEvent());
        //builder.addEventListeners(new ChannelLeaveEvent());
        //builder.addEventListeners(new BlackListListener());
        // --------------REGISTER---------------

        this.cmdManager = new CommandManager();

        shardManager = builder.build();
        System.out.println("[BaumbalabungaBot] Bot wurde gestartet...");

        AudioSourceManagers.registerRemoteSources(audioplayermanager);

        shutdown();
        runLoop();
    }

    public void shutdown() {

        new Thread(() -> {

            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                while ((line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("exit")) {
                        if (shardManager != null) {
                            shardManager.setStatus(OnlineStatus.OFFLINE);
                            shardManager.shutdown();
                            System.out.print("[BaumbalabungaBot] Bot gestoppt...");
                        }
                        reader.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    // -----------------------------Statusmeldung--------------------------------

    public void runLoop() {
        this.loop = new Thread(() -> {
            long time = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() >= time + 1000) {
                    time = System.currentTimeMillis();

                    onSecond();
                }
            }
        });
        this.loop.setName("Loop");
        this.loop.start();
    }

    int next = 15;
    String[] status = new String[] { "mit den Usern.", "mit seinem Dödel.", "Discord.exe", "auf den Discord herab.",
            "Max´s Holzfäller Podcast.", "mit Tobi´s Mam.", "Trios und trinkt Ja!-Cola.", "am Discord rum.", "Namensänderungsbehörde für Felix." };

    public void onSecond() {

        if (next <= 0) {

            Random rndm = new Random();
            int i = rndm.nextInt(status.length);

            shardManager.getShards().forEach(jda -> {

                String text = status[i];

                if (text.equalsIgnoreCase("auf den Discord herab.")) {
                    jda.getPresence().setActivity(Activity.watching(text));
                } else if (text.equalsIgnoreCase("Max´s Holzfäller Podcast.")) {
                    jda.getPresence().setActivity(Activity.listening(text));
                } else if (text.equalsIgnoreCase("Tobi´s Mam auf Twitch an.")) {
                    jda.getPresence().setActivity(Activity.watching(text));
                } else {
                    jda.getPresence().setActivity(Activity.playing(text));
                }
            });
            next = 15;
        } else {
            next--;
        }
    }

    // -----------------------------Statusmeldung--------------------------------

    public CommandManager getCmdManager() { return cmdManager; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public MySQL getMySQL() { return mySQL; }
    public Main getPlugin(){ return  this; }

    public void runAddVoiceChannelPoints(Member m) {
        this.points = new Thread(() -> {
            long time = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() >= time + 1000 * 60) {
                    time = System.currentTimeMillis();

                    if(invoicechannel.contains(m)) {
                        if (!mySQL.userIsExisting(m.getId())) {
                            mySQL.createNewPlayer(m.getId(), m.getUser().getName(), 0, 0, 0, 1, 0);
                            System.out.println(m.getUser().getName() + " war eine Minute im Channel!");
                        } else {
                            mySQL.setPunkte(m.getId(), m.getUser().getName(), 0, 0, 0, 1, 0);
                            System.out.println(m.getUser().getName() + " war eine Minute im Channel!");
                        }
                    }
                }
            }
        });
        this.points.setName("AddVoiceChannelPoints");
        this.points.start();
    }

}
