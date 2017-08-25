const NUMBERS_REGEX = [
  { numberSeparator: /[ ,]/g, decimalMark: /[.]/,  regex: /\b\d{1,3}(?:[,]\d{3})+(?:[.]\d(?:\d| \d)*)?(?!\d)/ },
  { numberSeparator: /[ ]/g,  decimalMark: /[.,]/, regex: /\b\d{1,3}(?:[ ]\d{3})+(?:[.,]\d(?:\d| \d)*)?(?!\d)/ },
  { numberSeparator: /[ ]/g,  decimalMark: /[.]/,  regex: /(?:\b\d+)?[.]\d(?:\d| \d)*(?!\d)/ },
  { numberSeparator: /[ .]/g, decimalMark: /[,]/,  regex: /\b\d{1,3}(?:[.]\d{3})+(?:[,]\d(?:\d| \d)*)?(?!\d)/ },
  { numberSeparator: /[ ]/g,  decimalMark: /[,]/,  regex: /\b\d+(?:[,]\d(?:\d| \d)*)?(?!\d)/ }
];

this.parseNumber = (text) => {
  let bestMatch = null;
  NUMBERS_REGEX.forEach(({ regex, numberSeparator, decimalMark }) => {
    const match = regex.exec(text);
    if (match) {
      const parsed = match[0].replace(numberSeparator, '').replace(decimalMark, '.');
      if (!bestMatch || match[0].length > bestMatch.source.length) {
        bestMatch = { parsed, source: match[0], match };
      }
    }
  });
  return (!bestMatch) ? null : {
    value:  Number(bestMatch.parsed),
    source: bestMatch.source,
    start:  bestMatch.match.index,
    end:    (bestMatch.match.index + bestMatch.source.length) - 1
  };
};
