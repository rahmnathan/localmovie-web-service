'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import { MediaList } from './MediaList';

const movieRequest = {
    path: 'Movies',
    client: "WEBAPP",
    page: 0,
    resultsPerPage: 1000
};

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {media: []};
    }

    componentDidMount() {
        fetch('/localmovie/v2/media', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(movieRequest)
        }).then(response => response.json())
            .then(data => {
                this.setState({media: data})
            });
    }

    render() {
        return (
            <MediaList media={this.state.media}/>
        )
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);

