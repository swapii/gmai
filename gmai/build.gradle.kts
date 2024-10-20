plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.9.25"
    `maven-publish`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "$group.gmai"
            implementationClass = "gmai.GMAIPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("plugin") {
            artifactId = "$group.gmai.gradle.plugin"
            from(components["java"])
        }
    }
}
