package ar.edu.unq.desapp.grupoF.backenddesappapi.service.logger

import org.apache.logging.log4j.LogManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

@Aspect
@Component
class Logger {
    private val logger = LogManager.getLogger(Logger::class.java)

    @Around("execution(* ar.edu.unq.desapp.grupoF.backenddesappapi.webservice..*(..))")
    fun logServiceMethods(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = LocalDateTime.now()
        val user = getCurrentUser()
        val operationType = joinPoint.signature.toShortString()
        val parameters = joinPoint.args.joinToString(", ")

        var result: Any? = null
        val executionTime = measureTimeMillis {
            result = joinPoint.proceed()
        }

        logger.info("TimeStamp: $startTime, User: $user, Operation: $operationType, Execution time: $executionTime, Parameters: $parameters")
        return result
    }

    private fun getCurrentUser() =
        org.springframework.security.core.context.SecurityContextHolder.getContext().authentication?.name ?: "anonymous"
}