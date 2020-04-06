const path = require('path');
const fs = require('fs');
const { getHash, getHashSync, getHashReadable } = require('./hashCalculator');

const filePath = path.join(__dirname, '../resources/file.512.mb');

// SYNC

console.log('[Sync] MD5 hash is', getHashSync(filePath, 'md5'));
console.log('[Sync] SHA1 hash is', getHashSync(filePath, 'sha1'));


// ASYNC

getHash(filePath, 'md5')
  .then((value)=> console.log('[Async] MD5 hash is', value));
getHash(filePath, 'sha1')
  .then((value)=> console.log('[Async] SHA1 hash is', value));


// ASYNC Chaining streams

const metadata = {};
const readStream = fs.createReadStream(filePath);
const sha1Stream = getHashReadable(readStream, 'sha1', metadata);

sha1Stream.pipe(
  getHashReadable(sha1Stream, 'md5', metadata)
)
.pipe(
  fs.createWriteStream('/dev/null')
)
.on('finish', () => {
  console.log('[Chained]', JSON.stringify(metadata));
})
