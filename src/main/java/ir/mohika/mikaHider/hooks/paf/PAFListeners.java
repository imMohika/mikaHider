package ir.mohika.mikaHider.hooks.paf;

import de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PAFListeners implements PartyEventListenerInterface {

  private final MikaHider plugin;

  public PAFListeners(MikaHider plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onLeftParty(PAFPlayer pafLeft, @Nullable PlayerParty playerParty) {
    if (playerParty == null) {
      return;
    }

    Player left = Bukkit.getPlayer(pafLeft.getUniqueId());
    if (left == null) {
      return;
    }

    // hide player that left for other party members
    for (PAFPlayer pafPlayer : playerParty.getAllPlayers()) {
      if (pafLeft.getName().equals(pafPlayer.getName())) {
        continue;
      }

      Player player = Bukkit.getPlayer(pafPlayer.getUniqueId());
      if (player == null) {
        continue;
      }

      PlayerManager.PlayerState playerState = plugin.getPlayerManager().getPlayerState(player);
      if (playerState == PlayerManager.PlayerState.PAF) {
        if (plugin.getPafHook().getPafConfig().getApiHook().isShowFriends()
            && pafLeft.isAFriendOf(pafPlayer)) {
          continue;
        }
        player.hidePlayer(plugin, left);
      }
    }
  }

  @Override
  public void onPartyCreated(@Nullable PlayerParty playerParty) {
    if (playerParty == null) {
      return;
    }
    // don't ask me what's going on here
    for (PAFPlayer pafPlayer : playerParty.getAllPlayers()) {
      Player player = Bukkit.getPlayer(pafPlayer.getUniqueId());
      if (player == null) {
        continue;
      }

      PlayerManager.PlayerState playerState = plugin.getPlayerManager().getPlayerState(player);
      if (playerState != PlayerManager.PlayerState.PAF) {
        continue;
      }

      for (PAFPlayer pafOther : playerParty.getAllPlayers()) {
        if (pafOther.getUniqueId().equals(pafPlayer.getUniqueId())) {
          continue;
        }

        Player other = Bukkit.getPlayer(pafPlayer.getUniqueId());
        if (other == null) {
          continue;
        }
        player.showPlayer(plugin, other);
      }
    }
  }

  @Override
  public void onPartyJoin(PAFPlayer pafJoined, @Nullable PlayerParty playerParty) {
    if (playerParty == null) {
      return;
    }

    Player joined = Bukkit.getPlayer(pafJoined.getUniqueId());
    if (joined == null) {
      return;
    }

    // show player that joined for other party members
    for (PAFPlayer pafPlayer : playerParty.getAllPlayers()) {
      if (pafJoined.getName().equals(pafPlayer.getName())) {
        continue;
      }

      Player player = Bukkit.getPlayer(pafPlayer.getUniqueId());
      if (player == null) {
        continue;
      }

      PlayerManager.PlayerState playerState = plugin.getPlayerManager().getPlayerState(player);
      if (playerState == PlayerManager.PlayerState.PAF) {
        player.showPlayer(plugin, joined);
      }
    }
  }

  @Override
  public void onPartyLeaderChanged(PAFPlayer pafPlayer, @Nullable PlayerParty playerParty) {}
}
