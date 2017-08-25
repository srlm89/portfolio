this.units = {};

this.units.temperature = {
  fahrenheit: {
    conversions: {
      celsius: (degreesF) => (5 * (degreesF - 32)) / 9.0
    },
    id:    'fahrenheit',
    unit:  '°F',
    regex: /(?:° ?F|° ?fahrenheit|fahrenheit|(?:degrees?|degs?) (?:f|fahrenheit))\b/i
  },
  celsius: {
    conversions: {
      fahrenheit: (degreesC) => ((9 * degreesC) / 5.0) + 32
    },
    id:    'celsius',
    unit:  '°C',
    regex: /(?:° ?C|° ?celsius|celsius|(?:degrees?|degs?) (?:c|celsius))\b/i
  }
};

this.units.time = {
  week: {
    conversions: {
      day:    (weeks) => weeks * 7,
      hour:   (weeks) => weeks * 168,
      minute: (weeks) => weeks * 10080,
      second: (weeks) => weeks * 604800
    },
    scale: {
      down: 'day'
    },
    id:    'week',
    unit:  'week',
    regex: /\b(?:weeks?)\b/i
  },
  day: {
    conversions: {
      week:   (days) => days / 7,
      hour:   (days) => days * 24,
      minute: (days) => days * 1440,
      second: (days) => days * 86400
    },
    scale: {
      up:   'week',
      down: 'hour'
    },
    id:    'day',
    unit:  'd',
    regex: /\b(?:d|days?)\b/i
  },
  hour: {
    conversions: {
      week:   (hours) => hours / 168,
      day:    (hours) => hours / 24,
      minute: (hours) => hours * 60,
      second: (hours) => hours * 3600
    },
    scale: {
      up:   'day',
      down: 'minute'
    },
    id:    'hour',
    unit:  'h',
    regex: /\b(?:hs?|hours?|hrs?)\b/i
  },
  minute: {
    conversions: {
      week:   (minutes) => minutes / 10080,
      day:    (minutes) => minutes / 1440,
      hour:   (minutes) => minutes / 60,
      second: (minutes) => minutes * 60
    },
    scale: {
      up:   'hour',
      down: 'second'
    },
    id:    'minute',
    unit:  'min',
    regex: /\b(?:mins?|minutes?)\b/i
  },
  second: {
    conversions: {
      week:   (seconds) => seconds / 604800,
      day:    (seconds) => seconds / 86400,
      hour:   (seconds) => seconds / 3600,
      minute: (seconds) => seconds / 60
    },
    scale: {
      up: 'minute'
    },
    id:    'second',
    unit:  's',
    regex: /\b(?:s|seconds?|secs?)\b/i
  }
};

