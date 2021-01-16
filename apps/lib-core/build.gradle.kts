import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    java
    idea
    id("com.google.protobuf") version "0.8.8"
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    maven("https://plugins.gradle.org/m2/")
}

val protobufVersion = "3.11.3"
val vertxVersion = "3.9.4"

dependencies {


    api(project(":subProjects:nice-scaffold"))
    api("io.vertx:vertx-codegen:$vertxVersion")
    api("io.vertx:vertx-lang-kotlin-gen:$vertxVersion")
    api("io.vertx:vertx-service-proxy:$vertxVersion")
    api("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
    api("com.google.protobuf:protobuf-java:$protobufVersion")
    api("com.google.flatbuffers:flatbuffers-java:1.12.0")

    api("org.jetbrains.kotlin:kotlin-reflect:1.4.20")
    // android gradle依赖：implementation 和compile的区别
    // 参考: https://www.jianshu.com/p/f34c179bc9d0 根据需要选择使用不同的依赖设定方式

    kapt("io.vertx:vertx-codegen:3.9.4:processor")
    compileOnly("io.vertx:vertx-codegen:3.9.4")


    configurations.all {
        this.exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }

    api("com.google.protobuf:protobuf-gradle-plugin:0.8.14")
}


tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}


protobuf{
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
}

sourceSets{
    main{
        java.srcDirs("${buildDir.absolutePath}/generated/source/proto/main/java")
        println("${buildDir.absolutePath}/generated/source/proto/main")
    }
}