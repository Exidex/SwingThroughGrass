package com.exidex.stg;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author exidex.
 * @since 08.12.2017.
 */
@Config(modid = STG.MODID)
@Mod.EventBusSubscriber
public class STGConfig {

	@Config.Comment("Whether to cancel click event propagation.\n" +
			"Setting this to 'true' will prevent destroying of blocks like tall grass and vanilla flowers.\n" +
			"If you expect vanilla clients on server leave it 'false' otherwise this will introduce server-client desync.")
	public static boolean cancelClickEventPropagation = false;

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(STG.MODID)) {
			ConfigManager.load(STG.MODID, Config.Type.INSTANCE);
		}
	}
}
