package it.zolla.ecommerce;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("it.zolla.ecommerce");

        noClasses()
            .that()
            .resideInAnyPackage("it.zolla.ecommerce.service..")
            .or()
            .resideInAnyPackage("it.zolla.ecommerce.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..it.zolla.ecommerce.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
