const express = require('express');
const app = express();
const path = require('path');

app.use(express.static(path.resolve(__dirname, '..', '..', 'dist')));

app.get('/', (req, res) => {
  const document = path.resolve(__dirname, '..', 'static', 'document.html');
  res.sendFile(document);
});

module.exports = app;
