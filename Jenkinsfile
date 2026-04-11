#!/usr/bin/env groovy
@Library('jenkins-shared-lib') 

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
                    buildjar()
                    
                   
                }
            }
        }
        stage (" deploying the app"){
            steps {
                script {
                    buildimage('piratehammad/nana_practice_jenkins_2:jma-2.0')
                    
                    
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