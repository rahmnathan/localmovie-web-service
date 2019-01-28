import React from 'react';

const movieStyle = {
    fontSize: 14,
    wordWrap: 'normal',
    margin: 2,
    textAlign: 'center',
    float: 'left'
};

export class Category extends React.Component {
    constructor(props) {
        super(props);
        this.selectCategory = this.selectCategory.bind(this);
    }

    selectCategory(e) {
        this.props.selectCategory(e.target.value);
    }

    render() {
        return (
            <select style={movieStyle} onChange={this.selectCategory} >
                <option value='Movies'>Movies</option>
                <option value='Series'>Series</option>
            </select>
        );
    }
}