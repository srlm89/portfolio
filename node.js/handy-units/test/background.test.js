const _ = require('lodash');

describe('background', () => {
  let context;
  let onMessageListener;

  beforeEach(function () {
    context = {};
    _.set(context, 'parseUnit', this.sinon.stub());
    _.set(context, 'browser.contextMenus.remove', this.sinon.stub());
    _.set(context, 'browser.contextMenus.create', this.sinon.stub());
    _.set(context, 'browser.runtime.onMessage.addListener', (func) => onMessageListener = func);
    this.loadClass({ context, className: 'background.js' });
  });

  describe('function onMessageListener(selection)', () => {
    describe('empty conversions', () => {
      beforeEach(() => {
        context.parseUnit.withArgs('selection').returns([]);
      });

      beforeEach(() => {
        onMessageListener('selection');
      });

      it('contextMenus.remove() not called', () =>
        context.browser.contextMenus.remove.should.have.not.been.called
      );

      it('contextMenus.create() not called', () =>
        context.browser.contextMenus.remove.should.have.not.been.called
      );
    });

    describe('call #1 (1 conversion)', () => {
      beforeEach(() => {
        context.parseUnit.withArgs('selection#1').returns(['conversion#1.A']);
      });

      beforeEach(() => {
        context.browser.contextMenus.create.onCall(0).returns('1.1');
        context.browser.contextMenus.create.onCall(1).returns('1.2');
      });

      beforeEach(() => {
        onMessageListener('selection#1');
      });

      it('contextMenus.remove() callCount', () =>
        context.browser.contextMenus.remove.should.have.callCount(0)
      );

      it('contextMenus.create() callCount', () =>
        context.browser.contextMenus.create.should.have.callCount(2)
      );

      it('contestMenus.create() args', () => {
        context.browser.contextMenus.create.getCall(0).should.have.been.calledWithExactly({
          title:    "Handy units: '%s'",
          contexts: ['selection'],
          parentId: undefined
        });
        context.browser.contextMenus.create.getCall(1).should.have.been.calledWithExactly({
          title:    'conversion#1.A',
          contexts: ['selection'],
          parentId: '1.1'
        });
      });

      describe('call #2 (2 conversion)', () => {
        beforeEach(() => {
          context.browser.contextMenus.create.reset();
          context.browser.contextMenus.remove.reset();
        });

        beforeEach(() => {
          context.parseUnit.withArgs('selection#2').returns(['conversion#2.A', 'conversion#2.B']);
        });

        beforeEach(() => {
          context.browser.contextMenus.create.onCall(0).returns('2.1');
          context.browser.contextMenus.create.onCall(1).returns('2.2');
          context.browser.contextMenus.create.onCall(2).returns('2.3');
        });

        beforeEach(() => {
          onMessageListener('selection#2');
        });

        it('contextMenus.remove() callCount', () =>
          context.browser.contextMenus.remove.should.have.callCount(2)
        );

        it('contextMenus.remove() args', () => {
          context.browser.contextMenus.remove.getCall(0).should.have.been.calledWithExactly('1.2');
          context.browser.contextMenus.remove.getCall(1).should.have.been.calledWithExactly('1.1');
        });

        it('contextMenus.create() callCount', () =>
          context.browser.contextMenus.create.should.have.callCount(3)
        );

        it('contestMenus.create() args', () => {
          context.browser.contextMenus.create.getCall(0).should.have.been.calledWithExactly({
            title:    "Handy units: '%s'",
            contexts: ['selection'],
            parentId: undefined
          });
          context.browser.contextMenus.create.getCall(1).should.have.been.calledWithExactly({
            title:    'conversion#2.A',
            contexts: ['selection'],
            parentId: '2.1'
          });
          context.browser.contextMenus.create.getCall(2).should.have.been.calledWithExactly({
            title:    'conversion#2.B',
            contexts: ['selection'],
            parentId: '2.1'
          });
        });

        describe('call #3 (0 conversions)', () => {
          beforeEach(() => {
            context.browser.contextMenus.create.reset();
            context.browser.contextMenus.remove.reset();
          });

          beforeEach(() => {
            context.parseUnit.withArgs('selection#3').returns([]);
          });

          beforeEach(() => {
            onMessageListener('selection#3');
          });

          it('contextMenus.remove() callCount', () =>
            context.browser.contextMenus.remove.should.have.callCount(3)
          );

          it('contextMenus.remove() args', () => {
            context.browser.contextMenus.remove.getCall(0).should.have.been.calledWithExactly('2.3');
            context.browser.contextMenus.remove.getCall(1).should.have.been.calledWithExactly('2.2');
            context.browser.contextMenus.remove.getCall(2).should.have.been.calledWithExactly('2.1');
          });

          it('contextMenus.create() callCount', () =>
            context.browser.contextMenus.create.should.have.callCount(0)
          );
        });
      });
    });
  });
});
