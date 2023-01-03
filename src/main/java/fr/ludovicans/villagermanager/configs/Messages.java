package fr.ludovicans.villagermanager.configs;

import fr.ludovicans.lanslib.configuration.ConfigurationManager;

public class Messages {

    public Messages(final ConfigurationManager configurationManager) {
        final String CONTENT = """
                reload: '&aLes fichiers de configurations ont bien été reload.'
                reload-file: '&aLe fichier de configuration &e{filename} &aa bien été reload.'
                unknown-file: '&cLe fichier de configuration &6{filename} &cn''existe pas.'
                blacklisted-trade: "&3&lEden&b&lCraft &8➜ &cL'échange de cet item a été désactivé sur le serveur."
                trade-disabled: "&3&lEden&b&lCraft &8➜ &cLes échanges avec les PNJ ont été desactivé sur le serveur."
                """;

        configurationManager.initNewFile(".", "messages.yml", CONTENT);
    }
}
