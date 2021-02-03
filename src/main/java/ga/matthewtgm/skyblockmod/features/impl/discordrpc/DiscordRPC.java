package ga.matthewtgm.skyblockmod.features.impl.discordrpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.time.OffsetDateTime;

public class DiscordRPC implements IPCListener {

    public static DiscordRPC INSTANCE = new DiscordRPC();
    public boolean connected;
    private final RichPresence.Builder builder = new RichPresence.Builder();
    private final IPCClient client = new IPCClient(784383977774907392L);
    //private final Logger logger = LoggingUtils.getInstance().createClassLogger(this.getClass());

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void start() {
        try {
            client.setListener(new IPCListener() {

                @Override
                public void onReady(IPCClient client) {
                    client.sendRichPresence(builder.build());
                }

            });
            client.connect();
            builder.setStartTimestamp(OffsetDateTime.now());
            client.sendRichPresence(builder.build());
        } catch (Exception e) {
            if (e instanceof RuntimeException || e instanceof FileNotFoundException) return;
            e.printStackTrace();
            this.setConnected(false);
        }
        if (this.isConnected()) {
            //logger.info("Connected to SkyBlockModRPC");
        } else {
            //logger.info("Did NOT connect to SkyBlockModRPC");
        }
    }

    public void stop() {
        try {
            client.close();
            this.setConnected(false);
        } catch (Exception e) {
            this.setConnected(true);
            if(e instanceof NoDiscordClientException || e instanceof RuntimeException) return;
            e.printStackTrace();
        }
    }

    public void setPresence(String firstLine) {
        builder.setDetails(firstLine);
        client.sendRichPresence(builder.build());
    }

    public void setPresence(String firstLine, String secondLine) {
        builder.setDetails(firstLine)
                .setState(secondLine);
        client.sendRichPresence(builder.build());
    }

    public void setPresence(String firstLine, String secondLine, String largeImage) {
        builder.setDetails(firstLine)
                .setState(secondLine)
                .setLargeImage(largeImage);
        client.sendRichPresence(builder.build());
    }

    public void setPresence(String firstLine, String secondLine, String largeImage, String smallImage) {
        builder.setDetails(firstLine)
                .setState(secondLine)
                .setLargeImage(largeImage)
                .setSmallImage(smallImage);
        client.sendRichPresence(builder.build());
    }

    @Override
    public void onReady(IPCClient client) {
        this.setConnected(true);
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
        this.setConnected(false);
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        this.setConnected(false);
    }

}