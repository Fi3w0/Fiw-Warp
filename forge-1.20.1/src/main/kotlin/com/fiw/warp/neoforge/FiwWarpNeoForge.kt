package com.fiw.warp.neoforge

import com.fiw.warp.Constants
import com.fiw.warp.FiwWarp
import com.fiw.warp.command.FiwWarpCommands
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.event.server.ServerStoppingEvent
import net.minecraftforge.event.TickEvent

@Mod(Constants.MOD_ID)
object FiwWarpNeoForge {
	init {
		FiwWarp.init(FMLPaths.CONFIGDIR.get())
		MinecraftForge.EVENT_BUS.register(this)
	}

	@SubscribeEvent
	fun onRegisterCommands(event: RegisterCommandsEvent) {
		FiwWarpCommands.register(event.dispatcher)
	}

	@SubscribeEvent
	fun onServerTick(event: TickEvent.ServerTickEvent) {
		if (event.phase == TickEvent.Phase.END) FiwWarp.tick(event.server)
	}

	@SubscribeEvent
	fun onBlockBreak(event: BlockEvent.BreakEvent) {
		(event.player as? ServerPlayer)?.let(FiwWarp::onBlockBreak)
	}

	@SubscribeEvent
	fun onAttack(event: AttackEntityEvent) {
		(event.entity as? ServerPlayer)?.let(FiwWarp::onAttack)
	}

	@SubscribeEvent
	fun onServerStopping(event: ServerStoppingEvent) {
		FiwWarp.save()
	}
}
