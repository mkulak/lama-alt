plugins {
    kotlin("js") version "1.4.21"
}


val kotlinVersion = "1.4.21"
val kjsVersion = "pre.138-kotlin"

group = "me.user"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

dependencies {
    implementation("org.jetbrains:kotlin-react-router-dom:5.2.0-$kjsVersion-$kotlinVersion")

    implementation("org.jetbrains:kotlin-react:16.13.1-$kjsVersion-$kotlinVersion")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-$kjsVersion-$kotlinVersion")
    implementation("org.jetbrains:kotlin-styled:5.2.0-$kjsVersion-$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-js:1.0.0-RC2")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))
    implementation(npm("react-is", "16.13.1"))
    implementation(npm("styled-components", "5.2.0"))
    implementation(npm("inline-style-prefixer", "6.0.0"))

    testImplementation(kotlin("test-js"))
}

kotlin {
    js(LEGACY) {
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
