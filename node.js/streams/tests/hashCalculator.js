const crypto = require('crypto');
const fs = require('fs');
const { PassThrough } = require('stream');

function getHashSync(filePath, algorithm) {
  const fileContent = fs.readFileSync(filePath);
  const hash = crypto.createHash(algorithm);
  hash.update(fileContent, 'binary');
  const value = hash.digest('hex');
  return value;
}

function getHash(filePath, algorithm) {
  return new Promise(function(resolve,reject) {
    var fileContent = fs.createReadStream(filePath);
    const hash = crypto.createHash(algorithm);
    hash.setEncoding('hex');
    hash.on('finish', function () {
        const data = hash.read();
        resolve(data);
    })
    fileContent.pipe(hash);
  });
}

/**
 * @returns {Stream}
 */
function getHashReadable(readStream, algorithm, metadata) {
  const passThrough = new PassThrough();

  const hash = crypto.createHash(algorithm);
  hash.setEncoding('hex');
  hash.on('finish', function () {
      const data = hash.read();
      metadata[algorithm] = data;
  });

  readStream.pipe(hash);
  readStream.pipe(passThrough);

  return passThrough;
}

module.exports = {
  getHash,
  getHashReadable,
  getHashSync
};
