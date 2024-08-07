package ir.mohika.mikaHider.hooks.paf;

import de.simonsator.partyandfriends.spigot.api.events.BridgeNotAvailableException;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventManager;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import ir.mohika.mikaHider.MikaHider;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PAFHook {
  private final MikaHider plugin;
  @Getter private PAFHookConfig pafConfig;

  public PAFHook(MikaHider plugin) throws RuntimeException {
    checkDeps();
    this.plugin = plugin;
    this.pafConfig = plugin.getCfg().getHooks().getPartyAndFriends();
    if (!pafConfig.isEnabled()) {
      throw new RuntimeException("PAF hook is disabled but i got called?");
    }

    if (pafConfig.getApiHook().isShowParty() && PartyEventManager.isBridgeAvailable()) {
      try {
        PartyEventManager.registerPartyEventListener(new PAFListeners(plugin));
      } catch (BridgeNotAvailableException e) {
        plugin.getLogger().severe("Something went wrong while registering party listeners!");
        e.printStackTrace();
      }
    } else {
      plugin.getLogger().warning("PAF-GUI isn't installed on server");
      plugin.getLogger().warning("It's only needed for showing/hiding players when party changes");
      plugin
          .getLogger()
          .warning(
              "If you need these two install it from 'https://www.spigotmc.org/resources/10123/'");
    }
  }

  public void showPafOnly(Player player) {
    PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(player.getUniqueId());
    Set<String> dontHide = new HashSet<>();

    if (pafConfig.getApiHook().isShowFriends()) {
      dontHide.addAll(pafPlayer.getFriends().stream().map(p -> p.getName().toLowerCase()).toList());
    }

    if (pafConfig.getApiHook().isShowParty()) {
      PlayerParty party = PartyManager.getInstance().getParty(pafPlayer);
      if (party != null) {
        dontHide.addAll(
            party.getAllPlayers().stream().map(p -> p.getName().toLowerCase()).toList());
      }
    }

    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p == player || dontHide.contains(p.getName().toLowerCase())) continue;
      player.hidePlayer(plugin, p);
    }
  }

  private void checkDeps() throws RuntimeException {
    if (!Bukkit.getPluginManager().isPluginEnabled("FriendsAPIForPartyAndFriends")) {
      throw new RuntimeException(
          "PAF hook is enabled but 'FriendsAPIForPartyAndFriends' isn't installed, check plugin config");
    }

    if (!Bukkit.getPluginManager().isPluginEnabled("Spigot-Party-API-PAF")) {
      throw new RuntimeException(
          "PAF hook is enabled but 'FriendsAPIForPartyAndFriends' isn't installed, check plugin config");
    }
  }
}
