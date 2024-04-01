import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("api")
            .pathsToMatch("/api/**")
            .build()
    }
}