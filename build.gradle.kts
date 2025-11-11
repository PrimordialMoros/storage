import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar

plugins {
    `java-library`
    alias(libs.plugins.maven.publish)
    signing
}

group = "me.moros"
version = "4.0.0"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.jspecify)
    compileOnly(libs.hikari)
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        options.encoding = "UTF-8"
    }
    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
    jar {
        val licenseName = "LICENSE_${rootProject.name.uppercase()}"
        from("$rootDir/LICENSE") {
            into("META-INF")
            rename { licenseName }
        }
    }
}

mavenPublishing {
    pom {
        name = project.name
        description = "A utility library to easily build and wrap HikariDataSources"
        url = "https://github.com/PrimordialMoros/storage"
        inceptionYear = "2020"
        licenses {
            license {
                name = "The GNU Affero General Public License, Version 3.0"
                url = "https://www.gnu.org/licenses/agpl-3.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "moros"
                name = "Moros"
                url = "https://github.com/PrimordialMoros"
            }
        }
        scm {
            connection = "scm:git:https://github.com/PrimordialMoros/storage.git"
            developerConnection = "scm:git:ssh://git@github.com/PrimordialMoros/storage.git"
            url = "https://github.com/PrimordialMoros/storage"
        }
        issueManagement {
            system = "Github"
            url = "https://github.com/PrimordialMoros/storage/issues"
        }
    }
    configure(JavaLibrary(JavadocJar.Javadoc(), true))
    publishToMavenCentral()
    signAllPublications()
}
