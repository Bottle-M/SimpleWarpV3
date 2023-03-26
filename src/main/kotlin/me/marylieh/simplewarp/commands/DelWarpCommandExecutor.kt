package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DelWarpCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender

        if (player.hasPermission("simplewarp.delwarp")) {
            if (args.size == 1) {
                val warpId = args[0]
                // 如果有player-warps-only这个设计的话，删除的时候不应该不进行检查。
                if (Config.getConfig().getBoolean("player-warps-only")) {
                    if (Config.getConfig().getString(".Warps.${warpId}.Owner") != player.uniqueId.toString()) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }
                }
                Config.getConfig().set(".Warps.$warpId", null)
                Config.save()
                player.sendMessage(Messages.warpDeleted(warpId))
            } else {
                player.sendMessage(Messages.usage("§7/warp <warpName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}