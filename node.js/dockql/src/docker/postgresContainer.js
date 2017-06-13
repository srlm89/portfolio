const _        = require('lodash');
const Bluebird = require('bluebird');

module.exports = function postgresContainer(dockerContainer) {
  function PostgresContainer(options) {
    const name     = _.get(options, 'container');
    const image    = _.get(options, 'image');
    const username = _.get(options, 'username');
    const password = _.get(options, 'password');
    const hostPort = _.get(options, 'hostPort');
    dockerContainer.call(this, name, image, {
      PortBindings: {
        '5432/tcp': [{
          HostPort: hostPort.toString()
        }]
      },
      Env: [`POSTGRES_USER=${username}`, `POSTGRES_PASSWORD=${password}`]
    });
  }

  PostgresContainer.prototype = Object.create(dockerContainer.prototype);

  /**
   * @returns {Promise}
   */
  PostgresContainer.prototype.waitReady = function () {
    return this.exec([
      'sh',
      '-c',
      'until pg_isready; do sleep 1; done'
    ]).then((output) =>
        new Bluebird((resolve) => {
          output.on('data', (data) => {
            // eslint-disable-next-line no-console
            console.log(data.toString('utf-8').trim());
          });
          output.on('end', () => resolve(this));
        })
      );
  };

  return PostgresContainer;
};

