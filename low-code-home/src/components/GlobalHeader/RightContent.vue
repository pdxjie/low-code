<template>
  <div :class="wrpCls">
    <avatar-dropdown v-show="hasToken" :menu="showMenu" :current-user="currentUser" :class="prefixCls" />
    <a-tooltip v-show="!hasToken" placement="bottom">
      <template slot="title">
        <span>登录享用更多功能</span>
      </template>
      <a-avatar @click="toLogin" style="margin-right: 20px" size="large" class="antd-pro-global-header-index-avatar" />
    </a-tooltip>

  </div>
</template>

<script>
import AvatarDropdown from './AvatarDropdown'

export default {
  name: 'RightContent',
  components: {
    AvatarDropdown
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-global-header-index-action'
    },
    isMobile: {
      type: Boolean,
      default: () => false
    },
    topMenu: {
      type: Boolean,
      required: true
    },
    theme: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      showMenu: true,
      currentUser: {}
    }
  },
  computed: {
    wrpCls () {
      return {
        'ant-pro-global-header-index-right': true,
        [`ant-pro-global-header-index-${(this.isMobile || !this.topMenu) ? 'light' : this.theme}`]: true
      }
    },
    hasToken () {
      return this.$store.getters.token
    }
  },
  mounted () {
    setTimeout(() => {
      this.currentUser = {
        name: 'Serati Ma'
      }
    }, 1500)
  },
  methods: {
    toLogin () {
      this.$router.push({ name: 'login' })
    }
  }
}
</script>
