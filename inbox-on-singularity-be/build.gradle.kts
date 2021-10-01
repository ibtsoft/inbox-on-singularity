plugins {
    application
    java
    id("com.palantir.docker") version "0.28.0"
}

repositories {
    jcenter()
    mavenLocal()
}

dependencies {
    implementation("com.ibtsoft:singularity-core:0.0.1-SNAPSHOT")
    implementation("com.ibtsoft:singularity-security:0.0.1-SNAPSHOT")
    implementation("com.ibtsoft:singularity-web:0.0.1-SNAPSHOT")

    implementation("com.google.guava:guava:27.0.1-jre")

    testImplementation("junit:junit:4.12")
    testImplementation("org.slf4j:slf4j-log4j12:1.7.30")
}

application {
    mainClassName = "com.ibtsoft.inbox.main.SocketIoServerApplication"
}

tasks.jar {
    manifest {
        attributes(
                "Main-Class" to "com.ibtsoft.inbox.main.InboxSocketIoApplication",
                "Implementation-Title" to "Gradle",
                "Implementation-Version" to archiveVersion
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

docker {
    name = "eu.gcr.io/angular-axle-102013/${project.name}:${project.version}"
    files(tasks.jar.get().archiveFile)
}