package com.fiw.warp.util

import net.minecraft.commands.CommandSourceStack

/** The older vanilla API accepts the same configured operator levels directly. */
object Perms {
	fun atLeast(source: CommandSourceStack, level: Int): Boolean =
		level <= 0 || source.hasPermission(level)
}
