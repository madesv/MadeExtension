plugins {
    kotlin("jvm")
    java
    id("net.minecrell.plugin-yml.bukkit")
    id("com.github.johnrengelman.shadow")
}

version = "1.0.0-RELEASE"

bukkit {
    main = "kr.madesv.towny_ext.MadeTownyExtensionPlugin"
    apiVersion = "1.18"
    name = "MadeTownyExtension"
    author = "vjh0107"
    this.version = getVersion().toString()
    softDepend = listOf(
        "Skript",
        "Towny"
    )
}

repositories {
    mavenCentral()
    mavenAll(
        Dependency.MINECRAFT,
        Dependency.SKRIPT,
        Dependency.TOWNY
    )
}

dependencies {
    compileOnlyAll(
        Dependency.MINECRAFT,
        Dependency.SKRIPT,
        Dependency.TOWNY
    )
    implementationAll(
        Dependency.GUICE,
        Dependency.LOMBOK,
        Dependency.JAVAX_INJECT,
        Dependency.AOPALLIANCE
    )
}