this.units.area = {
  square_kilometer: {
    conversions: {
      hectare:      (km2) => km2 * 1e2,
      square_meter: (km2) => km2 * 1e6,
      square_mile:  (km2) => km2 / (1.609344 * 1.609344),
      acre:         (km2) => km2 * (640 / (1.609344 * 1.609344)),
      square_yard:  (km2) => km2 * (3097600 / (1.609344 * 1.609344)),
      square_foot:  (km2) => km2 * (27878400 / (1.609344 * 1.609344)),
      square_inch:  (km2) => km2 * (4014489600 / (1.609344 * 1.609344))
    },
    system: 'metric',
    id:     'square_kilometer',
    unit:   'km2',
    regex:  /\b(?:km2|(?:sq|square) (?:kms?|kilometers?|kilometres?))\b/i
  },
  hectare: {
    conversions: {
      square_kilometer: (ha) => ha / 1e2,
      square_meter:     (ha) => ha * 1e4,
      square_mile:      (ha) => ha / (16.09344 * 16.09344),
      acre:             (ha) => ha * (6.40 / (1.609344 * 1.609344)),
      square_yard:      (ha) => ha * (30976 / (1.609344 * 1.609344)),
      square_foot:      (ha) => ha * (278784 / (1.609344 * 1.609344)),
      square_inch:      (ha) => ha * (40144896 / (1.609344 * 1.609344))
    },
    system: 'metric',
    id:     'hectare',
    unit:   'ha',
    regex:  /\b(?:ha|hectares?)\b/i
  },
  square_meter: {
    conversions: {
      square_kilometer: (m2) => m2 / 1e6,
      hectare:          (m2) => m2 / 1e4,
      square_mile:      (m2) => m2 / (1609.344 * 1609.344),
      acre:             (m2) => m2 * (640 / (1609.344 * 1609.344)),
      square_yard:      (m2) => m2 * (30976 / (160.9344 * 160.9344)),
      square_foot:      (m2) => m2 * (278784 / (160.9344 * 160.9344)),
      square_inch:      (m2) => m2 * (40144896 / (160.9344 * 160.9344))
    },
    system: 'metric',
    id:     'square_meter',
    unit:   'm2',
    regex:  /\b(?:m2|(?:sq|square) (?:m|mts?|meters?|metres?))\b/i
  },
  square_mile: {
    conversions: {
      square_kilometer: (mi2) => mi2 * (1.609344 * 1.609344),
      hectare:          (mi2) => mi2 * (16.09344 * 16.09344),
      square_meter:     (mi2) => mi2 * (1609.344 * 1609.344),
      acre:             (mi2) => mi2 * 640,
      square_yard:      (mi2) => mi2 * 3097600,
      square_foot:      (mi2) => mi2 * 27878400,
      square_inch:      (mi2) => mi2 * 4014489600
    },
    system: 'american/british',
    id:     'square_mile',
    unit:   'mi2',
    regex:  /\b(?:mi2|(?:sq|square) (?:mis?|miles?))\b/i
  },
  acre: {
    conversions: {
      square_kilometer: (acre) => acre * ((1.609344 * 1.609344) / 640),
      hectare:          (acre) => acre * ((16.09344 * 16.09344) / 640),
      square_meter:     (acre) => acre * ((1609.344 * 1609.344) / 640),
      square_mile:      (acre) => acre / 640,
      square_yard:      (acre) => acre * 4840,
      square_foot:      (acre) => acre * 43560,
      square_inch:      (acre) => acre * 6272640
    },
    system: 'american/british',
    id:     'acre',
    unit:   'acre',
    regex:  /\b(?:acres?)\b/i
  },
  square_yard: {
    conversions: {
      square_kilometer: (yd2) => yd2 * ((0.1609344 * 0.1609344) / 30976),
      hectare:          (yd2) => yd2 * ((1.609344 * 1.609344) / 30976),
      square_meter:     (yd2) => yd2 * ((160.9344 * 160.9344) / 30976),
      square_mile:      (yd2) => yd2 / 3097600,
      acre:             (yd2) => yd2 / 4840,
      square_foot:      (yd2) => yd2 * 9,
      square_inch:      (yd2) => yd2 * 1296
    },
    system: 'american/british',
    id:     'square_yard',
    unit:   'yd2',
    regex:  /\b(?:yd2|(?:sq|square) (?:yds?|yards?))\b/i
  },
  square_foot: {
    conversions: {
      square_kilometer: (ft2) => ft2 * ((0.1609344 * 0.1609344) / 278784),
      hectare:          (ft2) => ft2 * ((1.609344 * 1.609344) / 278784),
      square_meter:     (ft2) => ft2 * ((160.9344 * 160.9344) / 278784),
      square_mile:      (ft2) => ft2 / 27878400,
      acre:             (ft2) => ft2 / 43560,
      square_yard:      (ft2) => ft2 / 9,
      square_inch:      (ft2) => ft2 * 144
    },
    system: 'american/british',
    id:     'square_foot',
    unit:   'ft2',
    regex:  /\b(?:ft2|(?:sq|square) (?:foot|feet|ft)|sf)\b/i
  },
  square_inch: {
    conversions: {
      square_kilometer: (in2) => in2 * ((0.1609344 * 0.1609344) / 40144896),
      hectare:          (in2) => in2 * ((1.609344 * 1.609344) / 40144896),
      square_meter:     (in2) => in2 * ((160.9344 * 160.9344) / 40144896),
      square_mile:      (in2) => in2 / 4014489600,
      acre:             (in2) => in2 / 6272640,
      square_yard:      (in2) => in2 / 1296,
      square_foot:      (in2) => in2 / 144
    },
    system: 'american/british',
    id:     'square_inch',
    unit:   'in2',
    regex:  /\b(?:in2|(?:sq|square) (?:in|inch|inches))\b/i
  }
};

