package me.marylieh.simplewarp.utils

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

object Config {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    fun loadConfig() {
        val dir = File("./plugins/SimpleWarp")

        if (!dir.exists()) {
            dir.mkdirs()
        }

        file = File(dir, "Warps.yml")

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        config = YamlConfiguration.loadConfiguration(file)
        initConfig()
    }

    fun getConfig(): YamlConfiguration {
        return config
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun reload() {
        try {
            config.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
    }
    private fun initConfig() {
        // 传送延迟时间(in seconds)
        if (config.get("delay-before-tp") == null) {
            config.set("delay-before-tp", 0) // 默认立即传送
        }
        // 即将传送的时候是否不允许玩家移动
        if (config.get("no-move-allowed-before-tp") == null) {
            config.set("no-move-allowed-before-tp", false) // 默认传送前可以移动
        }
        if (config.get("position-system") == null) {
            config.set("position-system", false)
        }
        /*  作为fork版本，这里就不整什么自动更新了，花里胡哨
        if (config.get("auto-update") == null) {
            config.set("auto-update", true)
        }
        */
        if (config.get("player-warps-only") == null) {
            config.set("player-warps-only", false)
        }
        save()
    }
}