### The commented-out code is also usable. With a few minor changes, it can be used as well. Other than that, all the code is ready to copy and use.
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
                    gv.buildApp()
                }
                
            }
        }

        stage ('test'){
            when {
                expression {
                    params.executeTests == true
                }
            }

            steps{
                script {
                    gv.testApp()
                }
               
            }
        }

        stage ('deploy'){
            // input{
            //     message "select the environment to deploy"
            //     parameters{
            //         choice(name: 'ONE', choices: ['dev', 'qa', 'prod'], description:'Select the environment to deploy')
            //         choice(name: 'TWO', choices: ['dev', 'qa', 'prod'], description:'Select the environment to deploy')
            //     }
            // }

            steps{
                script {
                    env.ENV = input message: "select the environment to deploy to", ok: "Done", parameters: [choice(name: 'ONE', choices: ['dev', 'qa', 'prod'], description:''), choice(name: 'TWO', choices: ['dev', 'qa', 'prod'], description:'')]
                    gv.deployApp()
                    echo "deploying to ${ENV}"
                    // echo "deploying to ${ONE}"
                    // echo "deploying to ${TWO}"
                }
                
            }
        }
    }
}