package fr.ludovicans.villagermanager;

import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class BreedBlocker implements Listener {

    private final ConfigurationManager configurationManager;

    public BreedBlocker (ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    /**
     * This listener is used to cancel breed on villagers.
     *
     * @param e event played.
     */
    @EventHandler
    private void onVillagerBreed(EntityBreedEvent e) {
        final boolean villagerReproduction = configurationManager
                .getConfigurationFile("config.yml")
                .getBoolean("villagers-reproduction");

        if (villagerReproduction)
            return;

        if (e.getEntity().getType().equals(EntityType.VILLAGER)) {
            e.setCancelled(true);
        }
    }

}
