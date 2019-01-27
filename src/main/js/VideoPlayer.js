import React from 'react';
import { Player } from 'video-react';

const videoBaseUri = 'localmovie/v2/media/stream.mp4?path=';

const buildVideoPath = function (media) {
    return videoBaseUri + encodeURI(media.path);
};

const posterBasePath = '/localmovie/v2/media/poster?path=';

export const buildPosterUri = function (media) {
    if(media.movie.image === 'noImage'){
        return 'noPicture.gif';
    } else {
        return posterBasePath + encodeURI(media.path);
    }
};

export class VideoPlayer extends React.Component {
    render() {
        let component = null;

        if(this.props.media !== null){
            component = <Player playsInline poster={buildPosterUri(this.props.media)} src={buildVideoPath(this.props.media)}/>;
        }

        return (component);
    }
}
