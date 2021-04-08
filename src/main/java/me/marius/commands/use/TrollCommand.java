package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TrollCommand implements ServerCommand {

    private int amount = 1;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (!channel.getName().equalsIgnoreCase("umfragen") && !channel.getName().equalsIgnoreCase("news")
                && !channel.getName().equalsIgnoreCase("memes-und-mehr")
                && !channel.getName().equalsIgnoreCase("ls-mods") && !channel.getName().equalsIgnoreCase("musik")
                && !channel.getName().equalsIgnoreCase("zitate")) {
            if(args.length == 2){
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();

                if(args[1].equalsIgnoreCase("spam")){

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {

                        int sekunden = 0;

                        @Override
                        public void run() {

                            sekunden++;

                            channel.sendMessage("<@&800672450836430859>").queue();

                            if(sekunden >= 15){
                                timer.cancel();
                            }

                        }
                    }, 0, 500);


                }
            }else{
                channel.purgeMessages(Utils.get(channel, amount));
                channel.sendTyping().queue();
                channel.sendMessage("Benutze #troll <spam>").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, amount));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
