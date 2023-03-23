package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.TabCompleter

class WarpTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        val list = ArrayList<String>()
        if (sender !is Player) return list
        val player: Player = sender
        if (player.hasPermission("simplewarp.warps")) {

            if (Config.getConfig().get("player-warps-only") == null) {
                Config.getConfig().set("player-warps-only", false)
                println("Old Version of Config detected! Setting player-warps-only to false!")
                Config.save()
            }

            if (Config.getConfig().getBoolean("player-warps-only")) {
                val filtered = Config.getConfig().getConfigurationSection(".Warps")?.getKeys(false)?.filter{value -> value.lowercase().startsWith(args[0].lowercase())}
                    ?.filter { Config.getConfig().getString(".Warps.${it}.Owner") == player.uniqueId.toString() }
                filtered?.forEach{list.add(it)}
                return list
            }

            val filtered = Config.getConfig().getConfigurationSection(".Warps")?.getKeys(false)?.filter{value -> value.lowercase().startsWith(args[0].lowercase())}
            filtered?.forEach{list.add(it)}
        }
        return list
    }
}
