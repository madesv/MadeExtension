import com.vjh0107.gradle.*
import com.vjh0107.gradle.Dependency

plugins {
    kotlin("jvm")
    java
    id("net.minecrell.plugin-yml.bukkit")
    id("com.github.johnrengelman.shadow")
}

version = "2.0.0-RELEASE"

bukkit {
    main = "kr.madesv.extension.MadeExtensionPlugin"
    apiVersion = "1.18"
    name = "MadeExtension"
    author = "vjh0107"
    this.version = getVersion().toString()
    softDepend = listOf(
        "Skript",
        "Towny",
        "mcMMO",
        "TownyChat"
    )
}

repositories {
    mavenOf(
        Dependency.WORLDGUARD,
        Dependency.SPIGOT,
        Dependency.SKRIPT,
        Dependency.TOWNY,
        Dependency.MCMMO
    )
    mavenCentral()
}

dependencies {
    compileOnly(fileTree("libs"))
    compileOnlyOf(
        Dependency.SPIGOT,
        Dependency.SKRIPT,
        Dependency.TOWNY,
        Dependency.MCMMO
    )
    implementationOf(
        Dependency.GUICE,
        Dependency.LOMBOK,
        Dependency.JAVAX_INJECT,
        Dependency.AOPALLIANCE
    )
}

tasks {
    val testBukkitModulePath = "../TEST_BUKKIT"

    shadowJar {
        archiveFileName.set("${bukkit.name}-${bukkit.version}.jar")
        destinationDirectory.set(file("$testBukkitModulePath/plugins"))
    }

    this.register<TestBukkitTask>("runTestBukkitTask") {
        dependsOn("shadowJar")
        bukkitFileName.set("1.18.2.jar")
        testBukkitDir.set(file(testBukkitModulePath))
    }
}
