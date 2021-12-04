package com.exidex.swingthroughgrass;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

import java.util.function.Predicate;

@Mod("swingthroughgrass")
public class SwingThroughGrass {

    public SwingThroughGrass() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    @SuppressWarnings("unchecked")
    private void processIMC(final InterModProcessEvent event) {
        event.getIMCStream("ENTITY_FILTER"::equals)
                .map(InterModComms.IMCMessage::messageSupplier)
                .map(supplier -> (Predicate<LivingEntity>) supplier.get())
                .forEach(LeftClickEventHandler.PREDICATES::add);
    }
}
