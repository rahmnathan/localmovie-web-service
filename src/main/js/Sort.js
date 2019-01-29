import React from 'react';

const sortStyle = {
    align: 'center',
    marginTop: 10,
    color: 'white',
    marginRight: 10,
    display: 'inline-block'
};

export class Sort extends React.Component {
    constructor(props) {
        super(props);
        this.selectSort = this.selectSort.bind(this);
    }

    selectSort(e) {
        this.props.selectSort(e.target.value);
    }

    render() {
        return (
            <div style={sortStyle}>
                <p style={sortStyle}>Sort: </p>
                <select onChange={this.selectSort} >
                    <option value='title'>Title</option>
                    <option value='year'>Year</option>
                    <option value='rating'>Rating</option>
                    <option value='added'>Date Added</option>
                </select>
            </div>
        );
    }
}