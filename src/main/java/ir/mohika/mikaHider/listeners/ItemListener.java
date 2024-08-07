package ir.mohika.mikaHider.listeners;

import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.hider.Hider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {
  private final MikaHider plugin;

  public ItemListener(MikaHider plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onItemUse(PlayerInteractEvent event) {
    if (event.getHand() != EquipmentSlot.HAND) {
      return;
    }

    if (event.getAction() != Action.RIGHT_CLICK_AIR
        && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    ItemStack itemStack = event.getItem();
    if (itemStack == null) {
      return;
    }

    Hider hider = Hider.from(itemStack);
    if (hider == null) {
      return;
    }

    Player player = event.getPlayer();
    hider.toggle(player);
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    Hider hider = Hider.from(event.getItemDrop().getItemStack());
    if (hider == null) {
      return;
    }

    event.setCancelled(true);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    ItemStack item = event.getCurrentItem();
    if (item == null) {
      return;
    }

    Hider hider = Hider.from(item);
    if (hider == null) {
      return;
    }

    InventoryAction action = event.getAction();
    if (action == InventoryAction.PICKUP_ONE
        || action == InventoryAction.PICKUP_SOME
        || action == InventoryAction.PICKUP_HALF
        || action == InventoryAction.PICKUP_ALL
        || action == InventoryAction.MOVE_TO_OTHER_INVENTORY
        || action == InventoryAction.CLONE_STACK
        || action == InventoryAction.HOTBAR_SWAP
        || action == InventoryAction.SWAP_WITH_CURSOR
        || event.isShiftClick()) {
      event.setCancelled(true);
    }
  }
}
