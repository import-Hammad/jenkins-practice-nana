def gv
pipeline {
    agent any 
    tools {
        maven "maven-3.92"
    }
    stages {
        stage ('init'){
            steps {
                sciprt{
                    gv = load "script.groovy"
                }
            }
        }

        stage ("build jar"){
            steps {
                script {
                    echo "building the jar file"
                    sh "mvn package"
                }
            }
        }
        stage (" deploying the app"){
            steps {
                script {
                    echo "deploying the app"
                    withCredentials ([usernamePassword(credentialsID: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASS")]){
                        sh 'docker build -t piratehammad/nana_practice_jenkins_2:jma-2.0 .'
                        sh 'echo $PASS | docker login -u $USER --password-stdin'}}'
                        sh 'docker push piratehammad/nana_practice_jenkins_2:jma-2.0')])
                }
            }
        }
         stage ("deploying the app"){
            steps {
                script {
                    echo "deploying the application"
                    
                }
            }
        }
    }
}