import com.vjh0107.gradle.Dependency
import com.vjh0107.gradle.TestBukkitTask
import com.vjh0107.gradle.compileOnlyOf
import com.vjh0107.gradle.implementationOf
import com.vjh0107.gradle.mavenOf

plugins {
    kotlin("jvm")
    java
    id("net.minecrell.plugin-yml.bukkit")
    id("com.github.johnrengelman.shadow")
}

version = "2.2.1-RELEASE"

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
    testImplementation(Dependency.TOWNY.dependency)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
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
