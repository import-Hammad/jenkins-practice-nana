
pipeline {
    agent any
    tools {
        maven "maven-3.92"
    }
    stages {
        stage  ('increment  version') {
            steps  {
                script  {
                    echo  "incrementing the version number"
                    sh  "mvn  build-helper:parse-version  versions:set  \
                    -DnewVersion=\\\$(parsedVersion.majorVersion).\\\$(parsedVersion.minorVersion).\\\$(parsedVersion.nextIncrementalVersion) \
                    version:commit"
                    def  matcher  =  readFile('pom.xml')  =~ '<version>(.+)</version>'
                    def  version  =  matcher[0][1]
                    env.IMAGE_NAME  = "$version-$BUILD_NUMBER"
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
                    echo "deploying the app"
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh 'docker build -t piratehammad/nana_practice_jenkins_2:$IMAGE_NAME .'
                        sh 'echo $PASS | docker login -u $USER --password-stdin'
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