import React from 'react';
import ReactDOM from 'react-dom';
import {VideoPlayer, viewingVideos} from "./VideoPlayer";
import { Router, navigate } from "@reach/router"
import {MainPage} from './MainPage';

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
        this.state = {
            media: [],
            originalMedia: new Map(),
            currentMedia: null,
            genre: 'all',
            searchText: '',
            sort: 'title',
            currentPath: 'Movies'
        };

        this.selectMedia = this.selectMedia.bind(this);
        this.filterMedia = this.filterMedia.bind(this);
        this.selectCategory = this.selectCategory.bind(this);
        this.stopVideo = this.stopVideo.bind(this);
        this.selectGenre = this.selectGenre.bind(this);
        this.selectSort = this.selectSort.bind(this);
    }

    selectMedia(media) {
        let newState = {currentMedia: media, currentPath: media.path};
        if(viewingVideos(media.path)){
            newState = {currentMedia: media};
            this.setState(newState);
            navigate('/video-player');
        }

        this.setState(newState);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(!viewingVideos(this.state.currentPath)) {
            if(this.state.currentPath !== prevState.currentPath){
                this.loadMedia();
                this.setState({searchText: '', genre: 'all'})
            } else if(this.state.genre !== prevState.genre ||
                this.state.searchText !== prevState.searchText ||
                this.state.sort !== prevState.sort) {

                let resultMedia = [];
                if(this.state.originalMedia.has(this.state.currentPath)){
                    resultMedia = this.state.originalMedia.get(this.state.currentPath);
                }

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
                    resultMedia = resultMedia.filter(function (media) {
                        if(media.movie === null || media.movie.title === null){
                            return false;
                        }

                        return media.movie.title.toLowerCase().includes(currentSearchText);
                    });
                }

                let currentSort = this.state.sort;
                if(currentSort !== null) {
                    resultMedia = resultMedia.sort(function (media1, media2) {
                        if(media1 === null || media2 === null || media1.movie === null || media2.movie === null){
                            return true;
                        }

                        switch (currentSort) {
                            case 'title':
                                return media1.movie.title > media2.movie.title;
                            case 'year':
                                return media1.movie.releaseYear < media2.movie.releaseYear;
                            case 'added':
                                return media1.created < media2.created;
                            case 'rating':
                                return media1.movie.imdbRating < media2.movie.imdbRating;
                            default:
                                return true;
                        }
                    });
                }

                this.setState({media: resultMedia})
            }
        }
    }

    selectSort(sort){
        if(sort !== null){
            this.setState({sort: sort})
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
        this.setState({currentMedia: null, media: this.state.originalMedia.get(this.state.currentPath)});
        navigate('/');
    }

    loadMedia() {
        if(this.state.originalMedia.has(this.state.currentPath)){
            this.setState({ media: this.state.originalMedia.get(this.state.currentPath)});
        }

        fetch('/localmovie/v2/media', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(buildMovieRequest(this.state.currentPath))
        }).then(response => response.json())
            .then(data => {
                let originalMedia = this.state.originalMedia;
                originalMedia.set(this.state.currentPath, data);
                this.setState({media: data, originalMedia: originalMedia})
            });
    }

    componentDidMount() {
        this.loadMedia();
    }

    filterMedia(searchText){
        this.setState({searchText: searchText})
    }

    render(){
        return(
            <Router>
                <MainPage path='/' selectSort={this.selectSort} selectGenre={this.selectGenre} filterMedia={this.filterMedia} selectCategory={this.selectCategory} media={this.state.media} selectMedia={this.selectMedia} />
                <VideoPlayer path='/video-player' stopVideo={this.stopVideo} media={this.state.currentMedia}/>
            </Router>
        );
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);
