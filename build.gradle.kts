import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.6.10"
    application
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.6.0"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "aplicacion.DifficultAppKt"
    }
}

application {
    mainClass.set("aplicacion.DifficultAppKt")
}
group = "ar.edu.unsam.phm"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14
java.targetCompatibility = JavaVersion.VERSION_14


repositories {
    mavenCentral()
}

val springVersion = "2.7.0"
val jacksonVersion = "2.13.2"
val javaConnectorVersion = "8.0.28"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-neo4j:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-web-services:$springVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    testImplementation(kotlin("test"))

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    /* configuracion para sql /
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    
    

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-rest
    / IMPORTANTE // Vulnerabilities from dependencies: CVE-2022-22965 */
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    runtimeOnly("mysql:mysql-connector-java:$javaConnectorVersion")
    runtimeOnly("org.neo4j:neo4j-ogm-bolt-driver:3.2.23")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "14"
    }
}