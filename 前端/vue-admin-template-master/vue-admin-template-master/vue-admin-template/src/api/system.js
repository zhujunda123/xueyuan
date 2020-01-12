import request from '@/utils/request'

export default {
getSystemInfo() {
  return request({
    url: '/eduservice/system',
    method: 'get',
  })
}
}