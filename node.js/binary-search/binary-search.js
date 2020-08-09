function binarySearch(array, value) {
  let start = 0;
  let end = array.length - 1;
  let middle = 0;
  while (start <= end) {
    middle = Math.round((start + end) / 2);
    if (array[middle] < value) {
      start = middle + 1;
    } else if (array[middle] > value) {
      end = middle - 1;
    } else {
      break;
    }
  }

  return array[middle] === value ? middle : -1;
}
