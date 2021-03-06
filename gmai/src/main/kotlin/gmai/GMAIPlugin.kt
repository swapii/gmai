package gmai

import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class GMAIPlugin: Plugin<Settings> {

    override fun apply(settings: Settings) {

        with(settings) {

            val rootProjectPathLength = rootDir.absolutePath.length

            val excludedProjects =
                listOf(
                    File(rootDir, "buildSrc")
                )

            rootDir.findAllPotentialModuleDirs()
                .filter { it.list()!!.any { child -> child.startsWith("build.gradle") } }
                .filterNot { it in excludedProjects }
                .forEach { moduleDir ->

                    val moduleName =
                        moduleDir.absolutePath.substring(rootProjectPathLength)
                            .replace(File.separator, "-")
                            .replaceFirst('-', ':')

                    include(moduleName)

                    project(moduleName).projectDir = moduleDir

                }

        }
    }

}

private fun File.findAllPotentialModuleDirs(): Sequence<File> =
    files()
        .filterModuleDirs()
        .flatMap { sequenceOf(it) + it.findAllPotentialModuleDirs() }

private fun Sequence<File>.filterModuleDirs(): Sequence<File> =
    this
        .filter { it.isDirectory }
        .filterNot { it.isHidden }
        .filterNot { it.name.startsWith('.') }
        .filterNot { it.name == "build" }
        .filterNot { it.name == "src" }

private fun File.files(): Sequence<File> =
    (listFiles() ?: emptyArray<File>()).asSequence()
