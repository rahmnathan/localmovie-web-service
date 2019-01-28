'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import { MediaList } from './MediaList';
import { VideoPlayer } from './VideoPlayer';
import { viewingVideos } from "./VideoPlayer";
import { ControlBar } from './ControlBar';

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
        this.state = {media: [], originalMedia: [], currentMedia: null, currentPath: 'Movies'};
        this.selectMedia = this.selectMedia.bind(this);
        this.filterMedia = this.filterMedia.bind(this);
        this.selectCategory = this.selectCategory.bind(this);
    }

    selectMedia(media) {
        this.setState({currentMedia: media, currentPath: media.path});
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(!viewingVideos(this.state.currentPath) && this.state.currentPath !== prevState.currentPath){
            this.loadMedia();
        }

    }

    selectCategory(category) {
        if(category !== null){
            this.setState({currentPath: category})
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
                this.setState({media: data, originalMedia: data})
            });
    }

    componentDidMount() {
        this.loadMedia();
    }

    filterMedia(searchText){
        if(searchText !== null && searchText !== '') {
            let filteredMedia = this.state.originalMedia.filter(function (media) {
                return media.movie.title.toLowerCase().includes(searchText);
            });
            this.setState({media: filteredMedia});
        } else {
            this.setState({media: this.state.originalMedia});
        }
    }

    render() {
        return (
            <div>
                <ControlBar filterMedia={this.filterMedia} selectCategory={this.selectCategory}/>
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

