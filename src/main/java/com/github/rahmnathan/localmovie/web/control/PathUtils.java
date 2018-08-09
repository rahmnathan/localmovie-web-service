package com.github.rahmnathan.localmovie.web.control;

class PathUtils {

    private PathUtils(){
        // No need to instantiate this
    }

    static String getTitle(String fileName){
        if (fileName.charAt(fileName.length() - 4) == '.') {
            return fileName.substring(0, fileName.length() - 4);
        }

        return fileName;
    }
}
