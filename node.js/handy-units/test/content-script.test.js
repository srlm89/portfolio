const _ = require('lodash');

describe('content-script', () => {
  let context;

  beforeEach(function () {
    context = {};
    _.set(context, 'browser.runtime.sendMessage', this.sinon.stub());
    _.set(context, 'document', {});
    this.loadClass({ context, className: 'content-script.js' });
  });

  describe('function document.onmouseup()', () => {
    describe('when selection has not been set', () => {
      beforeEach(() => {
        context.document.onmouseup();
      });

      it('api.runtime.sendMessage() not called', () =>
        context.browser.runtime.sendMessage.should.have.callCount(0)
      );
    });

    describe('when selection is set to empty string', () => {
      beforeEach(() => {
        context.document.onselectionchange({
          target: {
            getSelection: () => ''
          }
        });
      });

      beforeEach(() => {
        context.document.onmouseup();
      });

      it('api.runtime.sendMessage() not called', () =>
        context.browser.runtime.sendMessage.should.have.callCount(0)
      );
    });

    describe('when selection has been set for the first time', () => {
      beforeEach(() => {
        context.document.onselectionchange({
          target: {
            getSelection: () => 'event.target.getSelection()#1'
          }
        });
      });

      beforeEach(() => {
        context.document.onmouseup();
      });

      it('api.runtime.sendMessage() called with selection', () => {
        context.browser.runtime.sendMessage.should.have.callCount(1);
        context.browser.runtime.sendMessage.should.have.been.calledWithExactly('event.target.getSelection()#1');
      });

      describe('and mouseup happens again', () => {
        beforeEach(() => {
          context.browser.runtime.sendMessage.reset();
        });

        beforeEach(() => {
          context.document.onmouseup();
        });

        it('api.runtime.sendMessage() not called again', () => {
          context.browser.runtime.sendMessage.should.have.callCount(0);
        });
      });

      describe('when selection is set for the second time', () => {
        beforeEach(() => {
          context.browser.runtime.sendMessage.reset();
        });

        beforeEach(() => {
          context.document.onselectionchange({
            target: {
              getSelection: () => 'event.target.getSelection()#2'
            }
          });
        });

        beforeEach(() => {
          context.document.onmouseup();
        });

        it('api.runtime.sendMessage() called with new selection', () => {
          context.browser.runtime.sendMessage.should.have.callCount(1);
          context.browser.runtime.sendMessage.should.have.been.calledWithExactly('event.target.getSelection()#2');
        });
      });
    });
  });
});
