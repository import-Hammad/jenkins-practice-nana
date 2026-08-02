pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    stages {
        stage("build jar") {
            steps {
                script {
                    echo "building the jar file"
                    sh "mvn package"
                }
            }
        }

        stage("build and push image") {
            steps {
                script {
                    echo "building the docker image"
                    withCredentials([usernamePassword(credentialsId: 'Dockerhub_credentials', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
                        sh 'docker build -t piratehammad/demo-app:jma-2.0 .'
                        sh 'echo $PASSWORD | docker login -u $USER --password-stdin'
                        sh 'docker push piratehammad/demo-app:jma-2.0'
                    }
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
