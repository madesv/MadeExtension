package com.vjh0107.gradle

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.mavenOf(vararg dependency: Dependency) {
    dependency.mapNotNull { it.maven }.forEach {
        maven(it)
    }
}

fun DependencyHandler.compileOnlyOf(vararg dependency: Dependency) {
    this.addOf("compileOnly", *dependency)
}

fun DependencyHandler.implementationOf(vararg dependency: Dependency) {
    this.addOf("implementation", *dependency)
}

fun DependencyHandler.addOf(configurationName: String, vararg dependency: Dependency) {
    dependency.mapNotNull { it.dependency }.forEach {
        add(configurationName, it)
    }
}