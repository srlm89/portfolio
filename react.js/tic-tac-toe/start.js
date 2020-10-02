const app = require('./src/server');

const port = 3000;

app.listen(port, (err) => {
  if (err) {
    console.error('Error initializing web server', err);
  } else {
    console.log(`Sever listening on 0.0.0.0:${port}`);
  }
});
