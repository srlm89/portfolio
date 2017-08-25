describe('generate-conversions', () => {
  let generateConversions;
  let units;

  beforeEach(function () {
    const $context = ['units.js', 'parse-number.js', 'parse-unit.js', 'generate-conversions.js'].reduce(
      (context, className) => this.loadClass({ context, className }), {}
    );
    generateConversions = $context.generateConversions;
    units = $context.units;
  });

  describe('invalid parse result', () => {
    let expect;

    beforeEach(function () {
      expect = this.expect;
    });

    it('should return null', () =>
      expect(generateConversions('zzZZ')).to.be.null
    );
  });

  function doTest({ text, conversions }) {
    it(text, () => {
      const result = generateConversions(text);

      result.should.have.property('source', text);
      result.conversions.should.have.keys(conversions);

      Object.keys(result.conversions).forEach((system) => {
        const actual = result.conversions[system];
        const expected = conversions[system];

        actual.should.have.lengthOf(expected.length);
        actual.forEach((array, i) => {
          array.should.have.lengthOf(expected[i].length);
          array.forEach(({ unit, value }, j) => {
            unit.id.should.equal(expected[i][j].unit);
            value.should.be.closeTo(expected[i][j].value, 1e-5);
          });
        });
      });
    });
  }

  describe('valid parse result', () => {
    describe('temperature', () => {
      describe('fahrenheit', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'celsius', value: 32 }]
            ]
          },
          text: '89.6 degree fahrenheit'
        });
      });

      describe('celsius', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'fahrenheit', value: 89.6 }]
            ]
          },
          text: '32 degree celsius'
        });
      });
    });

    describe('time', () => {
      describe('week', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'day', value: 7 }],
              [{ unit: 'hour', value: 168 }],
              [{ unit: 'minute', value: 10080 }],
              [{ unit: 'second', value: 604800 }]
            ]
          },
          text: '1 week'
        });
      });

      describe('day', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'week', value: 8 / 7 }],
              [
                { unit: 'week', value: 1 },
                { unit: 'day', value: 1 }
              ],
              [{ unit: 'hour', value: 192 }],
              [{ unit: 'minute', value: 11520 }],
              [{ unit: 'second', value: 691200 }]
            ]
          },
          text: '8 days'
        });
      });

      describe('hour', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'week', value: 1000 / 168 }],
              [
                { unit: 'week', value: 5 },
                { unit: 'day', value: 6 },
                { unit: 'hour', value: 16 }
              ],
              [{ unit: 'day', value: 1000 / 24 }],
              [
                { unit: 'day', value: 41 },
                { unit: 'hour', value: 16 }
              ],
              [{ unit: 'minute', value: 60 * 1e3 }],
              [{ unit: 'second', value: 3600 * 1e3 }]
            ]
          },
          text: '1000 hours'
        });
      });

      describe('minute', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'week', value: 1000 / 10080 }],
              [{ unit: 'day', value: 1000 / 1440 }],
              [{ unit: 'hour', value: 1000 / 60 }],
              [
                { unit: 'hour', value: 16 },
                { unit: 'minute', value: 40 }
              ],
              [{ unit: 'second', value: 1000 * 60 }]
            ]
          },
          text: '1000 minutes'
        });
      });

      describe('second', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: 'week', value: 604802 / 604800 }],
              [
                { unit: 'week', value: 1 },
                { unit: 'day', value: 0 },
                { unit: 'hour', value: 0 },
                { unit: 'minute', value: 0 },
                { unit: 'second', value: 2 }
              ],
              [{ unit: 'day', value: 604802 / 86400 }],
              [
                { unit: 'day', value: 7 },
                { unit: 'hour', value: 0 },
                { unit: 'minute', value: 0 },
                { unit: 'second', value: 2 }
              ],
              [{ unit: 'hour', value: 604802 / 3600 }],
              [
                { unit: 'hour', value: 168 },
                { unit: 'minute', value: 0 },
                { unit: 'second', value: 2 }
              ],
              [{ unit: 'minute', value: 604802 / 60 }],
              [
                { unit: 'minute', value: 10080 },
                { unit: 'second', value: 2 }
              ]
            ]
          },
          text: '604802 seconds'
        });
      });
    });

    describe('area', () => {
      describe('square_kilometer', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'hectare', value: 4 * 1e2 }],
              [{ unit: 'square_meter', value: 4 * 1e6 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 4 / (1.609344 * 1.609344) }],
              [{ unit: 'acre', value: 4 * (640 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_yard', value: 4 * (3097600 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_foot', value: 4 * (27878400 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_inch', value: 4 * (4014489600 / (1.609344 * 1.609344)) }]
            ]
          },
          text: '4 km2'
        });
      });

      describe('hectare', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: 1 }],
              [{ unit: 'square_meter', value: 1e6 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 1 / (1.609344 * 1.609344) }],
              [{ unit: 'acre', value: 1 * (640 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_yard', value: 1 * (3097600 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_foot', value: 1 * (27878400 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_inch', value: 1 * (4014489600 / (1.609344 * 1.609344)) }]
            ]
          },
          text: '100 ha'
        });
      });

      describe('square_meter', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: 4 }],
              [{ unit: 'hectare', value: 4 * 1e2 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 4 / (1.609344 * 1.609344) }],
              [{ unit: 'acre', value: 4 * (640 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_yard', value: 4 * (3097600 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_foot', value: 4 * (27878400 / (1.609344 * 1.609344)) }],
              [{ unit: 'square_inch', value: 4 * (4014489600 / (1.609344 * 1.609344)) }]
            ]
          },
          text: '4000000 m2'
        });
      });

      describe('square_mile', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: (1.609344 * 1.609344) }],
              [{ unit: 'hectare', value: (1.609344 * 1.609344) * 1e2 }],
              [{ unit: 'square_meter', value: (1.609344 * 1.609344) * 1e6 }]
            ],
            'american/british': [
              [{ unit: 'acre', value: 640 }],
              [{ unit: 'square_yard', value: 3097600 }],
              [{ unit: 'square_foot', value: 27878400 }],
              [{ unit: 'square_inch', value: 4014489600 }]
            ]
          },
          text: '1 mi2'
        });
      });

      describe('acre', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: (1.609344 * 1.609344) }],
              [{ unit: 'hectare', value: (1.609344 * 1.609344) * 1e2 }],
              [{ unit: 'square_meter', value: (1.609344 * 1.609344) * 1e6 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 1 }],
              [{ unit: 'square_yard', value: 3097600 }],
              [{ unit: 'square_foot', value: 27878400 }],
              [{ unit: 'square_inch', value: 4014489600 }]
            ]
          },
          text: '640 acre'
        });
      });

      describe('square_yard', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: (1.609344 * 1.609344) }],
              [{ unit: 'hectare', value: (1.609344 * 1.609344) * 1e2 }],
              [{ unit: 'square_meter', value: (1.609344 * 1.609344) * 1e6 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 1 }],
              [{ unit: 'acre', value: 640 }],
              [{ unit: 'square_foot', value: 27878400 }],
              [{ unit: 'square_inch', value: 4014489600 }]
            ]
          },
          text: '3097600 yd2'
        });
      });

      describe('square_foot', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: (1.609344 * 1.609344) }],
              [{ unit: 'hectare', value: (1.609344 * 1.609344) * 1e2 }],
              [{ unit: 'square_meter', value: (1.609344 * 1.609344) * 1e6 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 1 }],
              [{ unit: 'acre', value: 640 }],
              [{ unit: 'square_yard', value: 3097600 }],
              [{ unit: 'square_inch', value: 4014489600 }]
            ]
          },
          text: '27878400 ft2'
        });
      });

      describe('square_inch', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'square_kilometer', value: (1.609344 * 1.609344) }],
              [{ unit: 'hectare', value: (1.609344 * 1.609344) * 1e2 }],
              [{ unit: 'square_meter', value: (1.609344 * 1.609344) * 1e6 }]
            ],
            'american/british': [
              [{ unit: 'square_mile', value: 1 }],
              [{ unit: 'acre', value: 640 }],
              [{ unit: 'square_yard', value: 3097600 }],
              [{ unit: 'square_foot', value: 27878400 }]
            ]
          },
          text: '4014489600 in2'
        });
      });
    });

    describe('speed', () => {
      describe('kilometer_per_hour', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'meter_per_second', value: 1 / 3.6 }]
            ],
            'american/british': [
              [{ unit: 'mile_per_hour', value: 1 / 1.609344 }],
              [{ unit: 'foot_per_second', value: 5280 / 5793.6384 }]
            ],
            unknown: [
              [{ unit: 'knot', value: 1 / 1.852 }]
            ]
          },
          text: '1 km/h'
        });
      });

      describe('meter_per_second', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'kilometer_per_hour', value: 3.6 }]
            ],
            'american/british': [
              [{ unit: 'mile_per_hour', value: 3.6 / 1.609344 }],
              [{ unit: 'foot_per_second', value: 3.6 * (5280 / 5793.6384) }]
            ],
            unknown: [
              [{ unit: 'knot', value: 3.6 / 1.852 }]
            ]
          },
          text: '1 m/s'
        });
      });

      describe('mile_per_hour', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'kilometer_per_hour', value: 1.609344 }],
              [{ unit: 'meter_per_second', value: 1.609344 / 3.6 }]
            ],
            'american/british': [
              [{ unit: 'foot_per_second', value: 1.609344 * (5280 / 5793.6384) }]
            ],
            unknown: [
              [{ unit: 'knot', value: 1.609344 / 1.852 }]
            ]
          },
          text: '1 mph'
        });
      });

      describe('foot_per_second', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'kilometer_per_hour', value: 1.609344 * (3600 / 5280) }],
              [{ unit: 'meter_per_second', value: 0.3048 }]
            ],
            'american/british': [
              [{ unit: 'mile_per_hour', value: 3600 / 5280 }]
            ],
            unknown: [
              [{ unit: 'knot', value: (3600 * 1.609344) / (5280 * 1.852) }]
            ]
          },
          text: '1 ft/s'
        });
      });

      describe('knot', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'kilometer_per_hour', value: 1.852 }],
              [{ unit: 'meter_per_second', value: 1.852 / 3.6 }]
            ],
            'american/british': [
              [{ unit: 'mile_per_hour', value: 1.852 / 1.609344 }],
              [{ unit: 'foot_per_second', value: 1.852 * (5280 / 5793.6384) }]
            ]
          },
          text: '1 knot'
        });
      });
    });

    describe('volume', () => {
      describe('liter', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'milliliter', value: 1e3 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 1 / 3.785411784 }],
              [{ unit: 'us_quart', value: 4 / 3.785411784 }],
              [
                { unit: 'us_quart', value: 1 },
                { unit: 'us_pint', value: 0 },
                { unit: 'us_fluid_ounce', value: 32 * ((4 / 3.785411784) - 1) }
              ],
              [{ unit: 'us_pint', value: 8 / 3.785411784 }],
              [
                { unit: 'us_pint', value: 2 },
                { unit: 'us_fluid_ounce', value: 32 * ((4 / 3.785411784) - 1) }
              ],
              [{ unit: 'us_fluid_ounce', value: 128 / 3.785411784 }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 1 / 4.54609 }],
              [{ unit: 'uk_quart', value: 4 / 4.54609 }],
              [{ unit: 'uk_pint', value: 8 / 4.54609 }],
              [
                { unit: 'uk_pint', value: 1 },
                { unit: 'uk_fluid_ounce', value: 20 * ((8 / 4.54609) - 1) }
              ],
              [{ unit: 'uk_fluid_ounce', value: 160 / 4.54609 }]
            ]
          },
          text: '1 l'
        });
      });

      describe('milliliter', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 1 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 1 / 3.785411784 }],
              [{ unit: 'us_quart', value: 4 / 3.785411784 }],
              [
                { unit: 'us_quart', value: 1 },
                { unit: 'us_pint', value: 0 },
                { unit: 'us_fluid_ounce', value: 32 * ((4 / 3.785411784) - 1) }
              ],
              [{ unit: 'us_pint', value: 8 / 3.785411784 }],
              [
                { unit: 'us_pint', value: 2 },
                { unit: 'us_fluid_ounce', value: 32 * ((4 / 3.785411784) - 1) }
              ],
              [{ unit: 'us_fluid_ounce', value: 128 / 3.785411784 }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 1 / 4.54609 }],
              [{ unit: 'uk_quart', value: 4 / 4.54609 }],
              [{ unit: 'uk_pint', value: 8 / 4.54609 }],
              [
                { unit: 'uk_pint', value: 1 },
                { unit: 'uk_fluid_ounce', value: 20 * ((8 / 4.54609) - 1) }
              ],
              [{ unit: 'uk_fluid_ounce', value: 160 / 4.54609 }]
            ]
          },
          text: '1000 ml'
        });
      });

      describe('us_gallon', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 3.785411784 }],
              [{ unit: 'milliliter', value: 3785.411784 }]
            ],
            american: [
              [{ unit: 'us_quart', value: 4 }],
              [{ unit: 'us_pint', value: 8 }],
              [{ unit: 'us_fluid_ounce', value: 128 }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 3.785411784 / 4.54609 }],
              [{ unit: 'uk_quart', value: 4 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_quart', value: 3 },
                { unit: 'uk_pint', value: 0 },
                { unit: 'uk_fluid_ounce', value: 40 * ((4 * (3.785411784 / 4.54609)) - 3) }
              ],
              [{ unit: 'uk_pint', value: 8 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_pint', value: 6 },
                { unit: 'uk_fluid_ounce', value: ((8 * (3.785411784 / 4.54609)) - 6) * 20 }
              ],
              [{ unit: 'uk_fluid_ounce', value: 160 * (3.785411784 / 4.54609) }]
            ]
          },
          text: '1 gal'
        });
      });

      describe('us_quart', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 3.785411784 }],
              [{ unit: 'milliliter', value: 3785.411784 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 1 }],
              [{ unit: 'us_pint', value: 8 }],
              [{ unit: 'us_fluid_ounce', value: 128 }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 3.785411784 / 4.54609 }],
              [{ unit: 'uk_quart', value: 4 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_quart', value: 3 },
                { unit: 'uk_pint', value: 0 },
                { unit: 'uk_fluid_ounce', value: 40 * ((4 * (3.785411784 / 4.54609)) - 3) }
              ],
              [{ unit: 'uk_pint', value: 8 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_pint', value: 6 },
                { unit: 'uk_fluid_ounce', value: ((8 * (3.785411784 / 4.54609)) - 6) * 20 }
              ],
              [{ unit: 'uk_fluid_ounce', value: 160 * (3.785411784 / 4.54609) }]
            ]
          },
          text: '4 quarts'
        });
      });

      describe('us_pint', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 3.785411784 }],
              [{ unit: 'milliliter', value: 3785.411784 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 1 }],
              [{ unit: 'us_quart', value: 4 }],
              [{ unit: 'us_fluid_ounce', value: 128 }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 3.785411784 / 4.54609 }],
              [{ unit: 'uk_quart', value: 4 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_quart', value: 3 },
                { unit: 'uk_pint', value: 0 },
                { unit: 'uk_fluid_ounce', value: 40 * ((4 * (3.785411784 / 4.54609)) - 3) }
              ],
              [{ unit: 'uk_pint', value: 8 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_pint', value: 6 },
                { unit: 'uk_fluid_ounce', value: ((8 * (3.785411784 / 4.54609)) - 6) * 20 }
              ],
              [{ unit: 'uk_fluid_ounce', value: 160 * (3.785411784 / 4.54609) }]
            ]
          },
          text: '8 pints'
        });
      });

      describe('us_fluid_ounce', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 3.785411784 }],
              [{ unit: 'milliliter', value: 3785.411784 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 1 }],
              [{ unit: 'us_quart', value: 4 }],
              [{ unit: 'us_pint', value: 8 }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 3.785411784 / 4.54609 }],
              [{ unit: 'uk_quart', value: 4 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_quart', value: 3 },
                { unit: 'uk_pint', value: 0 },
                { unit: 'uk_fluid_ounce', value: 40 * ((4 * (3.785411784 / 4.54609)) - 3) }
              ],
              [{ unit: 'uk_pint', value: 8 * (3.785411784 / 4.54609) }],
              [
                { unit: 'uk_pint', value: 6 },
                { unit: 'uk_fluid_ounce', value: ((8 * (3.785411784 / 4.54609)) - 6) * 20 }
              ],
              [{ unit: 'uk_fluid_ounce', value: 160 * (3.785411784 / 4.54609) }]
            ]
          },
          text: '128 fl oz'
        });
      });

      describe('uk_gallon', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 4.54609 }],
              [{ unit: 'milliliter', value: 4546.09 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 4.54609 / 3.785411784 }],
              [
                { unit: 'us_gallon', value: 1 },
                { unit: 'us_quart', value: 0 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_quart', value: 4 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_quart', value: 4 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_pint', value: 8 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_pint', value: 9 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_fluid_ounce', value: 128 * (4.54609 / 3.785411784) }]
            ],
            british: [
              [{ unit: 'uk_quart', value: 4.0 }],
              [{ unit: 'uk_pint', value: 8.0 }],
              [{ unit: 'uk_fluid_ounce', value: 160.0 }]
            ]
          },
          text: '1 imp gal'
        });
      });

      describe('uk_quart', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 4.54609 }],
              [{ unit: 'milliliter', value: 4546.09 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 4.54609 / 3.785411784 }],
              [
                { unit: 'us_gallon', value: 1 },
                { unit: 'us_quart', value: 0 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_quart', value: 4 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_quart', value: 4 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_pint', value: 8 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_pint', value: 9 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_fluid_ounce', value: 128 * (4.54609 / 3.785411784) }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 1.0 }],
              [{ unit: 'uk_pint', value: 8.0 }],
              [{ unit: 'uk_fluid_ounce', value: 160.0 }]
            ]
          },
          text: '4 imp quart'
        });
      });

      describe('uk_pint', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 4.54609 }],
              [{ unit: 'milliliter', value: 4546.09 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 4.54609 / 3.785411784 }],
              [
                { unit: 'us_gallon', value: 1 },
                { unit: 'us_quart', value: 0 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_quart', value: 4 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_quart', value: 4 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_pint', value: 8 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_pint', value: 9 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_fluid_ounce', value: 128 * (4.54609 / 3.785411784) }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 1.0 }],
              [{ unit: 'uk_quart', value: 4.0 }],
              [{ unit: 'uk_fluid_ounce', value: 160.0 }]
            ]
          },
          text: '8 imp pint'
        });
      });

      describe('uk_fluid_ounce', () => {
        doTest({
          conversions: {
            metric: [
              [{ unit: 'liter', value: 4.54609 }],
              [{ unit: 'milliliter', value: 4546.09 }]
            ],
            american: [
              [{ unit: 'us_gallon', value: 4.54609 / 3.785411784 }],
              [
                { unit: 'us_gallon', value: 1 },
                { unit: 'us_quart', value: 0 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_quart', value: 4 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_quart', value: 4 },
                { unit: 'us_pint', value: 1 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_pint', value: 8 * (4.54609 / 3.785411784) }],
              [
                { unit: 'us_pint', value: 9 },
                { unit: 'us_fluid_ounce', value: 16 * ((8 * (4.54609 / 3.785411784)) - 9) }
              ],
              [{ unit: 'us_fluid_ounce', value: 128 * (4.54609 / 3.785411784) }]
            ],
            british: [
              [{ unit: 'uk_gallon', value: 1.0 }],
              [{ unit: 'uk_quart', value: 4.0 }],
              [{ unit: 'uk_pint', value: 8.0 }]
            ]
          },
          text: '160 imp fl oz'
        });
      });
    });

    describe('mass', () => {
      describe('us_ton', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('uk_ton', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('ton', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('kilogram', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('gram', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('milligram', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('stone', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('pound', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('ounce', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });
    });

    describe('length', () => {
      describe('kilometer', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('meter', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('centimeter', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('millimeter', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('mile', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('yard', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('foot', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });

      describe('inch', () => {
        doTest({
          conversions: {
            unknown: [
              [{ unit: '', value: 1.0 }]
            ]
          },
          text: ''
        });
      });
    });
  });
});
