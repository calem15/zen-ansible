timestamps {
  currentBuild.displayName = "${branch_tag}"
  node('node-1') {
    if (params.build) {
      stage('Build') {
        cleanWs notFailBuild: true
        checkout([$class: 'GitSCM',
                  branches: [[name: "${branch_tag}"]],
                  doGenerateSubmoduleConfigurations: false,
                  extensions: [],
                  submoduleCfg: [],
                  userRemoteConfigs: [[credentialsId: 'MyPrivateKey', refspec: '+refs/heads/*:refs/tags/*', url: 'git@github.com:zenrooms/qa-test.git']]
                  ])
            git credentialsId: 'my-private-key' , branch: "$branch_tag", url: 'git@github.com:zenrooms/qa-test.git'
            sh "sudo docker build -t calem15/qa-test:$branch_tag . -f ./Dockerfile "
            sh "sudo docker tag calem15/qa-test:$branch_tag calem15/qa-test:latest"
            sh "sudo docker push calem15/qa-test:$branch_tag"
            sh "sudo docker push calem15/qa-test:latest"
          }
        }
      }
      node('master') {
        stage('Deploy') {
          cleanWs notFailBuild: true
          git branch: 'master', url: 'git@github.com:calem15/zen-ansible.git'
          sh """
             ansible-playbook  -i inventory/$srv_env deploy-qatest.yml --diff \
             -e docker_image_version=$branch_tag
          """
        }
      }
    } catch (e) {
      currentBuild.result = "FAILED"
      throw e
    } finally {
      notifyBuild(currentBuild.result)
    }
}
def notifyBuild(String buildStatus = 'STARTED') {
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"

  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }
  slackSend (channel: 'jenkins-job', color: colorCode, message: summary, teamDomain: 'zenrooms', tokenCredentialId: 'slack-access-token-here')
}
