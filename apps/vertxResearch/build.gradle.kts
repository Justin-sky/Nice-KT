import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm")

    // Apply the application plugin to add support for building a CLI application.
    application
}

dependencies {
    implementation(kotlin("reflect"))

    //api(files("conf"))
    api(project(":subProjects:nice-scaffold"))

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")


    configurations.all {
        this.exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
}

application {
    // Define the main class for the application.
    mainClass.set("com.lj.Test")

    // 可以在此添加jvm内存参数, eg: '-Xms512m', '-Xmx4096m'
    applicationDefaultJvmArgs = listOf("-Duser.timezone=GMT+8", "-Dfile.encoding=UTF-8", "-Dsun.jnu.encoding=UTF-8")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val distZip: Zip by tasks
distZip.into(project.name) {
    from(".")
    include("conf/**")
}

val distTar: Tar by tasks
distTar.enabled = false

val installDist: Sync by tasks
installDist.into("conf") {
    from("./conf")
    include("**")
}

