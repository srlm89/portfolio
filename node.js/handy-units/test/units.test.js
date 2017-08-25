describe('units', () => {
  let units;

  beforeEach(function () {
    units = this.loadClass({ className: 'units.js' }).units;
  });

  describe('unique ids', () => {
    let ids;
    let keys;

    beforeEach(() => {
      ids = [];
      keys = [];
      Object.keys(units).forEach((typeKey) => {
        Object.keys(units[typeKey]).forEach((unitKey) => {
          ids.push(units[typeKey][unitKey].id);
          keys.push(unitKey);
        });
      });
    });

    it('should be equal', () =>
      ids.should.deep.equal(keys)
    );
  });

  describe('temperature', () => {
    describe('fahrenheit', () => {
      it('conversion to celsius', () => {
        units.temperature.fahrenheit.conversions.celsius(89.6).should.be.closeTo(32, 1e-5);
      });
    });

    describe('celsius', () => {
      it('conversion to fahrenheit', () => {
        units.temperature.celsius.conversions.fahrenheit(32).should.be.closeTo(89.6, 1e-5);
      });
    });
  });

  describe('time', () => {
    describe('week', () => {
      it('conversion to day', () => {
        units.time.week.conversions.day(1).should.be.closeTo(7, 1e-5);
      });

      it('conversion to hour', () => {
        units.time.week.conversions.hour(1).should.be.closeTo(168, 1e-5);
      });

      it('conversion to minute', () => {
        units.time.week.conversions.minute(1).should.be.closeTo(10080, 1e-5);
      });

      it('conversion to second', () => {
        units.time.week.conversions.second(1).should.be.closeTo(604800, 1e-5);
      });
    });

    describe('day', () => {
      it('conversion to week', () => {
        units.time.day.conversions.week(7).should.be.closeTo(1, 1e-5);
      });

      it('conversion to hour', () => {
        units.time.day.conversions.hour(1).should.be.closeTo(24, 1e-5);
      });

      it('conversion to minute', () => {
        units.time.day.conversions.minute(1).should.be.closeTo(1440, 1e-5);
      });

      it('conversion to second', () => {
        units.time.day.conversions.second(1).should.be.closeTo(86400, 1e-5);
      });
    });

    describe('hour', () => {
      it('conversion to week', () => {
        units.time.hour.conversions.week(168).should.be.closeTo(1, 1e-5);
      });

      it('conversion to day', () => {
        units.time.hour.conversions.day(24).should.be.closeTo(1, 1e-5);
      });

      it('conversion to minute', () => {
        units.time.hour.conversions.minute(1).should.be.closeTo(60, 1e-5);
      });

      it('conversion to second', () => {
        units.time.hour.conversions.second(1).should.be.closeTo(3600, 1e-5);
      });
    });

    describe('minute', () => {
      it('conversion to week', () => {
        units.time.minute.conversions.week(10080).should.be.closeTo(1, 1e-5);
      });

      it('conversion to day', () => {
        units.time.minute.conversions.day(1440).should.be.closeTo(1, 1e-5);
      });

      it('conversion to hour', () => {
        units.time.minute.conversions.hour(60).should.be.closeTo(1, 1e-5);
      });

      it('conversion to second', () => {
        units.time.minute.conversions.second(1).should.be.closeTo(60, 1e-5);
      });
    });

    describe('second', () => {
      it('conversion to week', () => {
        units.time.second.conversions.week(604800).should.be.closeTo(1, 1e-5);
      });

      it('conversion to day', () => {
        units.time.second.conversions.day(86400).should.be.closeTo(1, 1e-5);
      });

      it('conversion to hour', () => {
        units.time.second.conversions.hour(3600).should.be.closeTo(1, 1e-5);
      });

      it('conversion to minute', () => {
        units.time.second.conversions.minute(60).should.be.closeTo(1, 1e-5);
      });
    });
  });

  describe('area', () => {
    describe('square_kilometer', () => {
      it('conversion to hectare', () => {
        units.area.square_kilometer.conversions.hectare(1).should.be.closeTo(1e2, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.square_kilometer.conversions.square_meter(1).should.be.closeTo(1e6, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.square_kilometer.conversions.square_mile(1.609344 * 1.609344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.square_kilometer.conversions.acre(1.609344 * 1.609344).should.be.closeTo(640, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.square_kilometer.conversions.square_yard(1.609344 * 1.609344).should.be.closeTo(3097600, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.square_kilometer.conversions.square_foot(1.609344 * 1.609344).should.be.closeTo(27878400, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.square_kilometer.conversions.square_inch(1.609344 * 1.609344).should.be.closeTo(4014489600, 1e-5);
      });
    });

    describe('hectare', () => {
      it('conversion to square_kilometer', () => {
        units.area.hectare.conversions.square_kilometer(1e2).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.hectare.conversions.square_meter(1).should.be.closeTo(1e4, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.hectare.conversions.square_mile(16.09344 * 16.09344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.hectare.conversions.acre(16.09344 * 16.09344).should.be.closeTo(640, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.hectare.conversions.square_yard(16.09344 * 16.09344).should.be.closeTo(3097600, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.hectare.conversions.square_foot(16.09344 * 16.09344).should.be.closeTo(27878400, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.hectare.conversions.square_inch(16.09344 * 16.09344).should.be.closeTo(4014489600, 1e-5);
      });
    });

    describe('square_meter', () => {
      it('conversion to square_kilometer', () => {
        units.area.square_meter.conversions.square_kilometer(1e6).should.be.closeTo(1, 1e-5);
      });

      it('conversion to hectare', () => {
        units.area.square_meter.conversions.hectare(1e4).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.square_meter.conversions.square_mile(1609.344 * 1609.344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.square_meter.conversions.acre(1609.344 * 1609.344).should.be.closeTo(640, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.square_meter.conversions.square_yard(1609.344 * 1609.344).should.be.closeTo(3097600, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.square_meter.conversions.square_foot(1609.344 * 1609.344).should.be.closeTo(27878400, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.square_meter.conversions.square_inch(1609.344 * 1609.344).should.be.closeTo(4014489600, 1e-5);
      });
    });

    describe('square_mile', () => {
      it('conversion to square_kilometer', () => {
        units.area.square_mile.conversions.square_kilometer(1).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to hectare', () => {
        units.area.square_mile.conversions.hectare(1).should.be.closeTo(16.09344 * 16.09344, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.square_mile.conversions.square_meter(1).should.be.closeTo(1609.344 * 1609.344, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.square_mile.conversions.acre(1).should.be.closeTo(640, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.square_mile.conversions.square_yard(1).should.be.closeTo(3097600, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.square_mile.conversions.square_foot(1).should.be.closeTo(27878400, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.square_mile.conversions.square_inch(1).should.be.closeTo(4014489600, 1e-5);
      });
    });

    describe('acre', () => {
      it('conversion to square_kilometer', () => {
        units.area.acre.conversions.square_kilometer(640).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to hectare', () => {
        units.area.acre.conversions.hectare(640).should.be.closeTo(16.09344 * 16.09344, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.acre.conversions.square_meter(640).should.be.closeTo(1609.344 * 1609.344, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.acre.conversions.square_mile(640).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.acre.conversions.square_yard(1).should.be.closeTo(4840, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.acre.conversions.square_foot(1).should.be.closeTo(43560, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.acre.conversions.square_inch(1).should.be.closeTo(6272640, 1e-5);
      });
    });

    describe('square_yard', () => {
      it('conversion to square_kilometer', () => {
        units.area.square_yard.conversions.square_kilometer(30976).should.be.closeTo(0.1609344 * 0.1609344, 1e-5);
      });

      it('conversion to hectare', () => {
        units.area.square_yard.conversions.hectare(30976).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.square_yard.conversions.square_meter(3.0976).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.square_yard.conversions.square_mile(3097600).should.be.closeTo(1, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.square_yard.conversions.acre(4840).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.square_yard.conversions.square_foot(1).should.be.closeTo(9, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.square_yard.conversions.square_inch(1).should.be.closeTo(1296, 1e-5);
      });
    });

    describe('square_foot', () => {
      it('conversion to square_kilometer', () => {
        units.area.square_foot.conversions.square_kilometer(278784).should.be.closeTo(0.1609344 * 0.1609344, 1e-5);
      });

      it('conversion to hectare', () => {
        units.area.square_foot.conversions.hectare(278784).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.square_foot.conversions.square_meter(27.8784).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.square_foot.conversions.square_mile(27878400).should.be.closeTo(1, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.square_foot.conversions.acre(43560).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.square_foot.conversions.square_yard(9).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_inch', () => {
        units.area.square_foot.conversions.square_inch(1).should.be.closeTo(144, 1e-5);
      });
    });

    describe('square_inch', () => {
      it('conversion to square_kilometer', () => {
        units.area.square_inch.conversions.square_kilometer(40144896).should.be.closeTo(0.1609344 * 0.1609344, 1e-5);
      });

      it('conversion to hectare', () => {
        units.area.square_inch.conversions.hectare(40144896).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to square_meter', () => {
        units.area.square_inch.conversions.square_meter(4014.4896).should.be.closeTo(1.609344 * 1.609344, 1e-5);
      });

      it('conversion to square_mile', () => {
        units.area.square_inch.conversions.square_mile(4014489600).should.be.closeTo(1, 1e-5);
      });

      it('conversion to acre', () => {
        units.area.square_inch.conversions.acre(6272640).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_yard', () => {
        units.area.square_inch.conversions.square_yard(1296).should.be.closeTo(1, 1e-5);
      });

      it('conversion to square_foot', () => {
        units.area.square_inch.conversions.square_foot(144).should.be.closeTo(1, 1e-5);
      });
    });
  });

  describe('speed', () => {
    describe('kilometer_per_hour', () => {
      it('conversion to meter_per_second', () => {
        units.speed.kilometer_per_hour.conversions.meter_per_second(3.6).should.be.closeTo(1, 1e-5);
      });

      it('conversion to mile_per_hour', () => {
        units.speed.kilometer_per_hour.conversions.mile_per_hour(1.609344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot_per_second', () => {
        units.speed.kilometer_per_hour.conversions.foot_per_second(1.609344 * (3600 / 5280)).should.be.closeTo(1, 1e-5);
      });

      it('conversion to knot', () => {
        units.speed.kilometer_per_hour.conversions.knot(1.852).should.be.closeTo(1, 1e-5);
      });
    });

    describe('meter_per_second', () => {
      it('conversion to kilometer_per_hour', () => {
        units.speed.meter_per_second.conversions.kilometer_per_hour(1).should.be.closeTo(3.6, 1e-5);
      });

      it('conversion to mile_per_hour', () => {
        units.speed.meter_per_second.conversions.mile_per_hour(1.609344 / 3.6).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot_per_second', () => {
        units.speed.meter_per_second.conversions.foot_per_second(0.3048).should.be.closeTo(1, 1e-5);
      });

      it('conversion to knot', () => {
        units.speed.meter_per_second.conversions.knot(1.852 / 3.6).should.be.closeTo(1, 1e-5);
      });
    });

    describe('mile_per_hour', () => {
      it('conversion to kilometer_per_hour', () => {
        units.speed.mile_per_hour.conversions.kilometer_per_hour(1).should.be.closeTo(1.609344, 1e-5);
      });

      it('conversion to meter_per_second', () => {
        units.speed.mile_per_hour.conversions.meter_per_second(3.6).should.be.closeTo(1.609344, 1e-5);
      });

      it('conversion to foot_per_second', () => {
        units.speed.mile_per_hour.conversions.foot_per_second(3600 / 5280).should.be.closeTo(1, 1e-5);
      });

      it('conversion to knot', () => {
        units.speed.mile_per_hour.conversions.knot(1.852).should.be.closeTo(1.609344, 1e-5);
      });
    });

    describe('foot_per_second', () => {
      it('conversion to kilometer_per_hour', () => {
        units.speed.foot_per_second.conversions.kilometer_per_hour(5280 / 3600).should.be.closeTo(1.609344, 1e-5);
      });

      it('conversion to meter_per_second', () => {
        units.speed.foot_per_second.conversions.meter_per_second(1).should.be.closeTo(0.3048, 1e-5);
      });

      it('conversion to mile_per_hour', () => {
        units.speed.foot_per_second.conversions.mile_per_hour(5280 / 3600).should.be.closeTo(1, 1e-5);
      });

      it('conversion to knot', () => {
        units.speed.foot_per_second.conversions.knot(1.852 * (5280 / 3600)).should.be.closeTo(1.609344, 1e-5);
      });
    });

    describe('knot', () => {
      it('conversion to kilometer_per_hour', () => {
        units.speed.knot.conversions.kilometer_per_hour(1).should.be.closeTo(1.852, 1e-5);
      });

      it('conversion to meter_per_second', () => {
        units.speed.knot.conversions.meter_per_second(3.6).should.be.closeTo(1.852, 1e-5);
      });

      it('conversion to mile_per_hour', () => {
        units.speed.knot.conversions.mile_per_hour(1.609344).should.be.closeTo(1.852, 1e-5);
      });

      it('conversion to foot_per_second', () => {
        units.speed.knot.conversions.foot_per_second(1.609344 * (3600 / 5280)).should.be.closeTo(1.852, 1e-5);
      });
    });
  });

  describe('volume', () => {
    describe('liter', () => {
      it('conversion to milliliter', () => {
        units.volume.liter.conversions.milliliter(1).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.liter.conversions.us_gallon(3.785411784).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.liter.conversions.us_quart(3.785411784).should.be.closeTo(4, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.liter.conversions.us_pint(3.785411784).should.be.closeTo(8, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.liter.conversions.us_fluid_ounce(3.785411784).should.be.closeTo(128, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.liter.conversions.uk_gallon(4.54609).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.liter.conversions.uk_quart(4.54609).should.be.closeTo(4, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.liter.conversions.uk_pint(4.54609).should.be.closeTo(8, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.liter.conversions.uk_fluid_ounce(4.54609).should.be.closeTo(160, 1e-5);
      });
    });

    describe('milliliter', () => {
      it('conversion to liter', () => {
        units.volume.milliliter.conversions.liter(1e3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.milliliter.conversions.us_gallon(3785.411784).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.milliliter.conversions.us_quart(3785.411784).should.be.closeTo(4, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.milliliter.conversions.us_pint(3785.411784).should.be.closeTo(8, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.milliliter.conversions.us_fluid_ounce(3785.411784).should.be.closeTo(128, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.milliliter.conversions.uk_gallon(4546.09).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.milliliter.conversions.uk_quart(4546.09).should.be.closeTo(4, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.milliliter.conversions.uk_pint(4546.09).should.be.closeTo(8, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.milliliter.conversions.uk_fluid_ounce(4546.09).should.be.closeTo(160, 1e-5);
      });
    });

    describe('us_gallon', () => {
      it('conversion to liter', () => {
        units.volume.us_gallon.conversions.liter(1).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.us_gallon.conversions.milliliter(1).should.be.closeTo(3785.411784, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.us_gallon.conversions.us_quart(1).should.be.closeTo(4, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.us_gallon.conversions.us_pint(1).should.be.closeTo(8, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.us_gallon.conversions.us_fluid_ounce(1).should.be.closeTo(128, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.us_gallon.conversions.uk_gallon(4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.us_gallon.conversions.uk_quart(4.54609).should.be.closeTo(4 * 3.785411784, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.us_gallon.conversions.uk_pint(4.54609).should.be.closeTo(8 * 3.785411784, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.us_gallon.conversions.uk_fluid_ounce(4.54609).should.be.closeTo(160 * 3.785411784, 1e-5);
      });
    });

    describe('us_quart', () => {
      it('conversion to liter', () => {
        units.volume.us_quart.conversions.liter(4).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.us_quart.conversions.milliliter(4).should.be.closeTo(3785.411784, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.us_quart.conversions.us_gallon(4).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.us_quart.conversions.us_pint(1).should.be.closeTo(2, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.us_quart.conversions.us_fluid_ounce(1).should.be.closeTo(32, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.us_quart.conversions.uk_gallon(4 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.us_quart.conversions.uk_quart(4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.us_quart.conversions.uk_pint(4.54609).should.be.closeTo(2 * 3.785411784, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.us_quart.conversions.uk_fluid_ounce(4.54609).should.be.closeTo(40 * 3.785411784, 1e-5);
      });
    });

    describe('us_pint', () => {
      it('conversion to liter', () => {
        units.volume.us_pint.conversions.liter(8).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.us_pint.conversions.milliliter(8).should.be.closeTo(3785.411784, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.us_pint.conversions.us_gallon(8).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.us_pint.conversions.us_quart(2).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.us_pint.conversions.us_fluid_ounce(1).should.be.closeTo(16, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.us_pint.conversions.uk_gallon(8 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.us_pint.conversions.uk_quart(2 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.us_pint.conversions.uk_pint(4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.us_pint.conversions.uk_fluid_ounce(4.54609).should.be.closeTo(20 * 3.785411784, 1e-5);
      });
    });

    describe('us_fluid_ounce', () => {
      it('conversion to liter', () => {
        units.volume.us_fluid_ounce.conversions.liter(128).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.us_fluid_ounce.conversions.milliliter(128).should.be.closeTo(3785.411784, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.us_fluid_ounce.conversions.us_gallon(128).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.us_fluid_ounce.conversions.us_quart(32).should.be.closeTo(1, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.us_fluid_ounce.conversions.us_pint(16).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.us_fluid_ounce.conversions.uk_gallon(128 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.us_fluid_ounce.conversions.uk_quart(32 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.us_fluid_ounce.conversions.uk_pint(16 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.us_fluid_ounce.conversions.uk_fluid_ounce(0.8 * 4.54609).should.be.closeTo(3.785411784, 1e-5);
      });
    });

    describe('uk_gallon', () => {
      it('conversion to liter', () => {
        units.volume.uk_gallon.conversions.liter(1).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.uk_gallon.conversions.milliliter(1).should.be.closeTo(4546.09, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.uk_gallon.conversions.us_gallon(3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.uk_gallon.conversions.us_quart(3.785411784).should.be.closeTo(4 * 4.54609, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.uk_gallon.conversions.us_pint(3.785411784).should.be.closeTo(8 * 4.54609, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.uk_gallon.conversions.us_fluid_ounce(3.785411784).should.be.closeTo(128 * 4.54609, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.uk_gallon.conversions.uk_quart(1).should.be.closeTo(4, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.uk_gallon.conversions.uk_pint(1).should.be.closeTo(8, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.uk_gallon.conversions.uk_fluid_ounce(1).should.be.closeTo(160, 1e-5);
      });
    });

    describe('uk_quart', () => {
      it('conversion to liter', () => {
        units.volume.uk_quart.conversions.liter(4).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.uk_quart.conversions.milliliter(4).should.be.closeTo(4546.09, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.uk_quart.conversions.us_gallon(4 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.uk_quart.conversions.us_quart(3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.uk_quart.conversions.us_pint(3.785411784).should.be.closeTo(2 * 4.54609, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.uk_quart.conversions.us_fluid_ounce(3.785411784).should.be.closeTo(32 * 4.54609, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.uk_quart.conversions.uk_gallon(4).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.uk_quart.conversions.uk_pint(1).should.be.closeTo(2, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.uk_quart.conversions.uk_fluid_ounce(1).should.be.closeTo(40, 1e-5);
      });
    });

    describe('uk_pint', () => {
      it('conversion to liter', () => {
        units.volume.uk_pint.conversions.liter(8).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.uk_pint.conversions.milliliter(8).should.be.closeTo(4546.09, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.uk_pint.conversions.us_gallon(8 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.uk_pint.conversions.us_quart(2 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.uk_pint.conversions.us_pint(3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.uk_pint.conversions.us_fluid_ounce(3.785411784).should.be.closeTo(16 * 4.54609, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.uk_pint.conversions.uk_gallon(8).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.uk_pint.conversions.uk_quart(2).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_fluid_ounce', () => {
        units.volume.uk_pint.conversions.uk_fluid_ounce(1).should.be.closeTo(20, 1e-5);
      });
    });

    describe('uk_fluid_ounce', () => {
      it('conversion to liter', () => {
        units.volume.uk_fluid_ounce.conversions.liter(160).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to milliliter', () => {
        units.volume.uk_fluid_ounce.conversions.milliliter(160).should.be.closeTo(4546.09, 1e-5);
      });

      it('conversion to us_gallon', () => {
        units.volume.uk_fluid_ounce.conversions.us_gallon(160 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_quart', () => {
        units.volume.uk_fluid_ounce.conversions.us_quart(40 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_pint', () => {
        units.volume.uk_fluid_ounce.conversions.us_pint(20 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to us_fluid_ounce', () => {
        units.volume.uk_fluid_ounce.conversions.us_fluid_ounce(1.25 * 3.785411784).should.be.closeTo(4.54609, 1e-5);
      });

      it('conversion to uk_gallon', () => {
        units.volume.uk_fluid_ounce.conversions.uk_gallon(160).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_quart', () => {
        units.volume.uk_fluid_ounce.conversions.uk_quart(40).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_pint', () => {
        units.volume.uk_fluid_ounce.conversions.uk_pint(20).should.be.closeTo(1, 1e-5);
      });
    });
  });

  describe('mass', () => {
    describe('us_ton', () => {
      it('conversion to uk_ton', () => {
        units.mass.us_ton.conversions.uk_ton(1.0160469088).should.be.closeTo(0.90718474, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.us_ton.conversions.ton(1).should.be.closeTo(0.90718474, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.us_ton.conversions.kilogram(1).should.be.closeTo(907.18474, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.us_ton.conversions.gram(1).should.be.closeTo(907184.74, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.us_ton.conversions.milligram(1).should.be.closeTo(907184740, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.us_ton.conversions.stone(14).should.be.closeTo(2000, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.us_ton.conversions.pound(1).should.be.closeTo(2000, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.us_ton.conversions.ounce(1).should.be.closeTo(32000, 1e-5);
      });
    });

    describe('uk_ton', () => {
      it('conversion to us_ton', () => {
        units.mass.uk_ton.conversions.us_ton(0.90718474).should.be.closeTo(1.0160469088, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.uk_ton.conversions.ton(1).should.be.closeTo(1.0160469088, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.uk_ton.conversions.kilogram(1).should.be.closeTo(1016.0469088, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.uk_ton.conversions.gram(1).should.be.closeTo(1016046.9088, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.uk_ton.conversions.milligram(1).should.be.closeTo(1016046908.8, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.uk_ton.conversions.stone(1).should.be.closeTo(160, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.uk_ton.conversions.pound(1).should.be.closeTo(2240, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.uk_ton.conversions.ounce(1).should.be.closeTo(35840, 1e-5);
      });
    });

    describe('ton', () => {
      it('conversion to us_ton', () => {
        units.mass.ton.conversions.us_ton(0.90718474).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.ton.conversions.uk_ton(1.0160469088).should.be.closeTo(1, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.ton.conversions.kilogram(1).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.ton.conversions.gram(1).should.be.closeTo(1e6, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.ton.conversions.milligram(1).should.be.closeTo(1e9, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.ton.conversions.stone(6.35029318).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.ton.conversions.pound(0.45359237).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.ton.conversions.ounce(28.349523125).should.be.closeTo(1e6, 1e-5);
      });
    });

    describe('kilogram', () => {
      it('conversion to us_ton', () => {
        units.mass.kilogram.conversions.us_ton(907.18474).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.kilogram.conversions.uk_ton(1016.0469088).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.kilogram.conversions.ton(1e3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.kilogram.conversions.gram(1).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.kilogram.conversions.milligram(1).should.be.closeTo(1e6, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.kilogram.conversions.stone(6.35029318).should.be.closeTo(1, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.kilogram.conversions.pound(0.45359237).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.kilogram.conversions.ounce(28.349523125).should.be.closeTo(1e3, 1e-5);
      });
    });

    describe('gram', () => {
      it('conversion to us_ton', () => {
        units.mass.gram.conversions.us_ton(907184.74).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.gram.conversions.uk_ton(1016046.9088).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.gram.conversions.ton(1e6).should.be.closeTo(1, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.gram.conversions.kilogram(1e3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.gram.conversions.milligram(1).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.gram.conversions.stone(6350.29318).should.be.closeTo(1, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.gram.conversions.pound(453.59237).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.gram.conversions.ounce(28.349523125).should.be.closeTo(1, 1e-5);
      });
    });

    describe('milligram', () => {
      it('conversion to us_ton', () => {
        units.mass.milligram.conversions.us_ton(907184740).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.milligram.conversions.uk_ton(1016046908.8).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.milligram.conversions.ton(1e9).should.be.closeTo(1, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.milligram.conversions.kilogram(1e6).should.be.closeTo(1, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.milligram.conversions.gram(1e3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.milligram.conversions.stone(6350293.18).should.be.closeTo(1, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.milligram.conversions.pound(453592.37).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.milligram.conversions.ounce(28349.523125).should.be.closeTo(1, 1e-5);
      });
    });

    describe('stone', () => {
      it('conversion to us_ton', () => {
        units.mass.stone.conversions.us_ton(2000).should.be.closeTo(14, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.stone.conversions.uk_ton(160).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.stone.conversions.ton(1).should.be.closeTo(0.00635029318, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.stone.conversions.kilogram(1).should.be.closeTo(6.35029318, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.stone.conversions.gram(1).should.be.closeTo(6350.29318, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.stone.conversions.milligram(1).should.be.closeTo(6350293.18, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.stone.conversions.pound(1).should.be.closeTo(14, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.stone.conversions.ounce(1).should.be.closeTo(224, 1e-5);
      });
    });

    describe('pound', () => {
      it('conversion to us_ton', () => {
        units.mass.pound.conversions.us_ton(2000).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.pound.conversions.uk_ton(2240).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.pound.conversions.ton(1).should.be.closeTo(0.00045359237, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.pound.conversions.kilogram(1).should.be.closeTo(0.45359237, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.pound.conversions.gram(1).should.be.closeTo(453.59237, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.pound.conversions.milligram(1).should.be.closeTo(453592.37, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.pound.conversions.stone(14).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ounce', () => {
        units.mass.pound.conversions.ounce(1).should.be.closeTo(16, 1e-5);
      });
    });

    describe('ounce', () => {
      it('conversion to us_ton', () => {
        units.mass.ounce.conversions.us_ton(32000).should.be.closeTo(1, 1e-5);
      });

      it('conversion to uk_ton', () => {
        units.mass.ounce.conversions.uk_ton(35840).should.be.closeTo(1, 1e-5);
      });

      it('conversion to ton', () => {
        units.mass.ounce.conversions.ton(1).should.be.closeTo(0.000028349523125, 1e-5);
      });

      it('conversion to kilogram', () => {
        units.mass.ounce.conversions.kilogram(1).should.be.closeTo(0.028349523125, 1e-5);
      });

      it('conversion to gram', () => {
        units.mass.ounce.conversions.gram(1).should.be.closeTo(28.349523125, 1e-5);
      });

      it('conversion to milligram', () => {
        units.mass.ounce.conversions.milligram(1).should.be.closeTo(28349.523125, 1e-5);
      });

      it('conversion to stone', () => {
        units.mass.ounce.conversions.stone(224).should.be.closeTo(1, 1e-5);
      });

      it('conversion to pound', () => {
        units.mass.ounce.conversions.pound(16).should.be.closeTo(1, 1e-5);
      });
    });
  });

  describe('length', () => {
    describe('kilometer', () => {
      it('conversion to meter', () => {
        units.length.kilometer.conversions.meter(1).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.kilometer.conversions.centimeter(1).should.be.closeTo(1e5, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.kilometer.conversions.millimeter(1).should.be.closeTo(1e6, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.kilometer.conversions.mile(1.609344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.kilometer.conversions.yard(0.0009144).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.kilometer.conversions.foot(0.0003048).should.be.closeTo(1, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.kilometer.conversions.inch(0.0000254).should.be.closeTo(1, 1e-5);
      });
    });

    describe('meter', () => {
      it('conversion to kilometer', () => {
        units.length.meter.conversions.kilometer(1e3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.meter.conversions.centimeter(1).should.be.closeTo(1e2, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.meter.conversions.millimeter(1).should.be.closeTo(1e3, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.meter.conversions.mile(1609.344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.meter.conversions.yard(0.9144).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.meter.conversions.foot(0.3048).should.be.closeTo(1, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.meter.conversions.inch(0.0254).should.be.closeTo(1, 1e-5);
      });
    });

    describe('centimeter', () => {
      it('conversion to kilometer', () => {
        units.length.centimeter.conversions.kilometer(1e5).should.be.closeTo(1, 1e-5);
      });

      it('conversion to meter', () => {
        units.length.centimeter.conversions.meter(1e2).should.be.closeTo(1, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.centimeter.conversions.millimeter(1).should.be.closeTo(10, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.centimeter.conversions.mile(160934.4).should.be.closeTo(1, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.centimeter.conversions.yard(91.44).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.centimeter.conversions.foot(30.48).should.be.closeTo(1, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.centimeter.conversions.inch(2.54).should.be.closeTo(1, 1e-5);
      });
    });

    describe('millimeter', () => {
      it('conversion to kilometer', () => {
        units.length.millimeter.conversions.kilometer(1e6).should.be.closeTo(1, 1e-5);
      });

      it('conversion to meter', () => {
        units.length.millimeter.conversions.meter(1e3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.millimeter.conversions.centimeter(10).should.be.closeTo(1, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.millimeter.conversions.mile(1609344).should.be.closeTo(1, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.millimeter.conversions.yard(914.4).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.millimeter.conversions.foot(304.8).should.be.closeTo(1, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.millimeter.conversions.inch(25.4).should.be.closeTo(1, 1e-5);
      });
    });

    describe('mile', () => {
      it('conversion to kilometer', () => {
        units.length.mile.conversions.kilometer(1).should.be.closeTo(1.609344, 1e-5);
      });

      it('conversion to meter', () => {
        units.length.mile.conversions.meter(1).should.be.closeTo(1609.344, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.mile.conversions.centimeter(1).should.be.closeTo(160934.4, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.mile.conversions.millimeter(1).should.be.closeTo(1609344, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.mile.conversions.yard(1).should.be.closeTo(1760, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.mile.conversions.foot(1).should.be.closeTo(5280, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.mile.conversions.inch(1).should.be.closeTo(63360, 1e-5);
      });
    });

    describe('yard', () => {
      it('conversion to kilometer', () => {
        units.length.yard.conversions.kilometer(1).should.be.closeTo(0.0009144, 1e-5);
      });

      it('conversion to meter', () => {
        units.length.yard.conversions.meter(1).should.be.closeTo(0.9144, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.yard.conversions.centimeter(1).should.be.closeTo(91.44, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.yard.conversions.millimeter(1).should.be.closeTo(914.4, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.yard.conversions.mile(1760).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.yard.conversions.foot(1).should.be.closeTo(3, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.yard.conversions.inch(1).should.be.closeTo(36, 1e-5);
      });
    });

    describe('foot', () => {
      it('conversion to kilometer', () => {
        units.length.foot.conversions.kilometer(1).should.be.closeTo(0.0003048, 1e-5);
      });

      it('conversion to meter', () => {
        units.length.foot.conversions.meter(1).should.be.closeTo(0.3048, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.foot.conversions.centimeter(1).should.be.closeTo(30.48, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.foot.conversions.millimeter(1).should.be.closeTo(304.8, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.foot.conversions.mile(5280).should.be.closeTo(1, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.foot.conversions.yard(3).should.be.closeTo(1, 1e-5);
      });

      it('conversion to inch', () => {
        units.length.foot.conversions.inch(1).should.be.closeTo(12, 1e-5);
      });
    });

    describe('inch', () => {
      it('conversion to kilometer', () => {
        units.length.inch.conversions.kilometer(1).should.be.closeTo(0.00002534, 1e-5);
      });

      it('conversion to meter', () => {
        units.length.inch.conversions.meter(1).should.be.closeTo(0.0254, 1e-5);
      });

      it('conversion to centimeter', () => {
        units.length.inch.conversions.centimeter(1).should.be.closeTo(2.54, 1e-5);
      });

      it('conversion to millimeter', () => {
        units.length.inch.conversions.millimeter(1).should.be.closeTo(25.4, 1e-5);
      });

      it('conversion to mile', () => {
        units.length.inch.conversions.mile(63360).should.be.closeTo(1, 1e-5);
      });

      it('conversion to yard', () => {
        units.length.inch.conversions.yard(36).should.be.closeTo(1, 1e-5);
      });

      it('conversion to foot', () => {
        units.length.inch.conversions.foot(12).should.be.closeTo(1, 1e-5);
      });
    });
  });
});
