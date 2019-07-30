package com.exidex.swingthroughgrass;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public final class LeftClickEventHandler {

	public static final List<Predicate<LivingEntity>> PREDICATES = Lists.newArrayList();

	private LeftClickEventHandler() { }

	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {

		BlockState state = event.getWorld().getBlockState(event.getPos());
		if (!state.getCollisionShape(event.getWorld(), event.getPos()).isEmpty()) {
			return;
		}

		PlayerEntity player = event.getEntityPlayer();
		if (player == null) {
			return;
		}

		EntityRayTraceResult rayTraceResult = rayTraceEntity(player, 1.0F, 4.5D);

		if (rayTraceResult != null) {
			if (!event.getWorld().isRemote) {
				player.attackTargetEntityWithCurrentItem(rayTraceResult.getEntity());
				player.resetCooldown();
			}
			event.setCanceled(SwingThroughGrassConfig.CANCEL_CLICK_EVENT_PROPAGATION.get());
		}
	}

	@Nullable
	private static EntityRayTraceResult rayTraceEntity(PlayerEntity player, float partialTicks, double blockReachDistance) {
		Vec3d from = player.getEyePosition(partialTicks);
		Vec3d look = player.getLook(partialTicks);
		Vec3d to = from.add(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance);

		return ProjectileHelper.func_221271_a(
			player.world,
			player,
			from,
			to,
			new AxisAlignedBB(from, to),
			EntityPredicates.CAN_AI_TARGET
				.and(e -> e != null
					&& e.canBeCollidedWith()
					&& e instanceof LivingEntity
					&& !(e instanceof FakePlayer)
					&& PREDICATES.stream().allMatch(predicate -> predicate.test((LivingEntity) e))
				)
		);
	}
}
