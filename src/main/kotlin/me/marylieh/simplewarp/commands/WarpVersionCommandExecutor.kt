package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WarpVersionCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        sender.sendMessage("${SimpleWarp.instance.prefix} §aVersion: ${SimpleWarp.instance.version}")
        sender.sendMessage("${SimpleWarp.instance.prefix} §3Forked version by SomeBottle")
        sender.sendMessage("${SimpleWarp.instance.prefix} §3Original developer -§e marie(marylieh)")
        return true
    }
}