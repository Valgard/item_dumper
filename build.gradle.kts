val targetVersion = providers.gradleProperty("targetVersion").getOrElse("")
val modVersion = "1.0.0"
val suffix = if (targetVersion.isNotEmpty()) "+$targetVersion" else ""

val hytaleVersions = listOf("2026.02.19-6c1fa8857", "2026.03.26-89796e57b")

tasks.named<Jar>("jar") {
    archiveBaseName.set("ItemDumper")
    archiveVersion.set("$modVersion$suffix")
}

if (targetVersion.isNotEmpty()) {
    tasks.named("processResources") {
        doLast {
            patchManifestServerVersion(targetVersion)
        }
    }
}

fun patchManifestServerVersion(version: String) {
    val manifest = layout.buildDirectory.file("resources/main/manifest.json").get().asFile
    if (!manifest.exists()) return
    val content = manifest.readText()
    val patched = if ("\"ServerVersion\"" in content) {
        content.replace(
            Regex(""""ServerVersion"\s*:\s*"[^"]*""""),
            """"ServerVersion": "$version""""
        )
    } else {
        content.replace(
            "\"Main\":",
            "\"ServerVersion\": \"$version\",\n    \"Main\":"
        )
    }
    manifest.writeText(patched)
}

val buildAll by tasks.registering {
    group = "build"
    description = "Baut JARs für alle Hytale-Versionen: $hytaleVersions"
    dependsOn("classes")

    doLast {
        val classesDir = layout.buildDirectory.dir("classes/java/main").get().asFile
        val resourcesDir = layout.buildDirectory.dir("resources/main").get().asFile
        val libsDir = layout.buildDirectory.dir("libs").get().asFile

        hytaleVersions.forEach { version ->
            patchManifestServerVersion(version)

            val jarFile = libsDir.resolve("ItemDumper-$modVersion+$version.jar")
            ant.withGroovyBuilder {
                "jar"("destfile" to jarFile) {
                    "fileset"("dir" to classesDir)
                    "fileset"("dir" to resourcesDir)
                }
            }
            logger.lifecycle("Built: ${jarFile.name}")
        }
    }
}
