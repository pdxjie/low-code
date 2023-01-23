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
        <a-input v-model="generatorConfig.project" placeholder="请输入项目名称（英文）"/>
      </a-form-item>
      <a-form-item label="包名">
        <a-input v-model="generatorConfig.pPackage" placeholder="请输入包名（com.example.project）"/>
      </a-form-item>
      <a-form-item label="表名">
        <a-select
          mode="multiple"
          disabled
          :maxTagCount="4"
          :default-value="tables"
          style="width: 100%"
        ></a-select>
      </a-form-item>
      <a-form-item label="代码分层样式">
        <a-select default-value="业务分层" disabled/>
      </a-form-item>
      <a-form-item label="类名样式">
        <a-tabs default-active-key="1" @change="callback">
          <a-tab-pane key="1" tab="驼峰命名">
            <a-switch default-checked disabled />
          </a-tab-pane>
          <a-tab-pane key="2" tab="自定义命名" force-render>
            <a-input v-model="generatorConfig.customDesignate" placeholder="填写需要去除表名前缀(用英文逗号分隔)"/>
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
        filePath: '',
        outPath: '',
        project: '',
        pPackage: '',
        customDesignate: ''
      },
      tables: []
    }
  },
  watch: {
    generatorCodeVisible: {
      immediate: true,
      handler (newVal, oldVal) {
        if (newVal) {
          this.tables.length = 0
          this.tableNames.forEach(item => {
            this.tables.push(item.tableName)
          })
        } else {
          this.generatorConfig = {
            filePath: '',
            outPath: '',
            project: '',
            pPackage: '',
            customDesignate: ''
          }
        }
      }
    }
  },
  computed: {
    choseTables () {
      return this.$store.getters.choseTables
    }
  },
  methods: {
    loadData () {},
    onChange (val) {
      this.generatorConfig.outPath = val[0] + '\\' + val[1]
    },
    callback () {}
  }
}
</script>

<style scoped>

</style>
