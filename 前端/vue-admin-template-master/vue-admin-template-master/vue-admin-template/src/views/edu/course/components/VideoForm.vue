<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog
    :visible.sync="dialogVisible"
    title="添加课时"
  >
    <el-form
      :model="video"
      label-width="120px"
    >
      <el-form-item label="课时标题">
        <el-input v-model="video.title" />
      </el-form-item>
      <el-form-item label="课时排序">
        <el-input-number
          v-model="video.sort"
          :min="0"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="是否免费">
        <el-radio-group v-model="video.free">
          <el-radio :label="true">免费</el-radio>
          <el-radio :label="false">默认</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="上传视频">
        <!-- 上传视频 TODO -->
      </el-form-item>
    </el-form>
    <div
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="close">取 消</el-button>
      <el-button
        type="primary"
        @click="saveOrUpdate"
      >确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import video from '@/api/video'
export default {

  // 父组件向子组件传值
  props: {
    courseId: {
      type: String,
      default: null
    }
  },

  data () {
    return {
      dialogVisible: false,
      video: {// 课时对象
        chapterId: '',
        title: '',
        sort: 0,
        free: false,
        videoSourceId: ''
      }
    }
  },

  methods: {
    // open () {
    //   this.dialogVisible = true
    // },
    open (chapterId, videoId) {
      this.dialogVisible = true
      this.video.chapterId = chapterId
      if (videoId) {
        video.getById(videoId).then(response => {
          this.video = response.data.item
        })
      }
    },

    save () {
      this.video.courseId = this.courseId
      console.log(this.video)
      video.save(this.video).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        })
        // 关闭组件
        this.close()
        // 调用父组件监听函数
        this.$emit('onSaveSuccess')
      })
    },

    close () {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },

    resetForm () {
      this.video.title = ''
      this.video.sort = 0
      this.video.chapterId = ''
      this.video.free = false
      this.video.videoSourceId = ''
    },
    saveOrUpdate () {
      if (!this.video.id) {
        this.save()
      } else {
        this.update()
      }
    },


    update () {
      video.updateById(this.video).then(response => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        // 关闭组件
        this.close()
        // 调用父组件监听函数
        this.$emit('onSaveSuccess')
      })
    }
  }
}
</script>