plugins {
    id("java-library")
    // Versions: https://projects.neoforged.net/neoforged/moddevgradle
    id("net.neoforged.moddev") version "2.0.107"
    // Versions: https://github.com/firstdarkdev/modpublisher
    id("com.hypherionmc.modutils.modpublisher") version "2.1.8"
}

val modMinecraftVersion: String by project
val modVersion: String by project
val modNeoForgeVersion: String by project
val modMinecraftMappingsVersion: String by project
val modLoaderVersionRange: String by project
val modMinecraftVersionRange: String by project
val modNeoForgeVersionRange: String by project

val curseApiKey: String? by project

group = "com.exidex.swingthroughgrass"
version = "$modMinecraftVersion-$modVersion"

base {
    archivesName = "swingthroughgrass"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<ProcessResources> {
    val replaceProperties = mapOf(
        "modMinecraftVersion" to modMinecraftVersion,
        "modVersion" to modVersion,
        "modNeoForgeVersion" to modNeoForgeVersion,
        "modMinecraftMappingsVersion" to modMinecraftMappingsVersion,
        "modLoaderVersionRange" to modLoaderVersionRange,
        "modMinecraftVersionRange" to modMinecraftVersionRange,
        "modNeoForgeVersionRange" to modNeoForgeVersionRange,
    )

    inputs.properties(replaceProperties)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to "swingthroughgrass",
                "Specification-Vendor" to "exidex",
                "Specification-Version" to "1",
                "Implementation-Title" to name,
                "Implementation-Version" to version,
                "Implementation-Vendor" to "exidex"
            )
        )
    }
}

neoForge {
    version = modNeoForgeVersion

    parchment {
        minecraftVersion = modMinecraftVersion
        mappingsVersion = modMinecraftMappingsVersion
    }

    runs {
        register("client") {
            client()
        }
        register("clientData") {
            clientData()
        }
        register("server") {
            server()
        }

        configureEach {
            // "SCAN": For mods scan.
            // "REGISTRIES": For firing of registry events.
            // "REGISTRYDUMP": For getting the contents of all registries.
            systemProperty("forge.logging.markers", "REGISTRIES")

            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        register("swingthroughgrass") {
            sourceSet(sourceSets["main"])
        }
    }
}

publisher {
    apiKeys {
        if (curseApiKey != null) {
            curseforge(curseApiKey)
        } else {
            println("Skipping CurseForge task because no api key were provided")
        }
    }

    curseID.set("264353")

    artifact.set(tasks.jar)
    displayName.set("swingthroughgrass-$modMinecraftVersion-$modVersion.jar")
    changelog.set(file("CHANGELOG.md"))

    setReleaseType(com.hypherionmc.modpublisher.properties.ReleaseType.RELEASE)
    setLoaders(com.hypherionmc.modpublisher.properties.ModLoader.NEOFORGE)
    setGameVersions(modMinecraftVersion)
}

