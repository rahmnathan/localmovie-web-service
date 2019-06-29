<h1>LocalMovies</h1>

[![Build Status](https://nathanrahm-jenkins.ddns.net/buildStatus/icon?job=localmovie-web)](https://nathanrahm-jenkins.ddns.net/job/localmovie-web/)

This project is a video streaming service for my media collection. The project consists of
several components: Android client, web client, and the back-end system. The backend system
is deployed in an on-prem Kubernetes instance. The web service exposes a set of endpoints
through an NGINX ingress that serve up: media metadata, media events, and media streams. Helm 
charts for Kubernetes deployment information can be found in the docker repository under the 
kubernetes/localmovies directory.

The following diagram is an attempt to show the components and data-flow of the backend
services for this project.

![Imgur Image](https://imgur.com/4hVN0WZ.png)

<h2>localmovie-web-service</h2>

The web-service component contains all the endpoints needed for client interaction 
(other than authentication which is provided by Keycloak). The endpoints include:

1) Retrieving media metadata (includes sorting and pagination).
2) Retrieving media count to support pagination.
3) Retrieving media events.
4) Retrieve media poster.
5) Stream media.