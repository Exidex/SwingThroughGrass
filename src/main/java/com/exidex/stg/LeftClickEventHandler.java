package com.exidex.stg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import java.util.List;

@Mod(modid = STG.MODID, name = STG.NAME, version = STG.VERSION)
public class LeftClickEventHandler {

	@Mod.Instance(STG.MODID)
	public static LeftClickEventHandler instance;

	private LeftClickEventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onLeftClick(PlayerInteractEvent event) {
		if (event.action == Action.LEFT_CLICK_BLOCK) {
			Block block = event.world.getBlock(event.x, event.y, event.z);
			if (block != null && block.getCollisionBoundingBoxFromPool(event.world, event.x, event.y, event.z) != null)
				return;

			EntityPlayer entityPlayer = event.entityPlayer;

			float blockReachDistance = 4.5F;

			Vec3 from = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + (double) entityPlayer.getEyeHeight(), entityPlayer.posZ);
			Vec3 vec3d = entityPlayer.getLook(1.0F);
			Vec3 to = from.addVector(vec3d.xCoord * blockReachDistance, vec3d.yCoord * blockReachDistance, vec3d.zCoord * blockReachDistance);

			Entity targetEntity = getEntityClosestToStartPos(entityPlayer, event.world, from, to);

			if (targetEntity != null) {
				if (!event.world.isRemote) {
					// Assuming a 0-damage attack to mimic a left click
					entityPlayer.attackEntityFrom(DamageSource.generic, 0);
				}
				event.setCanceled(true);
			}
		}
	}

	private Entity getEntityClosestToStartPos(Entity entityIn, World world, Vec3 startPos, Vec3 endPos) {
		Entity entity = null;
		double d0 = 0.0D;
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityIn, entityIn.boundingBox.addCoord(endPos.xCoord - startPos.xCoord, endPos.yCoord - startPos.yCoord, endPos.zCoord - startPos.zCoord).expand(1.0, 1.0, 1.0));
		for (Entity entity1 : list) {
			if (entity1.canBeCollidedWith()) {
				float f = 0.3F;
				AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(startPos, endPos);

				if (movingobjectposition != null) {
					double d1 = startPos.squareDistanceTo(movingobjectposition.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}
		return entity;
	}
}
