<template>
  <div>
    <a-form class="data-source-form" :form="datasourceInfo">
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="图标">
        <a-upload
          name="avatar"
          list-type="picture-card"
          class="avatar-uploader"
          :show-upload-list="false"
        >
          <img class="icon-style" v-if="imageUrl" :src="imageUrl" alt="avatar" />
          <div v-else>
            <a-icon :type="loading ? 'loading' : 'select'" />
            <div class="ant-upload-text">
              选择图标
            </div>
          </div>
        </a-upload>
        <a-button type="primary" @click="selectLogo">选择图标</a-button>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="数据源名称">
        <a-input v-model="datasourceInfo.sourceName" placeholder="请输入数据源名称"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="IP">
        <a-input v-model="datasourceInfo.sourceIp" placeholder="【默认】0.0.0.0"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="端口号">
        <a-input v-model="datasourceInfo.sourcePort" placeholder="【默认】3306"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="用户名">
        <a-input v-model="datasourceInfo.sourceAccount" placeholder="【默认】root"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="密码">
        <a-input-password v-model="datasourceInfo.sourcePassword" placeholder="请输入密码"/>
      </a-form-item>
    </a-form>
    <a-drawer
      title="选择图标"
      :closable="false"
      width="450"
      :visible="logoVisible"
      @close="onClose"
    >
      <LogoPicker ref="choseIcon"/>
      <div
        :style="{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e9e9e9',
          padding: '10px 16px',
          background: '#fff',
          textAlign: 'right',
          zIndex: 1,
        }"
      >
        <a-button :style="{ marginRight: '8px' }" @click="onClose">
          取消
        </a-button>
        <a-button type="primary" @click="choseSure">
          确认
        </a-button>
      </div>
    </a-drawer>
  </div>
</template>

<script>
import LogoPicker from '@/views/account/center/LogoPicker'
export default {
  name: 'AddDataSourceConfigInfo',
  props: ['addDataSourceVisible'],
  components: { LogoPicker },
  data () {
    return {
      formItemLayout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 16 }
      },
      loading: false,
      imageUrl: '',
      logoVisible: false,
      datasourceInfo: {
        sourceCover: '',
        sourceName: '',
        sourceAccount: '',
        sourcePassword: '',
        sourcePort: ''
      }
    }
  },
  watch: {
    addDataSourceVisible (nVal, oVal) {
      if (nVal) {
        this.datasourceInfo = {}
        this.imageUrl = ''
      }
    }
  },
  methods: {
    selectLogo () {
      this.logoVisible = true
    },
    onClose () {
      this.logoVisible = false
    },
    choseSure () {
      this.imageUrl = this.$refs.choseIcon.iconUrl
      this.datasourceInfo.sourceCover = this.$refs.choseIcon.iconUrl
      this.logoVisible = false
    }
  }
}
</script>

<style lang='less' scoped>
 ::v-deep .ant-form-item-control {
   line-height: 10px!important;
 }
 ::v-deep .ant-upload.ant-upload-select-picture-card > .ant-upload {
   cursor: default!important;
   pointer-events: none!important;
 }
 .icon-style {
   width: 102px;
   height: 102px;
 }
</style>
