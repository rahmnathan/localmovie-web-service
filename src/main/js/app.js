'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

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
            <MovieList media={this.state.media}/>
        )
    }
}

class MovieList extends React.Component {
    render() {
        const mediaList = this.props.media.map(media =>
            <Movie key={media.path} media={media}/>
        );
        return (
            <div>
                {mediaList}
            </div>
        )
    }
}

const movieStyle = {
    borderStyle: 'solid',
    borderColor: '#2b2b2b',
    backgroundColor: 'rgb(21, 21, 30)',
    maxWidth: 150,
    padding: 5,
    height: 295,
    display: 'inline-table'
};

const textStyle = {
    color: 'white',
    fontSize: 15,
    fontWeight: 'bold',
    wordWrap: 'normal',
    margin: 2
};

class Movie extends React.Component {
    render() {
        return (
            <div style={movieStyle}>
                <p style={textStyle}>{this.props.media.fileName}</p>
            </div>
        )
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);

