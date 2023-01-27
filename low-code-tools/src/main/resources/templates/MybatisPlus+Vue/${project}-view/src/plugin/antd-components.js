import Vue from 'vue'
import 'ant-design-vue/dist/antd.css'
import {
  Button,
  Layout,
  Menu,
  Icon,
  Avatar,
  Dropdown,
  Card,
  Radio,
  Modal,
  Notification,
  Popconfirm,
  Table,
  Divider,
  Spin,
  Form,
  Select,
  DatePicker,
  Input,
  Tag,
  Row,
  Col,
  Pagination,
  Tabs,
  Result,
  InputNumber
} from 'ant-design-vue'
Vue.use(InputNumber)
Vue.use(Result)
Vue.use(Pagination)
Vue.use(Tabs)
Vue.use(Row)
Vue.use(Col)
Vue.use(Tag)
Vue.use(Input)
Vue.use(DatePicker)
Vue.use(Select)
Vue.use(Form)
Vue.use(Spin)
Vue.use(Popconfirm)
Vue.use(Button)
Vue.use(Layout)
Vue.use(Menu)
Vue.use(Icon)
Vue.use(Avatar)
Vue.use(Dropdown)
Vue.use(Card)
Vue.use(Radio)
Vue.use(Modal)
Vue.use(Divider)
Vue.use(Table)
Vue.prototype.$notification = Notification
