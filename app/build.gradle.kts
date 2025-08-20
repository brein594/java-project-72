import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    application
    checkstyle
    id("org.sonarqube") version "6.2.0.5505"
    id("io.freefair.lombok") version "8.14"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("hexlet.code.App")
}
group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("gg.jte:jte:3.1.16")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("org.apache.commons:commons-lang3:3.18.0")
    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("io.javalin:javalin-testtools:6.2.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
         //showStackTraces = true
         //showCauses = true
       showStandardStreams = true
    }
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
