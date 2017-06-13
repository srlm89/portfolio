const Bluebird = require('bluebird');

module.exports = function dockerContainer(dockerApi) {
  function DockerContainer(name, image, options) {
    // eslint-disable-next-line new-cap
    const docker    = new dockerApi();
    this.container = docker.createContainer(name, image, options);
  }

  /**
   * @returns {Promise}
   */
  DockerContainer.prototype.start = function () {
    return this.container.then((c) =>
      c.start().then(() => {
        // eslint-disable-next-line no-console
        console.log('#~ Started container', c.id);
        return this.waitReady();
      })
    );
  };

  /**
   * @returns {Promise}
   */
  DockerContainer.prototype.waitReady = function () {
    return Bluebird.resolve(this);
  };

  /**
   * @param {Array<String>} cmdArray
   * @returns {Promise}
   */
  DockerContainer.prototype.exec = function (cmdArray) {
    return this.container.then((c) =>
      c.exec({ Cmd: cmdArray, AttachStdout: true })
        .then((exec) => exec.start())
        .then((result) => result.output)
    );
  };

  /**
   * @returns {Promise}
   */
  DockerContainer.prototype.stop = function () {
    return this.container.then((c) =>
      c.stop().then(() => {
        // eslint-disable-next-line no-console
        console.log('#~ Stopped container', c.id);
      })
      .catch((err) => {
        if (err.statusCode !== 304) {
          throw err;
        }
      })
    );
  };

  /**
   * @returns {Promise}
   */
  DockerContainer.prototype.destroy = function () {
    return this.stop().then(() =>
      this.container.then((c) =>
        c.remove().then(() => {
          // eslint-disable-next-line no-console
          console.log('#~ Removed container', c.id);
        })
      )
    );
  };

  return DockerContainer;
};
