pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    stages {
        stage('increment version') {
            steps {
                script {
                    echo "incrementing the version number"
                    sh "mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit"
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building the jar file"
                    sh "mvn clean package"
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
                    echo "building and pushing the app"
                    withCredentials([usernamePassword(credentialsId: 'Dockerhub_credentials', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
                        sh 'docker build -t piratehammad/nana_practice_jenkins_2:$IMAGE_NAME .'
                        sh 'echo $PASSWORD | docker login -u $USER --password-stdin'
                        sh 'docker push piratehammad/nana_practice_jenkins_2:$IMAGE_NAME'
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