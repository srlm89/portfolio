const _        = require('lodash');
const Bluebird = require('bluebird');

module.exports = function (dockerContainer) {
  function MySQLContainer(options) {
    const name     = _.get(options, 'container');
    const image    = _.get(options, 'image');
    const hostPort = _.get(options, 'hostPort');
    dockerContainer.call(this, name, image, {
      PortBindings: {
        '3306/tcp': [{
          HostPort: hostPort.toString()
        }]
      },
      Env: ['MYSQL_ROOT_PASSWORD=root']
    });
  }

  MySQLContainer.prototype = Object.create(dockerContainer.prototype);

  /**
   * @returns {Promise}
   */
  MySQLContainer.prototype.waitReady = function () {
    return this.exec([
      'sh',
      '-c',
      'until mysqladmin ping -h 127.0.0.1 --silent; do echo "Waiting for mysql readiness" && sleep 2; done'
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

  return MySQLContainer;
};
