plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    api(project(":common:domain"))

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
}

configurations {
    create("testResources")
}
tasks.register<Jar>("quote.domainTestResourcesArchive") {
    archiveBaseName.set("quote.domainTestResources")
    from(project.the<SourceSetContainer>()["main"].output)
    from(project.the<SourceSetContainer>()["test"].output)
}
artifacts {
    add("testResources", tasks["quote.domainTestResourcesArchive"])
}
