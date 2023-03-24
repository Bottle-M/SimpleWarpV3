package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException

object Config {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration
    private lateinit var plugin: Plugin

    // 设置插件对象
    fun setPlugin(plugin: Plugin) {
        this.plugin = SimpleWarp.instance
    }

    fun loadConfig() {
        val dir = plugin.dataFolder // 插件目录

        if (!dir.exists()) {
            dir.mkdirs()
        }

        file = File(dir, "configs.yml")

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

    fun reload(): Boolean {
        try {
            config.load(file)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
        return false
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