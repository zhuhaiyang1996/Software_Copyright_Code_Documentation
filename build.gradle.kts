import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "me.zhuhaiyang"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml-lite:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")
    implementation("org.apache.poi:poi-scratchpad:5.2.2")
    implementation("org.apache.logging.log4j:log4j-core:2.18.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

// 配置打包成 jar 文件
tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

application {
    mainClass.set("MainKt")
}