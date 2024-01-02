package com.exidex.stg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;

@Mod(modid = STG.MODID, name = STG.NAME, version = STG.VERSION, acceptableRemoteVersions = "*")
public class STG {

    public static final String MODID = "stg";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "SwingThroughGrass";

    @Instance(STG.MODID)
        public static STG instance;
}