this.units.speed = {
  kilometer_per_hour: {
    conversions: {
      meter_per_second: (kmh) => kmh / 3.6,
      mile_per_hour:    (kmh) => kmh / 1.609344,
      foot_per_second:   (kmh) => kmh * (5280 / 5793.6384),
      knot:             (kmh) => kmh / 1.852
    },
    system: 'metric',
    id:     'kilometer_per_hour',
    unit:   'km/h',
    regex:  /\b(?:kms?[/](?:h|hour)|kms? [/] (?:h|hour)|kph|kmh|kms? per hour)\b/i
  },
  meter_per_second: {
    conversions: {
      kilometer_per_hour: (mps) => mps * 3.6,
      mile_per_hour:      (mps) => mps * (3.6 / 1.609344),
      foot_per_second:    (mps) => mps / 0.3048,
      knot:               (mps) => mps * (3.6 / 1.852)
    },
    system: 'metric',
    id:     'meter_per_second',
    unit:   'm/s',
    regex:  /\b(?:m[/](?:s|sec)|m [/] (?:s|sec)|mps)\b/i
  },
  mile_per_hour: {
    conversions: {
      kilometer_per_hour: (mph) => mph * 1.609344,
      meter_per_second:   (mph) => mph * (1.609344 / 3.6),
      foot_per_second:    (mph) => mph * (5280 / 3600),
      knot:               (mph) => mph * (1.609344 / 1.852)
    },
    system: 'american/british',
    id:     'mile_per_hour',
    unit:   'mph',
    regex:  /\b(?:mph|mi[/]h|mi [/] h)\b/i
  },
  foot_per_second: {
    conversions: {
      kilometer_per_hour: (fts) => fts * (1.609344 * (3600 / 5280)),
      meter_per_second:   (fts) => fts * 0.3048,
      mile_per_hour:      (fts) => fts * (3600 / 5280),
      knot:               (fts) => fts * ((3600 * 1.609344) / (5280 * 1.852))
    },
    system: 'american/british',
    id:     'foot_per_second',
    unit:   'ft/s',
    regex:  /\b(?:ft[/](?:s|sec)|ft [/] (?:s|sec)|fts)\b/i
  },
  knot: {
    conversions: {
      kilometer_per_hour: (kn) => kn * 1.852,
      meter_per_second:   (kn) => kn * (1.852 / 3.6),
      mile_per_hour:      (kn) => kn * (1.852 / 1.609344),
      foot_per_second:    (kn) => kn * ((1.852 * 5280) / (1.609344 * 3600))
    },
    id:     'knot',
    unit:   'kn',
    regex:  /\b(?:kn|knots?)\b/i
  }
};

