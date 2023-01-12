<template>
  <div>
    <a-form :form="generatorConfig" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="代码生成目录">
        <Cascader
          :options="options"
          :load-data="loadData"
          placeholder="请选择代码生成目录"
          @change="onChange"
        />
      </a-form-item>
      <a-form-item label="项目名称">
        <a-input placeholder="请输入项目名称（英文）"/>
      </a-form-item>
      <a-form-item label="包名">
        <a-input placeholder="请输入包名（小写）"/>
      </a-form-item>
      <a-form-item label="表名">
        <a-select
          mode="multiple"
          disabled
          :maxTagCount="4"
          :default-value="tableNames"
          style="width: 100%"
        ></a-select>
      </a-form-item>
      <a-form-item label="代码分层样式">
        <a-select default-value="业务分层" disabled/>
      </a-form-item>
      <a-form-item label="类名样式">
        <a-tabs default-active-key="1" @change="callback">
          <a-tab-pane key="1" tab="驼峰命名">
            <a-switch default-checked disabled @change="onChange" />
          </a-tab-pane>
          <a-tab-pane key="2" tab="自定义命名" force-render>
            <a-input placeholder="填写需要去除表名前缀"/>
          </a-tab-pane>
        </a-tabs>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { Cascader } from 'ant-design-vue'

export default {
  name: 'GeneratorCode',
  components: { Cascader },
  // eslint-disable-next-line vue/require-prop-types
  props: ['generatorCodeVisible', 'options', 'tableNames'],
  data () {
    return {
      generatorConfig: {
        filePath: ''
      },
      tables: []
    }
  },
  watch: {
    generatorCodeVisible: {
      immediate: true,
      handler (newVal, oldVal) {
        console.log(newVal, oldVal)
        if (!newVal) {
          this.tables = []
        }
      }
    }
  },
  mounted () {
    console.log('gen', this.tableNames)
  },
  methods: {
    loadData () {},
    onChange () {},
    callback () {}
  }
}
</script>

<style scoped>

</style>
