package kr.ontherec.authorization;


import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "kr.ontherec.authorization", importOptions = DoNotIncludeTests.class)
public class PackageDependencyTest {

	private static final String GLOBAL = "..global..";
	private static final String MEMBER = "..member..";
	private static final String SECURITY = "..security..";

	@ArchTest
	ArchRule securityPackageRule = classes().that().resideInAPackage(SECURITY)
			.should().onlyHaveDependentClassesThat().resideInAnyPackage(SECURITY);

	@ArchTest
	ArchRule memberPackageRule = classes().that().resideInAPackage(MEMBER)
			.should().onlyHaveDependentClassesThat().resideInAnyPackage(SECURITY, MEMBER);

	@ArchTest
	ArchRule globalPackageRule = classes().that().resideInAPackage(GLOBAL)
			.should().onlyHaveDependentClassesThat().resideInAnyPackage(GLOBAL, MEMBER, SECURITY)
			.andShould().onlyDependOnClassesThat(
					resideInAnyPackage(GLOBAL).or(DescribedPredicate.not(resideInAnyPackage("kr.ontherec.authorization")))
			);

	@ArchTest
	ArchRule cycleCheck = slices().matching("kr.ontherec.authorization.(*)..")
			.should().beFreeOfCycles();

}
