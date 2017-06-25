freeStyleJob('boddenberg.it/ascent') {
	logRotator(-1, 30)
	label('masterSlave')
	description('Android Semiautomated CEllular Network Testing (GitHub link for more information)')

	scm {
		github('boddenberg-it/ascent', 'master')
	}

	triggers {
		githubPush()
	}

	steps {
		shell("shellcheck --format checkstyle *.sh | tee > shellcheck.xml")
	}

	publishers {
		checkstyle('shellcheck.xml') {
			healthLimits(3, 20)
			thresholdLimit('high')
			defaultEncoding('UTF-8')
			canRunOnFailed(true)
			useStableBuildAsReference(true)
			useDeltaValues(true)
			computeNew(true)
			shouldDetectModules(true)
			thresholds(
				unstableTotal: [all: 9, high: 10, normal: 11, low: 12],
				failedTotal: [all: 13, high: 14, normal: 15, low: 16],
				unstableNew: [all: 1, high: 2, normal: 3, low: 4],
				failedNew: [all: 5, high: 6, normal: 7, low: 8]
			)
		}
		archiveArtifacts('ascent.*, shellcheck.xml')
	}
}
