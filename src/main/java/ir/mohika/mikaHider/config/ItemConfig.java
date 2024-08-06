package ir.mohika.mikaHider.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Getter
@NoArgsConstructor
public class ItemConfig {
  private Material material = Material.GRASS_BLOCK;
  private String displayName = "ITEM";
  private @Nullable List<String> lore = new ArrayList<>();

  public ItemConfig(Material material, String displayName, @Nullable List<String> lore) {
    this.material = material;
    this.displayName = displayName;
    this.lore = lore != null ? lore : new ArrayList<>();
  }
}
