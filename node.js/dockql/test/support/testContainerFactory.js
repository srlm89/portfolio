require('bluebird').longStackTraces();

const dockerFactory = require('../../src/index');

module.exports = {
  createContainer
};

function createContainer() {
  const { container } = dockerFactory();
  return container;
}
