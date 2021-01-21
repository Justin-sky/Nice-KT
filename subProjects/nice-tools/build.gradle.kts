import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    id("maven-publish")
}

dependencies {
//    api(kotlin("stdlib-jdk8"))
    api(kotlin("reflect"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.9")

    // android gradle依赖：implementation 和compile的区别
    // 参考: https://www.jianshu.com/p/f34c179bc9d0 根据需要选择使用不同的依赖设定方式
    api(project(":subProjects:nice-log"))

    api("com.fasterxml.jackson.module:jackson-module-jsonSchema:2.10.2")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2") {
        this.exclude(group = "org.jetbrains.kotlin")
    }
    api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.10.2")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.2")
    api("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.2")
    api("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.10.2")

    api("com.typesafe:config:1.3.4")
    api("org.apache.commons:commons-lang3:3.9")
    api("org.jodd:jodd-util:6.0.0")
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }

    val userHome = System.getProperty("user.home")

    repositories {
        var myRepo =  "$userHome\\Documents\\u3dWorkspace\\NICE_Framework\\Nice-KT-Repository"
        System.getProperty("myRepo")?.apply {
            myRepo = this
        }
        maven {
            name = "myRepo"
            url = uri("file://$myRepo")
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
