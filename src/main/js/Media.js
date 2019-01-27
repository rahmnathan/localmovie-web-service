import React from 'react';

const movieStyle = {
    borderStyle: 'solid',
    borderColor: '#2b2b2b',
    backgroundColor: 'rgb(21, 21, 30)',
    maxWidth: 150,
    padding: 5,
    height: 295,
    display: 'inline-table',
    justifyItems: 'center'
};

const textStyle = {
    color: 'white',
    fontSize: 15,
    fontWeight: 'bold',
    wordWrap: 'normal',
    margin: 2
};

const posterStyle = {
    height: 200,
    width: 125
};

const posterUrl = '/localmovie/v2/media/poster?path=';

const buildPosterUri = function (media) {
    if(media.movie.image === 'noImage'){
        return 'noPicture.gif';
    } else {
        return posterUrl + encodeURI(media.path);
    }
};

export class Media extends React.Component {
    render() {
        return (
            <div style={movieStyle}>
                <img src={buildPosterUri(this.props.media)} alt='noPicture.gif' style={posterStyle}/>
                <p style={textStyle}>{this.props.media.movie.title}</p>
                <p style={textStyle}>{this.props.media.movie.releaseYear}</p>
                <p style={textStyle}>{this.props.media.movie.imdbRating}</p>
            </div>
        )
    }
}