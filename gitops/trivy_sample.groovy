pipeline {
    agent { label "maven38" }

    options {
        // timestamps()    // 日志会有时间
        skipDefaultCheckout()   // 删除隐式checkout scm语句
        disableConcurrentBuilds()   //禁止并行
        timeout(time:10, unit:'MINUTES') //设置流水线超时时间
    }

    // environment {
    //     ACR_REGISTRY_CREDS = credentials('acepi001cr01_username_pass')
    // }

    stages {
        stage('VulnerabilityScan') {
            steps {
                container("trivy"){
                    script{
                        sh '''
                            // export TRIVY_USERNAME=${ACR_REGISTRY_CREDS_USR}
                            // export TRIVY_PASSWORD=${ACR_REGISTRY_CREDS_PSW}
                            trivy client --remote http://trivy.gitops-system:4954 -f json -o trivy-report.json jenkins:0.1
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            recordIssues(tools: [trivy(pattern: 'trivy-report.json')])
        }
    }
}