plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.github.bannirui"
version = "1.0.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2022.2.5")
    type.set("IC")

    plugins.set(listOf(
    ))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
