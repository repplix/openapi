# CI/CD process 

This README provides a guideline how to build a CI/CD pipeline using GitHub actions. 
As a result a new docker image is created that can be used with kubernetes or docker. 

In the following we assume a docker-swarm setup which is a typical starting point for clustering your container. 

## Create new release and docker image

Within this template we use the maven-jib plugin to create and upload a new image. To simplify this process 
and to combine it with some other great features of GitHubs CI/CD possibilities the image should be build on GitHub. 

So, if you want to create and docker image just go to GitHub actions and start action `New Release`

### GitHub Actions

*   [mavenBuild.yml](.github/workflows/mavenBuild.yml):
    *   Builds the project after each push
    *   Can be started manually from GitHub web page if required

*   [newRelease.yml](.github/workflows/newRelease.yml):
    *   Manually create a new release using maven via GitHub web page
    *   Can only be run via GitHub web page

*   [autoMerge.yml](.github/workflows/autoMerge.yml):
    *   Automatic merge of dependency updates with new patch or minor versions of dependencies. 
    *   See https://github.com/ridedott/merge-me-action

*   [dependabot.yml](.github/dependabot.yml):
    *   Check of new dependencies
  
### Docker-Stacks

*   [developerStack.yml](deploy/developerStack.yml)
    *   Includes all required dependencies to run the application during development on your local machine

*   [docker-compose.yml](deploy/docker-compose.yml)
    *   Stack to run the application as stack in your production environment

### Deploy Stack
In order to deploy the stack, you can use following command
```shell
docker stack deploy --compose-file ./deploy/docker-compose.yml jexxatemplate
```
