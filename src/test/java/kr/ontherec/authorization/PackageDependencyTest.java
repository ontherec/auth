package kr.ontherec.authorization;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = AuthorizationApplication.class)
public class PackageDependencyTest {

	private static final String GLOBAL = "..global..";
	private static final String MEMBER = "..member..";
	private static final String SECURITY = "..security..";

	@ArchTest
	ArchRule securityPackageRule = classes().that().resideInAPackage(SECURITY)
			.should().onlyBeAccessed().byClassesThat().resideInAnyPackage(SECURITY);

	@ArchTest
	ArchRule memberPackageRule = classes().that().resideInAPackage(MEMBER)
			.should().onlyBeAccessed().byClassesThat().resideInAnyPackage(SECURITY, MEMBER);

	@ArchTest
	ArchRule globalPackageRule = classes().that().resideInAPackage(GLOBAL)
			.should().onlyBeAccessed().byClassesThat().resideInAnyPackage(GLOBAL, MEMBER, SECURITY);

	@ArchTest
	ArchRule cycleCheck = slices().matching("kr.ontherec.authorization.(*)..")
			.should().beFreeOfCycles();

}
