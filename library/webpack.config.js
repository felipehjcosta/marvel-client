const path = require('path');
const webpack = require('webpack');
module.exports = (env) => {
    return {
        mode: 'development',
        devtool: 'source-map',
        entry: path.resolve(__dirname, 'build/classes/kotlin/js/main/library.js'),
        resolve: {
            "modules": [
                path.resolve(__dirname, '../build/js')
            ]
        },
        output: {
            filename: "main.js",
            libraryTarget: "umd",
            globalObject: 'this',
            path: path.resolve(__dirname, 'build/webpack')
        }
    };
}