import request from "@/utils/request"

export function login(form) {
  return request({
    url: '/login',
    method: 'post',
    params: form
  })
}
