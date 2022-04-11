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

version = "3.0.1"

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
        Dependency.AOPALLIANCE,
        Dependency.BYTEBUDDY,
        Dependency.BYTEBUDDY_AGENT
    )
    annotationProcessor(Dependency.LOMBOK.dependency)

    testImplementation(Dependency.TOWNY.dependency)
    testImplementation(Dependency.BYTEBUDDY_AGENT.dependency)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

private object BuildConfig {
    const val BUKKIT_TESTING = false
}

tasks {
    val testBukkitModulePath = "../TEST_BUKKIT"

    shadowJar {
        archiveFileName.set("${bukkit.name}-${bukkit.version}.jar")
        val path = if (BuildConfig.BUKKIT_TESTING) "$testBukkitModulePath/plugins" else "output"
        destinationDirectory.set(file(path))
    }

    this.register<TestBukkitTask>("runTestBukkitTask") {
        dependsOn("shadowJar")
        bukkitFileName.set("1.18.2.jar")
        testBukkitDir.set(file(testBukkitModulePath))
    }
}
