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

        stage('build jar') {
            steps {
                script {
                    echo "building the jar file"
                    sh "mvn clean package"
                }
            }
        }

        stage('build and push image') {
            steps {
                script {
                    echo "building and pushing the app"
                    withCredentials([usernamePassword(credentialsId: 'Dockerhub_credentials', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
                        sh "docker build -t piratehammad/nana_practice_jenkins_2:${IMAGE_NAME} ."
                        sh 'echo $PASSWORD | docker login -u $USER --password-stdin'
                        sh "docker push piratehammad/nana_practice_jenkins_2:${IMAGE_NAME}"
                    }
                }
            }
        }

        stage('deploy the app') {
            steps {
                script {
                    echo 'deploying the app'
                    def dockerCMD = "bash ./server.sh ${IMAGE_NAME}"
                    def ec2Instance = "ubuntu@54.91.135.131" // use your instance ip
                    sshagent(['ec2-server-key']) {
                        sh "scp -o StrictHostKeyChecking=no server.sh ${ec2Instance}:/home/ubuntu/"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${dockerCMD}"
                    }
                }
            }
        } // ✅ closing deploy stage

        stage('commit version update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh "git remote set-url origin https://${USER}:${PASS}@github.com/import-Hammad/jenkins-practice-nana.git"
                        sh 'git add .'
                        sh "git commit -m 'Updated version number to ${IMAGE_NAME}'"
                        sh 'git push origin HEAD:jenkins_job'
                    }
                }
            }
        }
    }
}
