package ir.mohika.mikaHider.listeners;

import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.hider.Hider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
  private final MikaHider plugin;

  public PlayerListener(MikaHider plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    plugin.getPlayerManager().playerJoined(player);
    Hider.givePlayer(player);
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    plugin.getPlayerManager().removePlayer(player);
  }
}
