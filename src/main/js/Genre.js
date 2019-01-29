import React from 'react';

const genreStyle = {
    align: 'center',
    marginTop: 10,
    color: 'white',
};

export class Genre extends React.Component {
    constructor(props) {
        super(props);
        this.selectGenre = this.selectGenre.bind(this);
    }

    selectGenre(e) {
        this.props.selectGenre(e.target.value);
    }

    render() {
        return (
            <div style={genreStyle}>
                Genre:
                <select onChange={this.selectGenre} >
                    <option value='all'>All</option>
                    <option value='action'>Action</option>
                    <option value='comedy'>Comedy</option>
                    <option value='fantasy'>Fantasy</option>
                    <option value='horror'>Horror</option>
                    <option value='sci-fi'>Sci-Fi</option>
                    <option value='thriller'>Thriller</option>
                </select>
            </div>
        );
    }
}