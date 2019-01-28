import React from 'react';

const searchBoxStyle = {
    color: 'white',
    fontSize: 14,
    margin: 'auto',
    textAlign: 'center'
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
            <p style={searchBoxStyle}>Search<br/><input onChange={this.filterMedia} type='text' /></p>
        );
    }
}