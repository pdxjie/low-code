const getters = {
  isMobile: state => state.app.isMobile,
  lang: state => state.app.lang,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.name,
  welcome: state => state.user.welcome,
  roles: state => state.user.roles,
  userInfo: state => state.user.info,
  addRouters: state => state.permission.addRouters,
  multiTab: state => state.app.multiTab,
  dataSource: state => state.database.dataSource,
  tables: state => state.database.tables,
  tableData: state => state.table.currentTableData,
  onlineTableData: state => state.table.onlineTableData,
  currentDataSource: state => state.table.currentDataSource
}

export default getters
