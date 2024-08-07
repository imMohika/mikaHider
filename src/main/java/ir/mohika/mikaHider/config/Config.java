package ir.mohika.mikaHider.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import ir.mohika.mikaHider.hooks.paf.PAFHookConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

@SuppressWarnings("FieldMayBeFinal")
@Configuration
@Getter
@NoArgsConstructor
public class Config {
  @Comment({"Which slot should the Hider item be in", "Should be between 0 - 8 (HotBar slots)"})
  private int inventorySlot = 7;

  @Comment("Inventory Item when State is Show all")
  private ItemConfig showingItem =
      new ItemConfig(Material.LIME_DYE, "<green>Showing all Players", null);

  @Comment("Inventory Item when State is Hide all")
  private ItemConfig hidingItem =
      new ItemConfig(Material.GRAY_DYE, "<gray>Hiding all Players", null);

  private Hooks hooks = new Hooks();

  @Configuration
  @Getter
  @NoArgsConstructor
  public static class Hooks {
    @Comment({
      "Hook into Party And Friends",
      "Adds Friends & Party State Feature",
      "Check: https://www.spigotmc.org/resources/party-and-friends-extended-edition-for-bungeecord-velocity-supports-1-7-1-21-x.10123/"
    })
    private PAFHookConfig partyAndFriends = new PAFHookConfig();
  }
}
