import request from '@/utils/request'

export default {
  getList() {
  return request({
    url: '/eduservice/table/list',
    method: 'get',
  })
},
backup(dbName) {
  return request({
    url: '/eduservice/table/backups/'+dbName,
    method: 'get',
  })
}
}