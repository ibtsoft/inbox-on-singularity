plugins {
    application
    java
    id("com.palantir.docker") version "0.35.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.singularity:singularity-core:0.0.1-SNAPSHOT")
    implementation("com.singularity:singularity-security:0.0.1-SNAPSHOT")
    implementation("com.singularity:singularity-web:0.0.1-SNAPSHOT")

    implementation("com.google.guava:guava:27.0.1-jre")

    testImplementation("junit:junit:4.12")
    testImplementation("org.slf4j:slf4j-log4j12:1.7.30")
}

/*
application {
    mainClass = com.ibtsoft.inbox.main.InboxSocketIoApplication
}
*/

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

tasks.jar {
    manifest {
        attributes(
                "Main-Class" to "com.ibtsoft.inbox.main.InboxSocketIoApplication",
                "Implementation-Title" to "Gradle",
                "Implementation-Version" to archiveVersion
        )
    }
    //from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

docker {
    name = "eu.gcr.io/angular-axle-102013/${project.name}:${project.version}"
    files(tasks.jar.get().archiveFile)
}
