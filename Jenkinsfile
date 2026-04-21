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
        stage ('increment version') {
            steps {
                script {
                        echo "Incrementing value..."
                        sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                        def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                        def version = matcher[0][1]
                        env.IMAGE_NAME = "piratehammad/react-nodejs-app:${version}-${BUILD_NUMBER}"
                }
            }
        }                                              // Bug 2 fixed

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
                    def dockerCMD = "bash ./server.sh ${IMAGE_NAME}"
                    def ec2Instance = "ubuntu@54.91.135.131"
                    sshagent(['ec2-server-key']) {
                        sh "scp server.sh  ${ec2Instance}:/home/ubuntu/"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} '${dockerCMD}'"  // Bug 5 fixed
                    }
                }
            }
        }
        stage('commit version update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh 'git remote set-url origin https://${USER}:${PASS}@github.com/import-Hammad/jenkins-practice-nana.git'
                        sh 'git add .'
                        sh "git commit -m 'Updated version number to $IMAGE_NAME'"
                        sh 'git push origin HEAD:jenkins_job'
                    }
                }
            }
        }
    }
}

