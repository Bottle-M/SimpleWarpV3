package me.marylieh.simplewarp.commands.position

import me.marylieh.simplewarp.SimpleWarp
import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Messages
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PositionCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
        }
        val player: Player = sender as Player
        if (!player.hasPermission("simplewarp.position")) {
            player.sendMessage(Messages.noPermission)
            return true
        }

        if (!Config.getConfig().getBoolean("position-system")) {
            player.sendMessage(Messages.featureNotAvailable)
            return true
        }

        val allPositions = Config.getConfig().getConfigurationSection("Positions")?.getKeys(false)
        if (args.size == 1 || args.size == 2) {
            when (args[0]) {
                "list" -> {
                    if (!player.hasPermission("simplewarp.position.list")) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }
                    player.sendMessage(
                        Messages.custom(Messages.listPositions(allPositions))
                    )
                }

                "del" -> {
                    if (!player.hasPermission("simplewarp.position.del")) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }

                    if (Config.getConfig().get("Positions.${args[1]}") != null) {

                        Config.getConfig().set("Positions.${args[1]}", null)
                        Config.save()

                        player.sendMessage(Messages.positionDeleted(args[1]))
                    } else {
                        player.sendMessage(Messages.posNotExist)
                        return true
                    }
                }

                else -> {
                    val id = args[0]

                    if (Config.getConfig().getString("Positions.$id") != null) {

                        if (!player.hasPermission("simplewarp.position.view")) {
                            player.sendMessage(Messages.noPermission)
                            return true
                        }

                        val world = Config.getConfig().getString("Positions.${id}.World")

                        val x = Config.getConfig().getInt("Positions.${id}.X")
                        val y = Config.getConfig().getInt("Positions.${id}.Y")
                        val z = Config.getConfig().getInt("Positions.${id}.Z")

                        player.sendMessage(Messages.custom("§9$id §8[§6$x§8, §6$y§8, §6$z§8, §6$world§8]"))
                        return true
                    }

                    if (!player.hasPermission("simplewarp.position.create")) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }

                    val world = player.world.name

                    val x = player.location.blockX
                    val y = player.location.blockY
                    val z = player.location.blockZ

                    Config.getConfig().set("Positions.${id}.World", world)
                    Config.getConfig().set("Positions.${id}.X", x)
                    Config.getConfig().set("Positions.${id}.Y", y)
                    Config.getConfig().set("Positions.${id}.Z", z)

                    Bukkit.broadcast(Component.text("${SimpleWarp.plugin.prefix} §a$id §7from §a${player.name} §8[§6$x§8, §6$y §8,§6 $z §8,§6 $world§8]"))

                    Config.save()
                }
            }
        } else {
            player.sendMessage(Messages.usage("§c/position <list | position | del>"))
        }
        return true
    }
}