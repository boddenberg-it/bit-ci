freeStyleJob('boddenberg.it/asrec') {
	logRotator(-1, 10)
	label('masterSlave')
	description('Android Screen RECorder (GitHub link for more information)')

	scm {
		github('boddenberg-it/asrec', 'master')
	}

	triggers {
		githubPush()
  }

	steps {
		shell("shellcheck --format checkstyle *.sh | tee > asrec.xml")
		//shell("./build.sh test") FIRST ACTIVATE X11 for build user
	}

	publishers {
  	checkstyle('asrec.xml') {
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
		archiveArtifacts('asrec.groovy, build.sh, manifest')
  }
}
