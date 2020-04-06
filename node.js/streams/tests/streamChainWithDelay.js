const fs = require('fs');
const path = require('path');
const stream = require('stream');
const logUtils = require('../utils/logUtils');

const filePath = path.join(__dirname, '../resources/file.512.mb');
const timeout = logUtils.logMemory({ intervalInMs: 25 });

const readableStream = fs.createReadStream(filePath);

const duplexStream1 = new stream.PassThrough({
  write(chunk, encoding, callback) {
    this.push(chunk, encoding);
    callback();
  }
});

const duplexStream2 = new stream.Transform({
  transform(chunk, encoding, callback) {
    this.push(chunk, encoding);
    callback();
  }
});

const writableStream = new stream.Writable({
  write(chunk, encoding, callback) {
    setTimeout(callback, 2);
  }
});

logUtils.logPushes('RR', readableStream);
logUtils.logPushes('W1', duplexStream1);
logUtils.logPushes('W2', duplexStream2);

logUtils.logWrites('W1', duplexStream1);
logUtils.logWrites('W2', duplexStream2);
logUtils.logWrites('W3', writableStream);

readableStream
  .pipe(duplexStream1)
  .pipe(duplexStream2)
  .pipe(writableStream);

writableStream.on('finish', () => {
  timeout.stop();
});
