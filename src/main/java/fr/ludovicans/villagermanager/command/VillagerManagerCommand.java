package fr.ludovicans.villagermanager.command;

import fr.ludovicans.lanslib.acf.BaseCommand;
import fr.ludovicans.lanslib.acf.annotation.*;
import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import fr.ludovicans.lanslib.utils.ColoredText;
import fr.ludovicans.villagermanager.VillagerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

@CommandAlias("villagermanager|vm")
public class VillagerManagerCommand extends BaseCommand {

    private static final String BASE_PERMISSION = "villagermanager.command";
    private static final ConfigurationManager CM = VillagerManager.getINSTANCE().getConfigurationManager();

    @Subcommand("reload")
    @Syntax("[fileName]")
    @CommandCompletion("@vmreload")
    @CommandPermission(BASE_PERMISSION + ".reload")
    public static void onReload(CommandSender sender, @Optional String fileName){
        FileConfiguration messages = CM.getConfigurationFile("messages.yml");
        if (fileName != null && !fileName.isEmpty()) {
            if (CM.getConfigurationFile(fileName) != null) {
                CM.reloadFile(fileName);
                sender.sendMessage(new ColoredText(
                        messages.getString("reload-file", "Configuration file " + fileName + " reloaded.")
                                .replaceAll("\\{filename}", fileName))
                        .buildComponent());
            } else {
                sender.sendMessage(new ColoredText(
                        messages.getString("unknown-file", "Unknown configuration file.")
                                .replaceAll("\\{filename}", fileName))
                        .buildComponent());
            }
        } else {
            CM.reloadFiles();
            sender.sendMessage(new ColoredText(
                    messages.getString("reload", "Configuration files reloaded.")).buildComponent()
            );
        }
    }

}
