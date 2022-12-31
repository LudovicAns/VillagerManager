package fr.ludovicans.villagermanager;

import fr.ludovicans.lanslib.configuration.ConfigurationManager;
import fr.ludovicans.lanslib.utils.ColoredText;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TradeBlacklist implements Listener {

    private final ConfigurationManager configurationManager;

    public TradeBlacklist(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerTrade(PlayerTradeEvent event) {
        if (event.isCancelled()) return;

        final List<ItemStack> blacklist = getBlacklist();

        if (blacklist.contains(event.getTrade().getResult())) {
            final Player player = event.getPlayer();

            final String message = configurationManager
                    .getConfigurationFile("messages.yml")
                    .getString("blacklisted-trade");

            player.sendMessage(new ColoredText(message).buildComponent());
            event.setCancelled(true);
        }
    }

    private List<ItemStack> getBlacklist() {
        final ConfigurationSection blacklistSection = configurationManager
                .getConfigurationFile("config.yml")
                .getConfigurationSection("trades.blacklist");

        final List<ItemStack> blacklist = new ArrayList<>();

        List<String> enchantments = null;
        try {
            enchantments = Objects.requireNonNull(blacklistSection).getStringList("enchanted-books");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (enchantments != null) {
            final List<ItemStack> enchantedBookList = enchantments
                    .stream()
                    .map(s -> s.split(":"))
                    .map(strings -> {
                        final Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(strings[0].toLowerCase()));
                        final int level = Integer.parseInt(strings[1]);

                        if (enchantment == null)
                            return null;

                        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);

                        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) book.getItemMeta();
                        enchantmentStorageMeta.addStoredEnchant(enchantment, level, true);

                        book.setItemMeta(enchantmentStorageMeta);
                        return book;
                    })
                    .filter(Objects::nonNull)
                    .toList();

            blacklist.addAll(enchantedBookList);
        }

        List<String> materials = null;
        try {
            materials = Objects.requireNonNull(blacklistSection).getStringList("materials");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (materials != null) {
            final List<ItemStack> materialsItemList = materials
                    .stream()
                    .map(Material::getMaterial)
                    .filter(Objects::nonNull)
                    .map(ItemStack::new)
                    .toList();

            blacklist.addAll(materialsItemList);
        }

        return blacklist;
    }
}
