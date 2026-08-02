#!/usr/bin/env groovy
@Library('jenkins-shared-lib')_

def gv

pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    
    stages {
        stage ('init'){
            steps{
                script {
                    gv = load "script.groovy"
                }
            }
        }

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
                    buildImage 'piratehammad/demo-app:jma-3.0'
                    dockerLogin()
                    dockerPush 'piratehammad/demo-app:jma-3.0'
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}