import React from 'react';
import { SearchBox } from "./SearchBox";
import {Category} from "./Category";

const textStyle = {
    marginLeft: 'auto',
    marginRight: 'auto',
    display: 'block'
};

export class ControlBar extends React.Component {

    render(){
        return (
            <div style={textStyle}>
                <SearchBox filterMedia={this.props.filterMedia}/>
                <Category selectCategory={this.props.selectCategory}/>
            </div>
        );
    }
}