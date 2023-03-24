package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.SimpleWarp
import me.marylieh.simplewarp.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * 这个模块用作接管/simplewarp相关指令的执行
 */
class SimpleWarpCommandExecutor : CommandExecutor {
    private val promptMsg = "${SimpleWarp.instance.prefix} Invalid Argument. Usage: §c/simplewarp <version | reload>"
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // 接受一个参数
        if (args.size == 1) {
            when (args[0]) {
                "version" -> {
                    // 版本信息
                    sender.sendMessage("${SimpleWarp.instance.prefix} §aVersion: ${SimpleWarp.instance.version}")
                    sender.sendMessage("${SimpleWarp.instance.prefix} §3Forked version by§e SomeBottle")
                    sender.sendMessage("${SimpleWarp.instance.prefix} §3Original developer -§e marie(marylieh)")
                }

                "reload" -> {
                    // 重载配置
                    if (Config.reload()) {
                        sender.sendMessage("${SimpleWarp.instance.prefix} §bSuccessfully reloaded the configs.")
                    } else {
                        sender.sendMessage("${SimpleWarp.instance.prefix} §bFailed to reload configs!")
                    }
                }

                else -> {
                    sender.sendMessage(promptMsg)
                }
            }
        } else {
            sender.sendMessage(promptMsg)
        }
        return true
    }
}