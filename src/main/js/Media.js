import React from 'react';

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

export class Media extends React.Component {
    render() {
        return (
            <div style={movieStyle}>
                <p style={textStyle}>{this.props.media.fileName}</p>
            </div>
        )
    }
}