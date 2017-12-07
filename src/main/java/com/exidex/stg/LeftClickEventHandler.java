package com.exidex.stg;

import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * @author exidex.
 * @since 02.04.2017.
 */
public final class LeftClickEventHandler {

	private LeftClickEventHandler() {}

	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {

		IBlockState state = event.getWorld().getBlockState(event.getPos()).getActualState(event.getWorld(),event.getPos());
		if (state.getCollisionBoundingBox(event.getWorld(), event.getPos()) != Block.NULL_AABB)
			return;

		EntityPlayer entityPlayer = event.getEntityPlayer();

		float blockReachDistance = 4.5F;

		Vec3d from = new Vec3d(entityPlayer.posX, entityPlayer.posY + (double)entityPlayer.getEyeHeight(), entityPlayer.posZ);
		Vec3d vec3d = entityPlayer.getLook(1.0F);
		Vec3d to = from.addVector(vec3d.x * blockReachDistance, vec3d.y * blockReachDistance, vec3d.z * blockReachDistance);

		Entity targetEntity = getEntityClosestToStartPos(entityPlayer, event.getWorld(), from, to);

		if(targetEntity != null) {
			if (!event.getWorld().isRemote) {
				entityPlayer.attackTargetEntityWithCurrentItem(targetEntity);
				entityPlayer.resetCooldown();
			}
			event.setCanceled(true);
		}
	}

	private static Entity getEntityClosestToStartPos(Entity entityIn, World world, Vec3d startPos, Vec3d endPos) {
		Entity entity = null;
		List<Entity> list = world.getEntitiesInAABBexcluding(entityIn, new AxisAlignedBB(startPos.x,startPos.y,startPos.z, endPos.x, endPos.y, endPos.z), Predicates.and(EntitySelectors.NOT_SPECTATING, entity1 -> entity1 != null && entity1.canBeCollidedWith()));
		double d0 = 0.0D;
		AxisAlignedBB axisAlignedBB;

		for(Entity entity1 : list) {
			axisAlignedBB = entity1.getEntityBoundingBox().expand(0.3D, 0.3D, 0.3D);
			RayTraceResult raytraceResult = axisAlignedBB.calculateIntercept(startPos, endPos);

			if(raytraceResult != null) {
				double d1 = startPos.squareDistanceTo(raytraceResult.hitVec);

				if(d1 < d0 || d0 == 0.0D) {
					entity = entity1;
					d0 = d1;
				}
			}
		}
		return entity;
	}
}
