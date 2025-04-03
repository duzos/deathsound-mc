package dev.duzo.respawnsfx.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class DeathSoundTracker extends HashMap<UUID, SoundEvent> {
	public SoundEvent get(ServerPlayer player) {
		return get(player.getUUID());
	}

	public Optional<SoundEvent> getOptional(ServerPlayer player) {
		return Optional.ofNullable(get(player));
	}

	public void put(ServerPlayer player, SoundEvent sound) {
		put(player.getUUID(), sound);
	}

	public CompoundTag toNbt() {
		CompoundTag tag = new CompoundTag();
		for (Entry<UUID, SoundEvent> entry : entrySet()) {
			tag.putString(entry.getKey().toString(), entry.getValue().getLocation().toString());
		}
		return tag;
	}

	public void fromNbt(CompoundTag tag) {
		for (String key : tag.getCompound("Tracker").getAllKeys()) {
			UUID uuid = UUID.fromString(key);
			String soundName = tag.getString(key);
			SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(ResourceLocation.parse(soundName));
			put(uuid, soundEvent);
		}
	}
}
