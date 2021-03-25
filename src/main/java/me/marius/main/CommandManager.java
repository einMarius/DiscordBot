package me.marius.main;

import me.marius.commands.types.ServerCommand;
import me.marius.commands.use.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager(){

        this.commands = new ConcurrentHashMap<>();

        this.commands.put("clear", new ClearCommand());
        this.commands.put("zitat", new ZitateCommand());
        this.commands.put("new", new NewsCommand());
        this.commands.put("umfrage", new UmfrageCommand());
        this.commands.put("bot", new OnlineOfflineCommand());
        this.commands.put("createteams", new CreateTeamsCommand());
        this.commands.put("thais", new ThaisCommand());
        this.commands.put("hesterno", new HesternoCommand());
        this.commands.put("odet", new OdetAmoCommand());
        this.commands.put("tongilianus", new TongilianusCommand());
        this.commands.put("rememberregistration", new RememberCommand());
        this.commands.put("addroles", new AddRolesCommand());
        this.commands.put("boost", new PlayCommand());
        this.commands.put("top", new TopCommand());
        this.commands.put("stats", new StatsCommand());

    }

    public boolean perform(String command, Member m, TextChannel channel, Message message){

        ServerCommand cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null){

            cmd.performCommand(m, channel, message);
            return true;
        }
        return false;
    }

}
