package com.exidex.stg;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

public class LeftClickEventHandler {
    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            World world = event.entityPlayer.worldObj;
            int x = event.x;
            int y = event.y;
            int z = event.z;

            // Check if the block is walkable
            if (world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z) == null) {
                System.out.println("Player left-clicked at a walkable block: " + x + ", " + y + ", " + z);

                // Check for entities in the clicked block
                AxisAlignedBB blockBoundingBox = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
                List<Entity> entitiesInBlock = world.getEntitiesWithinAABB(Entity.class, blockBoundingBox);

                if (!entitiesInBlock.isEmpty()) {
                    // There is at least one entity in the clicked block
                    // Hit the first entity in the list
                    Entity targetEntity = entitiesInBlock.get(0);
                    event.entityPlayer.attackTargetEntityWithCurrentItem(targetEntity);
                }
            }
        }
    }
}
