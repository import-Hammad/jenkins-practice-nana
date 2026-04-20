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
    environment {
        IMAGE_NAME = "piratehammad/react-nodejs-app:1.0"  // Bug 1 fixed
    }
    stages {                                               // Bug 2 fixed

        stage('build app') {
            steps {
                script {
                    buildjar()                             // Bug 3 fixed
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    buildimage(env.IMAGE_NAME)             // Bug 4 fixed
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    echo 'deploying the app'
                    def dockerCMD = "docker run -d -p 3080:3080 ${env.IMAGE_NAME}"
                    sshagent(['ec2-server-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@3.88.12.245 ${dockerCMD}"  // Bug 5 fixed
                    }
                }
            }
        }

    }
}