<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps
      :active="active"
      finish-status="success"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="发布课程" />
    </el-steps>

    <!-- 课程信息表单 TODO -->
    <!-- 课程信息表单 -->
    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 所属分类 TODO -->
      <!-- 所属分类：级联下拉列表 -->
      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select
          @change="subjectLevelOneChanged"
          v-model="courseInfo.subjectParentId"
          placeholder="请选择"
        >
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select
          v-model="courseInfo.subjectId"
          placeholder="请选择"
        >
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择"
        >
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介 TODO -->
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce
          :height="300"
          v-model="courseInfo.description"
        />
      </el-form-item>
      <!-- 课程封面 TODO -->

      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        /> 元
      </el-form-item>
    </el-form>

    <div style="text-align:center">
      <el-button
        :disabled="saveBtnDisabled"
        type="primary"
        @click="next()"
      >保存并下一步</el-button>
    </div>
  </div>
</template>
<script>
const defaultForm = {
  title: '',
  subjectId: '',
  subjectParentId: '',
  teacherId: '',
  lessonNum: 0,
  description: '',
  cover: '',
  price: 0
}
import course from '@/api/course'
import subject from '@/api/subject'
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },
  data () {
    return {
      teacherList: [],
      active: 0,
      courseInfo: defaultForm,
      saveBtnDisabled: false, // 保存按钮是否禁用
      subjectNestedList: [],//一级分类列表
      subSubjectList: []//二级分类列表
    }
  },

  watch: {
    $route (to, from) {
      console.log('watch $route')
      this.init()
    }
  },

  created () {
    console.log('info created')
    this.init()
  },

  methods: {
    subjectLevelOneChanged (value) {
      console.log(value)
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === value) {
          this.subSubjectList = this.subjectNestedList[i].children
          this.courseInfo.subjectId = ''
        }
      }
    },
    getLevelAll () {
      subject.getAllSubjectList().then(response => {
        this.subjectNestedList = response.data.items
      })
    },
    getTeacherList () {
      course.getAllTeacherList().then(res => {
        this.teacherList = res.data.items
      })
    },
    init () {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.fetchCourseInfoById(id)
        console.log(id)
      } else {
        this.courseInfo = { ...defaultForm }
        this.getTeacherList()
        this.getLevelAll()
      }
    },
    fetchCourseInfoById (id) {
      course.getCourseInfofById(id).then(response => {
        this.courseInfo = response.data.item
        subject.getAllSubjectList().then(response => {
          this.subjectNestedList = response.data.items

          // 填充二级菜单：遍历一级菜单列表，
          for (let i = 0; i < this.subjectNestedList.length; i++) {
            // 找到和courseInfo.subjectParentId一致的父类别记录
            if (this.subjectNestedList[i].id === this.courseInfo.subjectParentId) {
              // 拿到当前类别下的子类别列表，将子类别列表填入二级下拉菜单列表
              this.subSubjectList = this.subjectNestedList[i].children
            }
          }

        })
        this.getTeacherList()
      })
    },
    next () {
      console.log('next')
      // this.saveBtnDisabled = true
      if (!this.courseInfo.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 保存
    saveData () {
      course.saveCourseInfo(this.courseInfo).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        })
        return response// 将响应结果传递给then
      }).then(response => {
        this.$router.push({ path: '/edu/course/chapter/' + response.data.courseId })
      })
    },

    updateData () {
      // this.saveBtnDisabled = true
      console.log(11111)
      console.log(this.courseInfo)
      course.updateCourseInfoById(this.courseInfo).then(response => {
        console.log(2222)
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
      }).then(() => {
        this.$router.push({ path: '/edu/course/chapter/' + this.courseInfo.id })
      })
    }
  }
}
</script>

<style scoped>
.tinymce-container {
  position: relative;
  line-height: normal;
}
</style>
