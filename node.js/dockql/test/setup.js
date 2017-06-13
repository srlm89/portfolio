const _               = require('lodash');
const bluebird        = require('bluebird');
const chai            = require('chai');
const chaiLike        = require('chai-like');
const chaiAsPromised  = require('chai-as-promised');
const sinon           = require('sinon');
const sinonChai       = require('sinon-chai');
const sinonAsPromised = require('sinon-as-promised');

sinonAsPromised(bluebird);
global.should = chai.should();
global.expect = chai.expect;

chai.use(chaiAsPromised);
chai.use(sinonChai);
chai.use(chaiLike);

beforeEach(function setupTest() {
  this.sinon       = sinon.sandbox.create();
  this.sinonAssert = sinon.assert;
});

afterEach(function closeTest() {
  this.sinon.restore();
});

let testStubs;

beforeEach(function setupMyStubs() {
  const $sinon       = this.sinon;
  const $sinonAssert = this.sinonAssert;
  testStubs          = {};
  Object.assign($sinon, {
    mystubs: {
      add(object, objectName, method) {
        _.set(testStubs, `${objectName}.${method}`, $sinon.stub(object, method));
      },
      check(expectedCallArgs) {
        Object.keys(testStubs).forEach((stubName) => {
          Object.keys(testStubs[stubName]).forEach((methodName) => {
            const expectedArgs  = _.get(expectedCallArgs, `${stubName}.${methodName}`);
            if (_.isNil(expectedArgs)) {
              testStubs[stubName][methodName].should.have.callCount(0);
            } else {
              testStubs[stubName][methodName].should.have.callCount(1);
              testStubs[stubName][methodName].should.have.been.calledWithExactly(...expectedArgs);
            }
          });
        });
      },
      inorder(...stubs) {
        $sinonAssert.callOrder(...stubs);
      }
    }
  });
});
