const path = require('path');
const webpack = require('webpack');
module.exports = (env) => {
    return {
        mode: 'development',
        devtool: 'source-map',
        entry: path.resolve(__dirname, 'build/classes/kotlin/main/library-js.js'),
        resolve: {
            "modules": [
                path.resolve(__dirname, '/build/classes/kotlin/js/main'),
                "node_modules"
            ]
        },
        output: {
            filename: "main.js",
            libraryTarget: "umd",
            path: path.resolve(__dirname, 'build/webpack')
        },
        plugins: [
            new webpack.DefinePlugin({
                'MARVEL_PRIVATE_KEY': JSON.stringify(env.MARVEL_PRIVATE_KEY),
                'MARVEL_PUBLIC_KEY': JSON.stringify(env.MARVEL_PUBLIC_KEY)
            })
        ]
    };
}