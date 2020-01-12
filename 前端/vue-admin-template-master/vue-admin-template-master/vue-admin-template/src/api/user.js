import request from '@/utils/request'

export default {
getUserList() {
  return request({
    url: '/eduservice/user',
    method: 'get',
  })
},
removeUserbyId(id){
  return request({
    url: '/eduservice/user/'+id,
    method: 'delete',
  })
},
getUserById(id){
  return request({
    url: '/eduservice/user/'+id,
    method: 'get',
  })
},
upDataById(id,user){
  return request({
    url: '/eduservice/user/updateEduUser/'+id,
    method: 'put',
    data:user
  })
},
saveUser(user){
  return request({
    url: '/eduservice/user/addUser',
    method: 'post',
    data:user
  })
}
}