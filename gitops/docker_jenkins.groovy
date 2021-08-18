pipelineJob('gitops/aldi-jenkins') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('http://560GHD11/aldi-gitops/aldi-jenkins.git')
            credentials('gitlab_username_pass')
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