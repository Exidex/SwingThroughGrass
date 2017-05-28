package com.exidex.stg;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * @author exidex.
 * @since 02.04.2017.
 */
@Mod(modid = STG.MODID, name = STG.NAME, version = STG.VERSION)
public class STG {

    public static final String MODID = "stg";
    public static final String VERSION = "1.0.2";
    public static final String NAME = "SwingThroughGrass";

    @Mod.Instance(STG.MODID)
	public static STG instance;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(LeftClickEventHandler.class);
    }
}
