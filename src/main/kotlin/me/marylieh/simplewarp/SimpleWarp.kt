package me.marylieh.simplewarp

import me.marylieh.simplewarp.commands.*
import me.marylieh.simplewarp.commands.position.PositionCommandExecutor
import me.marylieh.simplewarp.utils.Config
import org.bukkit.plugin.java.JavaPlugin
import me.marylieh.simplewarp.utils.TeleportDelayer

class SimpleWarp : JavaPlugin() {

    val prefix = "§6[SimpleWarp]"
    val version = "3.7"

    // 伴生对象
    companion object {
        lateinit var instance: SimpleWarp
            private set
    }

    override fun onLoad() {
        Config.setPlugin(this) // 将Plugin对象传递给Config
        TeleportDelayer.setPlugin(this) // 将Plugin对象传递给TeleportDelayer
        Config.loadConfig()
        instance = this
    }

    override fun onEnable() {
        registerCommands()
        // if (Config.getConfig().getBoolean("auto-update")) {val updater = Updater(this, 395393, this.file, Updater.UpdateType.DEFAULT, true)}
    }

    override fun onDisable() {
        Config.save()
    }

    private fun registerCommands() {
        val setWarpCommand = getCommand("setwarp") ?: error("Couldn't get setwarp command! This should not happen!")
        val delWarpCommand = getCommand("delwarp") ?: error("Couldn't get delwarp command! This should not happen!")
        val warpCommand = getCommand("warp") ?: error("Couldn't get warp command! This should not happen!")
        val warpsCommand = getCommand("warps") ?: error("Couldn't get warps command! This should not happen!")
        val simpleWarpCommand =
            getCommand("simplewarp") ?: error("Couldn't get simplewarp command! This should not happen!")
        val positionCommand = getCommand("position") ?: error("Couldn't get position command! This should not happen!")
        setWarpCommand.setExecutor(SetWarpCommandExecutor())
        delWarpCommand.setExecutor(DelWarpCommandExecutor())
        warpCommand.setExecutor(WarpCommandExecutor())
        warpsCommand.setExecutor(WarpsCommandExecutor())
        simpleWarpCommand.setExecutor(SimpleWarpCommandExecutor())
        positionCommand.setExecutor(PositionCommandExecutor())
        simpleWarpCommand.tabCompleter = SimpleWarpTabCompleter()
        warpCommand.tabCompleter = WarpTabCompleter()
        delWarpCommand.tabCompleter = WarpTabCompleter()
    }


}