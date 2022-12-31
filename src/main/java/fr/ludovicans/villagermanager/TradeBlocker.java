package fr.ludovicans.villagermanager;

import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TradeBlocker implements Listener {

    private final ConfigurationManager configurationManager;

    public TradeBlocker(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerTrade(PlayerTradeEvent event) {
        final FileConfiguration configurationFile = configurationManager.getConfigurationFile("config.yml");
        final boolean tradeEnabled = configurationFile.getBoolean("trades.enabled");

        if (!tradeEnabled) {
            event.setCancelled(true);
        }
    }

}
