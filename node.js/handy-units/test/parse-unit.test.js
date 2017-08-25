describe('parse-unit', () => {
  let parseUnit;
  let units;

  beforeEach(function () {
    const $context = ['units.js', 'parse-number.js', 'parse-unit.js'].reduce(
      (context, className) => this.loadClass({ context, className }), {}
    );
    parseUnit = $context.parseUnit;
    units = $context.units;
  });

  describe('valid parse result', () => {
    function doTest({ text, value, type, unit, source }) {
      describe(text, () => {
        let matching;

        beforeEach(() => {
          matching = parseUnit(text);
        });

        it('should return expected result', () => {
          matching.should.have.property('value', value);
          matching.should.have.property('source', source || text);
          matching.should.have.property('type', type);
          matching.unit.should.deep.equal(units[type][unit]);
        });
      });
    }

    describe('temperature', () => {
      describe('fahrenheit', () => {
        [
          { text: '105°F' },
          { text: '105° F' },
          { text: '105 °F' },
          { text: '105 °f' },
          { text: '105 °Fahrenheit' },
          { text: '105 ° Fahrenheit' },
          { text: '105 fahrenheit' },
          { text: '105 degree fahrenheit' },
          { text: '105 degrees fahrenheit' },
          { text: '105 degree F' },
          { text: '105 degrees F' },
          { text: '105 deg fahrenheit' },
          { text: '105 degs fahrenheit' }
        ].forEach(({ text }) =>
          doTest({ text, value: 105, type: 'temperature', unit: 'fahrenheit' })
        );
      });

      describe('celsius', () => {
        [
          { text: '32°C' },
          { text: '32° C' },
          { text: '32 °C' },
          { text: '32 °Celsius' },
          { text: '32 ° Celsius' },
          { text: '32 °c' },
          { text: '32 celsius' },
          { text: '32 degree celsius' },
          { text: '32 degrees celsius' },
          { text: '32 degree C' },
          { text: '32 degrees C' },
          { text: '32 deg celsius' },
          { text: '32 degs celsius' }
        ].forEach(({ text }) =>
          doTest({ text, value: 32, type: 'temperature', unit: 'celsius' })
        );
      });
    });

    describe('time', () => {
      describe('week', () => {
        [
          { text: '1week' },
          { text: '1 week' },
          { text: '1 weeks' }
        ].forEach(({ text }) =>
          doTest({ text, value: 1, type: 'time', unit: 'week' })
        );
      });

      describe('day', () => {
        [
          { text: '7d' },
          { text: '7 d' },
          { text: '7 day' },
          { text: '7 days' }
        ].forEach(({ text }) =>
          doTest({ text, value: 7, type: 'time', unit: 'day' })
        );
      });

      describe('hour', () => {
        [
          { text: '24h' },
          { text: '24 h' },
          { text: '24 hs' },
          { text: '24 hour' },
          { text: '24 hours' },
          { text: '24 hr' },
          { text: '24 hrs' }
        ].forEach(({ text }) =>
          doTest({ text, value: 24, type: 'time', unit: 'hour' })
        );
      });

      describe('minute', () => {
        [
          { text: '60min' },
          { text: '60 min' },
          { text: '60 mins' },
          { text: '60 minute' },
          { text: '60 minutes' }
        ].forEach(({ text }) =>
          doTest({ text, value: 60, type: 'time', unit: 'minute' })
        );
      });

      describe('second', () => {
        [
          { text: '30s' },
          { text: '30 s' },
          { text: '30 sec' },
          { text: '30 secs' },
          { text: '30 second' },
          { text: '30 seconds' }
        ].forEach(({ text }) =>
          doTest({ text, value: 30, type: 'time', unit: 'second' })
        );
      });
    });

    describe('area', () => {
      describe('square_kilometer', () => {
        [
          { text: '10km2' },
          { text: '10 km2' },
          { text: '10 km\u00B2', source: '10 km2' },
          { text: '10 square kilometer' },
          { text: '10 square kilometers' },
          { text: '10 square kilometre' },
          { text: '10 square kilometres' },
          { text: '10 square km' },
          { text: '10 square kms' },
          { text: '10 sq kilometer' },
          { text: '10 sq kilometers' },
          { text: '10 sq kilometre' },
          { text: '10 sq kilometres' },
          { text: '10 sq km' },
          { text: '10 sq kms' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 10, type: 'area', unit: 'square_kilometer' })
        );
      });

      describe('hectare', () => {
        [
          { text: '20ha' },
          { text: '20 ha' },
          { text: '20 hectare' },
          { text: '20 hectares' }
        ].forEach(({ text }) =>
          doTest({ text, value: 20, type: 'area', unit: 'hectare' })
        );
      });

      describe('square_meter', () => {
        [
          { text: '4m2' },
          { text: '4 m2' },
          { text: '4 m\u00B2', source: '4 m2' },
          { text: '4 square m' },
          { text: '4 square mt' },
          { text: '4 square mts' },
          { text: '4 square meter' },
          { text: '4 square meters' },
          { text: '4 square metre' },
          { text: '4 square metres' },
          { text: '4 sq m' },
          { text: '4 sq mt' },
          { text: '4 sq mts' },
          { text: '4 sq meter' },
          { text: '4 sq meters' },
          { text: '4 sq metre' },
          { text: '4 sq metres' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 4, type: 'area', unit: 'square_meter' })
        );
      });

      describe('square_mile', () => {
        [
          { text: '15mi2' },
          { text: '15 mi2' },
          { text: '15 mi\u00B2', source: '15 mi2' },
          { text: '15 square mi' },
          { text: '15 square mis' },
          { text: '15 square mile' },
          { text: '15 square miles' },
          { text: '15 sq mi' },
          { text: '15 sq mis' },
          { text: '15 sq mile' },
          { text: '15 sq miles' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 15, type: 'area', unit: 'square_mile' })
        );
      });

      describe('acre', () => {
        [
          { text: '100acre' },
          { text: '100 acre' },
          { text: '100 acres' }
        ].forEach(({ text }) =>
          doTest({ text, value: 100, type: 'area', unit: 'acre' })
        );
      });

      describe('square_yard', () => {
        [
          { text: '30yd2' },
          { text: '30 yd2' },
          { text: '30 yd\u00B2', source: '30 yd2' },
          { text: '30 square yd' },
          { text: '30 square yds' },
          { text: '30 square yard' },
          { text: '30 square yards' },
          { text: '30 sq yd' },
          { text: '30 sq yds' },
          { text: '30 sq yard' },
          { text: '30 sq yards' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 30, type: 'area', unit: 'square_yard' })
        );
      });

      describe('square_foot', () => {
        [
          { text: '12ft2' },
          { text: '12 ft2' },
          { text: '12 ft\u00B2', source: '12 ft2' },
          { text: '12 square foot' },
          { text: '12 square feet' },
          { text: '12 square ft' },
          { text: '12 sq foot' },
          { text: '12 sq feet' },
          { text: '12 sq ft' },
          { text: '12 sf' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 12, type: 'area', unit: 'square_foot' })
        );
      });

      describe('square_inch', () => {
        [
          { text: '8in2' },
          { text: '8 in2' },
          { text: '8 in\u00B2', source: '8 in2' },
          { text: '8 square in' },
          { text: '8 square inch' },
          { text: '8 square inches' },
          { text: '8 sq in' },
          { text: '8 sq inch' },
          { text: '8 sq inches' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 8, type: 'area', unit: 'square_inch' })
        );
      });
    });

    describe('speed', () => {
      describe('kilometer_per_hour', () => {
        [
          { text: '80km/h' },
          { text: '80 km/h' },
          { text: '80 kms/h' },
          { text: '80 km/hour' },
          { text: '80 kms/hour' },
          { text: '80 km / h' },
          { text: '80 kms / h' },
          { text: '80 km / hour' },
          { text: '80 kms / hour' },
          { text: '80 kph' },
          { text: '80 kmh' },
          { text: '80 km per hour' },
          { text: '80 kms per hour' }
        ].forEach(({ text }) =>
          doTest({ text, value: 80, type: 'speed', unit: 'kilometer_per_hour' })
        );
      });

      describe('meter_per_second', () => {
        [
          { text: '5m/s' },
          { text: '5 m/s' },
          { text: '5 m/sec' },
          { text: '5 m / s' },
          { text: '5 m / sec' },
          { text: '5 mps' }
        ].forEach(({ text }) =>
          doTest({ text, value: 5, type: 'speed', unit: 'meter_per_second' })
        );
      });

      describe('mile_per_hour', () => {
        [
          { text: '60mph' },
          { text: '60 mph' },
          { text: '60 mi/h' },
          { text: '60 mi / h' }
        ].forEach(({ text }) =>
          doTest({ text, value: 60, type: 'speed', unit: 'mile_per_hour' })
        );
      });

      describe('foot_per_second', () => {
        [
          { text: '2ft/s' },
          { text: '2 ft/s' },
          { text: '2 ft / s' },
          { text: '2 ft/sec' },
          { text: '2 ft / sec' },
          { text: '2 fts' }
        ].forEach(({ text }) =>
          doTest({ text, value: 2, type: 'speed', unit: 'foot_per_second' })
        );
      });

      describe('knot', () => {
        [
          { text: '80kn' },
          { text: '80 kn' },
          { text: '80 knot' },
          { text: '80 knots' }
        ].forEach(({ text }) =>
          doTest({ text, value: 80, type: 'speed', unit: 'knot' })
        );
      });
    });

    describe('volume', () => {
      describe('liter', () => {
        [
          { text: '2.5l' },
          { text: '2.5 l' },
          { text: '2.5 liter' },
          { text: '2.5 liters' },
          { text: '2.5 litre' },
          { text: '2.5 litres' },
          { text: '2.5 lt' },
          { text: '2.5 ltr' },
          { text: '2.5 dm3' },
          { text: '2.5 dm\u00B3', source: '2.5 dm3' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 2.5, type: 'volume', unit: 'liter' })
        );
      });

      describe('milliliter', () => {
        [
          { text: '250ml' },
          { text: '250 ml' },
          { text: '250 milliliter' },
          { text: '250 milliliters' },
          { text: '250 millilitre' },
          { text: '250 millilitres' },
          { text: '250 milli lt' },
          { text: '250 milli ltr' },
          { text: '250 cm3' },
          { text: '250 cm\u00B3', source: '250 cm3' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 250, type: 'volume', unit: 'milliliter' })
        );
      });

      describe('us_gallon', () => {
        [
          { text: '4.5 us gal' },
          { text: '4.5 us gals' },
          { text: '4.5 us gallon' },
          { text: '4.5 us gallons' },
          { text: '4.5 gallon' },
          { text: '4.5 gallons' },
          { text: '4.5gal' },
          { text: '4.5 gal' },
          { text: '4.5 gals' }
        ].forEach(({ text }) =>
          doTest({ text, value: 4.5, type: 'volume', unit: 'us_gallon' })
        );
      });

      describe('us_quart', () => {
        [
          { text: '6 us qt' },
          { text: '6 us qts' },
          { text: '6 us quart' },
          { text: '6 us quarts' },
          { text: '6 quart' },
          { text: '6 quarts' },
          { text: '6qt' },
          { text: '6 qt' },
          { text: '6 qts' }
        ].forEach(({ text }) =>
          doTest({ text, value: 6, type: 'volume', unit: 'us_quart' })
        );
      });

      describe('us_pint', () => {
        [
          { text: '1.5 us pt' },
          { text: '1.5 us pts' },
          { text: '1.5 us pint' },
          { text: '1.5 us pints' },
          { text: '1.5 pint' },
          { text: '1.5 pints' },
          { text: '1.5pt' },
          { text: '1.5 pt' }
        ].forEach(({ text }) =>
          doTest({ text, value: 1.5, type: 'volume', unit: 'us_pint' })
        );
      });

      describe('us_fluid_ounce', () => {
        [
          { text: '12 us fl oz' },
          { text: '12 us fl ounce' },
          { text: '12 us fl ounces' },
          { text: '12 us fluid oz' },
          { text: '12 us fluid ounce' },
          { text: '12 us fluid ounces' },
          { text: '12 fluid oz' },
          { text: '12 fluid ounce' },
          { text: '12 fluid ounces' },
          { text: '12 fl ounce' },
          { text: '12 fl ounces' },
          { text: '12 fl oz' },
          { text: '12 oz fl' }
        ].forEach(({ text }) =>
          doTest({ text, value: 12, type: 'volume', unit: 'us_fluid_ounce' })
        );
      });

      describe('uk_gallon', () => {
        [
          { text: '4.5 imp gal' },
          { text: '4.5 imp gals' },
          { text: '4.5 imp gallon' },
          { text: '4.5 imp gallons' },
          { text: '4.5 imperial gallon' },
          { text: '4.5 imperial gallons' },
          { text: '4.5 imperial gal' },
          { text: '4.5 imperial gals' }
        ].forEach(({ text }) =>
          doTest({ text, value: 4.5, type: 'volume', unit: 'uk_gallon' })
        );
      });

      describe('uk_quart', () => {
        [
          { text: '6 imp qt' },
          { text: '6 imp qts' },
          { text: '6 imp quart' },
          { text: '6 imp quarts' },
          { text: '6 imperial qt' },
          { text: '6 imperial qts' },
          { text: '6 imperial quart' },
          { text: '6 imperial quarts' }
        ].forEach(({ text }) =>
          doTest({ text, value: 6, type: 'volume', unit: 'uk_quart' })
        );
      });

      describe('uk_pint', () => {
        [
          { text: '1.5 imp pt' },
          { text: '1.5 imp pts' },
          { text: '1.5 imp pint' },
          { text: '1.5 imp pints' },
          { text: '1.5 imperial pt' },
          { text: '1.5 imperial pts' },
          { text: '1.5 imperial pint' },
          { text: '1.5 imperial pints' }
        ].forEach(({ text }) =>
          doTest({ text, value: 1.5, type: 'volume', unit: 'uk_pint' })
        );
      });

      describe('uk_fluid_ounce', () => {
        [
          { text: '12 imp fl oz' },
          { text: '12 imp fl ounce' },
          { text: '12 imp fl ounces' },
          { text: '12 imp fluid oz' },
          { text: '12 imp fluid ounce' },
          { text: '12 imp fluid ounces' },
          { text: '12 imperial fluid oz' },
          { text: '12 imperial fluid ounce' },
          { text: '12 imperial fluid ounces' }
        ].forEach(({ text }) =>
          doTest({ text, value: 12, type: 'volume', unit: 'uk_fluid_ounce' })
        );
      });
    });

    describe('mass', () => {
      describe('us_ton', () => {
        [
          { text: '25 short tn' },
          { text: '25 short tns' },
          { text: '25 short ton' },
          { text: '25 short tons' },
          { text: '25 us tn' },
          { text: '25 us tns' },
          { text: '25 us ton' },
          { text: '25 us tons' }
        ].forEach(({ text }) =>
          doTest({ text, value: 25, type: 'mass', unit: 'us_ton' })
        );
      });

      describe('uk_ton', () => {
        [
          { text: '36 long tn' },
          { text: '36 long tns' },
          { text: '36 long ton' },
          { text: '36 long tons' },
          { text: '36 imp tn' },
          { text: '36 imp tns' },
          { text: '36 imp ton' },
          { text: '36 imp tons' },
          { text: '36 imperial tn' },
          { text: '36 imperial tns' },
          { text: '36 imperial ton' },
          { text: '36 imperial tons' }
        ].forEach(({ text }) =>
          doTest({ text, value: 36, type: 'mass', unit: 'uk_ton' })
        );
      });

      describe('ton', () => {
        [
          { text: '18 metric t' },
          { text: '18 metric tn' },
          { text: '18 metric tns' },
          { text: '18 metric ton' },
          { text: '18 metric tons' },
          { text: '18 metric tonne' },
          { text: '18t' },
          { text: '18 t' },
          { text: '18 tn' },
          { text: '18 tns' },
          { text: '18 ton' },
          { text: '18 tons' },
          { text: '18 tonne' }
        ].forEach(({ text }) =>
          doTest({ text, value: 18, type: 'mass', unit: 'ton' })
        );
      });

      describe('kilogram', () => {
        [
          { text: '75kg' },
          { text: '75 kg' },
          { text: '75 kgs' },
          { text: '75 kilogram' },
          { text: '75 kilograms' }
        ].forEach(({ text }) =>
          doTest({ text, value: 75, type: 'mass', unit: 'kilogram' })
        );
      });

      describe('gram', () => {
        [
          { text: '120g' },
          { text: '120 g' },
          { text: '120 gram' },
          { text: '120 grams' },
          { text: '120 gr' },
          { text: '120 grs' },
          { text: '120 gm' },
          { text: '120 gms' }
        ].forEach(({ text }) =>
          doTest({ text, value: 120, type: 'mass', unit: 'gram' })
        );
      });

      describe('milligram', () => {
        [
          { text: '85mg' },
          { text: '85 mg' },
          { text: '85 mgs' },
          { text: '85 milligram' },
          { text: '85 milligrams' }
        ].forEach(({ text }) =>
          doTest({ text, value: 85, type: 'mass', unit: 'milligram' })
        );
      });

      describe('stone', () => {
        [
          { text: '42st' },
          { text: '42 st' },
          { text: '42 stone' },
          { text: '42 stones' }
        ].forEach(({ text }) =>
          doTest({ text, value: 42, type: 'mass', unit: 'stone' })
        );
      });

      describe('pound', () => {
        [
          { text: '55lb' },
          { text: '55 lb' },
          { text: '55 lbs' },
          { text: '55 pound' },
          { text: '55 pounds' }
        ].forEach(({ text }) =>
          doTest({ text, value: 55, type: 'mass', unit: 'pound' })
        );
      });

      describe('ounce', () => {
        [
          { text: '5oz' },
          { text: '5 oz' },
          { text: '5 ounce' },
          { text: '5 ounces' }
        ].forEach(({ text }) =>
          doTest({ text, value: 5, type: 'mass', unit: 'ounce' })
        );
      });
    });

    describe('length', () => {
      describe('kilometer', () => {
        [
          { text: '16km' },
          { text: '16 km' },
          { text: '16 kms' },
          { text: '16 kilometer' },
          { text: '16 kilometers' },
          { text: '16 kilometre' },
          { text: '16 kilometres' }
        ].forEach(({ text }) =>
          doTest({ text, value: 16, type: 'length', unit: 'kilometer' })
        );
      });

      describe('meter', () => {
        [
          { text: '60m' },
          { text: '60 m' },
          { text: '60 meter' },
          { text: '60 meters' },
          { text: '60 metre' },
          { text: '60 metres' },
          { text: '60 mt' },
          { text: '60 mts' }
        ].forEach(({ text }) =>
          doTest({ text, value: 60, type: 'length', unit: 'meter' })
        );
      });

      describe('centimeter', () => {
        [
          { text: '10.5cm' },
          { text: '10.5 cm' },
          { text: '10.5 cms' },
          { text: '10.5 centimeter' },
          { text: '10.5 centimeters' },
          { text: '10.5 centimetre' },
          { text: '10.5 centimetres' }
        ].forEach(({ text }) =>
          doTest({ text, value: 10.5, type: 'length', unit: 'centimeter' })
        );
      });

      describe('millimeter', () => {
        [
          { text: '26mm' },
          { text: '26 mm' },
          { text: '26 millimeter' },
          { text: '26 millimeters' },
          { text: '26 millimetre' },
          { text: '26 millimetres' }
        ].forEach(({ text }) =>
          doTest({ text, value: 26, type: 'length', unit: 'millimeter' })
        );
      });

      describe('mile', () => {
        [
          { text: '21mi' },
          { text: '21 mi' },
          { text: '21 mis' },
          { text: '21 mile' },
          { text: '21 miles' }
        ].forEach(({ text }) =>
          doTest({ text, value: 21, type: 'length', unit: 'mile' })
        );
      });

      describe('yard', () => {
        [
          { text: '71yd' },
          { text: '71 yd' },
          { text: '71 yds' },
          { text: '71 yard' },
          { text: '71 yards' }
        ].forEach(({ text }) =>
          doTest({ text, value: 71, type: 'length', unit: 'yard' })
        );
      });

      describe('foot', () => {
        [
          { text: '18ft' },
          { text: '18 ft' },
          { text: '18 foot' },
          { text: '18 feet' },
          { text: "18'" },
          { text: "18 '" },
          { text: '18`' },
          { text: '18 `' },
          { text: '18\u2032', source: "18'" },
          { text: '18 \u2032', source: "18 '" }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 18, type: 'length', unit: 'foot' })
        );
      });

      describe('inch', () => {
        [
          { text: '28in' },
          { text: '28 in' },
          { text: '28 ins' },
          { text: '28 inch' },
          { text: '28 inches' },
          { text: "28''" },
          { text: "28 ''" },
          { text: '28"' },
          { text: '28 "' },
          { text: '28``' },
          { text: '28 ``' },
          { text: '28\u2033', source: '28"' },
          { text: '28 \u2033', source: '28 "' }
        ].forEach(({ text, source }) =>
          doTest({ text, source, value: 28, type: 'length', unit: 'inch' })
        );
      });
    });

    describe('non-trivial cases', () => {
      describe('in middle of text', () => {
        doTest({
          text:   'top speed, namely, 23 mph, and',
          value:  23,
          type:   'speed',
          unit:   'mile_per_hour',
          source: '23 mph'
        });
      });

      describe('dot for abbreviations', () => {
        doTest({
          text:   '10 sq. km.',
          value:   10,
          type:   'area',
          unit:   'square_kilometer',
          source: '10 sq km'
        });

        doTest({
          text:   '10 fl. oz.',
          value:   10,
          type:   'volume',
          unit:   'us_fluid_ounce',
          source: '10 fl oz'
        });

        doTest({
          text:   '10 imp. fl. oz.',
          value:   10,
          type:   'volume',
          unit:   'uk_fluid_ounce',
          source: '10 imp fl oz'
        });
      });

      describe('many spaces', () => {
        doTest({
          text:   '    123 345 l',
          value:  123345,
          type:   'volume',
          unit:   'liter',
          source: '123 345 l'
        });

        doTest({
          text:   '123   345 km',
          value:  123345,
          type:   'length',
          unit:   'kilometer',
          source: '123 345 km'
        });

        doTest({
          text:   '123   345    imperial     tons',
          value:  123345,
          type:   'mass',
          unit:   'uk_ton',
          source: '123 345 imperial tons'
        });
      });
    });

    describe('combined time units', () => {
      describe('2 combinations', () => {
        [
          { text: '1 week and 7 days', value: 14, unit: 'day' },
          { text: '1 day and 12 hours', value: 36, unit: 'hour' },
          { text: '1 hour and 30 minutes', value: 90, unit: 'minute' },
          { text: '1 minute and 30 seconds', value: 90, unit: 'second' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'time' })
        );
      });

      describe('3 combinations', () => {
        [
          { text: '1 week, 2 days and 3 hours', value: 219, unit: 'hour' },
          { text: '1 day, 2 hours and 3 minutes', value: 1563, unit: 'minute' },
          { text: '1 hour, 2 minutes, and 3 seconds', value: 3723, unit: 'second' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'time' })
        );
      });

      describe('4 combinations', () => {
        [
          { text: '1 week, 2 days, 3 hours and 4 minutes', value: 13144, unit: 'minute' },
          { text: '1 day, 2 hours, 3 minutes and 4 seconds', value: 93784, unit: 'second' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'time' })
        );
      });

      describe('5 combinations', () => {
        [
          { text: '1 week, 2 days, 3 hours, 4 minutes and 5 seconds', value: 788645, unit: 'second' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'time' })
        );
      });
    });

    describe('combined volume units', () => {
      describe('2 combinations', () => {
        [
          { text: '1 gallon and 2 quarts', value: 6, unit: 'us_quart' },
          { text: '1 quart and 2 pints', value: 4, unit: 'us_pint' },
          { text: '1 pint and 2 fluid ounces', value: 18, unit: 'us_fluid_ounce' },

          { text: '1 imp gallon and 2 imp quarts', value: 6, unit: 'uk_quart' },
          { text: '1 imp quart and 2 imp pints', value: 4, unit: 'uk_pint' },
          { text: '1 imp pint and 2 imp fluid ounces', value: 22, unit: 'uk_fluid_ounce' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'volume' })
        );
      });

      describe('3 combinations', () => {
        [
          { text: '1 gallon, 2 quarts and 3 pints', value: 15, unit: 'us_pint' },
          { text: '1 quart, 2 pints and 3 fluid ounces', value: 67, unit: 'us_fluid_ounce' },

          { text: '1 imp gallon, 2 imp quarts and 3 imp pints', value: 15, unit: 'uk_pint' },
          { text: '1 imp quart, 2 imp pints and 3 imp fluid ounces', value: 83, unit: 'uk_fluid_ounce' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'volume' })
        );
      });

      describe('4 combinations', () => {
        [
          { text: '1 gallon, 2 quarts, 3 pints and 4 fluid ounces', value: 244, unit: 'us_fluid_ounce' },

          { text: '1 imp gallon, 2 imp quarts, 3 imp pints and 4 imp fluid ounces', value: 304, unit: 'uk_fluid_ounce' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'volume' })
        );
      });
    });

    describe('combined mass units', () => {
      describe('2 combinations', () => {
        [
          { text: '1 pound and 8 ounces', value: 24, unit: 'ounce' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'mass' })
        );
      });
    });

    describe('combined length units', () => {
      describe('with implicit inch unit', () => {
        [
          { text: '3 yard, 5 foot 8', value: 176, unit: 'inch' },
          { text: '3 ft 9', value: 45, unit:  'inch' },
          { text: '3`9', value: 45, unit:  'inch' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'length' })
        );
      });

      describe('2 combinations', () => {
        [
          { text: '2 miles and 80 yards', value: 3600, unit: 'yard' },
          { text: '10 yard with another 6 foot', value: 36, unit: 'foot' },
          { text: '5 foot 9 inches', value: 69, unit:  'inch' },
          { text: '5 foot and 9 inches', value: 69, unit:  'inch' },
          { text: '5`9``', value: 69, unit:  'inch' },
          { text: '5\'9"', value: 69, unit:  'inch' },
          { text: '5\' 9"', value: 69, unit:  'inch' },
          { text: '5 \' 9 "', value: 69, unit:  'inch' },
          { text: '5 ft 9"', value: 69, unit:  'inch' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'length' })
        );
      });

      describe('3 combinations', () => {
        [
          { text: '4 miles, adding 8 yards, and finally 3 feet', value: 21147, unit: 'foot' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'length' })
        );
      });

      describe('4 combinations', () => {
        [
          { text: '1 mile 10 yards 5 feet 2 inches', value: 63782, unit: 'inch' }
        ].forEach(({ text, value, unit }) =>
          doTest({ text, value, unit, type:  'length' })
        );
      });
    });
  });

  describe('invalid parse result', () => {
    let expect;

    beforeEach(function () {
      expect = this.expect;
    });

    function doTest({ text }) {
      describe(text, () => {
        it('should return null', () =>
          expect(parseUnit(text)).to.be.null
        );
      });
    }

    [
      { text: '' },
      { text: '10 x-x km' },
      { text: '10 degrees France' },
      { text: '11 degrees cry' },
      { text: '12 weeksi' },
      { text: '13 dayze' },
      { text: '14 Hz' },
      { text: '15 minu' },
      { text: '16 secz' },
      { text: '17 km2s' },
      { text: '18 sq meterz' },
      { text: '19 haha' },
      { text: '20 mi23' },
      { text: '21 square ydz' },
      { text: '22 ft21' },
      { text: '23 in21' },
      { text: '24 acred' },
      { text: '25 kphx' },
      { text: '26 mpsz' },
      { text: '27 mphz' },
      { text: '28 ftss' },
      { text: '29 knk' },
      { text: '30 ll' },
      { text: '31 mll' },
      { text: '32 galsa' },
      { text: '33 quartz' },
      { text: '34 pintz' },
      { text: '35 fl ozz' },
      { text: '36 imp galsa' },
      { text: '37 imp qtx' },
      { text: '38 imp ptx' },
      { text: '39 imp fl ozx' },
      { text: '40 short tno' },
      { text: '41 long tna' },
      { text: '42 tonnes' },
      { text: '43 kgsk' },
      { text: '44 gz' },
      { text: '45 mgz' },
      { text: '46 std' },
      { text: '47 lbz' },
      { text: '48 ouncez' },
      { text: '49 kmi' },
      { text: '50 mtsz' },
      { text: '51 cmsz' },
      { text: '52 mmx' },
      { text: '53 mislp' },
      { text: '54 ydx' },
      { text: '55 ftx' },
      { text: '56 inx' },
      { text: '18gallonz' },
      { text: '18 kmw' },
      { text: '18```' }
    ].forEach(doTest);
  });
});
