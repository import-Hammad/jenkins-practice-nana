#!/usr/bin/env groovy
@Library('jenkins-shared-library')_

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
                    buildimage(piratehammad/nana_practice_jenkins_2:jma-3.0)
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    gv.deployapp()
                }
            }
        }
    }
}