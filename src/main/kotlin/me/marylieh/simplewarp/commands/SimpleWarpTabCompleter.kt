package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.TabCompleter

class SimpleWarpTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val list = ArrayList<String>()
        list.add("version")
        list.add("reload")
        return list
    }
}
