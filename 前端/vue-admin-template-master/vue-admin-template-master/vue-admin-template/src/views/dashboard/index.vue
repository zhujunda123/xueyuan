<template>
  <div class="dashboard-container">
    <!-- <div class="dashboard-text">name:{{ name }}</div>
    <div class="dashboard-text">roles:<span v-for="role in roles" :key="role">{{ role }}</span></div> -->
 
       	
 <!-- <div class="dashboard-text">当前时间：{{date}}</div>
  -->
      <div class="dashboard-text">用户名:{{ name }}</div>
    <div class="dashboard-text">权限:<span v-for="role in roles" :key="role">{{ role }}</span></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  // data() {
  //   return {
  //     date: new Date("month dd,yyyy hh:mm:ss")
  //   };
  // },
  // created() {
  //   let _this = this; // 声明一个变量指向Vue实例this，保证作用域一致
  //   this.timer = setInterval(() => {
  //     _this.date = new Date(); // 修改数据date
  //   }, 1000)
  // },
  // beforeDestroy() {
  //   if (this.timer) {
  //     clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
  //   }
  // },
  // methods: {
     

  // },
 
  


}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
#times{
                width: 200px;
                height: 20px;
                border: 3px solid gray;  /*如果不加实线无法显示边框*/
            }

.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
