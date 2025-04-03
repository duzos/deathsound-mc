package dev.duzo.respawnsfx.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;

public class DeathSoundData extends SavedData {
	public DeathSoundTracker tracker;

	private DeathSoundData() {
		this.tracker = new DeathSoundTracker();
	}

	private static DeathSoundData create() {
		return new DeathSoundData();
	}

	private static DeathSoundData load(CompoundTag compoundTag) {
		DeathSoundData data = create();
		data.tracker.fromNbt(compoundTag);
		return data;
	}

	public static DeathSoundData getInstance() {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server == null) {
			return null;
		}

		DeathSoundData data = server.overworld().getDataStorage().computeIfAbsent(DeathSoundData::load, DeathSoundData::create, "death_sound");

		data.setDirty();

		return data;
	}

	@Override
	public CompoundTag save(CompoundTag compoundTag) {
		compoundTag.put("Tracker", tracker.toNbt());

		return compoundTag;
	}
}
