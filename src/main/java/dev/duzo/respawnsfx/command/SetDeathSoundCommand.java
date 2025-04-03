package dev.duzo.respawnsfx.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dev.duzo.respawnsfx.RespawnSoundMod;
import dev.duzo.respawnsfx.api.DeathSoundData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class SetDeathSoundCommand {
	public static void register(CommandDispatcher<CommandSourceStack> stack) {
		RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> requiredargumentbuilder = Commands.argument("sound", ResourceLocationArgument.id())
				.suggests(SuggestionProviders.AVAILABLE_SOUNDS)
				.executes(
						p_326320_ -> setSound(
								p_326320_.getSource(),
								ResourceLocationArgument.getId(p_326320_, "sound")
						)
				);
		stack.register(Commands.literal(RespawnSoundMod.MODID).then(requiredargumentbuilder));
	}

	private static int setSound(CommandSourceStack stack, ResourceLocation soundId) {
		ServerPlayer player = stack.getPlayer();

		if (player == null) {
			return 0;
		}

		DeathSoundData.getInstance().tracker.put(player, SoundEvent.createVariableRangeEvent(soundId));

		stack.sendSuccess(() -> Component.literal("Set death sound to " + soundId), true);

		return 1;
	}
}
