plugins {
    kotlin("jvm") version "1.6.10"
    java
    pmd
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1" apply false
    id("com.github.johnrengelman.shadow") version "6.0.0" apply false
}

pmd {
    isConsoleOutput = true
    toolVersion = "6.21.0"
    rulesMinimumPriority.set(5)
    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
}

group = "kr.madesv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}
