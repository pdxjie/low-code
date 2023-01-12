<template>
  <div>
    <a-form class="data-source-form">
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="图标">
        <a-upload
          name="avatar"
          list-type="picture-card"
          class="avatar-uploader"
          :show-upload-list="false"
          action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          :before-upload="beforeUpload"
          @change="handleChange"
        >
          <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
          <div v-else>
            <a-icon :type="loading ? 'loading' : 'plus'" />
            <div class="ant-upload-text">
              上传
            </div>
          </div>
        </a-upload>
        <a-button type="primary" @click="selectLogo">选择图标</a-button>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="数据源名称">
        <a-input placeholder="请输入数据源名称"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="IP">
        <a-input placeholder="【默认】0.0.0.0"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="端口号">
        <a-input placeholder="【默认】3306"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="用户名">
        <a-input placeholder="【默认】root"/>
      </a-form-item>
      <a-form-item
        :label-col="formItemLayout.labelCol"
        :wrapper-col="formItemLayout.wrapperCol"
        label="密码">
        <a-input-password placeholder="请输入密码"/>
      </a-form-item>
    </a-form>
    <a-drawer
      title="选择图标"
      :placement="placement"
      :closable="false"
      width="450"
      :visible="logoVisible"
      @close="onClose"
    >
      <LogoPicker/>
    </a-drawer>
  </div>
</template>

<script>
import LogoPicker from '@/views/account/center/LogoPicker'
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
export default {
  name: 'EditDataSourceConfigInfo',
  components: { LogoPicker },
  data () {
    return {
      formItemLayout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 16 }
      },
      loading: false,
      imageUrl: '',
      logoVisible: false
    }
  },
  methods: {
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, imageUrl => {
          this.imageUrl = imageUrl
          this.loading = false
        })
      }
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!')
      }
      return isJpgOrPng && isLt2M
    },
    selectLogo () {
      this.logoVisible = true
    },
    onClose () {
      this.logoVisible = false
    }
  }
}
</script>

<style lang='less' scoped>
 ::v-deep .ant-form-item-control {
   line-height: 10px!important;
 }
</style>
