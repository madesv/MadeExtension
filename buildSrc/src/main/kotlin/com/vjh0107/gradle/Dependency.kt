package com.vjh0107.gradle

interface Dependency {
    val maven: String?
    val dependency: String?

    object SPIGOT : Dependency {
        override val maven = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
        override val dependency = "org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT"
    }

    object SKRIPT : Dependency {
        override val maven = "https://repo.skriptlang.org/releases"
        override val dependency = "com.github.SkriptLang:Skript:2.6.1"
    }

    object TOWNY : Dependency {
        override val maven = "https://repo.glaremasters.me/repository/towny/"
        override val dependency = "com.palmergames.bukkit.towny:towny:0.98.0.0"
    }

    object MCMMO : Dependency {
        override val maven = "https://repo.codemc.org/repository/maven-public"
        override val dependency = "com.gmail.nossr50.mcMMO:mcMMO:2.1.209"
    }

    object WORLDGUARD : Dependency {
        override val maven = "https://maven.enginehub.org/repo/"
        override val dependency = null
    }

    object GUICE : Dependency {
        override val maven = null
        override val dependency = "com.google.inject:guice:5.1.0"
    }

    object JAVAX_INJECT : Dependency {
        override val maven = null
        override val dependency = "javax.inject:javax.inject:1"
    }

    object AOPALLIANCE : Dependency {
        override val maven = null
        override val dependency = "aopalliance:aopalliance:1.0"
    }

    object LOMBOK : Dependency {
        override val maven = null
        override val dependency = "org.projectlombok:lombok:1.18.22"
    }
}
