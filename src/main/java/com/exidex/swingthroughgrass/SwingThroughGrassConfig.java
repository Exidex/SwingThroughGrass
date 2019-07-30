package com.exidex.swingthroughgrass;

import net.minecraftforge.common.ForgeConfigSpec;

public class SwingThroughGrassConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue CANCEL_CLICK_EVENT_PROPAGATION;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        CANCEL_CLICK_EVENT_PROPAGATION = builder
                .comment("Whether to cancel click event propagation.",
                         "Setting this to 'true' will prevent destroying of blocks like tall grass and vanilla flowers.",
                         "If you expect vanilla clients on server leave it 'false' otherwise this will introduce server-client desync.")
                .define("cancelClickEventPropagation", false);

        SPEC = builder.build();
    }
}
