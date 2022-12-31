package fr.ludovicans.villagermanager;

import fr.ludovicans.lanslib.acf.PaperCommandManager;
import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import fr.ludovicans.villagermanager.command.VillagerManagerCommand;
import fr.ludovicans.villagermanager.configs.Config;
import fr.ludovicans.villagermanager.configs.Messages;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class VillagerManager extends JavaPlugin implements Listener {

    private static VillagerManager INSTANCE;
    private ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.configurationManager = new ConfigurationManager(this);
        configurationManager.setupDataFolder();

        new Config(configurationManager);
        new Messages(configurationManager);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new VillagerManagerCommand());
        manager.getCommandCompletions().registerAsyncCompletion(
                "vmreload",
                context -> configurationManager.getFilesMap().keySet().stream().map(File::getName).toList()
        );


        // Register main class (this) as an event.
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new TradeBlocker(configurationManager), this);
        this.getServer().getPluginManager().registerEvents(new TradeBlacklist(configurationManager), this);
    }

    @Override
    public void onDisable() {
        this.configurationManager.saveFiles();
    }

    /**
     * This listener is used to cancel breed on villagers.
     *
     * @param e event played.
     */
    @EventHandler
    private void onVillagerBreed(EntityBreedEvent e) {
        if (e.getEntity().getType().equals(EntityType.VILLAGER)) {
            e.setCancelled(true);
        }
    }

    public static VillagerManager getINSTANCE() {
        return INSTANCE;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}
