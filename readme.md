# Jenkins Pipeline – Shared Library Loaded Directly from GitHub

This project shows how to use a Jenkins Shared Library **without** setting it up in Jenkins' global config first — instead, the pipeline pulls the library directly from its GitHub repo every time it runs.

## What This Branch (`shared_lib_using_library_identifier`) Does

1. Loads the shared library straight from GitHub using the `library identifier` step — no manual setup needed in **Manage Jenkins → Global Pipeline Libraries**
2. Also loads a local `script.groovy` file the same way as the other branches
3. Builds the app into a `.jar` file
4. Builds a Docker image, logs in to Docker Hub, and pushes the image — using functions that come from the **external shared library**, not the local script
5. Deploys the app — using a function from the **local** `script.groovy`

## The Two Different Function Sources (Important)

This branch mixes two ways of getting reusable code, so it's worth being clear on which is which:

| Function | Comes from | How it's called |
|---|---|---|
| `buildJar()` | External shared library repo (`jenkins-shared-libraries-nana`) | Called directly, no prefix |
| `buildImage(...)` | External shared library repo | Called directly, no prefix |
| `dockerLogin()` | External shared library repo | Called directly, no prefix |
| `dockerPush(...)` | External shared library repo | Called directly, no prefix |
| `deployApp()` | Local `script.groovy` in this repo | Called as `gv.deployApp()` |

The external library's functions live in its own repo under a `vars/` folder, and Jenkins makes each file in there callable by name automatically — that's why they don't need a `gv.` prefix like the local script does.

## How the Library Gets Loaded

```groovy
library identifier: 'jenkins-shared-libraries-nana@master', retriever: modernSCM(
    [
        $class: 'GitSCMSource',
        remote: 'https://github.com/import-Hammad/jenkins-shared-libraries-nana.git',
        credentialsId: 'github-credentials'
    ]
)
```

This tells Jenkins exactly which repo, branch, and credentials to use — so the library doesn't need to be pre-registered anywhere in Jenkins settings. Any pipeline can pull it in this way, from any Jenkins instance.

## Requirements to Run This Pipeline

- Jenkins with Maven and Docker installed
- Maven configured in Jenkins under the name `maven-3.9`
- A `github-credentials` entry in Jenkins with access to the shared library repo
- A Docker Hub credential set up (used inside the shared library's `dockerLogin()`/`dockerPush()` functions)

## Tech Used

- Jenkins
- Groovy (Jenkins Shared Library)
- Maven
- Docker & Docker Hub
