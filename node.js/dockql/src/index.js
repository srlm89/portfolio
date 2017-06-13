const dependable = require('dependable');
const path       = require('path');

module.exports = function index() {
  const container = dependable.container();
  const entries   = ['docker'];
  entries.forEach((entry) => container.load(path.join(__dirname, entry)));
  return {
    postgresContainer: container.get('postgresContainer'),
    mysqlContainer:    container.get('mysqlContainer'),
    container
  };
};
