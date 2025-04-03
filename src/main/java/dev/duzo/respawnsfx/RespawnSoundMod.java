package dev.duzo.respawnsfx;

import com.mojang.logging.LogUtils;
import dev.duzo.respawnsfx.command.SetDeathSoundCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RespawnSoundMod.MODID)
public class RespawnSoundMod {

	public static final String MODID = "respawnsfx";
	private static final Logger LOGGER = LogUtils.getLogger();


	public RespawnSoundMod(FMLJavaModLoadingContext context) {
		IEventBus modbus = context.getModEventBus();

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onCommandSetup(RegisterCommandsEvent event) {
		SetDeathSoundCommand.register(event.getDispatcher());
	}
}
