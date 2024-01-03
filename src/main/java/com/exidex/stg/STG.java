package com.exidex.stg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = STG.MODID, name = STG.NAME, version = STG.VERSION, acceptableRemoteVersions = "*")
public class STG {
    public static final String MODID = "stg";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "SwingThroughGrass";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new LeftClickEventHandler());

        // Some example code
        System.out.println("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
    }
}