this.units.volume = {
  liter: {
    conversions: {
      milliliter:     (l) => l * 1e3,
      us_gallon:      (l) => l / 3.785411784,
      us_quart:       (l) => l * (4 / 3.785411784),
      us_pint:        (l) => l * (8 / 3.785411784),
      us_fluid_ounce: (l) => l * (128 / 3.785411784),
      uk_gallon:      (l) => l / 4.54609,
      uk_quart:       (l) => l * (4 / 4.54609),
      uk_pint:        (l) => l * (8 / 4.54609),
      uk_fluid_ounce: (l) => l * (160 / 4.54609)
    },
    system: 'metric',
    id:     'liter',
    unit:   'l',
    regex:  /\b(?:l|liters?|ltr?|litres?|dm3)\b/i
  },
  milliliter: {
    conversions: {
      liter:          (ml) => ml / 1e3,
      us_gallon:      (ml) => ml / 3785.411784,
      us_quart:       (ml) => ml * (4 / 3785.411784),
      us_pint:        (ml) => ml * (8 / 3785.411784),
      us_fluid_ounce: (ml) => ml * (128 / 3785.411784),
      uk_gallon:      (ml) => ml / 4546.09,
      uk_quart:       (ml) => ml * (4 / 4546.09),
      uk_pint:        (ml) => ml * (8 / 4546.09),
      uk_fluid_ounce: (ml) => ml * (160 / 4546.09)
    },
    system: 'metric',
    id:     'milliliter',
    unit:   'ml',
    regex:  /\b(?:ml|milliliters?|millilitres?|milli ltr?|cm3)\b/i
  },
  us_gallon: {
    conversions: {
      liter:          (gal) => gal * 3.785411784,
      milliliter:     (gal) => gal * 3785.411784,
      us_quart:       (gal) => gal * 4,
      us_pint:        (gal) => gal * 8,
      us_fluid_ounce: (gal) => gal * 128,
      uk_gallon:      (gal) => gal * (3.785411784 / 4.54609),
      uk_quart:       (gal) => gal * (4 * (3.785411784 / 4.54609)),
      uk_pint:        (gal) => gal * (8 * (3.785411784 / 4.54609)),
      uk_fluid_ounce: (gal) => gal * (160 * (3.785411784 / 4.54609))
    },
    scale: {
      down: 'us_quart'
    },
    system: 'american',
    id:     'us_gallon',
    unit:   'us gal',
    regex:  /\b(?:us (?:gals?|gallons?)|gals?|gallons?)\b/i
  },
  us_quart: {
    conversions: {
      liter:          (qt) => qt * (3.785411784 / 4),
      milliliter:     (qt) => qt * (3785.411784 / 4),
      us_gallon:      (qt) => qt / 4,
      us_pint:        (qt) => qt * 2,
      us_fluid_ounce: (qt) => qt * 32,
      uk_gallon:      (qt) => qt * (3.785411784 / (4 * 4.54609)),
      uk_quart:       (qt) => qt * (3.785411784 / 4.54609),
      uk_pint:        (qt) => qt * (2 * (3.785411784 / 4.54609)),
      uk_fluid_ounce: (qt) => qt * (40 * (3.785411784 / 4.54609))
    },
    scale: {
      up:   'us_gallon',
      down: 'us_pint'
    },
    system: 'american',
    id:     'us_quart',
    unit:   'us qt',
    regex:  /\b(?:us (?:qts?|quarts?)|qts?|quarts?)\b/i
  },
  us_pint: {
    conversions: {
      liter:          (pt) => pt * (3.785411784 / 8),
      milliliter:     (pt) => pt * (3785.411784 / 8),
      us_gallon:      (pt) => pt / 8,
      us_quart:       (pt) => pt / 2,
      us_fluid_ounce: (pt) => pt * 16,
      uk_gallon:      (pt) => pt * (3.785411784 / (8 * 4.54609)),
      uk_quart:       (pt) => pt * (3.785411784 / (2 * 4.54609)),
      uk_pint:        (pt) => pt * (3.785411784 / 4.54609),
      uk_fluid_ounce: (pt) => pt * (20 * (3.785411784 / 4.54609))
    },
    scale: {
      up:   'us_quart',
      down: 'us_fluid_ounce'
    },
    system: 'american',
    id:     'us_pint',
    unit:   'us pt',
    regex:  /\b(?:us (?:pts?|pints?)|pts?|pints?)\b/i
  },
  us_fluid_ounce: {
    conversions: {
      liter:          (floz) => floz * (3.785411784 / 128),
      milliliter:     (floz) => floz * (3785.411784 / 128),
      us_gallon:      (floz) => floz / 128,
      us_quart:       (floz) => floz / 32,
      us_pint:        (floz) => floz / 16,
      uk_gallon:      (floz) => floz * (3.785411784 / (128 * 4.54609)),
      uk_quart:       (floz) => floz * (3.785411784 / (32 * 4.54609)),
      uk_pint:        (floz) => floz * (3.785411784 / (16 * 4.54609)),
      uk_fluid_ounce: (floz) => floz * (1.25 * (3.785411784 / 4.54609))
    },
    scale: {
      up: 'us_pint'
    },
    system: 'american',
    id:     'us_fluid_ounce',
    unit:   'us fl oz',
    regex:  /\b(?:us (?:fl|fluid) (?:oz|ounces?)|(?:fl|fluid) (?:oz|ounces?)|oz fl)\b/i
  },
  uk_gallon: {
    conversions: {
      liter:          (gal) => gal * 4.54609,
      milliliter:     (gal) => gal * 4546.09,
      us_gallon:      (gal) => gal * (4.54609 / 3.785411784),
      us_quart:       (gal) => gal * (4 * (4.54609 / 3.785411784)),
      us_pint:        (gal) => gal * (8 * (4.54609 / 3.785411784)),
      us_fluid_ounce: (gal) => gal * (128 * (4.54609 / 3.785411784)),
      uk_quart:       (gal) => gal * 4,
      uk_pint:        (gal) => gal * 8,
      uk_fluid_ounce: (gal) => gal * 160
    },
    scale: {
      down: 'uk_quart'
    },
    system: 'british',
    id:     'uk_gallon',
    unit:   'imp gal',
    regex:  /\b(?:(?:imp|imperial) (gals?|gallons?))\b/i
  },
  uk_quart: {
    conversions: {
      liter:          (qt) => qt * (4.54609 / 4),
      milliliter:     (qt) => qt * (4546.09 / 4),
      us_gallon:      (qt) => qt * (4.54609 / (4 * 3.785411784)),
      us_quart:       (qt) => qt * (4.54609 / 3.785411784),
      us_pint:        (qt) => qt * (2 * (4.54609 / 3.785411784)),
      us_fluid_ounce: (qt) => qt * (32 * (4.54609 / 3.785411784)),
      uk_gallon:      (qt) => qt / 4,
      uk_pint:        (qt) => qt * 2,
      uk_fluid_ounce: (qt) => qt * 40
    },
    scale: {
      up:   'uk_gallon',
      down: 'uk_pint'
    },
    system: 'british',
    id:     'uk_quart',
    unit:   'imp qt',
    regex:  /\b(?:(?:imp|imperial) (qts?|quarts?))\b/i
  },
  uk_pint: {
    conversions: {
      liter:          (pt) => pt * (4.54609 / 8),
      milliliter:     (pt) => pt * (4546.09 / 8),
      us_gallon:      (pt) => pt * (4.54609 / (8 * 3.785411784)),
      us_quart:       (pt) => pt * (4.54609 / (2 * 3.785411784)),
      us_pint:        (pt) => pt * (4.54609 / 3.785411784),
      us_fluid_ounce: (qt) => qt * (16 * (4.54609 / 3.785411784)),
      uk_gallon:      (pt) => pt / 8,
      uk_quart:       (pt) => pt / 2,
      uk_fluid_ounce: (pt) => pt * 20
    },
    scale: {
      up:   'uk_quart',
      down: 'uk_fluid_ounce'
    },
    system: 'british',
    id:     'uk_pint',
    unit:   'imp pt',
    regex:  /\b(?:(?:imp|imperial) (?:pts?|pints?))\b/i
  },
  uk_fluid_ounce: {
    conversions: {
      liter:          (floz) => floz * (4.54609 / 160),
      milliliter:     (floz) => floz * (4546.09 / 160),
      us_gallon:      (floz) => floz * (4.54609 / (160 * 3.785411784)),
      us_quart:       (floz) => floz * (4.54609 / (40 * 3.785411784)),
      us_pint:        (floz) => floz * (4.54609 / (20 * 3.785411784)),
      us_fluid_ounce: (floz) => floz * (0.8 * (4.54609 / 3.785411784)),
      uk_gallon:      (floz) => floz / 160,
      uk_quart:       (floz) => floz / 40,
      uk_pint:        (floz) => floz / 20
    },
    scale: {
      up: 'uk_pint'
    },
    system: 'british',
    id:     'uk_fluid_ounce',
    unit:   'imp fl oz',
    regex:  /\b(?:(?:imp|imperial) (?:fl|fluid) (?:oz|ounces?))\b/i
  }
};

