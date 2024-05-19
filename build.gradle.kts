
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	war
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	id("jacoco")
	id("org.sonarqube") version "4.4.1.3373"
}

// Jacoco configuration
jacoco {
	toolVersion = "0.8.11"

}

tasks.jacocoTestReport {
	reports {
		xml.required = true
	}
}


sonar {
	properties {
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.organization", "nahuel-p")
		property("sonar.projectKey", "Nahuel-P_unq-desapp-2024s1-grupo-f")
	}
}
group = "ar.edu.unq.desapp.grupoF"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
	implementation("org.hibernate.validator:hibernate-validator:7.0.1.Final")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
