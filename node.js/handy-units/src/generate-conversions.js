const TOLERANCE = 1e-10;

const getUnit = (unitType, unitId) => this.units[unitType][unitId];

const isGreaterThan = (target, value) => ((value + TOLERANCE) > target || (value - TOLERANCE) > target);

const separateNumber = (number) => {
  const result = {
    integer: Math.trunc(number),
    decimal: number % 1
  };
  if (isGreaterThan(1, result.decimal) || isGreaterThan(result.decimal, 0)) {
    result.integer += 1;
    result.decimal = 0;
  }
  return result;
};

const pushConversion = (conversions, unit, conversion) => {
  const system = unit.system || 'unknown';
  if (!(system in conversions)) {
    Object.assign(conversions, { [system]: [] });
  }
  conversions[system].push(conversion);
};

const accumulateScalingDown = (conversions, type, unit, value) => {
  let current = { unit, value };
  const chain = [current];
  while (current.unit.scale && current.unit.scale.down) {
    const { integer, decimal } = separateNumber(current.value);
    if (decimal !== 0) {
      const scaled = {
        unit:  getUnit(type, current.unit.scale.down),
        value: current.unit.conversions[current.unit.scale.down](decimal)
      };
      current.value = integer;
      chain.push(scaled);
      current = scaled;
    } else {
      break;
    }
  }
  if (chain.length > 1) {
    pushConversion(conversions, current.unit, chain);
  }
};

this.generateConversions = (text) => {
  const parsed = this.parseUnit(text);
  if (!parsed) {
    return null;
  }
  const conversions = {};
  Object.keys(parsed.unit.conversions).forEach((unitId) => {
    const value = parsed.unit.conversions[unitId](parsed.value);
    const unit = getUnit(parsed.type, unitId);
    pushConversion(conversions, unit, [{ unit, value }]);
    if (isGreaterThan(1, value)) {
      accumulateScalingDown(conversions, parsed.type, unit, value);
    }
  });
  console.log('>>', JSON.stringify(conversions, null, 2));
  return { conversions, source: parsed.source };
};
