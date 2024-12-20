plugins {
    java
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "mcdodik"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.apache.commons:commons-csv:1.9.0")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.h2database:h2:2.2.220")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
