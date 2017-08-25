const UNITS_LIST = ((list) => {
  Object.keys(this.units).forEach((type) => {
    const unitType = this.units[type];
    Object.keys(unitType).forEach((unitTypeKey) => {
      const unit = unitType[unitTypeKey];
      list.push({ type, unit });
    });
  });
  return list;
})([]);

const getUnit = (unitType, unitId) => this.units[unitType][unitId];

const normalize = (text) => (
  text
    .replace(/([a-zA-Z])[.]/g, '$1 ')
    .replace(/[\s]+/g, ' ')
    .replace('\u00B2', '2')
    .replace('\u00B3', '3')
    .replace('\u2032', "'")
    .replace('\u2033', '"')
    .trim()
);

const canHaveImplicitScaledDownUnit = (unitId, scaledUnitId) => (
  unitId === 'foot' && scaledUnitId === 'inch'
);

const defaultParseUnit = (text, units) => {
  let match = null;
  const number = this.parseNumber(text);
  if (number && ((number.end + 1) < text.length)) {
    const afterNumber = text.substring(number.end + 1);
    units.find(({ unit, type }) => {
      const unitMatch = unit.regex.exec(afterNumber);
      const unitFound = !!unitMatch && !/\S/.test(afterNumber.substring(0, unitMatch.index));
      if (unitFound) {
        match = {
          unit,
          type,
          value: number.value,
          start: number.start,
          end:   number.end + unitMatch.index + unitMatch[0].length
        };
      }
      return unitFound;
    });
  }
  return match || number;
};

const recursiveParseUnit = (text, units) => {
  const match = defaultParseUnit(text, units);
  if (match && (match.end < text.length - 1) && (match.unit && match.unit.scale && match.unit.scale.down)) {
    const remaining = text.substring(match.end + 1);
    const scaledUnit = getUnit(match.type, match.unit.scale.down);
    const scaledMatch = recursiveParseUnit(remaining, [{ type: match.type, unit: scaledUnit }]);
    if (scaledMatch && (scaledMatch.unit || canHaveImplicitScaledDownUnit(match.unit.id, scaledUnit.id))) {
      const matchedUnit = (scaledMatch.unit || scaledUnit);
      const convertedMatchValue = match.unit.conversions[matchedUnit.id](match.value);
      Object.assign(match, {
        unit:  matchedUnit,
        value: convertedMatchValue + scaledMatch.value,
        start: match.start,
        end:   match.end + scaledMatch.end + 1
      });
    }
  }
  return match;
};

this.parseUnit = (text) => {
  const normalized = normalize(text);
  const parsed = recursiveParseUnit(normalized, UNITS_LIST);
  if (parsed && parsed.unit) {
    return {
      type:   parsed.type,
      unit:   parsed.unit,
      value:  parsed.value,
      source: normalized.substring(parsed.start, parsed.end + 1)
    };
  }
  return null;
};
