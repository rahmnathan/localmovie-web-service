import React from 'react';
import { SearchBox } from "./SearchBox";
import { Category } from "./Category";
import { Genre } from "./Genre";
import { Sort } from './Sort';

const controlBarStyle = {
    textAlign: 'center'
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