package fr.ludovicans.villagermanager;

import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import fr.ludovicans.lanslib.utils.ColoredText;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class TradeBlocker implements Listener {

    private final ConfigurationManager configurationManager;

    public TradeBlocker(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerTrade(InventoryOpenEvent event) {
        final FileConfiguration configurationFile = configurationManager.getConfigurationFile("config.yml");
        final boolean tradeEnabled = configurationFile.getBoolean("trades.enabled");

        if (event.getInventory().getType() == InventoryType.MERCHANT && !tradeEnabled) {
            final String message = configurationManager
                    .getConfigurationFile("messages.yml")
                    .getString("trade-disabled");

            event.getPlayer().sendMessage(new ColoredText(message).buildComponent());
            event.setCancelled(true);
        }
    }

}
