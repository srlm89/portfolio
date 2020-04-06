const fs = require('fs');
const path = require('path');
const stream = require('stream');
const logUtils = require('../utils/logUtils');

const filePath = path.join(__dirname, '../resources/file.512.mb');
const timeout = logUtils.logMemory({ intervalInMs: 25 });

const readableStream = fs.createReadStream(filePath);

const writableStream1 = new stream.Writable({
  write(chunk, encoding, callback) {
    callback();
  }
});


const writableStream2 = new stream.Writable({
  write(chunk, encoding, callback) {
    callback();
  }
});


const writableStream3 = new stream.Writable({
  write(chunk, encoding, callback) {
    setTimeout(callback, 2);
  }
});

logUtils.logPushes('RR', readableStream);
logUtils.logPushes('W1', writableStream1);
logUtils.logPushes('W2', writableStream2);

logUtils.logWrites('W1', writableStream1);
logUtils.logWrites('W2', writableStream2);
logUtils.logWrites('W3', writableStream3);


readableStream.pipe(writableStream1);
readableStream.pipe(writableStream2);
readableStream.pipe(writableStream3);

writableStream3.on('finish', () => {
  timeout.stop();
});
