package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.entity.Player
import org.bukkit.Location
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs

object TeleportDelayer {
    // 用哈希表储存<玩家ID,传送任务>
    private val tasks = HashMap<String, TeleportTask>()
    private val scheduler: Timer = Timer()

    // 这里用玩家的UUID作为键
    fun tp(player: Player, location: Location, warpId: String) {
        // 传送前等待时间(In seconds)
        var timeDelay = Config.getConfig().getInt("delay-before-tp")
        if (timeDelay < 0) // 防止出现负数的等待时间(Seconds)
            timeDelay = 0
        // 等待时是否允许玩家移动
        val noMoveAllowed = Config.getConfig().getBoolean("no-move-allowed-before-tp")
        // 创建传送任务（计时器）对象
        val tpTask = TeleportTask(player, location, timeDelay, noMoveAllowed, warpId)
        val taskKey = player.uniqueId.toString()
        if (tasks.containsKey(taskKey) && tasks[taskKey]?.isWorking() == true) {
            // 如果当前用户下还有正在运行的计时器，就取消掉
            tasks[taskKey]?.stopWorking()
        }
        tasks[taskKey] = tpTask // 将传送任务存入哈希表
        // 如果有延迟时间，就提醒玩家
        if (timeDelay > 0) {
            var msg = "§aTeleportation will start in §6$timeDelay§a second(s). "
            if (noMoveAllowed)
                msg += "Please §cdo not move§a while waiting."
            player.sendMessage("${SimpleWarp.instance.prefix} $msg")
        }
        // 添加定时器任务
        scheduler.schedule(tpTask, 0, 1000); // 先立即执行一次，然后每间隔一秒执行一次，直至任务结束
    }
}

/**
 * 表示一个传送玩家到location位置的任务
 */
class TeleportTask(
    private val player: Player,
    private val location: Location,
    private var timeLeft: Int,
    private val noMoveAllowed: Boolean,
    private val warpId: String
) : TimerTask() {
    private var initialPos: Location = player.location // 玩家的最初位置
    private var status: Boolean = true

    // 计时器是否正在运行
    fun isWorking(): Boolean {
        return status
    }

    // 取消传送任务
    fun stopWorking() {
        this.cancel() // 取消定时器
        status = false
    }

    override fun run() {
        if (timeLeft <= 0) {
            player.sendMessage("${SimpleWarp.instance.prefix} §aYou have been teleported to §6$warpId§a!")
            player.teleport(location) // 倒计时结束立即传送
            stopWorking() // 玩家已传送，计时器任务停止
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
                stopWorking() // 取消定时器，传送取消
                player.sendMessage("${SimpleWarp.instance.prefix} §cTeleportation was cancelled due to movement.")
                return
            }
        }
        timeLeft--
    }
}