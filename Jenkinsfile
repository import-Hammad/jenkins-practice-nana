#!/usr/bin/env groovy
@Library('jenkins-shared-lib')_

pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    stages {
        stage('build jar') {
            steps {
                script {
                    buildJar()
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    buildImage()
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    deployApp()
                }
            }
        }
    }
}