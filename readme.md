# Jenkins Pipeline – Full CI/CD with EC2 Deploy & Version Commit

This project builds on the version-incrementing pipeline by adding two more real steps: actually deploying the built image to a live EC2 server, and pushing the version bump back to GitHub so the repo stays in sync with what was actually built.

## What This Branch (`deploy_app_commit_version`) Does

1. Automatically increments the app's version in `pom.xml` and builds a unique image tag from it (version + Jenkins build number)
2. Builds the app into a `.jar` file
3. Builds a Docker image with that unique tag and pushes it to Docker Hub
4. Copies `server.sh` to a remote EC2 instance over SSH, then runs it there to deploy the new image
5. Commits the updated `pom.xml` version back to GitHub, so the repo always reflects the version that was actually deployed

## What's New Compared to `build_version_incremented`

| Stage | What it adds |
|---|---|
| deploy the app | Actually deploys — sends `server.sh` to the EC2 server and runs it to pull/run the new image |
| commit version update | Pushes the version bump in `pom.xml` back to GitHub, closing the gap where earlier branches bumped the version locally but never saved it back to the repo |

## How the Deploy Step Works

```groovy
def dockerCMD = "bash ./server.sh ${IMAGE_NAME}"
def ec2Instance = "ubuntu@54.91.135.131"
sshagent(['ec2-server-key']) {
    sh "scp -o StrictHostKeyChecking=no server.sh ${ec2Instance}:/home/ubuntu/"
    sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${dockerCMD}"
}
```

Jenkins connects to the EC2 instance using the `ec2-server-key` SSH credential, copies `server.sh` over, then runs it remotely with the new image tag as an argument — `server.sh` handles pulling and running the correct Docker image on the server.

## How the Version Commit Works

```groovy
sh "git remote set-url origin https://${USER}:${PASS}@github.com/import-Hammad/jenkins-practice-nana.git"
sh 'git add .'
sh "git commit -m 'Updated version number to ${IMAGE_NAME}'"
sh 'git push origin HEAD:jenkins_job'
```

Using the `github-credentials` stored in Jenkins, the pipeline commits the version-bumped `pom.xml` and pushes it to the `jenkins_job` branch — keeping the version history accurate without any manual git commands.

## Requirements to Run This Pipeline

- Jenkins with Maven and Docker installed
- Maven configured in Jenkins under the name `maven-3.9`
- A Docker Hub credential added in Jenkins (ID: `Dockerhub_credentials`)
- An SSH key credential in Jenkins for the EC2 server (ID: `ec2-server-key`)
- A GitHub credential in Jenkins (ID: `github-credentials`) with push access to this repo
- A reachable EC2 instance with `server.sh` set up to accept an image tag and deploy it

## Tech Used

- Jenkins
- Maven
- Docker & Docker Hub
- AWS EC2 (SSH deploy)
- Git / GitHub
