import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
        options.encoding = "UTF-8"
    }

    jar {
        dependsOn("shadowJar")
    }

    shadowJar {
        if (project.hasProperty("cd"))
            archiveFileName.set("NoVillagers.jar")
        else
            archiveFileName.set("NoVillagers-${archiveVersion.getOrElse("unknown")}.jar")

        destinationDirectory.set(file(System.getenv("outputDir") ?: "$rootDir/build/"))

        minimize()
    }
}

bukkit {
    name = "NoVillagers"
    main = "pro.aerwyn81.novillagers.NoVillagers"
    authors = listOf("AerWyn81")
    apiVersion = "1.13"
    description = "Prevent villager spawning"
    version = rootProject.version.toString()
    website = "https://just2craft.fr"

    commands {
        register("novillagers") {
            description = "Plugin command"
            aliases = listOf("nv")
        }
    }

    permissions {
        register("novillagers.reload") {
            description = "Reload the plugin"
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("novillagers.debug") {
            description = "Show messages when villager is removed and some other stuff"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}