package com.exidex.stg;

import net.minecraftforge.fml.common.Mod;

/**
 * @author exidex.
 * @since 02.04.2017.
 */
@Mod(modid = STG.MODID, name = STG.NAME, version = STG.VERSION, acceptableRemoteVersions = "*")
public class STG {

    public static final String MODID = "stg";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "SwingThroughGrass";

    @Mod.Instance(STG.MODID)
	public static STG instance;
}
