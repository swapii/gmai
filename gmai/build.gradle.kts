plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.5.31"
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
