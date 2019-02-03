import React from 'react';

const categoryStyle = {
    align: 'center',
    marginTop: 10,
    color: 'white',
    display: 'inline-block',
    marginRight: 10
};

export class Category extends React.Component {
    constructor(props) {
        super(props);
        this.setPath = this.setPath.bind(this);
    }

    setPath(e) {
        this.props.setPath(e.target.value);
    }

    render() {
        return (
            <div style={categoryStyle}>
                <p style={categoryStyle}>Category: </p>
                <button style={{ marginRight: 5}} onClick={this.setPath} value='Movies'>Movies</button>
                <button onClick={this.setPath} value='Series'>Series</button>
            </div>
        );
    }
}