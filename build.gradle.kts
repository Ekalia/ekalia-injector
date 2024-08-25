/*
 * Copyright 2024 Ekalia <contact@ekalia.fr>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("java")
    id("java-library")
    id("net.linguica.maven-settings") version "0.5"
    id("maven-publish")
}

val isRelease = System.getenv("RELEASE") == "true"

val nexusUser = System.getenv()["NEXUS_USER"]
val nexusPass = System.getenv()["NEXUS_PASS"]
val isInCi = nexusUser != null && nexusPass != null

group = "fr.ekalia.injector"
version = "1.1.2" + if (isRelease) "" else "-SNAPSHOT"

System.out.println("Version: " + version + ", nexusUser null? " + (nexusUser == null) + ", nexusPass null? " + (nexusPass == null));

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
    api("io.github.classgraph:classgraph:4.8.174")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    compileOnly("org.jetbrains:annotations:24.0.0")
}
