package dev.duzo.respawnsfx.mixin;

import dev.duzo.respawnsfx.api.DeathSoundData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerListMixin {
	@Inject(method = "respawn", at = @At("TAIL"))
	private void respawnsfx$respawn(ServerPlayer player, boolean p_11238_, CallbackInfoReturnable<ServerPlayer> cir) {
		DeathSoundData.getInstance().tracker.getOptional(player).ifPresent(sfx -> player.serverLevel().playSound(null, BlockPos.containing(player.getPosition(0)), sfx, player.getSoundSource(), 1.0F, 1.0F));
	}
}
