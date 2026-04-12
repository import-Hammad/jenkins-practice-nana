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
                    gv = load "script.groovy"
                }
            }
        }

        stage('build jar') {
            steps {
                script {
                    gv.buildjar()
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    gv.buildapp()
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