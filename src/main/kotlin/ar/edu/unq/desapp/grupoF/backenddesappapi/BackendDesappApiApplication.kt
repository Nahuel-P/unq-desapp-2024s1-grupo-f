package ar.edu.unq.desapp.grupoF.backenddesappapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl", "ar.edu.unq.desapp.grupoF.backenddesappapi.webservice"])
class BackendDesappApiApplication

fun main(args: Array<String>) {
    runApplication<BackendDesappApiApplication>(*args)
}
