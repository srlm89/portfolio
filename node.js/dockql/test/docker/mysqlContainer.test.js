const Bluebird = require('bluebird');

const testContainerFactory = require('../support/testContainerFactory');

const container = testContainerFactory.createContainer();

describe('mysqlContainer', function () {
  this.timeout(60 * 1000);

  let mysqlContainer;

  beforeEach(() => {
    const MySQL    = container.get('mysqlContainer');
    mysqlContainer = new MySQL({
      container: 'mysqlContainer-test',
      image:     'mysql:5.7',
      hostPort:  '50500'
    });
    return mysqlContainer.start();
  });

  afterEach(() =>
    mysqlContainer.destroy()
  );

  describe('function exec()', () => {
    let content;

    beforeEach(() =>
      new Bluebird((resolve) => {
        mysqlContainer.exec([
          'mysql',
          '-uroot',
          '-proot',
          '-e',
          "SELECT md5('mysql-container')"
        ]).then((output) => {
          content = '';
          output.on('data', (data) => content += data);
          output.on('end', () => resolve(content));
        });
      })
    );

    it('runs queries', () => {
      content.should.contain('203a0ab75e517668d77eb4e536f04b8b');
    });
  });
});
