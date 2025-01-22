package kr.ontherec.authorization;


import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "kr.ontherec.authorization.member")
public class LayerDependencyTest {

	@ArchTest
	static final ArchRule layerDependenciesRule = layeredArchitecture().consideringAllDependencies()
			.layer("Applications").definedBy("..application..")
			.layer("Domain").definedBy("..domain..")
			.layer("Dao").definedBy("..dao..")

			.whereLayer("Applications").mayNotBeAccessedByAnyLayer()
			.whereLayer("Dao").mayOnlyBeAccessedByLayers("Applications")
			.whereLayer("Domain").mayOnlyBeAccessedByLayers("Applications", "Dao");

	@ArchTest
	ArchRule memberCycleCheck = slices().matching("kr.ontherec.authorization.member.(*)..")
			.should().beFreeOfCycles();
}
