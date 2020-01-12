import request from '@/utils/request'

export default {
  getTeacherPageList(page, limit, searchObj) {
  return request({
    url: '/eduservice/teacher/moreConditionPageList/'+ page +'/'+limit,
    method: 'post',
    data: searchObj
  })
},
removeById(id) {
  return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
  })
},
//添加
saveTeacher(teacher) {
  return request({
      url: '/eduservice/teacher/addTeacher',
      method: 'post',
      data: teacher
  })
},
getTeacherId(id) {
  return request({
      url: '/eduservice/teacher/getTeacherInfo/'+id,
      method: 'get'
  })
},
updateTeacherById(id,teacher) {
  return request({
      url: '/eduservice/teacher/updateTeacher/'+id,
      method: 'put',
      data: teacher
  })
}
}