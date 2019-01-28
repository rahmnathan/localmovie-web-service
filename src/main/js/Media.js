import React from 'react';

const movieStyle = {
    borderStyle: 'solid',
    borderColor: '#2b2b2b',
    backgroundColor: 'rgb(21, 21, 30)',
    maxWidth: 150,
    padding: 5,
    height: 295,
    display: 'inline-table'
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
    if(media.movie.image === 'noImage'){
        return 'noPicture.gif';
    } else {
        return posterBasePath + encodeURI(media.path);
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

    render() {
        return (
            <div style={movieStyle} onClick={this.selectMedia}>
                <img src={buildPosterUri(this.props.media)} alt='noPicture.gif' style={posterStyle}/>
                <p style={titleStyle}>{this.props.media.movie.title}</p>
                <p style={textStyle}>Year: {this.props.media.movie.releaseYear}</p>
                <p style={textStyle}>IMDB: {this.props.media.movie.imdbRating}</p>
            </div>
        )
    }
}