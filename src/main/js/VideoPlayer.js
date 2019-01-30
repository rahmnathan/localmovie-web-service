import React from 'react';
import { Player } from 'video-react';
import { buildPosterUri } from "./Media";

const videoBaseUri = 'localmovie/v2/media/stream.mp4?path=';

const buildVideoPath = function (media) {
    return videoBaseUri + encodeURIComponent(media.path);
};

export const viewingVideos = function (path) {
    return (path.includes("Movies") && path.split("/").length === 2) || path.split("/").length === 4;
};

const videoPlayerStyle = {
    position: 'absolute',
    height: '90%',
    width: '90%',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)'
};

const buttonStyle = {
    color: 'gray',
    backgroundColor: 'black',
    borderColor: 'black'
};

export class VideoPlayer extends React.Component {
    render() {
        let component = null;

        if (this.props.media !== null) {
            if (viewingVideos(this.props.media.path)) {
                component = (
                    <div style={videoPlayerStyle}>
                        <Player poster={buildPosterUri(this.props.media)}
                                src={buildVideoPath(this.props.media)}/>
                        <button style={buttonStyle} onClick={this.props.stopVideo}>Exit Video</button>
                    </div>
                );
            }
        }

        return (component);
    }
}