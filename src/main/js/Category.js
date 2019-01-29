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
        this.selectCategory = this.selectCategory.bind(this);
    }

    selectCategory(e) {
        this.props.selectCategory(e.target.value);
    }

    render() {
        return (
            <div style={categoryStyle}>
                <p style={categoryStyle}>Category: </p>
                <select onChange={this.selectCategory} >
                    <option value='Movies'>Movies</option>
                    <option value='Series'>Series</option>
                </select>
            </div>
        );
    }
}