this.units.mass = {
  us_ton: {
    conversions: {
      uk_ton:    (ton) => ton * (0.90718474 / 1.0160469088),
      ton:       (ton) => ton * 0.90718474,
      kilogram:  (ton) => ton * 907.18474,
      gram:      (ton) => ton * 907184.74,
      milligram: (ton) => ton * 907184740,
      stone:     (ton) => ton * (2000 / 14),
      pound:     (ton) => ton * 2000,
      ounce:     (ton) => ton * 32000
    },
    system: 'american',
    id:     'us_ton',
    unit:   'short tn',
    regex:  /\b(?:(?:short|us) (?:tns?|tons?))\b/i
  },
  uk_ton: {
    conversions: {
      us_ton:    (ton) => ton * (1.0160469088 / 0.90718474),
      ton:       (ton) => ton * 1.0160469088,
      kilogram:  (ton) => ton * 1016.0469088,
      gram:      (ton) => ton * 1016046.9088,
      milligram: (ton) => ton * 1016046908.8,
      stone:     (ton) => ton * 160,
      pound:     (ton) => ton * 2240,
      ounce:     (ton) => ton * 35840
    },
    system: 'british',
    id:     'uk_ton',
    unit:   'long tn',
    regex:  /\b(?:(?:long|imp|imperial) (?:tns?|tons?))\b/i
  },
  ton: {
    conversions: {
      us_ton:    (ton) => ton / 0.90718474,
      uk_ton:    (ton) => ton / 1.0160469088,
      kilogram:  (ton) => ton * 1e3,
      gram:      (ton) => ton * 1e6,
      milligram: (ton) => ton * 1e9,
      stone:     (ton) => ton * (1e3 / 6.35029318),
      pound:     (ton) => ton * (1e3 / 0.45359237),
      ounce:     (ton) => ton * (1e6 / 28.349523125)
    },
    system: 'metric',
    id:     'ton',
    unit:   'metric tn',
    regex:  /\b(?:metric (?:tns?|tons?|tonne|t)|t|tns?|tons?|tonne)\b/i
  },
  kilogram: {
    conversions: {
      us_ton:    (kg) => kg / 907.18474,
      uk_ton:    (kg) => kg / 1016.0469088,
      ton:       (kg) => kg / 1e3,
      gram:      (kg) => kg * 1e3,
      milligram: (kg) => kg * 1e6,
      stone:     (kg) => kg / 6.35029318,
      pound:     (kg) => kg / 0.45359237,
      ounce:     (kg) => kg * (1e3 / 28.349523125)
    },
    system: 'metric',
    id:     'kilogram',
    unit:   'kg',
    regex:  /\b(?:kgs?|kilograms?)\b/i
  },
  gram: {
    conversions: {
      us_ton:    (g) => g / 907184.74,
      uk_ton:    (g) => g / 1016046.9088,
      ton:       (g) => g / 1e6,
      kilogram:  (g) => g / 1e3,
      milligram: (g) => g * 1e3,
      stone:     (g) => g / 6350.29318,
      pound:     (g) => g / 453.59237,
      ounce:     (g) => g / 28.349523125
    },
    system: 'metric',
    id:     'gram',
    unit:   'g',
    regex:  /\b(?:g|grams?|grs?|gms?)\b/i
  },
  milligram: {
    conversions: {
      us_ton:   (mg) => mg / 907184740,
      uk_ton:   (mg) => mg / 1016046908.8,
      ton:      (mg) => mg / 1e9,
      kilogram: (mg) => mg / 1e6,
      gram:     (mg) => mg / 1e3,
      stone:    (mg) => mg / 6350293.18,
      pound:    (mg) => mg / 453592.37,
      ounce:    (mg) => mg / 28349.523125
    },
    system: 'metric',
    id:     'milligram',
    unit:   'mg',
    regex:  /\b(?:mgs?|milligrams?)\b/i
  },
  stone: {
    conversions: {
      us_ton:    (st) => st * (14 / 2000),
      uk_ton:    (st) => st / 160,
      ton:       (st) => st * 0.00635029318,
      kilogram:  (st) => st * 6.35029318,
      gram:      (st) => st * 6350.29318,
      milligram: (st) => st * 6350293.18,
      pound:     (st) => st * 14,
      ounce:     (st) => st * 224
    },
    system: 'american/british',
    id:     'stone',
    unit:   'st',
    regex:  /\b(?:st|stones?)\b/i
  },
  pound: {
    conversions: {
      us_ton:    (lb) => lb / 2000,
      uk_ton:    (lb) => lb / 2240,
      ton:       (lb) => lb * 0.00045359237,
      kilogram:  (lb) => lb * 0.45359237,
      gram:      (lb) => lb * 453.59237,
      milligram: (lb) => lb * 453592.37,
      stone:     (lb) => lb / 14,
      ounce:     (lb) => lb * 16
    },
    scale: {
      down: 'ounce'
    },
    system: 'american/british',
    id:     'pound',
    unit:   'lb',
    regex:  /\b(?:lbs?|pounds?)\b/i
  },
  ounce: {
    conversions: {
      us_ton:    (oz) => oz / 32000,
      uk_ton:    (oz) => oz / 35840,
      ton:       (oz) => oz * 0.000028349523125,
      kilogram:  (oz) => oz * 0.028349523125,
      gram:      (oz) => oz * 28.349523125,
      milligram: (oz) => oz * 28349.523125,
      stone:     (oz) => oz / 224,
      pound:     (oz) => oz / 16
    },
    scale: {
      up: 'pound'
    },
    system: 'american/british',
    id:     'ounce',
    unit:   'oz',
    regex:  /\b(?:oz|ounces?)\b/i
  }
};

