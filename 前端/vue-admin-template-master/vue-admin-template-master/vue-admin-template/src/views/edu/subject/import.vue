<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/课程分类.xls'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/eduservice/subject/import'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ fileUploadBtnText }}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
     data() {
        return {
        BASE_API: process.env.BASE_API, // 接口API地址
        fileUploadBtnText: '上传到服务器', // 按钮文字
        importBtnDisabled: false, // 按钮是否禁用,
        loading: false
        }
  },
  methods:{
      //提交表单的方法
      submitUpload(){
        this.fileUploadBtnText = '正在上传'
      this.importBtnDisabled = true
      this.loading = true
      this.$refs.upload.submit()

      },
      //上传成功调用的方法
      fileUploadSuccess(response){
          if (response.success === true) {
        this.fileUploadBtnText = '导入成功'
        this.loading = false
        this.$message({
            type: 'success',
            message: '导入成功'
        })
    } else {
        this.fileUploadBtnText = '导入失败'
        this.loading = false
        const messages = response.data.msgList
        let msgString = '<ul>'
        messages.forEach(msg => {
          msgString += `<li>${msg}</li>`
        })
        msgString += '</ul>'
        this.$alert(msgString, response.message, {
          dangerouslyUseHTMLString: true // 渲染返回值中的html
        })
    }

      },
     //上传失败调用的方法
      fileUploadError(){
        this.fileUploadBtnText = '导入失败'
        this.loading = false
        this.$message({
        type: 'error',
        message: '导入失败'
        })

      }
  }
    
}
</script>