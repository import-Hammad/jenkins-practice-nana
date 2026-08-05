# Jenkins Pipeline – Auto-Incrementing Build Version

This project shows how to automatically bump the app's version number on every build, so each Docker image gets a unique, meaningful tag instead of a hardcoded one.

## What This Branch (`build_version_incremented`) Does

1. Automatically increments the version number in `pom.xml` on every run, using Maven's `build-helper` and `versions` plugins
2. Reads the new version back out of `pom.xml` and combines it with the Jenkins build number to form a unique image tag
3. Builds the app into a `.jar` file
4. Builds a Docker image using that unique tag, then pushes it to Docker Hub
5. Deploys the app (placeholder step)

## How the Version Increment Works

```groovy
sh "mvn build-helper:parse-version versions:set \
-DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} \
versions:commit"
```

This tells Maven to read the current version from `pom.xml` (e.g. `1.2.0`), keep the major and minor numbers the same, and bump only the last number up by one (`1.2.0` → `1.2.1`). It then saves this new version straight back into `pom.xml`.

After that, the pipeline reads the updated version out of `pom.xml`:

```groovy
def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
def version = matcher[0][1]
env.IMAGE_NAME = "$version-$BUILD_NUMBER"
```

`$BUILD_NUMBER` is a variable Jenkins provides automatically, and it goes up by one on every run. Combining it with the version gives an image tag like:

```
1.2.1-47
```

so every single build produces a Docker image with its own unique tag — no image ever gets silently overwritten.

## Pipeline Stages

| Stage | What it does |
|---|---|
| increment version | Bumps the version in `pom.xml` and builds the image tag |
| build jar | Builds the application |
| build and push image | Builds the Docker image with the new tag, then pushes it to Docker Hub |
| deploy the app | Deploys the app (in progress) |

## Requirements to Run This Pipeline

- Jenkins with Maven and Docker installed
- Maven configured in Jenkins under the name `maven-3.9`
- The `build-helper-maven-plugin` and `versions-maven-plugin` available to the project (via `pom.xml`)
- A Docker Hub credential added in Jenkins (ID: `Dockerhub_credentials`)

## Tech Used

- Jenkins
- Maven
- Docker & Docker Hub
