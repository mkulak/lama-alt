import com.github.gradle.node.npm.task.NpxTask

plugins {
    kotlin("js") version "1.4.21"
    id("com.github.node-gradle.node") version "3.0.0-rc5"
}

val kotlinVersion = "1.4.21"
val kjsVersion = "pre.138-kotlin"
val reactVersion = "16.13.1"

group = "me.user"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

dependencies {
    implementation("org.jetbrains:kotlin-react-router-dom:5.2.0-$kjsVersion-$kotlinVersion")

    implementation("org.jetbrains:kotlin-react:$reactVersion-$kjsVersion-$kotlinVersion")
    implementation("org.jetbrains:kotlin-react-dom:$reactVersion-$kjsVersion-$kotlinVersion")
    implementation("org.jetbrains:kotlin-styled:5.2.0-$kjsVersion-$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-js:1.0.0-RC2")
    implementation(npm("react", reactVersion))
    implementation(npm("react-dom", reactVersion))
    implementation(npm("react-is", reactVersion))
    implementation(npm("styled-components", "5.2.0"))
    implementation(npm("inline-style-prefixer", "6.0.0"))

    testImplementation(kotlin("test-js"))
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
}

val tailwindCss = tasks.register<NpxTask>("tailwindcss") {

    // Output CSS location
    val generatedFile = "build/distributions/tailwind-generated.css"

    // Location of the tailwind config file
    val tailwindConfig = "css/tailwind.css"

    command.set("tailwind")
    args.set(listOf("build", tailwindConfig, "-o", generatedFile))

    dependsOn(tasks.npmInstall)

    // The location of the source files which Tailwind scans when running ```purgecss```
    inputs.dir("src/main")

    inputs.file(tailwindConfig)
    outputs.file(generatedFile)
}

(tasks.getByName("processResources") as ProcessResources).apply {
    dependsOn(tailwindCss)
}
