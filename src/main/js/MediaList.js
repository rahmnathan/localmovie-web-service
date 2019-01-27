import React from 'react';
import { Media } from './Media';

export class MediaList extends React.Component {
    render() {
        const mediaList = this.props.media.map(media =>
            <Media key={media.path} media={media}/>
        );
        return (
            <div>
                {mediaList}
            </div>
        )
    }
}