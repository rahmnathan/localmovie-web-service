import React from 'react';
import { Player } from 'video-react';
import { buildPosterUri } from "./Media";

const videoBaseUri = 'localmovie/v2/media/stream.mp4?path=';

const backgroundTintStyle = {
    zIndex: -1,
    height: '100%',
    width: '100%',
    position: 'fixed',
    overflow: 'auto',
    top: 0,
    left: 0,
    background: 'rgba(0, 0, 0, 0.7)'
};

const buildVideoPath = function (media) {
    if(media !== null) {
        return videoBaseUri + encodeURIComponent(media.path);
    } else {
        return videoBaseUri;
    }
};

export const viewingVideos = function (path) {
    return (path.includes("Movies") && path.split("/").length === 2) || path.split("/").length === 4;
};

const videoPlayerStyle = {
    position: 'absolute',
    width: '80%',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    textAlign: 'center'
};

const buttonStyle = {
    color: 'gray',
    backgroundColor: 'black',
    borderColor: 'black'
};

export const VideoPlayer = ({ media, stopVideo }) => {
    return (
        <div style={videoPlayerStyle}>
            <Player poster={buildPosterUri(media)}
                    src={buildVideoPath(media)}/>
            <button style={buttonStyle} onClick={stopVideo}>Exit Video</button>
            <div style={backgroundTintStyle}/>;
        </div>
    );
};
