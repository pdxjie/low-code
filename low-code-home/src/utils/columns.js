export const tableColumn = [
  {
    title: '#',
    dataIndex: 'key',
    key: 'key',
    align: 'center',
    customRender: (text, record, index) => `${index + 1}`
  },
  {
    title: '表名',
    dataIndex: 'tableName',
    align: 'center'
  },
  {
    title: '表描述',
    dataIndex: 'remark',
    align: 'center'
  },
  {
    title: '所属数据库',
    dataIndex: 'tableCat',
    align: 'center'
  },
  {
    title: '表类型',
    dataIndex: 'tableType',
    align: 'center'
  }
]

export const databaseColumns = [
  {
    title: '#',
    dataIndex: 'key',
    key: 'key',
    align: 'center',
    width: 50,
    customRender: (text, record, index) => `${index + 1}`
  },
  {
    title: '字段名称',
    dataIndex: 'field',
    align: 'center',
    scopedSlots: { customRender: 'field' }
  },
  {
    title: '字段备注',
    dataIndex: 'fieldComment',
    align: 'center',
    scopedSlots: { customRender: 'fieldComment' }
  },
  {
    title: '字段长度',
    dataIndex: 'fieldLength',
    align: 'center',
    scopedSlots: { customRender: 'fieldLength' }
  },
  {
    title: '字段类型',
    dataIndex: 'fieldType',
    align: 'center',
    scopedSlots: { customRender: 'fieldType' }
  },
  {
    title: '主键',
    dataIndex: 'isPrimaryKey',
    align: 'center',
    scopedSlots: { customRender: 'isPrimaryKey' }
  },
  {
    title: '允许空值',
    dataIndex: 'isNull',
    align: 'center',
    scopedSlots: { customRender: 'isNull' }
  }
]

export const tableColumns = [
  {
    title: '#',
    dataIndex: 'key',
    key: 'key',
    align: 'center',
    customRender: (text, record, index) => `${index + 1}`
  },
  {
    title: '表名',
    dataIndex: 'tableName',
    align: 'center'
  },
  {
    title: '表描述',
    dataIndex: 'remark',
    align: 'center'
  },
  {
    title: '所属数据库',
    dataIndex: 'tableCat',
    align: 'center'
  },
  {
    title: '表类型',
    dataIndex: 'tableType',
    align: 'center'
  }
]

export const userColumns = [
  {
    title: '序号',
    dataIndex: 'key',
    key: 'key',
    align: 'center',
    customRender: (text, record, index) => `${index + 1}`
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    align: 'center',
    scopedSlots: { customRender: 'avatar' }
  },
  {
    title: '昵称',
    dataIndex: 'nickName',
    align: 'center'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    align: 'center'
  },
  {
    title: '注册时间',
    dataIndex: 'registerTime',
    align: 'center'
  }
]
