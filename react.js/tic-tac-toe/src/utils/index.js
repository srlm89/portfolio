export function generateBoard(rows, cols) {
  const board = [];
  for (let row = 0; row < rows; row += 1) {
    const boardRow = []
    for (let col = 0; col < cols; col += 1) {
      boardRow.push({ row, col });
    }
    board.push(boardRow);
  }

  return board;
}
