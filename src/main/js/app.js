'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import { MediaList } from './MediaList';
import { VideoPlayer } from './VideoPlayer';
import { viewingVideos } from "./VideoPlayer";

const buildMovieRequest = function (path) {
    return {
        path: path,
        client: "WEBAPP",
        page: 0,
        resultsPerPage: 1000
    }
};

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {media: [], currentMedia: null, currentPath: 'Series'};
        this.selectMedia = this.selectMedia.bind(this);
    }

    selectMedia(media) {
        this.setState({currentMedia: media, currentPath: media.path});
        if(!viewingVideos(media.path)){
            this.loadMedia();
        }
    }

    loadMedia() {
        fetch('/localmovie/v2/media', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(buildMovieRequest(this.state.currentPath))
        }).then(response => response.json())
            .then(data => {
                this.setState({media: data})
            });
    }

    componentDidMount() {
        this.loadMedia();
    }

    render() {
        return (
            <div>
                <VideoPlayer media={this.state.currentMedia}/>
                <MediaList media={this.state.media} selectMedia={this.selectMedia}/>
            </div>
        )
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);

