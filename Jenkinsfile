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
        IMAGE_NAME = "piratehammad/react-nodejs-app:1.0"
    }

        stage('build app') {
            steps {
                script {
                    buildjar()        // ← from shared library
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    
                    buildimage(env.IMAGE_NAME)    
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    echo 'deploying the app'
                    def shellCmd = "bash ./server.sh ${IMAGE_NAME}"
                    
                    sshagent (['ec2-server-key']){
                        sh "scp server.sh ubuntu@54.91.135.131:/home/ubuntu/"
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yml ubuntu@54.91.135.131:/home/ubuntu/"
                        sh 'ssh -o StrictHostKeyChecking=no ubuntu@54.91.135.131  ${shellCmd} '
                    }
                }
            }
        }
    }