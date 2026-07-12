plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.spotless)
}

version = "0.3.0"
group = "dev.arzcbnh"

loom {
    splitEnvironmentSourceSets()

    mods {
        create("tekoha-additions") {
            sourceSet(sourceSets.main.get())
        }
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

dependencies {
    minecraft(libs.minecraft)
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
}

tasks.processResources {
    val fabric_version = libs.versions.fabric.api.map {
        val (major, minor) = it.substringBefore("+").split(".")
        ">=$major.$minor"
    }

    inputs.property("version", project.version)
    inputs.property("minecraft_version", libs.versions.minecraft)
    inputs.property("fabric_version", fabric_version)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to libs.versions.minecraft.get(),
            "fabric_version" to fabric_version.get()
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    from("LICENSE.txt")
}

spotless {
    format("misc") {
        target(".gitattributes", ".gitignore", "*.kts", "*.properties", "*.toml")
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }

    java {
        palantirJavaFormat("2.94.0").formatJavadoc(true)
        formatAnnotations()
    }

    json {
        target("src/**/*.json")
        gson().indentWithSpaces(2)
    }
}
