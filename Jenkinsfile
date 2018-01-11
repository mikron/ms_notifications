#!groovy​

buildProfile = "snapshot"
if(BRANCH_NAME == "master") {
    buildProfile = "release"
}

node {
    def mvnHome = tool 'Maven'
    env.JAVA_HOME = tool 'jdk8'

    stage('Checkout changes') {
        properties([pipelineTriggers([[$class: 'GitHubPushTrigger'], pollSCM('H/15 * * * *')])])
        checkout scm
    }

    withEnv(['DOCKER_HOST=']) { // TODO replace with correct host ip (DOCKER_HOST=tcp://172.17.0.1:4243)
        stage('Build & test') {
            try {
                sh "${mvnHome}/bin/mvn -B -T 1.5C -P ${buildProfile} clean verify"
            } finally {
                junit '**/target/surefire-reports/*.xml,**/target/failsafe-reports/*.xml'
            }
        }

        /*stage('Analysis') {
            withSonarQubeEnv {
                sh "${mvnHome}/bin/mvn -B -P ${buildProfile} ${SONAR_MAVEN_GOAL} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_AUTH_TOKEN}"
            }
        }*/

        if (BRANCH_NAME == "master" || BRANCH_NAME == "develop") {
            stage('Deploy') {
                step([$class                                                : 'ArtifactArchiver', artifacts: 'api-internal/api-internal-web/target/*.jar, ' +
                        'queue/queue-consumer-web/target/*.jar', fingerprint: true])
                sh "${mvnHome}/bin/mvn -B -T 1.5C -P ${buildProfile} -Dmaven.test.skip=true deploy"
            }
        }
    }
}
