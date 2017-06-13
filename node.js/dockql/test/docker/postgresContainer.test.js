const Bluebird = require('bluebird');

const testContainerFactory = require('../support/testContainerFactory');

const container = testContainerFactory.createContainer();

describe('postgresContainer', function () {
  this.timeout(60 * 1000);

  let postgresContainer;

  beforeEach(() => {
    const Postgres    = container.get('postgresContainer');
    postgresContainer = new Postgres({
      container: 'postgresContainer-test',
      image:     'postgres:9.6',
      username:  'postgres',
      password:  '',
      hostPort:  '50500'
    });
    return postgresContainer.start();
  });

  afterEach(() =>
    postgresContainer.destroy()
  );

  describe('function exec()', () => {
    let content;

    beforeEach(() =>
      new Bluebird((resolve) => {
        postgresContainer.exec([
          'psql',
          '-U',
          'postgres',
          '-c',
          "SELECT md5('postgres-container')"
        ]).then((output) => {
          content = '';
          output.on('data', (data) => content += data);
          output.on('end', () => resolve(content));
        });
      })
    );

    it('runs queries', () => {
      content.should.contain('1fce530eac660ea19f470e6d9d6e12ca');
    });
  });
});
