import React from 'react';
import ReactDOM from 'react-dom';
import * as utils from '../../utils';
import styles from './App.css';

class App extends React.Component {
  constructor(props) {
    super(props);

    this.symbols = ['X', 'O'];
    this.state = {
      currentSymbolIndex: 0,
      board: utils.generateBoard(3, 3)
    };
  }

  handleResetClicked() {
    this.setState({ board: utils.generateBoard(3, 3)});
  }

  handleTileClicked(tile) {
    const { currentSymbolIndex, board } = this.state;
    const value = this.symbols[currentSymbolIndex];

    const newBoard = board.map((row) =>
      row.map((item) => {
        if (!(item.row === tile.row && item.col === tile.col)) {
          return item;
        }

        return { ...item, value };
      })
    );

    this.setState({
      currentSymbolIndex: 1 - this.state.currentSymbolIndex,
      board: newBoard
    });
  };

  renderTile({ row, col, value = '' }) {
    const isDisabled = value.length > 0;

    return (
      <button
        disabled={isDisabled}
        className={`tile ${isDisabled ? 'non-clickable' : 'clickable'}`}
        key={`tile-${row}-${col}`}
        onClick={() => this.handleTileClicked({ row, col })}
      >
        {value}
      </button>
    );
  }

  renderRow(row, i) {
    return (
      <span className="row" key={`row-${i}`}>
        {row.map((col, j) => this.renderTile(col, j))}
      </span>
    );
  }

  render() {
    const { board } = this.state;

    return (
      <div className="container">
        <span className="board">
          {board.map((row, i) => this.renderRow(row, i))}
        </span>
        <button
          className="reset"
          onClick={() => this.handleResetClicked()}
        >
        Reset
        </button>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.getElementById('root'));
