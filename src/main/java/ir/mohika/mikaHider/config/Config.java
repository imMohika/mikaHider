package ir.mohika.mikaHider.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

@SuppressWarnings("FieldMayBeFinal")
@Configuration
@Getter
@NoArgsConstructor
public class Config {
  @Comment({"Which slot should the Hider item be in", "Should be between 0 - 8 (hotbar slots)"})
  private int inventorySlot = 7;

  @Comment("Inventory Item when State is Show all")
  private ItemConfig showingItem =
      new ItemConfig(Material.LIME_DYE, "<green>Showing all Players", null);

  @Comment("Inventory Item when State is Hide all")
  private ItemConfig hidingItem =
      new ItemConfig(Material.GRAY_DYE, "<gray>Hiding all Players", null);

  @Comment("Inventory Item when State is Friends Only")
  private ItemConfig friendsItem =
      new ItemConfig(Material.ORANGE_DYE, "<gray>Showing Friends", null);
}
