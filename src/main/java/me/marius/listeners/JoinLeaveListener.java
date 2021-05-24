package me.marius.listeners;

import me.marius.main.LevelRoles;
import me.marius.main.Main;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class JoinLeaveListener extends ListenerAdapter {

    private Main plugin;
    public JoinLeaveListener(Main plugin) { this.plugin = plugin; }

    private static HashMap<Member, Long> cooldown = new HashMap<Member, Long>();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){

        if (cooldown.containsKey(e.getEntity())) {
            long secondsleft = ((cooldown.get(e.getEntity()) / 1000) + 10*60) - (System.currentTimeMillis() / 1000);
            if (secondsleft > 0) {
                System.out.println(e.getEntity().getUser().getName() + " hat einen Channel betreten, obwohl der Cooldown für ihn noch aktiviert ist! (Keine zusätzlichen Punkte)");
                Main.plugin.getMySQL().setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 0, 0, 0, 1);
                return;
            } else {
                if (!Main.plugin.getMySQL().userIsExisting(e.getEntity().getId())) {
                    Main.plugin.getMySQL().createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(824983261197500440L)).queue();
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                } else {
                    Main.plugin.getMySQL().setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                    LevelRoles.addRoles(e.getEntity());
                }
            }
        } else {
            if (!Main.plugin.getMySQL().userIsExisting(e.getEntity().getId())) {
                Main.plugin.getMySQL().createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(824983261197500440L)).queue();
                cooldown.put(e.getEntity(), System.currentTimeMillis());
            } else {
                Main.plugin.getMySQL().setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                cooldown.put(e.getEntity(), System.currentTimeMillis());
                LevelRoles.addRoles(e.getEntity());
            }
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e){

        if (cooldown.containsKey(e.getEntity())) {
            long secondsleft = ((cooldown.get(e.getEntity()) / 1000) + 10*60) - (System.currentTimeMillis() / 1000);
            if (secondsleft > 0) {
                System.out.println(e.getEntity().getUser().getName() + " hat einen Channel gewechselt, obwohl der Cooldown für ihn noch aktiviert ist! (Keine zusätzlichen Punkte)");
                Main.plugin.getMySQL().setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 0, 0, 0, 1);
                return;
            } else {
                if (!Main.plugin.getMySQL().userIsExisting(e.getEntity().getId())) {
                    Main.plugin.getMySQL().createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(824983261197500440L)).queue();
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                } else {
                    Main.plugin.getMySQL().setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                    LevelRoles.addRoles(e.getEntity());
                }
            }
        } else {
            if (!Main.plugin.getMySQL().userIsExisting(e.getEntity().getId())) {
                Main.plugin.getMySQL().createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(824983261197500440L)).queue();
                cooldown.put(e.getEntity(), System.currentTimeMillis());
            } else {
                Main.plugin.getMySQL().setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                cooldown.put(e.getEntity(), System.currentTimeMillis());
                LevelRoles.addRoles(e.getEntity());
            }
        }
    }

}
