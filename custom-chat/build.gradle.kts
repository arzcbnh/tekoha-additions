plugins {
    alias(libs.plugins.fabric.loom)
//    id("fabric-loom") version "${project.property("loom_version")}"
//    id("com.diffplug.spotless") version "8.1.0"
}

version = "0.1.0"
group = "dev.arzcbnh"

base {
    archivesName = "tekoha-custom-chat"
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create("tekoha") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    maven { url = uri("https://maven.nucleoid.xyz") }
}

dependencies {
    minecraft(libs.minecraft)
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation("eu.pb4:polymer-core:0.17.1+26.2")
//    "testImplementation"("net.fabricmc:fabric-loader-junit:${project.property("loader_version")}")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", providers.gradleProperty("minecraft_version"))
    inputs.property("loader_version", providers.gradleProperty("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to providers.gradleProperty("minecraft_version").get(),
            "loader_version" to providers.gradleProperty("loader_version").get()
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
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}

//spotless {
//    format("misc") {
//        target("*.gradle", ".gitattributes", ".gitignore", "*.properties")
//
//        trimTrailingWhitespace()
//        leadingTabsToSpaces()
//        endWithNewline()
//    }
//
//    java {
//        palantirJavaFormat().formatJavadoc(true)
//        formatAnnotations()
//    }
//
//    json {
//        target("src/**/*.json")
//        gson().indentWithSpaces(2)
//    }
//}
