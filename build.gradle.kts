plugins {
    id("java")
    id("net.linguica.maven-settings") version "0.5"
    id("maven-publish")
}

val isRelease = System.getenv("RELEASE") == "true"

val nexusUser = System.getenv()["NEXUS_USER"]
val nexusPass = System.getenv()["NEXUS_PASS"]
val isInCi = nexusUser != null && nexusPass != null

group = "fr.ekalia.injector"
version = "1.0.6" + if (isRelease) "" else "-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        name = "ekalia"
        url = uri("https://nexus.ekalia.fr/repository/maven-releases/")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        maven("https://nexus.ekalia.fr/repository/maven-${if (isRelease) "releases" else "snapshots"}/") {
            if (isInCi) {
                credentials {
                    username = nexusUser
                    password = nexusPass
                }
            } else { // Local
                name = "ekalia"
            }
        }
    }
}

dependencies {
    implementation("io.github.classgraph:classgraph:4.8.174")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.jetbrains:annotations:24.0.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}