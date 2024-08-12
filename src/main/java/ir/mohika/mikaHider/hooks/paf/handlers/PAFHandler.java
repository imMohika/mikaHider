package ir.mohika.mikaHider.hooks.paf.handlers;

import ir.mohika.mikaHider.MikaHider;
import org.bukkit.entity.Player;

public abstract class PAFHandler {
  protected final MikaHider plugin;

  public PAFHandler(MikaHider plugin) {
    this.plugin = plugin;
    checkDeps();
  }

  public abstract void showPaf(Player player);

  public void showAll(Player player) {}

  public void hideAll(Player player) {}

  protected abstract void checkDeps() throws RuntimeException;
}
