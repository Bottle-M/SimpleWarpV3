package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.SimpleWarp
import me.marylieh.simplewarp.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpsCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${SimpleWarp.instance.prefix} §4Just a Player can execute this command!")
            return true
        }
        val player: Player = sender

        if (player.hasPermission("simplewarp.warps")) {

            // 如果没有开启【玩家创建的坐标只能由玩家使用】的选项，就列出所有地标
            if (!Config.getConfig().getBoolean("player-warps-only")) {
                player.sendMessage(
                    "${SimpleWarp.instance.prefix} ${
                        Config.getConfig().getConfigurationSection(".Warps")?.getKeys(false)
                    }"
                )
                return true
            }

            val playerWarps = Config.getConfig().getConfigurationSection(".Warps")?.getKeys(false)
                ?.filter { Config.getConfig().getString(".Warps.${it}.Owner") == player.uniqueId.toString() }.toString()

            player.sendMessage("${SimpleWarp.instance.prefix} $playerWarps")
        } else {
            player.sendMessage("${SimpleWarp.instance.prefix} §cYou don't have the permission to do that!")
        }
        return true
    }
}