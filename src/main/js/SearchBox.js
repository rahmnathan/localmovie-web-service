import React from 'react';

const textStyle = {
    color: 'white',
    fontSize: 14,
    wordWrap: 'normal',
    margin: 2,
    textAlign: 'center',
    float: 'left'
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
            <p style={textStyle}>Search by title:<input onChange={this.filterMedia} type='text' /></p>
        );
    }
}