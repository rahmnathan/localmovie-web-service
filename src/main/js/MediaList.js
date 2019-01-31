import React from 'react';
import { Media } from './Media';

const mediaListStyle = {
    margin: 10,
    display: 'inline-block',
    width: '80%',
    paddingTop: 90
};

export class MediaList extends React.Component {
    constructor(props) {
        super(props);
        this.selectMedia = this.selectMedia.bind(this);
    }

    selectMedia(media) {
        this.props.selectMedia(media);
    }

    render() {
        const mediaList = this.props.media.map(media =>
            <Media  key={media.path} media={media} selectMedia={this.selectMedia}/>
        );
        return (
            <div style={mediaListStyle}>
                {mediaList}
            </div>
        )
    }
}