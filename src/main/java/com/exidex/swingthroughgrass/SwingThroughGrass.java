package com.exidex.swingthroughgrass;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

@Mod("swingthroughgrass")
public class SwingThroughGrass {

	public SwingThroughGrass() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
	}

	@SuppressWarnings("unchecked")
	private void processIMC(final InterModProcessEvent event) {
		event.getIMCStream("ENTITY_FILTER"::equals)
			.map(InterModComms.IMCMessage::getMessageSupplier)
			.map(supplier -> (Predicate<LivingEntity>) supplier.get())
			.forEach(LeftClickEventHandler.PREDICATES::add);
	}
}
