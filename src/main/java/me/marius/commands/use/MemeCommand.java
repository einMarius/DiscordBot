package me.marius.commands.use;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemeCommand implements ServerCommand {

    String[] colours =  new String[] {
            "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff", "45ffc4", "459fff", "4a2bfc", "000000", "b55400", "faef52", "93fc38", "76ff00"
    };

    private String postlink = "";
    private String title = "";
    private String url = "";

    private boolean isRunningMeme;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (channel.getIdLong() == 673615226955890710L || channel.getIdLong() == 816278405204017162L) {
            if (args.length == 1) {

                channel.purgeMessages(Utils.get(channel, 1));
                channel.sendTyping().queue();

                isRunningMeme = !isRunningMeme;

                new Thread(() -> {
                    while(isRunningMeme) {
                        JSONParser parser = new JSONParser();

                        try {
                            URL memeURL = new URL("https://meme-api.herokuapp.com/gimme");
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));

                            String lines;
                            while ((lines = bufferedReader.readLine()) != null) {
                                JSONArray array = new JSONArray();
                                array.add(parser.parse(lines));

                                for (Object o : array) {
                                    JSONObject jsonObject = (JSONObject) o;

                                    postlink = (String) jsonObject.get("postlink");
                                    title = (String) jsonObject.get("title");
                                    url = (String) jsonObject.get("url");
                                }
                            }
                            bufferedReader.close();

                            Random rand = new Random();
                            int i = rand.nextInt(colours.length);

                            String colour = colours[i];

                            EmbedBuilder builder = new EmbedBuilder()
                                    .setTitle(title, postlink)
                                    .setImage(url)
                                    .setColor(Color.decode("0x" + colour));

                            channel.sendMessage(builder.build()).queue();

                        } catch (Exception e) {
                            channel.sendMessage("Es gab einen Fehler beim laden des Memes!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isRunningMeme = false;
                    }
                }).start();

            } else {
                channel.purgeMessages(Utils.get(channel, 1));
                channel.sendTyping().queue();
                channel.sendMessage("Für geile Memes musst du #plsMeme schreiben!").complete().delete()
                        .queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            channel.purgeMessages(Utils.get(channel, 1));
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete()
                    .queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
