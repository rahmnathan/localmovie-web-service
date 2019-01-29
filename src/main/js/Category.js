import React from 'react';

const categoryStyle = {
    align: 'center',
    marginTop: 10,
    color: 'white',
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
                Category:
                <select onChange={this.selectCategory} >
                    <option value='Movies'>Movies</option>
                    <option value='Series'>Series</option>
                </select>
            </div>
        );
    }
}