package dev.duzo.deathsound.api;

import net.minecraft.core.HolderLookup;
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

	private static DeathSoundData load(CompoundTag compoundTag, HolderLookup.Provider lookupProvider) {
		DeathSoundData data = create();
		data.tracker.fromNbt(compoundTag);
		return data;
	}

	public static DeathSoundData getInstance() {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server == null) {
			return null;
		}

		DeathSoundData data = server.overworld().getDataStorage().computeIfAbsent(new Factory<>(DeathSoundData::create, DeathSoundData::load, null), "death_sound");

		data.setDirty();

		return data;
	}

	@Override
	public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
		compoundTag.put("Tracker", tracker.toNbt());

		return compoundTag;
	}
}
