buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.3.0")
    }
}
