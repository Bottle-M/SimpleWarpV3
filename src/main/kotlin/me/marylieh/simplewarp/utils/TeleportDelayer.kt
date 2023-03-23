package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import java.util.TimerTask
import org.bukkit.entity.Player
import org.bukkit.Location
import kotlin.math.abs

object TeleportDelayer {
    // 用哈希表储存<玩家ID,传送任务>
    private val tasks = HashMap<String, TeleportTask>()

    // 这里用玩家的UUID作为键
    fun tp(player: Player, location: Location) {
        // 传送前等待时间(In seconds)
        var timeLeft = Config.getConfig().getInt("delay-before-tp")
        if (timeLeft < 0) // 防止出现负数的等待时间
            timeLeft = 0
        // 等待时是否允许玩家移动
        val noMoveAllowed = Config.getConfig().getBoolean("no-move-allowed-before-tp")
        // 创建传送任务（计时器）对象
        val tpTask = TeleportTask(player, location, timeLeft, noMoveAllowed)
        tasks[player.uniqueId.toString()] = tpTask; // 存入哈希表
    }
}

/**
 * 表示一个传送玩家到location位置的任务
 */
class TeleportTask(
    private val player: Player,
    private val location: Location,
    private var timeLeft: Int,
    private val noMoveAllowed: Boolean
) : TimerTask() {
    private var initialPos: Location = player.location // 玩家的最初位置
    override fun run() {
        if (timeLeft <= 0) {
            player.teleport(location) // 倒计时结束立即传送
            this.cancel() // 玩家已传送，计时器任务停止
            return
        }
        // 如果在等待过程中不允许玩家移动，就需要检查玩家是否移动
        if (noMoveAllowed) {
            val currPos: Location = player.location // 获得玩家当前的坐标
            val xDiff = abs(currPos.x - initialPos.x) // 获得x,y,z上玩家移动的距离
            val yDiff = abs(currPos.y - initialPos.y)
            val zDiff = abs(currPos.z - initialPos.z)
            // 超过一格就算移动
            if (xDiff > 1 || yDiff > 1 || zDiff > 1) {
                this.cancel() // 取消定时器，传送取消
                player.sendMessage("${SimpleWarp.instance.prefix} §cDo not move while waiting for teleporting.")
                return
            }
        }
        timeLeft--
    }
}