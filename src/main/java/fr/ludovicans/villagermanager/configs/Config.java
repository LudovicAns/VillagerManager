package fr.ludovicans.villagermanager.configs;

import fr.ludovicans.lanslib.configuration.ConfigurationManager;

public class Config {

    public static String CONTENT = """
            # Autorise ou non la reproduction des villageois sur le serveur.
            villagers-reproduction: true
            
            trades:
              # Active ou désactive les échanges avec les villageois.
              enabled: true
              
              # Permet de bloquer les échanges de certain items. ATTENTION: il ne supprime pas la possibilité aux villageois
              # de proposer les items en questions.
              blacklist:
                # Permet de blacklist des livres enchantés.
                # Il suffiit d'ajouter l'enchantement à la liste suivi du séparateur ':' et du niveau de l'enchant.
                enchanted-books: [ "MENDING:1" ]
                # Permet de blacklist des matériaux spécifiques (STONE, IRON_SWORD, ...).
                materials: []
            """;

    public Config(final ConfigurationManager configurationManager) {
        configurationManager.initNewFile(".", "config.yml", CONTENT);
    }
}
