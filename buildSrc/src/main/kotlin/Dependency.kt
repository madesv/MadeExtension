import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

interface Dependency {
    val maven: String?
    val dependency: String

    object MINECRAFT : Dependency {
        override val maven = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
        override val dependency = "org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT"
    }

    object SKRIPT : Dependency {
        override val maven = "https://repo.skriptlang.org/releases"
        override val dependency = "com.github.SkriptLang:Skript:2.6"
    }

    object TOWNY : Dependency {
        override val maven = "https://repo.glaremasters.me/repository/towny/"
        override val dependency = "com.palmergames.bukkit.towny:towny:0.98.0.0"
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

fun RepositoryHandler.mavenAll(vararg dependency: Dependency) {
    dependency.mapNotNull { it.maven }.forEach {
        maven(it)
    }
}

fun DependencyHandler.compileOnlyAll(vararg dependency: Dependency) {
    dependency.forEach {
        add("compileOnly", it.dependency)
    }
}

fun DependencyHandler.implementationAll(vararg dependency: Dependency) {
    dependency.forEach {
        add("implementation", it.dependency)
    }
}
