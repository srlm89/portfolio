const fs              = require('fs');
const path            = require('path');
const chai            = require('chai');
const chaiAsPromised  = require('chai-as-promised');
const sinon           = require('sinon');
const sinonChai       = require('sinon-chai');
const sinonAsPromised = require('sinon-as-promised');

sinonAsPromised(Promise);
chai.use(chaiAsPromised);
chai.use(sinonChai);

global.should = chai.should();

beforeEach(function setupTest() {
  this.expect      = chai.expect;
  this.sinon       = sinon.sandbox.create();
  this.sinonAssert = sinon.assert;
});

beforeEach(function classLoader() {
  this.loadClass = ({ className, context = {} }) => {
    const classPath = path.join(__dirname, '..', 'src', className);
    const classCode = fs.readFileSync(classPath).toString();
    // eslint-disable-next-line no-eval
    (function () { eval(classCode); }).call(context);
    return context;
  };
});

afterEach(function closeTest() {
  this.sinon.restore();
});
