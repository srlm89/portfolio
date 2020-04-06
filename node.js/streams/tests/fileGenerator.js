const crypto = require('crypto');
const path = require('path');
const fs = require('fs');

const filePath = path.join(__dirname, '../resources/file.512.mb');

const writeStream = fs.createWriteStream(filePath);
const totalBytes = Math.round(512 * 1024 * 1024);
let pendingBytes = totalBytes;

while (pendingBytes > 0) {
  const byteChunkSize = 1024 * 1024;
  const chunkSize = Math.min(pendingBytes, byteChunkSize);
  const binaryData = crypto.randomBytes(chunkSize);
  writeStream.write(binaryData, (err) => {
    if (err) {
      throw new Error('Unexpected error writing to output', err);
    }
  });
  pendingBytes = pendingBytes - chunkSize;
}

console.log('Created file', filePath);
writeStream.end();
