package dev.duzo.deathsound.mixin;

import dev.duzo.deathsound.api.DeathSoundData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class EntityMixin {
	@Inject(method = "setHealth", at = @At("RETURN"))
	private void deathsound$setHealth(float val, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Player) {
			System.out.println(entity.level().isClientSide());
			System.out.println(entity.getHealth());
			System.out.println(val);
		}

		if (entity.getHealth() > 0) {
			return;
		}

		if (entity instanceof ServerPlayer serverPlayer) {
			System.out.println("ATUISDHHSGUD");

			SoundEvent sound = DeathSoundData.getInstance().tracker.get(serverPlayer);
			if (sound != null) {
				serverPlayer.serverLevel().playSound(null, serverPlayer.getOnPos(), sound, serverPlayer.getSoundSource(), 1.0F, 1.0F);
			}
		}
	}
}
