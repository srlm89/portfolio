describe('parse-number', () => {
  let parseNumber;

  beforeEach(function () {
    parseNumber = this.loadClass({ className: 'parse-number.js' }).parseNumber;
  });

  describe('basic valid parse result', () => {
    function doTest({ number, result }) {
      describe(number, () => {
        it('should return expected result', () =>
          parseNumber(number).should.deep.equal({
            value:  result,
            source: number,
            start:  0,
            end:    number.length - 1
          })
        );
      });
    }

    describe('integer number (plain)', () => {
      [
        { number: '1', result: 1 },
        { number: '12', result: 12 },
        { number: '123', result: 123 },
        { number: '1234', result: 1234 },
        { number: '12345', result: 12345 },
        { number: '123456', result: 123456 },
        { number: '1234567', result: 1234567 },
        { number: '12345678', result: 12345678 },
        { number: '123456789', result: 123456789 }
      ].forEach(doTest);
    });

    describe('integer number (comma)', () => {
      [
        { number: '1,234', result: 1234 },
        { number: '12,345', result: 12345 },
        { number: '123,456', result: 123456 },
        { number: '1,234,567', result: 1234567 },
        { number: '12,345,678', result: 12345678 },
        { number: '123,456,789', result: 123456789 }
      ].forEach(doTest);
    });

    describe('integer number (spaces)', () => {
      [
        { number: '1 234', result: 1234 },
        { number: '12 345', result: 12345 },
        { number: '123 456', result: 123456 },
        { number: '1 234 567', result: 1234567 },
        { number: '12 345 678', result: 12345678 },
        { number: '123 456 789', result: 123456789 }
      ].forEach(doTest);
    });

    describe('integer number (dot)', () => {
      [
        // { number: '1.234', result: 1234 },
        // { number: '12.345', result: 12345 },
        // { number: '123.456', result: 123456 },
        { number: '1.234.567', result: 1234567 },
        { number: '12.345.678', result: 12345678 },
        { number: '123.456.789', result: 123456789 }
      ].forEach(doTest);
    });

    describe('decimal number (american notation)', () => {
      [
        { number: '.1', result: 0.1 },
        { number: '.12', result: 0.12 },
        { number: '.123', result: 0.123 },
        { number: '.1234', result: 0.1234 },
        { number: '.12345', result: 0.12345 },
        { number: '.123456', result: 0.123456 },
        { number: '.1234567', result: 0.1234567 },
        { number: '.12345678', result: 0.12345678 },
        { number: '.123456789', result: 0.123456789 },
        { number: '1.2', result: 1.2 },
        { number: '12.3', result: 12.3 },
        { number: '123.4', result: 123.4 },
        { number: '1234.5', result: 1234.5 },
        { number: '12345.6', result: 12345.6 },
        { number: '123456.7', result: 123456.7 },
        { number: '1234567.8', result: 1234567.8 },
        { number: '12345678.9', result: 12345678.9 },
        { number: '1.23', result: 1.23 },
        { number: '12.34', result: 12.34 },
        { number: '123.45', result: 123.45 },
        { number: '1234.56', result: 1234.56 },
        { number: '12345.67', result: 12345.67 },
        { number: '123456.78', result: 123456.78 },
        { number: '1234567.89', result: 1234567.89 },
        { number: '1.234', result: 1.234 },
        { number: '12.345', result: 12.345 },
        { number: '123.456', result: 123.456 },
        { number: '1234.567', result: 1234.567 },
        { number: '12345.678', result: 12345.678 },
        { number: '123456.789', result: 123456.789 },
        { number: '1.2345', result: 1.2345 },
        { number: '12.3456', result: 12.3456 },
        { number: '123.4567', result: 123.4567 },
        { number: '1234.5678', result: 1234.5678 },
        { number: '12345.6789', result: 12345.6789 },
        { number: '1.23456', result: 1.23456 },
        { number: '12.34567', result: 12.34567 },
        { number: '123.45678', result: 123.45678 },
        { number: '1234.56789', result: 1234.56789 },
        { number: '1.234567', result: 1.234567 },
        { number: '12.345678', result: 12.345678 },
        { number: '123.456789', result: 123.456789 },
        { number: '1.2345678', result: 1.2345678 },
        { number: '12.3456789', result: 12.3456789 },
        { number: '1.23456789', result: 1.23456789 }
      ].forEach(doTest);

      describe('with spaces', () => {
        [
          { number: '.1 234', result: 0.1234 },
          { number: '.12 345', result: 0.12345 },
          { number: '.123 456', result: 0.123456 },
          { number: '.1 234 567', result: 0.1234567 },
          { number: '.12 345 678', result: 0.12345678 },
          { number: '.123 456 789', result: 0.123456789 },
          { number: '1.2 345', result: 1.2345 },
          { number: '12.3 456', result: 12.3456 },
          { number: '123.4 567', result: 123.4567 },
          { number: '1234.5 678', result: 1234.5678 },
          { number: '12345.6 789', result: 12345.6789 },
          { number: '1.23 456', result: 1.23456 },
          { number: '12.34 567', result: 12.34567 },
          { number: '123.45 678', result: 123.45678 },
          { number: '1234.56 789', result: 1234.56789 },
          { number: '1.234 567', result: 1.234567 },
          { number: '12.345 678', result: 12.345678 },
          { number: '123.456 789', result: 123.456789 },
          { number: '1.2 345 678', result: 1.2345678 },
          { number: '12.3 456 789', result: 12.3456789 },
          { number: '1.23 456 789', result: 1.23456789 }
        ].forEach(doTest);
      });
    });


    describe('decimal number (spanish notation)', () => {
      [
        { number: '0,1', result: 0.1 },
        { number: '0,12', result: 0.12 },
        // { number: '0,123', result: 0.123 },
        { number: '0,1234', result: 0.1234 },
        { number: '0,12345', result: 0.12345 },
        { number: '0,123456', result: 0.123456 },
        { number: '0,1234567', result: 0.1234567 },
        { number: '0,12345678', result: 0.12345678 },
        { number: '0,123456789', result: 0.123456789 },
        { number: '1,2', result: 1.2 },
        { number: '12,3', result: 12.3 },
        { number: '123,4', result: 123.4 },
        { number: '1234,5', result: 1234.5 },
        { number: '12345,6', result: 12345.6 },
        { number: '123456,7', result: 123456.7 },
        { number: '1234567,8', result: 1234567.8 },
        { number: '12345678,9', result: 12345678.9 },
        { number: '1,23', result: 1.23 },
        { number: '12,34', result: 12.34 },
        { number: '123,45', result: 123.45 },
        { number: '1234,56', result: 1234.56 },
        { number: '12345,67', result: 12345.67 },
        { number: '123456,78', result: 123456.78 },
        { number: '1234567,89', result: 1234567.89 },
        // { number: '1,234', result: 1.234 },
        // { number: '12,345', result: 12.345 },
        // { number: '123,456', result: 123.456 },
        { number: '1234,567', result: 1234.567 },
        { number: '12345,678', result: 12345.678 },
        { number: '123456,789', result: 123456.789 },
        { number: '1,2345', result: 1.2345 },
        { number: '12,3456', result: 12.3456 },
        { number: '123,4567', result: 123.4567 },
        { number: '1234,5678', result: 1234.5678 },
        { number: '12345,6789', result: 12345.6789 },
        { number: '1,23456', result: 1.23456 },
        { number: '12,34567', result: 12.34567 },
        { number: '123,45678', result: 123.45678 },
        { number: '1234,56789', result: 1234.56789 },
        { number: '1,234567', result: 1.234567 },
        { number: '12,345678', result: 12.345678 },
        { number: '123,456789', result: 123.456789 },
        { number: '1,2345678', result: 1.2345678 },
        { number: '12,3456789', result: 12.3456789 },
        { number: '1,23456789', result: 1.23456789 }
      ].forEach(doTest);

      describe('with spaces', () => {
        [
          { number: '0,1 234', result: 0.1234 },
          { number: '0,12 345', result: 0.12345 },
          { number: '0,123 456', result: 0.123456 },
          { number: '0,1 234 567', result: 0.1234567 },
          { number: '0,12 345 678', result: 0.12345678 },
          { number: '1,2 345', result: 1.2345 },
          { number: '12,3 456', result: 12.3456 },
          { number: '123,4 567', result: 123.4567 },
          { number: '1234,5 678', result: 1234.5678 },
          { number: '12345,6 789', result: 12345.6789 },
          { number: '1,23 456', result: 1.23456 },
          { number: '12,34 567', result: 12.34567 },
          { number: '123,45 678', result: 123.45678 },
          { number: '1234,56 789', result: 1234.56789 },
          { number: '1,234 567', result: 1.234567 },
          { number: '12,345 678', result: 12.345678 },
          { number: '123,456 789', result: 123.456789 },
          { number: '1,2 345 678', result: 1.2345678 },
          { number: '12,3 456 789', result: 12.3456789 },
          { number: '1,23 456 789', result: 1.23456789 }
        ].forEach(doTest);
      });
    });

    describe('decimal number (spaces and dot)', () => {
      [
        { number: '1 234.5', result: 1234.5 },
        { number: '12 345.6', result: 12345.6 },
        { number: '123 456.7', result: 123456.7 },
        { number: '1 234 567.8', result: 1234567.8 },
        { number: '12 345 678.9', result: 12345678.9 },
        { number: '1 234.56', result: 1234.56 },
        { number: '12 345.67', result: 12345.67 },
        { number: '123 456.78', result: 123456.78 },
        { number: '1 234 567.89', result: 1234567.89 },
        { number: '1 234.567', result: 1234.567 },
        { number: '12 345.678', result: 12345.678 },
        { number: '123 456.789', result: 123456.789 },
        { number: '1 234.5678', result: 1234.5678 },
        { number: '12 345.6789', result: 12345.6789 },
        { number: '1 234.56789', result: 1234.56789 }
      ].forEach(doTest);

      describe('with spaces', () => {
        [
          { number: '1 234.5 678', result: 1234.5678 },
          { number: '12 345.6 789', result: 12345.6789 },
          { number: '1 234.56 789', result: 1234.56789 }
        ].forEach(doTest);
      });
    });


    describe('decimal number (dot and comma)', () => {
      [
        { number: '1.234,5', result: 1234.5 },
        { number: '12.345,6', result: 12345.6 },
        { number: '123.456,7', result: 123456.7 },
        { number: '1.234.567,8', result: 1234567.8 },
        { number: '12.345.678,9', result: 12345678.9 },
        { number: '1.234,56', result: 1234.56 },
        { number: '12.345,67', result: 12345.67 },
        { number: '123.456,78', result: 123456.78 },
        { number: '1.234.567,89', result: 1234567.89 },
        { number: '1.234,567', result: 1234.567 },
        { number: '12.345,678', result: 12345.678 },
        { number: '123.456,789', result: 123456.789 },
        { number: '1.234,5678', result: 1234.5678 },
        { number: '12.345,6789', result: 12345.6789 },
        { number: '1.234,56789', result: 1234.56789 }
      ].forEach(doTest);

      describe('with spaces', () => {
        [
          { number: '1.234,5 678', result: 1234.5678 },
          { number: '12.345,6 789', result: 12345.6789 },
          { number: '1.234,56 789', result: 1234.56789 }
        ].forEach(doTest);
      });
    });


    describe('decimal number (spaces and comma)', () => {
      [
        { number: '1 234,5', result: 1234.5 },
        { number: '12 345,6', result: 12345.6 },
        { number: '123 456,7', result: 123456.7 },
        { number: '1 234 567,8', result: 1234567.8 },
        { number: '12 345 678,9', result: 12345678.9 },
        { number: '1 234,56', result: 1234.56 },
        { number: '12 345,67', result: 12345.67 },
        { number: '123 456,78', result: 123456.78 },
        { number: '1 234 567,89', result: 1234567.89 },
        { number: '1 234,567', result: 1234.567 },
        { number: '12 345,678', result: 12345.678 },
        { number: '123 456,789', result: 123456.789 },
        { number: '1 234,5678', result: 1234.5678 },
        { number: '12 345,6789', result: 12345.6789 },
        { number: '1 234,56789', result: 1234.56789 }
      ].forEach(doTest);

      describe('with spaces', () => {
        [
          { number: '1 234,5 678', result: 1234.5678 },
          { number: '12 345,6 789', result: 12345.6789 },
          { number: '1 234,56 789', result: 1234.56789 }
        ].forEach(doTest);
      });
    });
  });

  describe('non-basic valid parse result', () => {
    function doTest({ text, value, source, start, end }) {
      describe(text, () => {
        it('should return expected result', () =>
          parseNumber(text).should.deep.equal({ value, source, start, end })
        );
      });
    }

    [
      { text: '123 4567', value: 123, source: '123', start: 0, end: 2 },
      { text: '127 feet', value: 127, source: '127', start: 0, end: 2 },
      { text: '127feet', value: 127, source: '127', start: 0, end: 2 },
      { text: '33 km.', value: 33, source: '33', start: 0, end: 1 },
      { text: '33. 123', value: 33, source: '33', start: 0, end: 1 },
      { text: '33, 123', value: 33, source: '33', start: 0, end: 1 },
      { text: '1 233. 123', value: 1233, source: '1 233', start: 0, end: 4 },
      { text: '1 233, 123', value: 1233, source: '1 233', start: 0, end: 4 },
      { text: '3.3 The', value: 3.3, source: '3.3', start: 0, end: 2 }
    ].forEach(doTest);
  });

  describe('invalid parse result', () => {
    let expect;

    beforeEach(function () {
      expect = this.expect;
    });

    function doTest({ input }) {
      describe(input, () => {
        it('should be null', () =>
          expect(parseNumber(input)).to.be.null
        );
      });
    }

    [
      { input: '' },
      { input: 'vmbiprpvrz' },
      { input: 'bd8aceb02d' },
      { input: '.' },
      { input: '. ' }
    ].forEach(doTest);
  });
});
