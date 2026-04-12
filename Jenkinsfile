#!/usr/bin/env groovy
library identifier: 'jenkins-shared-libraries-nana@master', retriever: modernSCM(
    [
        $class: 'GitSCMSource',
        remote: 'https://github.com/import-Hammad/jenkins-shared-libraries-nana.git',
        credentialsId: 'github-credentials'
    ]
)

pipeline {
    agent any
    tools {
        maven "maven-3.92"
    }
    stages {
        stage('build jar') {
            steps {
                script {
                    buildjar()
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    def docker = new com.example.Docker(this)
                    docker.buildDockerImage("piratehammad/nana_practice_jenkins_2:jma-3.0")
                    docker.dockerLogin()
                    docker.dockerPush("piratehammad/nana_practice_jenkins_2:jma-3.0")
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    deployapp()
                }
            }
        }
    }
}