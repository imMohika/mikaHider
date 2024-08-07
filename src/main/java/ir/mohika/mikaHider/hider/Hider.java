package ir.mohika.mikaHider.hider;

import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.config.ItemConfig;
import ir.mohika.mikaHider.player.PlayerManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Hider {

  @Getter
  private static final NamespacedKey namespacedKey =
      new NamespacedKey(MikaHider.getInstance(), "hider");

  @Getter private final ItemStack item;
  private final MikaHider plugin;

  private Hider(MikaHider plugin, ItemStack item) {
    this.plugin = plugin;
    this.item = item;
  }

  public void toggle(Player player) {
    plugin.getPlayerManager().toggle(player);
    Bukkit.getScheduler().runTaskLater(plugin, () -> Hider.givePlayer(player), 1L);
  }

  public static Hider from(@NotNull ItemStack item) {
    if (!item.hasItemMeta()) {
      MikaHider.getInstance().getLogger().info("no meta");
      return null;
    }

    ItemMeta itemMeta = item.getItemMeta();
    if (!itemMeta.getPersistentDataContainer().has(namespacedKey)) {
      return null;
    }

    return new Hider(MikaHider.getInstance(), item);
  }

  public static void givePlayer(@NotNull Player player) {
    PlayerManager manager = MikaHider.getInstance().getPlayerManager();
    PlayerManager.PlayerState playerState = manager.getPlayerState(player);
    ItemStack item = createItemStack(playerState);
    player.getInventory().setItem(MikaHider.getInstance().getCfg().getInventorySlot(), item);
  }

  public static ItemStack createItemStack(@NotNull PlayerManager.PlayerState playerState) {
    ItemConfig itemConfig =
        switch (playerState) {
          case VISIBLE -> MikaHider.getInstance().getCfg().getShowingItem();
          case PAF -> MikaHider.getInstance().getCfg().getHooks().getPartyAndFriends().getPafItem();
          case HIDDEN -> MikaHider.getInstance().getCfg().getHidingItem();
        };

    Material material = itemConfig.getMaterial();
    ItemStack item = new ItemStack(material);
    ItemMeta itemMeta = item.getItemMeta();

    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, true);

    itemMeta.displayName(MikaHider.getMm().deserialize(itemConfig.getDisplayName()));
    List<String> lore = itemConfig.getLore();
    if (lore != null && !lore.isEmpty()) {
      itemMeta.lore(lore.stream().map(l -> MikaHider.getMm().deserialize(l)).toList());
    }

    item.setItemMeta(itemMeta);
    return item;
  }
}
