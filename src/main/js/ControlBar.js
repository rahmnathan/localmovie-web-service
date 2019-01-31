import React from 'react';
import { SearchBox } from "./SearchBox";
import { Category } from "./Category";
import { Genre } from "./Genre";
import { Sort } from './Sort';

const controlBarStyle = {
    textAlign: 'center',
    position: 'fixed',
    left: '50%',
    marginLeft: '-37.5%',
    width: '75%',
    background: 'rgb(21, 21, 30)',
    marginTop: 0
};

export class ControlBar extends React.Component {

    render(){
        return (
            <div style={controlBarStyle}>
                <SearchBox filterMedia={this.props.filterMedia}/>
                <Category selectCategory={this.props.selectCategory}/>
                <Genre selectGenre={this.props.selectGenre}/>
                <Sort selectSort={this.props.selectSort}/>
            </div>
        );
    }
}