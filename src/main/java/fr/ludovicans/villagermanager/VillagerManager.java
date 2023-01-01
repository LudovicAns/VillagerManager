package fr.ludovicans.villagermanager;

import fr.ludovicans.lanslib.acf.PaperCommandManager;
import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import fr.ludovicans.villagermanager.command.VillagerManagerCommand;
import fr.ludovicans.villagermanager.configs.Config;
import fr.ludovicans.villagermanager.configs.Messages;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class VillagerManager extends JavaPlugin {

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

        this.getServer().getPluginManager().registerEvents(new TradeBlocker(configurationManager), this);
        this.getServer().getPluginManager().registerEvents(new TradeBlacklist(configurationManager), this);
        this.getServer().getPluginManager().registerEvents(new BreedBlocker(configurationManager), this);
    }

    @Override
    public void onDisable() {
        this.configurationManager.saveFiles();
    }

    public static VillagerManager getINSTANCE() {
        return INSTANCE;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}
