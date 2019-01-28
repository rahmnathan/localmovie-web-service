import React from 'react';
import { Player } from 'video-react';
import { buildPosterUri } from "./Media";

const videoBaseUri = 'localmovie/v2/media/stream.mp4?path=';

const buildVideoPath = function (media) {
    return videoBaseUri + encodeURI(media.path);
};

export const viewingVideos = function (path) {
    return (path.includes("Movies") && path.split("/").length === 2) || path.split("/").length === 4;
};

export class VideoPlayer extends React.Component {
    render() {
        let component = null;

        if(this.props.media !== null){
            if(viewingVideos(this.props.media.path)) {
                component = <Player poster={buildPosterUri(this.props.media)}
                                    src={buildVideoPath(this.props.media)}/>;
            }
        }

        return (component);
    }
}
