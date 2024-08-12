package ir.mohika.mikaHider.hooks.paf.handlers;

import de.simonsator.partyandfriends.spigot.api.events.BridgeNotAvailableException;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventManager;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.hooks.paf.PAFHookConfig;
import ir.mohika.mikaHider.hooks.paf.PAFPartyListeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PAFApiHandler extends PAFHandler {
  private final PAFHookConfig.ApiHookConfig apiHookConfig;

  public PAFApiHandler(MikaHider plugin) {
    super(plugin);
    apiHookConfig = plugin.getCfg().getHooks().getPartyAndFriends().getApiHook();
  }

  @Override
  public void showPaf(Player player) {
    PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(player.getUniqueId());
    Set<String> dontHide = new HashSet<>();

    if (apiHookConfig.isShowFriends()) {
      dontHide.addAll(pafPlayer.getFriends().stream().map(p -> p.getName().toLowerCase()).toList());
    }

    if (apiHookConfig.isShowParty()) {
      Optional.ofNullable(PartyManager.getInstance().getParty(pafPlayer))
          .ifPresent(
              party -> {
                dontHide.addAll(
                    party.getAllPlayers().stream().map(p -> p.getName().toLowerCase()).toList());
              });
    }

    Bukkit.getOnlinePlayers().stream()
        .filter(p -> !p.getName().equalsIgnoreCase(player.getName()))
        .filter(p -> !dontHide.contains(p.getName().toLowerCase()))
        .forEach(p -> player.hidePlayer(plugin, p));
  }

  @Override
  protected void checkDeps() throws RuntimeException {
    if (!Bukkit.getPluginManager().isPluginEnabled("FriendsAPIForPartyAndFriends")) {
      throw new RuntimeException(
          "PAF API hook is enabled but 'FriendsAPIForPartyAndFriends' isn't installed, check plugin config");
    }

    if (!Bukkit.getPluginManager().isPluginEnabled("Spigot-Party-API-PAF")) {
      throw new RuntimeException(
          "PAF API hook is enabled but 'FriendsAPIForPartyAndFriends' isn't installed, check plugin config");
    }

    if (apiHookConfig.isShowParty() && PartyEventManager.isBridgeAvailable()) {
      try {
        PartyEventManager.registerPartyEventListener(new PAFPartyListeners(plugin));
      } catch (BridgeNotAvailableException e) {
        plugin.getLogger().severe("Something went wrong while registering party listeners!");
        e.printStackTrace();
      }
    } else {
      plugin.getLogger().warning("PAF-GUI isn't installed on server");
      plugin
          .getLogger()
          .warning("It's needed for supporting showing/hiding players when party changes");
      plugin
          .getLogger()
          .warning("If you need this install it from 'https://www.spigotmc.org/resources/10123/'");
    }
  }
}
