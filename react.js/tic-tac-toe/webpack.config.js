const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const projectPath = (...paths) => path.join(__dirname, ...paths);

module.exports = {
  entry: [projectPath('src', 'components', 'App', 'App.js')],
  output: {
    path: projectPath('dist'),
    filename: 'bundle.[name].js'
  },
  plugins: [
    new MiniCssExtractPlugin({ filename: '[name].css' })
  ],
  mode: 'development',
  module: {
    rules: [
      {
        test: /[.]js$/,
        include: [projectPath('src')],
        loader: 'babel-loader',
        query: {
          presets: ['@babel/preset-react']
        }
      },
      {
        test: /[.]css$/,
        include: [projectPath('src')],
        use: [MiniCssExtractPlugin.loader, 'css-loader']
      }
    ]
  }
};
