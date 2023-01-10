export const tableColumn = [
  {
    title: '表名',
    dataIndex: 'tableName',
    align: 'center'
  },
  {
    title: '表描述',
    dataIndex: 'desc',
    align: 'center'
  },
  {
    title: '版本',
    dataIndex: 'version',
    align: 'center'
  },
  {
    title: '更新状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' },
    align: 'center'
  },
  {
    title: '操作',
    dataIndex: 'action',
    scopedSlots: { customRender: 'action' },
    align: 'center'
  }
]

export const databaseColumns = [
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
    dataIndex: 'primaryKey',
    align: 'center',
    scopedSlots: { customRender: 'primaryKey' }
  },
  {
    title: '允许空值',
    dataIndex: 'isNull',
    align: 'center',
    scopedSlots: { customRender: 'isNull' }
  }
]
