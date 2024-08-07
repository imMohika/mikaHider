package ir.mohika.mikaHider.hooks.paf;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import ir.mohika.mikaHider.config.ItemConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

@SuppressWarnings("FieldMayBeFinal")
@Configuration
@Getter
@NoArgsConstructor
public class PAFHookConfig {
  private boolean enabled = false;

  @Comment({
    "Hooks into PAF using its APIs.",
    //    "With this type you can show both friends & party members",
    "Requires these dependencies to be installed on server:",
    "https://www.spigotmc.org/resources/spigot-friend-api-for-party-and-friends-for-bungeecord-velocity.12597/",
    "https://www.spigotmc.org/resources/spigot-party-api-for-party-and-friends.39751/"
  })
  private ApiHookConfig apiHook = new ApiHookConfig();

  @Comment("Inventory Item when State is PAF")
  private ItemConfig pafItem =
      new ItemConfig(Material.ORANGE_DYE, "<gold>Showing Party & Friends", null);

  @Configuration
  @NoArgsConstructor
  @Getter
  public static class ApiHookConfig {
    private boolean showFriends = true;
    private boolean showParty = true;
  }
}
