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

const layoutProps = {
    textAlign: 'center'
};

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            media: [],
            originalMedia: [],
            currentMedia: null,
            genre: 'all',
            searchText: '',
            currentPath: 'Movies'
        };

        this.selectMedia = this.selectMedia.bind(this);
        this.filterMedia = this.filterMedia.bind(this);
        this.selectCategory = this.selectCategory.bind(this);
        this.stopVideo = this.stopVideo.bind(this);
        this.selectGenre = this.selectGenre.bind(this);
    }

    selectMedia(media) {
        this.setState({currentMedia: media, currentPath: media.path});
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(!viewingVideos(this.state.currentPath)){
            if(this.state.currentPath !== prevState.currentPath){
                this.loadMedia();
                this.setState({searchText: '', genre: 'all'})
            } else if(this.state.genre !== prevState.genre || this.state.searchText !== prevState.searchText) {
                let resultMedia = this.state.originalMedia;

                let currentGenre = this.state.genre;
                if(currentGenre !== null && currentGenre !== 'all'){
                    resultMedia = resultMedia.filter(function (media) {
                        if(media.movie === null || media.movie.genre === null){
                            return false;
                        }

                        return media.movie.genre.toLowerCase().includes(currentGenre);
                    });
                }

                let currentSearchText = this.state.searchText;
                if(currentSearchText !== null && currentSearchText !== ''){
                    resultMedia = this.state.originalMedia.filter(function (media) {
                        return media.movie.title.toLowerCase().includes(currentSearchText);
                    });
                }

                this.setState({media: resultMedia})
            }
        }
    }

    selectCategory(category) {
        if(category !== null){
            this.setState({currentPath: category})
        }
    }

    selectGenre(genre){
        this.setState({genre: genre})
    }

    stopVideo() {
        this.setState({currentMedia: null})
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
        this.setState({searchText: searchText})
    }

    render() {
        return (
            <div style={layoutProps}>
                <ControlBar selectGenre={this.selectGenre} filterMedia={this.filterMedia} selectCategory={this.selectCategory}/>
                <MediaList media={this.state.media} selectMedia={this.selectMedia}/>
                <VideoPlayer stopVideo={this.stopVideo} media={this.state.currentMedia}/>
            </div>
        )
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);
