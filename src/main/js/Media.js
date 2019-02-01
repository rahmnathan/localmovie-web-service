import React from 'react';
import { LazyLoadImage } from 'react-lazy-load-image-component';

const movieStyle = {
    borderStyle: 'solid',
    borderColor: '#2b2b2b',
    backgroundColor: 'rgb(21, 21, 30)',
    maxWidth: 150,
    padding: 5,
    height: 295,
    display: 'inline-block',
    margin: 10
};

const textStyle = {
    color: 'white',
    fontSize: 14,
    wordWrap: 'normal',
    margin: 2,
    textAlign: 'center'
};

const titleStyle = {
    fontWeight: 'bold',
    color: 'white',
    fontSize: 16,
    wordWrap: 'normal',
    margin: 2,
    textAlign: 'center'
};

const posterStyle = {
    height: 200,
    width: 125
};

const posterBasePath = '/localmovie/v2/media/poster?path=';

export const buildPosterUri = function (media) {
    if(media.movie.image === null || media.movie.image === 'noImage'){
        return 'noPicture.gif';
    } else {
        return posterBasePath + encodeURIComponent(media.path);
    }
};

export class Media extends React.Component {
    constructor(props) {
        super(props);
        this.selectMedia = this.selectMedia.bind(this);
    }

    selectMedia() {
        this.props.selectMedia(this.props.media);
    }

    buildMedia(){
        let media = this.props.media;
        let movie = media.movie;

        let title = media.fileName.substr(0, media.fileName.length - 4);
        if(movie.title !== null){
            title = movie.title;
        }

        let year = 0;
        if(movie.releaseYear !== null){
            year = movie.releaseYear;
        }

        let rating = '';
        if(movie.imdbRating !== null){
            rating = movie.imdbRating;
        }

        return (
            <div style={movieStyle} onClick={this.selectMedia}>
                <LazyLoadImage src={buildPosterUri(media)} alt={title} style={posterStyle} scrollPosition={this.props.scrollPosition}/>
                <p style={titleStyle}>{title}</p>
                <p style={textStyle}>Year: {year}</p>
                <p style={textStyle}>IMDB: {rating}</p>
            </div>
        )
    }

    render() {
        return this.buildMedia();
    }
}