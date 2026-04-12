#!/usr/bin/env groovy
library identifier: 'jenkins-shared-libraries-nana@master', retriever: modernSCM(
    [
        $class: 'GitSCMSource',
        remote: 'https://github.com/import-Hammad/jenkins-shared-libraries-nana.git',
        credentialsId: 'github-credentials'
    ]
)

def gv

pipeline {
    agent any
    tools {
        maven "maven-3.92"
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }

        stage('build jar') {
            steps {
                script {
                    buildjar()        // ← from shared library
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    def docker = new com.example.Docker(this)   // ← from shared library src
                    docker.buildDockerImage("piratehammad/nana_practice_jenkins_2:jma-3.0")
                    docker.dockerLogin()
                    docker.dockerPush("piratehammad/nana_practice_jenkins_2:jma-3.0")
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    gv.deployapp()    // ← from script.groovy in your project
                }
            }
        }
    }
}