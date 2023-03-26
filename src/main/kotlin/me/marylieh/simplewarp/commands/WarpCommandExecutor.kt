package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Messages
import me.marylieh.simplewarp.utils.TeleportDelayer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender

        if (player.hasPermission("simplewarp.warp")) {
            if (args.size == 1) {
                var warpId = ""
                if (player.hasPermission("simplewarp.warps")) {
                    // 模糊匹配地标，用户可以只输入地标名的开头几个字符
                    val filtered = Config.getConfig().getConfigurationSection(".Warps")?.getKeys(false)
                        ?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
                    if (filtered?.size == 1) {
                        warpId = filtered[0]
                    }
                }
                // 没有模糊匹配到，就采用用户输入
                if (warpId == "") {
                    warpId = args[0]
                }
                // 地标不存在
                if (Config.getConfig().getString(".Warps.$warpId") == null) {
                    player.sendMessage(Messages.warpNotExist)
                    return true
                }

                if (Config.getConfig().getBoolean("player-warps-only")) {
                    if (Config.getConfig().getString(".Warps.${warpId}.Owner") != player.uniqueId.toString()) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }
                }

                val world = Bukkit.getWorld(Config.getConfig().getString(".Warps.${warpId}.World")!!)

                val x = Config.getConfig().getInt("Warps.${warpId}.X").toDouble()
                val y = Config.getConfig().getInt("Warps.${warpId}.Y").toDouble()
                val z = Config.getConfig().getInt("Warps.${warpId}.Z").toDouble()

                val yaw = Config.getConfig().getInt("Warps.${warpId}.Yaw").toFloat()
                val pitch = Config.getConfig().getInt("Warps.${warpId}.Pitch").toFloat()
                TeleportDelayer.tp(player, Location(world, x, y, z, yaw, pitch), warpId)

            } else {
                player.sendMessage(Messages.usage("§7/warp <warpName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}