import React from 'react';
import { Media } from './Media';

export class MediaList extends React.Component {
    constructor(props) {
        super(props);
        this.updateSelectedMedia = this.updateSelectedMedia.bind(this);
    }

    updateSelectedMedia(media) {
        this.props.playVideo(media);
    }

    render() {
        const mediaList = this.props.media.map(media =>
            <Media key={media.path} media={media} selectMedia={this.updateSelectedMedia}/>
        );
        return (
            <div>
                {mediaList}
            </div>
        )
    }
}