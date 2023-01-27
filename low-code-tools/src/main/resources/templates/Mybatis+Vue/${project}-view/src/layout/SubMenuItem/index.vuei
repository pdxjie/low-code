 <template>
    <a-sub-menu :key="menuInfo.path" v-bind="$props" v-on="$listeners">
        <span slot="title">
            <a-icon :type="menuInfo.meta.icon" /><span>{{ menuInfo.meta.title }}</span>
         </span>
         <template v-for="item in menuInfo.children">
             <a-menu-item v-if="!item.children" :key="item.path" @click="()=>$router.push({path:item.path})">
                  <span>{{ item.meta.title }}</span>
              </a-menu-item>
              <sub-menu-item v-else :key="item.path" :menu-info="item" />
        </template>

    </a-sub-menu>
 </template>

<script>
import { Menu } from 'ant-design-vue'
export default {
  name: 'SubMenuItem',
  props: {
    ...Menu.SubMenu.props,
    menuInfo: {
      type: Object
    }
  }
}
</script>

<style scoped>

</style>
