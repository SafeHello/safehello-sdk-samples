[Go to the Wiki for documentation](https://github.com/SafeHello/safehello-sdk-samples/wiki)

## Publish Docker Vapor Server

Everytime you need to release a new docker image for **getting-started-server**  you must
* Create a release branch with the following name: `release/VERSION`
  * `VERSION` will be the tag used to publish the image on [Safehellos's DockerHub](https://hub.docker.com/repository/docker/safehello/getting-started-server)
* Push the new release branch to the repo
* The new branch will spark a [Buildkite Job](https://buildkite.com/safehello/safehello-sdk-samples), where you should push the button `Build and Publish` if you want to publish a docker image based on the relese code.

Notes:
* DockerHub's credentials are stored on **Management** AWS Account with the name `/sh-mgmt/dockerhub/`. Go there if you need to update or change connection values.