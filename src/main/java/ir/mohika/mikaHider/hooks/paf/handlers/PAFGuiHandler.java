package ir.mohika.mikaHider.hooks.paf.handlers;

import de.simonsator.partyandfriendsgui.api.PartyFriendsAPI;
import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.hooks.paf.PAFHookConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PAFGuiHandler extends PAFHandler {
  private final PAFHookConfig.GuiHookConfig guiHookConfig;

  public PAFGuiHandler(MikaHider plugin) {
    super(plugin);
    guiHookConfig = plugin.getCfg().getHooks().getPartyAndFriends().getGuiHook();
  }

  @Override
  public void showPaf(Player player) {
    PartyFriendsAPI.setHideMode(
        player,
        switch (guiHookConfig.getShow()) {
          case PARTY -> guiHookConfig.getWorth().getParty();
          case FRIENDS -> guiHookConfig.getWorth().getFriends();
        });
  }

  @Override
  public void showAll(Player player) {
    PartyFriendsAPI.setHideMode(player, guiHookConfig.getWorth().getShow());
  }

  @Override
  public void hideAll(Player player) {
    PartyFriendsAPI.setHideMode(player, guiHookConfig.getWorth().getHide());
  }

  @Override
  protected void checkDeps() throws RuntimeException {
    if (!Bukkit.getPluginManager().isPluginEnabled("PartyAndFriendsGUI")) {
      throw new RuntimeException(
          "PAF GUI hook is enabled but 'PartyAndFriendsGUI' isn't installed, check plugin config");
    }
  }
}
