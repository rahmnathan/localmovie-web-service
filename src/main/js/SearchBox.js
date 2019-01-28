import React from 'react';

const searchBoxStyle = {
    color: 'white',
    fontSize: 14,
    margin: 'auto',
    textAlign: 'center',
    display: 'inline-block'
};

export class SearchBox extends React.Component {
    constructor(props){
        super(props);
        this.filterMedia = this.filterMedia.bind(this);
    }

    filterMedia(e){
        this.props.filterMedia(e.target.value);
    }

    render() {
        return (
            <p style={searchBoxStyle}>Search<input onChange={this.filterMedia} type='text' /></p>
        );
    }
}