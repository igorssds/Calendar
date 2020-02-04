pipeline {
	agent any

	environment {
		PROJECT_SONAR_KEY = "fd-api-template"
	}

	stages {
		stage("Build") {
			steps {
				withMaven(maven: "maven-3.6.0", mavenSettingsConfig: "oracle-maven", publisherStrategy: "EXPLICIT") {
					sh "mvn -Dmaven.test.skip=true clean package"
				}
			}
		}
		stage("Static Analysis") {
			environment {
				SONAR_HOME = tool "sonar-scanner-3.3.0"
			}

			steps {
				dependencyCheckAnalyzer scanpath: "${WORKSPACE}",
					outdir: "${WORKSPACE}/target/dependency-check",
					datadir: "",
					hintsFile: "",
					includeCsvReports: false,
					includeHtmlReports: true,
					includeJsonReports: false,
					includeVulnReports: true,
					isAutoupdateDisabled: false,
					skipOnScmChange: false,
					skipOnUpstreamChange: false,
					suppressionFile: "",
					zipExtensions: ""

				withSonarQubeEnv("SonarQube") {
					sh "${SONAR_HOME}/bin/sonar-scanner " +
						"-Dsonar.projectKey=${PROJECT_SONAR_KEY} " +
						"-Dsonar.projectBaseDir=${WORKSPACE} " +
						"-Dsonar.java.binaries=${WORKSPACE}/target/classes " +
						"-Dsonar.java.libraries=${WORKSPACE}/target/**/*.jar " +
						"-Dsonar.branch.name=${env.BRANCH_NAME} " +
						"-Dsonar.dependencyCheck.reportPath=${WORKSPACE}/target/dependency-check/dependency-check-report.xml " +
						"-Dsonar.dependencyCheck.htmlReportPath=${WORKSPACE}/target/dependency-check/dependency-check-report.html "
				}
			}
			post {
				success {
					dependencyCheckPublisher pattern: "",
						canComputeNew: false,
						defaultEncoding: "",
						healthy: "",
						unHealthy: ""
				}
			}
		}
	}
	post {
		always {
			cleanWs()
		}
	}
}