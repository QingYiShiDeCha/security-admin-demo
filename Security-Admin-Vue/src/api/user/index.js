import request from "@/utils/request"

export function ListUser() {
  return request({
    url: '/sys/user/list',
    method: 'get'
  })
}
