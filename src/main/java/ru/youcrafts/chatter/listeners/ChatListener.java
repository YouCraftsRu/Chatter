package ru.youcrafts.chatter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.youcrafts.chatter.Config;
import ru.youcrafts.chatter.channels.ChatHandlerInterface;
import ru.youcrafts.chatter.manager.ChannelManager;

import java.util.HashMap;

public class ChatListener implements Listener
{


    private final Config config;
    private final HashMap<String, ChatHandlerInterface> channels;


    public ChatListener(Config config, ChannelManager channelManager)
    {
        this.config = config;
        this.channels = channelManager.getChannels();
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        String symbol = event.getMessage().substring(0, 1);

        for (ChatHandlerInterface handler : this.channels.values()) {
            if (handler.isSupportBySymbol(symbol)) {
                handler.handle(event);
                break;
            }
        }

        event.setCancelled(true);
    }
}
