package com.exidex.swingthroughgrass;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        EntityHitResult rayTraceResult = rayTraceEntity(player, 1.0F, 4.5D);

        if (rayTraceResult != null) {
            if (!event.getWorld().isClientSide) {
                player.attack(rayTraceResult.getEntity());
                player.resetAttackStrengthTicker();
            }
        }
    }

    @Nullable
    private static EntityHitResult rayTraceEntity(Player player, float partialTicks, double blockReachDistance) {
        Vec3 from = player.getEyePosition(partialTicks);
        Vec3 look = player.getViewVector(partialTicks);
        Vec3 to = from.add(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance);

        return ProjectileUtil.getEntityHitResult(
                player.level,
                player,
                from,
                to,
                new AABB(from, to),
                EntitySelector.NO_CREATIVE_OR_SPECTATOR
                        .and(e -> e != null
                                && e.isPickable()
                                && e instanceof LivingEntity
                                && !(e instanceof FakePlayer)
                                && !getAllRidingEntities(player).contains(e)
                                && PREDICATES.stream().allMatch(predicate -> predicate.test((LivingEntity) e))
                        )
        );
    }

    private static List<Entity> getAllRidingEntities(Player player) {
        List<Entity> ridingEntities = new ArrayList<>();
        Entity entity = player;
        while (entity.isPassenger()) {
            entity = entity.getVehicle();
            ridingEntities.add(entity);
        }
        return ridingEntities;
    }

}
