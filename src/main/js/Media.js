import React from 'react';
import { LazyLoadImage } from 'react-lazy-load-image-component';

const movieStyle = {
    borderStyle: 'solid',
    borderColor: '#2b2b2b',
    backgroundColor: 'rgb(21, 21, 30)',
    width: 150,
    padding: 5,
    height: 295,
    display: 'inline-block',
    margin: 10,
    verticalAlign: 'top'
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
    textAlign: 'center',
    maxHeight: 40,
    overflow: 'hidden'
};

const posterStyle = {
    height: 200,
    width: 125
};

const hoveredMovieStyle = {
    borderStyle: 'solid',
    borderColor: '#2b2b2b',
    backgroundColor: 'rgb(21, 21, 30)',
    width: 210,
    padding: 5,
    height: 360,
    display: 'inline-block',
    margin: -20,
    marginBottom: -25,
    verticalAlign: 'top',
    zIndex: 5
};

const hoveredTitleStyle = {
    fontWeight: 'bold',
    color: 'white',
    fontSize: 16,
    wordWrap: 'normal',
    margin: 2,
    textAlign: 'center',
    maxHeight: 60,
    overflow: 'hidden'
};

const hoveredPosterStyle = {
    height: 250,
    width: 175
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
        this.setState({ hovered: false });
        this.selectMedia = this.selectMedia.bind(this);
        this.handleHover = this.handleHover.bind(this);
        this.removeHover = this.removeHover.bind(this);
    }

    selectMedia() {
        this.props.selectMedia(this.props.media);
    }

    buildMedia() {
        let media = this.props.media;
        let movie = media.movie;

        let title = media.fileName.substr(0, media.fileName.length - 4);
        if (movie.title !== null) {
            title = movie.title;
        }

        let year = 0;
        if (movie.releaseYear !== null) {
            year = movie.releaseYear;
        }

        let rating = '';
        if (movie.imdbRating !== null) {
            rating = movie.imdbRating;
        }

        if (this.state !== null &&this.state.hovered) {
            return (
                <div style={hoveredMovieStyle} onClick={this.selectMedia} onMouseEnter={this.handleHover} onMouseLeave={this.removeHover}>
                    <div>
                        <LazyLoadImage src={buildPosterUri(media)} alt={title} style={hoveredPosterStyle} scrollPosition={this.props.scrollPosition}/>
                        <p style={hoveredTitleStyle}>{title}</p>
                        <p style={textStyle}>Year: {year}</p>
                        <p style={textStyle}>IMDB: {rating}</p>
                    </div>
                </div>
            )
        }

        return (
            <div style={movieStyle} onClick={this.selectMedia} onMouseEnter={this.handleHover} onMouseLeave={this.removeHover}>
                <div>
                    <LazyLoadImage src={buildPosterUri(media)} alt={title} style={posterStyle} scrollPosition={this.props.scrollPosition}/>
                    <p style={titleStyle}>{title}</p>
                    <p style={textStyle}>Year: {year}</p>
                    <p style={textStyle}>IMDB: {rating}</p>
                </div>
            </div>
        )
    }

    render() {
        return this.buildMedia();
    }

    handleHover() {
        this.setState( {hovered: true});
    }

    removeHover() {
        this.setState( {hovered: false});
    }
}