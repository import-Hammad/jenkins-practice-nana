def gv

pipeline {
    agent any
    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description:'Select the version to build')
        booleanParam(name: 'executeTests', defaultValue: true, description: 'Run tests after build?')
    }

    stages {
        stage ('init'){
            steps{
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage ('build'){
            steps{
                script {
                    gv.buildapp()
                }
                
            }
        }

        stage ('test'){
            when {
                expression {
                    params.executeTests...
                }
            }

            steps{
                script {
                    gv.testapp()
                }
               
            }
        }

        stage ('deploy'){

            steps{
                script {
                    gv.deployapp()
                }
                
            }
        }
    }
}
 