pipelineJob('gitops/aldi-jenkins') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/goldginkgo/jenkins-docker.git')
          }
          branch('*/master')
        }
      }
      lightweight()
    }
  }

  triggers {
    gitlabPush {
        buildOnMergeRequestEvents(false)
        buildOnPushEvents(true)
        enableCiSkip(false)
        setBuildDescription(false)
        rebuildOpenMergeRequest('never')
    }
  }
}