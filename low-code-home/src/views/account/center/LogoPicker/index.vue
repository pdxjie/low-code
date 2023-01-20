<template>
  <div>
    <a-list :grid="{ gutter: 10, column: 4 }" :data-source="iconImages">
      <a-list-item class="card-style" slot="renderItem" slot-scope="item, index">
        <a-card hoverable class="icon-style" @click="choseIcon(index)">
          <img :src="item" alt="">
        </a-card>
        <i class="iconfont icon-003-xuanze" :style="{color:currentIndex === index ? '#17c023':'#EEE'}"></i>
      </a-list-item>
    </a-list>
  </div>
</template>

<script>
import { iconImages } from '@/utils/icon-images'

export default {
  name: 'LogoPicker',
  data () {
    return {
      iconImages,
      currentIndex: -1,
      iconUrl: ''
    }
  },
  methods: {
    choseIcon (index) {
      this.currentIndex = index
      this.iconUrl = iconImages[index]
    }
  }
}
</script>

<style lang='less' scoped>
.card-style {
  text-align: center;
  .icon-style {
    width: 100px;
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 5px 5px;
    img {
      width: 95px;
      height: 95px;
    }
  }
}

</style>
