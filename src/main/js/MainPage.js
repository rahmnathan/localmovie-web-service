'use strict';

import React from 'react';
import { MediaList } from './MediaList';
import { ControlBar } from './ControlBar';

const layoutProps = {
    textAlign: 'center'
};

export class MainPage extends React.Component {

    render() {
        return (
            <div style={layoutProps}>
                <ControlBar selectSort={this.props.selectSort} selectGenre={this.props.selectGenre} filterMedia={this.props.filterMedia} selectCategory={this.props.selectCategory}/>
                <MediaList media={this.props.media} selectMedia={this.props.selectMedia}/>
            </div>
        )
    }
}
