import request from '@/utils/request'

export default {
  saveCourseInfo (courseInfo) {
    return request({
      url: '/eduservice/course',
      method: 'post',
      data: courseInfo
    })
  },
  getAllTeacherList () {
    return request({
      url: '/eduservice/teacher',
      method: 'get'
    })
  },
  //根据课程id查询课程信息
  getCourseInfofById (id) {
    return request({
      url: '/eduservice/course/getCourseInfo/' + id,
      method: 'get'
    })
  },
  //修改课程
  updateCourseInfoById (courseInfo) {
    return request({
      url: `/eduservice/course/updateCourseInfo/' + ${courseInfo.id}`,
      method: 'put',
      data: courseInfo
    })
  },
  getPageList (page, limit, searchObj) {
    return request({
      url: `/eduservice/course/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  removeById (id) {
    return request({
      url: `/eduservice/course/${id}`,
      method: 'delete'
    })
  },
  getCoursePublishInfoById (id) {
    return request({
      url: `/eduservice/course/course-publish-info/${id}`,
      method: 'get'
    })
  },

  publishCourse (id) {
    return request({
      url: `/eduservice/course/publish-course/${id}`,
      method: 'put'
    })
  }
}