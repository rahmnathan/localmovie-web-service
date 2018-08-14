<h1>LocalMovies</h1>

This project is a video streaming service for my media collection. The project consists of
several components: Android client, web client, and the back-end system. The backend system
is deployed in an on-prem Kubernetes instance. The web service exposes a set of endpoints
through an NGINX ingress that serve up: media metadata, media events, and media streams.



The following diagram is an attempt to show the components and data-flow of the backend
services for this project.

![Imgur](https://i.imgur.com/Ni3fXd7.png)

<h2>localmovie-web-service</h2>

The web-service component contains all the endpoints needed for client interaction 
(other than authentication which is provided by Keycloak). The endpoints include:

1) Retrieving media metadata (includes sorting and pagination).
2) Retrieving media count to support pagination.
3) Retrieving media events.
4) Retrieve media poster.
5) Stream media.