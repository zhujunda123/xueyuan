<template>
    <div>

        <!-- <ul v-for="(value, key) in propsMap" :key="key">

		<li>{{key}}:{{value}}</li>
	</ul> -->

    <el-card class="box-card">
    <div slot="header" class="clearfix">
        <span>系统信息</span>
       
    </div>
    <div v-for="(value, key) in propsMap" :key="key" class="text item">
        <el-tag > {{key}}</el-tag>
       
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <el-tag type="info">{{value}}</el-tag>
    </div>
    </el-card>
   
    </div>
</template>

<script>
import system from '@/api/system'
export default {
    data(){
        return{
                propsMap:{}
        }
    },
    created(){
        this.getSystemInfo()

    },
    methods:{
        getSystemInfo(){
            system.getSystemInfo().then(
                response=>{
                    console.log(response.data.propsMap)
                    this.propsMap=response.data.propsMap
                }
            ).catch()
        }
    }
}
</script>
<style scoped>

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 100%;
  }

</style>