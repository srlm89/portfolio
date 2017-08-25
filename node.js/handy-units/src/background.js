const api = this.browser || this.chrome;

const menus = [];

const newContextMenu = ($menus, { title, parentId }) => {
  const menuId = api.contextMenus.create({ title, parentId, contexts: ['selection'] });
  $menus.push(menuId);
  return menuId;
};

const clean = ($menus) => {
  while ($menus.length) {
    const id = $menus.pop();
    api.contextMenus.remove(id);
  }
};

/**
 * - Listens for message sent from content-script.
 * - The message should contain the user selection.
 * - We generate unit conversions from said selection.
 * - We create context menus for each conversion.
 */
api.runtime.onMessage.addListener((selection) => {
  clean(menus);
  const conversions = this.parseUnit(selection);
  if (conversions) {
    const parentId = newContextMenu(menus, { title: "Handy units: '%s'" });
    conversions.forEach((title) => {
      newContextMenu(menus, {
        title,
        parentId
      });
    });
  }
});
