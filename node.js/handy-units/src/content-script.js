const api = this.browser || this.chrome;

let selection = '';
let processed = null;

/**
 * - Sends message to background script with caught selection.
 */
this.document.onmouseup = () => {
  if (selection && selection.length > 0 && processed !== selection) {
    api.runtime.sendMessage(processed = selection);
  }
};

/**
 * - Catches user selection.
 * - Sends message to background script.
 */
this.document.onselectionchange = (event) => {
  const target = event.target;
  selection = target.getSelection().toString();
};
