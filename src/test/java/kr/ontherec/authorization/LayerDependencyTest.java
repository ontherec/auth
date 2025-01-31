package kr.ontherec.authorization;


import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass.Predicates;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "kr.ontherec.authorization.member", importOptions = DoNotIncludeTests.class)
public class LayerDependencyTest {

	@ArchTest
	static final ArchRule layerDependenciesRule = layeredArchitecture().consideringAllDependencies()
			.layer("Presentation").definedBy("..presentation..")
			.layer("Application").definedBy("..application..")
			.layer("Domain").definedBy("..domain..")
			.layer("Dto").definedBy("..dto..")
			.layer("Dao").definedBy("..dao..")
			.layer("Exception").definedBy("..exception..")

			.whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
			.whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation")
			.whereLayer("Dao").mayOnlyBeAccessedByLayers("Presentation", "Application")
			.whereLayer("Dto").mayOnlyBeAccessedByLayers("Presentation", "Application")
			.whereLayer("Domain").mayOnlyBeAccessedByLayers("Presentation", "Application", "Dao", "Dto")
			.whereLayer("Domain").mayNotAccessAnyLayer()
			.whereLayer("Exception").mayNotAccessAnyLayer()

            .ignoreDependency(
					DescribedPredicate.alwaysTrue(),
					Predicates.resideOutsideOfPackages("kr.ontherec.authorization..")
			);


	@ArchTest
	ArchRule memberCycleCheck = slices().matching("kr.ontherec.authorization.member.(*)..")
			.should().beFreeOfCycles();
}
