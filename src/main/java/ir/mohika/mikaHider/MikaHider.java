package ir.mohika.mikaHider;

import de.exlll.configlib.YamlConfigurations;
import ir.mohika.mikaHider.config.Config;
import ir.mohika.mikaHider.listeners.ItemListener;
import ir.mohika.mikaHider.listeners.PlayerListener;
import ir.mohika.mikaHider.player.PlayerManager;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class MikaHider extends JavaPlugin {

  @Getter private static MikaHider instance;
  @Getter private PlayerManager playerManager;
  @Getter private Config cfg;
  @Getter private static final MiniMessage mm = MiniMessage.miniMessage();

  @Override
  public void onEnable() {
    instance = this;
    cfg = YamlConfigurations.update(getDataFolder().toPath().resolve("config.yml"), Config.class);

    playerManager = new PlayerManager(this);

    getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    getServer().getPluginManager().registerEvents(new ItemListener(this), this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}