this.units.length = {
  kilometer: {
    conversions: {
      meter:      (km) => km * 1e3,
      centimeter: (km) => km * 1e5,
      millimeter: (km) => km * 1e6,
      mile:       (km) => km / 1.609344,
      yard:       (km) => km / 0.0009144,
      foot:       (km) => km / 0.0003048,
      inch:       (km) => km / 0.0000254
    },
    system: 'metric',
    id:     'kilometer',
    unit:   'km',
    regex:  /\b(?:kms?|kilometers?|kilometres?)\b/i
  },
  meter: {
    conversions: {
      kilometer:  (m) => m / 1e3,
      centimeter: (m) => m * 1e2,
      millimeter: (m) => m * 1e3,
      mile:       (m) => m / 1609.344,
      yard:       (m) => m / 0.9144,
      foot:       (m) => m / 0.3048,
      inch:       (m) => m / 0.0254
    },
    system: 'metric',
    id:     'meter',
    unit:   'm',
    regex:  /\b(?:m|mts?|meters?|metres?)\b/i
  },
  centimeter: {
    conversions: {
      kilometer:  (cm) => cm / 1e5,
      meter:      (cm) => cm / 1e2,
      millimeter: (cm) => cm * 10,
      mile:       (cm) => cm / 160934.4,
      yard:       (cm) => cm / 91.44,
      foot:       (cm) => cm / 30.48,
      inch:       (cm) => cm / 2.54
    },
    system: 'metric',
    id:     'centimeter',
    unit:   'cm',
    regex:  /\b(?:cms?|centimeters?|centimetres?)\b/i
  },
  millimeter: {
    conversions: {
      kilometer:  (mm) => mm / 1e6,
      meter:      (mm) => mm / 1e3,
      centimeter: (mm) => mm / 10,
      mile:       (mm) => mm / 1609344,
      yard:       (mm) => mm / 914.4,
      foot:       (mm) => mm / 304.8,
      inch:       (mm) => mm / 25.4
    },
    system: 'metric',
    id:     'millimeter',
    unit:   'mm',
    regex:  /\b(?:mm|millimeters?|millimetres?)\b/i
  },
  mile: {
    conversions: {
      kilometer:  (mi) => mi * 1.609344,
      meter:      (mi) => mi * 1609.344,
      centimeter: (mi) => mi * 160934.4,
      millimeter: (mi) => mi * 1609344,
      yard:       (mi) => mi * 1760,
      foot:       (mi) => mi * 5280,
      inch:       (mi) => mi * 63360
    },
    scale: {
      down: 'yard'
    },
    system: 'american/british',
    id:     'mile',
    unit:   'mi',
    regex:  /\b(?:mis?|miles?)\b/i
  },
  yard: {
    conversions: {
      kilometer:  (yd) => yd * 0.0009144,
      meter:      (yd) => yd * 0.9144,
      centimeter: (yd) => yd * 91.44,
      millimeter: (yd) => yd * 914.4,
      mile:       (yd) => yd / 1760,
      foot:       (yd) => yd * 3,
      inch:       (yd) => yd * 36
    },
    scale: {
      up:   'mile',
      down: 'foot'
    },
    system: 'american/british',
    id:     'yard',
    unit:   'yd',
    regex:  /\b(?:yds?|yards?)\b/i
  },
  foot: {
    conversions: {
      kilometer:  (ft) => ft * 0.0003048,
      meter:      (ft) => ft * 0.3048,
      centimeter: (ft) => ft * 30.48,
      millimeter: (ft) => ft * 304.8,
      mile:       (ft) => ft / 5280,
      yard:       (ft) => ft / 3,
      inch:       (ft) => ft * 12
    },
    scale: {
      up:   'yard',
      down: 'inch'
    },
    system: 'american/british',
    id:     'foot',
    unit:   'ft',
    regex:  /(?:\b(?:ft|foot|feet)\b|'(?!')|`(?!`)|\u2032(?!\u2032))/i
  },
  inch: {
    conversions: {
      kilometer:  (inch) => inch * 0.00002534,
      meter:      (inch) => inch * 0.0254,
      centimeter: (inch) => inch * 2.54,
      millimeter: (inch) => inch * 25.4,
      mile:       (inch) => inch / 63360,
      yard:       (inch) => inch / 36,
      foot:       (inch) => inch / 12
    },
    scale: {
      up: 'foot'
    },
    system: 'american/british',
    id:     'inch',
    unit:   'in',
    regex:  /(?:\b(?:ins?|inch|inches)\b|''(?!')|"(?!")|``(?!`)|\u2033)/i
  }
};
