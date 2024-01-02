package com.exidex.stg;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@
public class STGConfig {

    // Configuration instance
    public static Configuration config;

    // Configuration properties
    public static boolean cancelClickEventPropagation = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(STG.MODID)) {
            syncConfig();
        }
    }

    private static void syncConfig() {
        Property prop;

        // Read the configuration values
        prop = config.get(Configuration.CATEGORY_GENERAL, "cancelClickEventPropagation", false,
                "Whether to cancel click event propagation. Setting this to 'true' will prevent destroying of blocks like tall grass and vanilla flowers. If you expect vanilla clients on the server, leave it 'false' otherwise this will introduce server-client desync.");
        cancelClickEventPropagation = prop.getBoolean(false);

        // Save the configuration file
        if (config.hasChanged()) {
            config.save();
        }
    }
}
