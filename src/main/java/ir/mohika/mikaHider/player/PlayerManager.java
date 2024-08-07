package ir.mohika.mikaHider.player;

import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.hooks.paf.PAFHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerManager {
  private final MikaHider plugin;
  private final Map<String, PlayerState> playerStates = new HashMap<>();
  private final boolean pafHook;

  public PlayerManager(MikaHider plugin) {
    this.plugin = plugin;
    pafHook = plugin.getCfg().getHooks().getPartyAndFriends().isEnabled();
  }

  public void removePlayer(@NotNull Player player) {
    this.playerStates.remove(player.getName().toLowerCase());
  }

  public PlayerState toggle(@NotNull Player player) {
    String name = player.getName().toLowerCase();
    PlayerState currentState = this.playerStates.get(name);
    if (currentState == null) {
      this.playerStates.put(name, PlayerState.VISIBLE);
      showPlayers(player);
      return PlayerState.VISIBLE;
    }

    PlayerState nextState = nextState(currentState);

    playerStates.put(name, nextState);
    switch (nextState) {
      case VISIBLE -> showPlayers(player);
      case PAF -> pafOnly(player);
      case HIDDEN -> hidePlayers(player);
    }

    return nextState;
  }

  private PlayerState nextState(PlayerState current) {
    return switch (current) {
      case VISIBLE -> pafHook ? PlayerState.PAF : PlayerState.HIDDEN;
      case PAF -> PlayerState.HIDDEN;
      case HIDDEN -> PlayerState.VISIBLE;
    };
  }

  private void showPlayers(Player player) {
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p == player) continue;
      player.showPlayer(plugin, p);
    }
  }

  private void pafOnly(Player player) {
    plugin.getPafHook().showPafOnly(player);
  }

  private void hidePlayers(Player player) {
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p == player) continue;
      player.hidePlayer(plugin, p);
    }
  }

  public void playerJoined(@NotNull Player joined) {
    for (Map.Entry<String, PlayerState> entry : playerStates.entrySet()) {
      Player player = Bukkit.getPlayer(entry.getKey());
      if (player == null) continue;
      switch (entry.getValue()) {
        case VISIBLE -> player.showPlayer(plugin, joined);
        case HIDDEN -> player.hidePlayer(plugin, joined);
      }
    }
  }

  public PlayerState getPlayerState(@NotNull Player player) {
    return playerStates.computeIfAbsent(player.getName().toLowerCase(), k -> PlayerState.VISIBLE);
  }

  public enum PlayerState {
    HIDDEN,
    VISIBLE,
    PAF
  }
}
