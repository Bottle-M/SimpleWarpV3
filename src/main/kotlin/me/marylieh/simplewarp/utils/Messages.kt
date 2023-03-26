package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp

/**
 * 本模块集中处理发送给玩家或者控制台的信息
 */
object Messages {
    // 提示信息中包含的前缀内容，往往是插件名
    private val PREFIX = SimpleWarp.plugin.prefix
    // 执行指令的不是玩家
    val notAPlayer: String = "$PREFIX §4This command can only be executed by a player!"
    // 指令执行者没有权限
    val noPermission: String = "$PREFIX §cYou don't have the permission to do that!"
    // 配置成功重载
    val reloadSuccess:String="$PREFIX Successfully reloaded the configs"
    // 配置重载失败
    val reloadFailure:String="$PREFIX §bFailed to reload configs!"
    val warpNotExist:String="$PREFIX §cThis warp doesn't exists!"
    fun warpDeleted(warpId: String): String {
        return "$PREFIX §aWarp §6$warpId §a was successfully deleted!"
    }

    fun warpSet(warpId: String): String {
        return "$PREFIX §aWarp §6${warpId}§a was successfully set!"
    }

    fun usage(info: String): String {
        return "$PREFIX §cUsage: $info"
    }

    // 自定义消息
    fun custom(info: String): String {
        return "$PREFIX $info"
    }
}