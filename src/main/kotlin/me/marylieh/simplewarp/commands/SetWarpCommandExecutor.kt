package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetWarpCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender

        if (player.hasPermission("simplewarp.setwarp")) {

            if (args.size == 1) {
                val warpId = args[0]
                val world: String = player.world.name

                val x = player.location.x
                val y = player.location.y
                val z = player.location.z

                val yaw = player.location.yaw
                val pitch = player.location.pitch

                Config.getConfig().set(".Warps.${warpId}.World", world)
                Config.getConfig().set(".Warps.${warpId}.X", x)
                Config.getConfig().set(".Warps.${warpId}.Y", y)
                Config.getConfig().set(".Warps.${warpId}.Z", z)

                Config.getConfig().set(".Warps.${warpId}.Yaw", yaw)
                Config.getConfig().set(".Warps.${warpId}.Pitch", pitch)

                Config.getConfig().set(".Warps.${warpId}.Owner", player.uniqueId.toString())

                player.sendMessage(Messages.warpSet(warpId))

                Config.save()
            } else {
                player.sendMessage(Messages.usage("§7/setwarp <warpName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }

        return true
    }
}