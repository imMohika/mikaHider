package ir.mohika.mikaHider.hooks.paf;

import ir.mohika.mikaHider.MikaHider;
import ir.mohika.mikaHider.hooks.paf.handlers.PAFApiHandler;
import ir.mohika.mikaHider.hooks.paf.handlers.PAFGuiHandler;
import ir.mohika.mikaHider.hooks.paf.handlers.PAFHandler;
import lombok.Getter;

public class PAFHook {
  private final MikaHider plugin;
  @Getter private PAFHookConfig pafConfig;
  @Getter private PAFHandler handler;

  public PAFHook(MikaHider plugin) throws RuntimeException {
    this.plugin = plugin;
    this.pafConfig = plugin.getCfg().getHooks().getPartyAndFriends();
    if (!pafConfig.isEnabled()) {
      throw new RuntimeException("PAF hook is disabled but i got called?");
    }

    handler =
        switch (pafConfig.getHookType()) {
          case GUI -> new PAFGuiHandler(plugin);
          case API -> new PAFApiHandler(plugin);
        };
  }
}
