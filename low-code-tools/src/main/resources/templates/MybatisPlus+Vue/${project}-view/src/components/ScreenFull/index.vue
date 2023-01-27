<template>
  <div @click="onToggle">
    <i class="iconfont" :class="isFullscreen ? 'icon-tuichuquanping':'icon-quanping'" style="font-size: 24px"></i>
  </div>
</template>

<script>
import screenfull from 'screenfull'

export default {
  name: 'ScreenFull',
  data () {
    return {
      isFullscreen: false
    }
  },
  mounted () {
    screenfull.on('change', this.change)
  },
  beforeDestroy () {
    screenfull.off('change', this.change)
  },
  methods: {
    onToggle () {
      this.isFullscreen = !this.isFullscreen
      screenfull.toggle()
    },
    change () {
      this.isFullscreen = screenfull.isFullscreen
    }
  }
}
</script>

<style scoped>

</style>
