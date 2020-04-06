const stats = {};

module.exports = {
  logEvents,
  logMemory,
  logPushes,
  logWrites
};

/**
 * Start logging memory usage periodically
 */
function logMemory({ intervalInMs = 500 } = {}) {
  const loopStatus = { active: true };
  logMemoryRecursive();

  return {
    stop: () => (loopStatus.active = false)
  };

  function logMemoryRecursive() {
    const { rss } = process.memoryUsage();
    const params = { rss, ...stats };
    console.log('#', Object.keys(params).map((key) => `${key},${params[key]}`).join(','));

    if (loopStatus.active) {
      setTimeout(logMemoryRecursive, intervalInMs);
    }
  }
};

/**
 * Wrap the specified stream in order to log read events
 */
function logPushes(id, stream) {
  const base = stream.push;
  return Object.assign(stream, { push: pushWrapper });

  function pushWrapper(...args) {
    if (args[0]) {
      const key = `${id}.p`;
      stats[key] = (stats[key] || 0) + args[0].length;
    }

    return base.apply(stream, args);
  }
}

/**
 * Wrap the specified stream in order to log write events
 */
function logWrites(id, stream) {
  const base = stream.write;
  return Object.assign(stream, { write: writeWrapper });

  function writeWrapper(...args) {
    const key = `${id}.w`;
    stats[key] = (stats[key] || 0) + args[0].length;
    return base.apply(stream, args);
  }
}

/**
 * Wrap the specified stream in order to log emitted events
 */
function logEvents(id, stream) {
  const base = stream.emit;
  const metrics = { count: {}, data: {} };
  return Object.assign(stream, { emit: emitWrapper });

  function emitWrapper(...args) {
    const event = args[0];
    metrics.count[event] = (metrics.count[event] || 0) + 1;
    if (event === 'data') {
      const size = args[1].length;
      if ((metrics.data.max || size) <= size) {
        metrics.data.max = size;
      }
      if ((metrics.data.min || size) >= size) {
        metrics.data.min = size;
      }
      metrics.data.total = (metrics.data.total || 0) + size;
    }
    if (!['readable', 'data', 'drain', 'pause', 'resume'].includes(event)) {
      console.log(id, event, JSON.stringify(metrics));
    }
    return base.apply(stream, args);
  }
}
