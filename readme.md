# Jenkins Pipeline – Global Shared Library

This project shows how to use a proper **Jenkins Shared Library** — reusable pipeline code stored in its own separate Git repo, instead of a `script.groovy` file living inside the same project.

## What This Branch (`jenkins_shared_library`) Does

The pipeline runs these steps:

1. Loads the global shared library `jenkins-shared-lib`
2. Builds the app into a `.jar` file using Maven
3. Builds a Docker image, logs in to Docker Hub, and pushes the image
4. Deploys the app using a local `script.groovy` function

## Two Ways of Sharing Code, Used Together

This branch mixes both patterns on purpose, to show the difference:

- **Global Shared Library** – functions like `buildJar()`, `buildImage()`, `dockerLogin()`, and `dockerPush()` come from a separate repo: [jenkins-shared-libraries-nana](https://github.com/import-Hammad/jenkins-shared-libraries-nana). It's registered in Jenkins under **Manage Jenkins → System → Global Pipeline Libraries** with the name `jenkins-shared-lib`, and loaded at the top of the Jenkinsfile with `@Library('jenkins-shared-lib')_`. Once loaded, its functions can be called directly — no `gv.` prefix needed.
- **Local Script Load** – `deployApp()` still comes from a `script.groovy` file sitting in this same repo, loaded the older way with `gv = load "script.groovy"` and called as `gv.deployApp()`.

## How the Files Work Together

- **`Jenkinsfile`** – defines the pipeline, loads the global library, and calls both library functions and local `script.groovy` functions
- **`script.groovy`** (this repo) – holds `deployApp()`
- **`jenkins-shared-libraries-nana`** (separate repo) – holds the reusable `buildJar`, `buildImage`, `dockerLogin`, and `dockerPush` functions under its `vars/` folder

## Pipeline Stages

| Stage | What it does |
|---|---|
| init | Loads `script.groovy` |
| build jar | Runs `mvn package` via the shared library's `buildJar()` |
| build and push image | Builds the Docker image, logs in, and pushes it via shared library functions |
| deploy the app | Deploys the app using the local `deployApp()` |

## Requirements to Run This Pipeline

- Jenkins with Maven and Docker installed
- Maven configured in Jenkins under the name `maven-3.9`
- A Docker Hub credential configured in Jenkins
- The `jenkins-shared-lib` library registered in **Manage Jenkins → System → Global Pipeline Libraries**, pointing to the [jenkins-shared-libraries-nana](https://github.com/import-Hammad/jenkins-shared-libraries-nana) repo

## Tech Used

- Jenkins
- Groovy
- Maven
- Docker & Docker Hub
