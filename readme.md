# Jenkins Pipeline – Parameters & Shared Library

This project shows how to add build parameters, conditional stages, and manual environment selection to a Jenkins pipeline using a shared Groovy script.

## What This Branch (`jenkins_parameters_and_shared_library`) Does

The pipeline runs these steps:

1. Loads shared build logic from `script.groovy`
2. Builds the app
3. Runs tests — but only if the `executeTests` parameter is checked
4. Pauses and asks the user to pick a deploy environment, then deploys

## Key Concepts Covered

- **Build Parameters** – `VERSION` (choice) and `executeTests` (checkbox) are set before every build, letting you control the run without changing the Jenkinsfile
- **Conditional Stages** – the `test` stage only runs `when` `executeTests` is `true`, using Jenkins' `when { expression { ... } }` block
- **Manual Input During a Run** – the `deploy` stage pauses with an `input` step, asking the user to choose the target environment(s) before continuing
- **Shared Script** – all the real logic (`buildApp`, `testApp`, `deployApp`) lives in `script.groovy` and is loaded into the pipeline with `load "script.groovy"`

## How the Files Work Together

- **`Jenkinsfile`** – defines parameters, stages, and pipeline flow
- **`script.groovy`** – contains the actual functions the pipeline calls

## Pipeline Stages

| Stage | What it does |
|---|---|
| init | Loads `script.groovy` |
| build | Builds the application |
| test | Runs tests, only if `executeTests` is checked |
| deploy | Asks which environment to deploy to, then deploys |

## Requirements to Run This Pipeline

- Jenkins with the required tools installed for your build
- No external credentials needed for this branch (no Docker Hub push here)

## Tech Used

- Jenkins
- Groovy
