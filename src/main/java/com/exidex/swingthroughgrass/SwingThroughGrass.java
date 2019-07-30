package com.exidex.swingthroughgrass;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Predicate;

@Mod("swingthroughgrass")
public class SwingThroughGrass {

	public SwingThroughGrass() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SwingThroughGrassConfig.SPEC, "swingthroughgrass.toml");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
	}

	@SuppressWarnings("unchecked")
	private void processIMC(final InterModProcessEvent event) {
		event.getIMCStream("ENTITY_FILTER"::equals)
			.map(InterModComms.IMCMessage::getMessageSupplier)
			.map(supplier -> (Predicate<LivingEntity>) supplier.get())
			.forEach(LeftClickEventHandler.PREDICATES::add);
	}
}
