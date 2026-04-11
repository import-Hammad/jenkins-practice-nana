pipeline {
    agent any
    stages {
        stage ('build'){
            steps {
                script {
                    echo "testing the app"
                    echo "executing pipeline for branch $BRANCH_NAME"

                }
            }
        }
        stage ('build'){
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    echo "building the app"

                }
            }
        }
        stage ("deploy"){
            steps {
                script {
                    echo "deploying the app"
                }
            }
        }
    }
}