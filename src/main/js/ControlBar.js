import React from 'react';
import { SearchBox } from "./SearchBox";
import {Category} from "./Category";
import { Genre } from "./Genre";

const controlBarStyle = {
    textAlign: 'center'
};

export class ControlBar extends React.Component {

    render(){
        return (
            <div style={controlBarStyle}>
                <SearchBox filterMedia={this.props.filterMedia}/>
                <Genre selectGenre={this.props.selectGenre}/>
                <Category selectCategory={this.props.selectCategory}/>
            </div>
        );
    }
}