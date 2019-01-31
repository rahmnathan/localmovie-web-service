import React from 'react';

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
    if(media.movie !== null && media.movie.image === 'noImage'){
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
        let title = '';
        let year = 0;
        let rating = '';

        if(this.props.media.movie !== null){
            title = this.props.media.movie.title;
            year = this.props.media.movie.releaseYear;
            rating = this.props.media.movie.imdbRating;
        }

        return (
            <div style={movieStyle} onClick={this.selectMedia}>
                <img src={buildPosterUri(this.props.media)} alt='noPicture.gif' style={posterStyle}/>
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