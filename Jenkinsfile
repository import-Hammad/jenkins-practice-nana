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
                    dv.buildjar()
                   
                }
            }
        }
        stage (" deploying the app"){
            steps {
                script {
                    gv.buildapp()
                    
                }
            }
        }
         stage ("deploying the app"){
            steps {
                script {
                    gv.deployapp()
                   
                    
                }
            }
        }
    }
}