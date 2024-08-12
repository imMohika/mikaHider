package ir.mohika.mikaHider.hooks.paf;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import ir.mohika.mikaHider.config.ItemConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("FieldMayBeFinal")
@Configuration
@Getter
@NoArgsConstructor
public class PAFHookConfig {
  private boolean enabled = false;

  @Comment({"Hook type can be 'GUI' or 'API'"})
  private HookType hookType = HookType.GUI;

  public enum HookType {
    GUI,
    API
  }

  @Comment({
    "Hooks into PAF using its GUI.",
    "With this type you can either show friends or party (use API if you want both)",
    "NOTE: This hook type makes plugin use PAF's hider feature instead of it's own",
    "Requires PAF-GUI to be installed on server",
  })
  private GuiHookConfig guiHook = new GuiHookConfig();

  @Configuration
  @NoArgsConstructor
  @Getter
  public static class GuiHookConfig {
    private HideMode show = HideMode.FRIENDS;

    public enum HideMode {
      PARTY,
      FRIENDS
    }

    @Comment({"Don't change these unless you know what you're doing"})
    private Worth worth = new Worth();

    @Configuration
    @NoArgsConstructor
    @Getter
    public static class Worth {
      private int show = 0;
      private int friends = 1;
      private int hide = 4;
      private int party = 5;
    }
  }

  @Comment({
    "Hooks into PAF using its APIs.",
    "With this type you can show both friends & party members",
    "Requires these dependencies to be installed on server:",
    "https://www.spigotmc.org/resources/12597/",
    "https://www.spigotmc.org/resources/39751/",
    "PAF-GUI is optional and can be installed for show/hide players when party changes",
    "https://www.spigotmc.org/resources/10123/"
  })
  private ApiHookConfig apiHook = new ApiHookConfig();

  @Configuration
  @NoArgsConstructor
  @Getter
  public static class ApiHookConfig {
    private boolean showFriends = true;
    private boolean showParty = true;
  }

  @Comment("Inventory Item when State is PAF")
  private ItemConfig pafItem =
      new ItemConfig(Material.ORANGE_DYE, "<gold>Showing Party & Friends", null);
}
