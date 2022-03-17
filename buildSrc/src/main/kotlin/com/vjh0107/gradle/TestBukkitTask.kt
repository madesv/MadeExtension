package com.vjh0107.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.PathSensitivity

abstract class TestBukkitTask : DefaultTask() {
    @get:Input
    abstract val bukkitFileName: Property<String>

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val testBukkitDir: DirectoryProperty

    @TaskAction
    fun run() {
        val testBukkitDir = testBukkitDir.get().asFile
        project.javaexec {
            this.main = "-jar"
            this.workingDir = testBukkitDir
            this.args("../${testBukkitDir.name}/${bukkitFileName.get()}")
        }
    }
}