# Jenkins CI/CD Pipeline – Docker Build & Push

This project sets up a Jenkins pipeline that automatically builds a Java app, packages it into a Docker image, and pushes it to Docker Hub.

## What This Branch (`Docker_CICD`) Does

The pipeline runs these steps every time it's triggered:

1. Loads shared build logic from `script.groovy`
2. Builds the Java app into a `.jar` file using Maven
3. Builds a Docker image of the app
4. Logs in to Docker Hub and pushes the image
5. Deploys the app (placeholder step for now)

## How the Files Work Together

- **`Jenkinsfile`** – defines the pipeline stages and calls functions from `script.groovy`
- **`script.groovy`** – holds the actual logic (build, package, Docker build/push), loaded into the pipeline at runtime with `load "script.groovy"`

This keeps the `Jenkinsfile` short and readable, while the real work lives in one separate, reusable file.

## Pipeline Stages

| Stage | What it does |
|---|---|
| init | Loads `script.groovy` so its functions can be used |
| build jar | Runs `mvn package` to build the application |
| build image | Builds the Docker image and pushes it to Docker Hub |
| deploy the app | Deploys the app (in progress) |

## Requirements to Run This Pipeline

- Jenkins with Maven and Docker installed
- Maven configured in Jenkins under the name `maven-3.9`
- A Docker Hub credential added in Jenkins (ID: `Dockerhub_credentials`)

## Tech Used

- Jenkins
- Groovy
- Maven
- Docker & Docker Hub
