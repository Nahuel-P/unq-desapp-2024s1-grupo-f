package ar.edu.unq.desapp.grupoF.backenddesappapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan(basePackages = ["ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl", "ar.edu.unq.desapp.grupoF.backenddesappapi.webservice", "ar.edu.unq.desapp.grupoF.backenddesappapi.repositories", "ar.edu.unq.desapp.grupoF.backenddesappapi.exception", "ar.edu.unq.desapp.grupoF.backenddesappapi.security", "ar.edu.unq.desapp.grupoF.backenddesappapi.service.client", "ar.edu.unq.desapp.grupoF.backenddesappapi.config"])
@EnableScheduling
@EnableCaching
class BackendDesappApiApplication

fun main(args: Array<String>) {
    runApplication<BackendDesappApiApplication>(*args)
}
