package com.exidex.swingthroughgrass;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;

import java.util.function.Predicate;

@Mod("swingthroughgrass")
public class SwingThroughGrass {

    public SwingThroughGrass(IEventBus modEventBus) {
        modEventBus.addListener(this::processIMC);
    }

    @SuppressWarnings("unchecked")
    private void processIMC(final InterModProcessEvent event) {
        event.getIMCStream("ENTITY_FILTER"::equals)
                .map(InterModComms.IMCMessage::messageSupplier)
                .map(supplier -> (Predicate<LivingEntity>) supplier.get())
                .forEach(LeftClickEventHandler.PREDICATES::add);
    }
}
