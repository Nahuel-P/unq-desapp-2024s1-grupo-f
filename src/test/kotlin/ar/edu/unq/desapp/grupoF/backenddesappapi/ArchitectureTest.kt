import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Repository

class ArchitectureTest {

    @Test
    fun `project should not use singletons`() {
        val importedClasses: JavaClasses =
            ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoF.backenddesappapi")

        val rule: ArchRule = noClasses()
            .should().beAnnotatedWith("javax.inject.Singleton")

        rule.check(importedClasses)
    }

    @Test
    fun `every file that ends with Repository should have the Repository annotation`() {
        val importedClasses: JavaClasses =
            ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoF.backenddesappapi")

        val rule: ArchRule = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .should().beAnnotatedWith(Repository::class.java)

        rule.check(importedClasses)
    }
}