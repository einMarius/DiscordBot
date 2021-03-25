package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class valorantstats implements ServerCommand {

    private int amount = 1;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentRaw().split(" ");

        if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                && !channel.getName().equalsIgnoreCase("zitate")
                && !channel.getName().equalsIgnoreCase("schulchat")) {
            if (args.length == 1) {

            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendMessage("Benutze für den Befehl nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
