import React from 'react';
import { Media } from './Media';

const mediaStyle = {
    margin: 10,
    display: 'inline-block'
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
            <div style={mediaStyle}>
                <Media  key={media.path} media={media} selectMedia={this.selectMedia}/>
            </div>
        );
        return (
            <div>
                {mediaList}
            </div>
        )
    }
}