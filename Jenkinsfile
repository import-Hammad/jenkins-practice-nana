def gv

pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    stages {
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    gv.buildApp()
                }
            }
        }

        stage("deploy the app") {
            steps {
                script {
                    echo "deploying the application"
                }
            }
        }
    }
}
