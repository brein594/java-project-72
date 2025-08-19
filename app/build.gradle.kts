plugins {
    id("java")
    application
    checkstyle
    id("org.sonarqube") version "6.2.0.5505"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass = "hexlet.code.App"
}

checkstyle {
    toolVersion = "10.12.4"
}

sonar {
    properties {
        property("sonar.projectKey", "brein594_java-project-72")
        property("sonar.organization", "brein594")
    }
}