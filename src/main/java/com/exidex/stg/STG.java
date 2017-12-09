package com.exidex.stg;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * @author exidex.
 * @since 02.04.2017.
 */
@Mod(modid = STG.MODID,
		name = STG.NAME,
		version = STG.VERSION,
		acceptedMinecraftVersions = "[1.12,1.13)",
		acceptableRemoteVersions = "*")
public class STG {

    public static final String MODID = "stg";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "SwingThroughGrass";

    @Mod.Instance(STG.MODID)
	public static STG instance;

    @Mod.EventHandler
    public void handleIMCMassages(FMLInterModComms.IMCEvent event) {
    	event.getMessages().forEach(imcMessage -> {
			imcMessage.getFunctionValue(EntityLivingBase.class, Boolean.class).ifPresent(LeftClickEventHandler.PREDICATES::add);
		});
	}
}
