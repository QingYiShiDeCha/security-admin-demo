import axios from "axios"
import { ElMessage } from "element-plus"

import { useAuthStore } from "@/stores/auth"

// 创建 axios 实例
const request = axios.create({
  baseURL: '/api', // 请求基础路径
  timeout: 12000   // 请求超时时间
});

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    const { getToken } = useAuthStore()
    // 在请求发送之前做一些事情，比如添加 token
    const token = getToken;
    console.log('请求拦截器', token)
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 如果有 token，附加到请求头
    }
    const requestData = config.params
    console.log('请求数据', requestData)
    // 可以在这里添加一些 loading 状态
    return config; // 返回配置，继续请求
  },
  (error) => {
    // 处理请求错误
    ElMessage.error("请求发送失败，请检查网络或稍后再试");
    return Promise.reject(error); // 拒绝请求并返回错误
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    // 处理响应数据
    const { data } = response;
    console.log('res', response)
    console.log('data', data)
    if (data.code !== 200) {
      // 如果接口返回的状态码不是 200，认为是错误
      ElMessage.error(data.message || "请求失败");
      return Promise.reject(new Error(data.message || "Error"));
    } else {
      // 正常返回数据
      return data;
    }
  },
  (error) => {
    // 处理响应错误
    let message = "请求失败";

    if (error.response) {
      // 根据 HTTP 状态码自定义错误提示
      const status = error.response.status;
      switch (status) {
        case 400:
          message = "请求错误";
          break;
        case 401:
          message = "未授权，请重新登录";
          // 可以在这里执行登出操作
          break;
        case 403:
          message = "拒绝访问";
          break;
        case 404:
          message = "请求地址不存在";
          break;
        case 500:
          message = "服务器内部错误";
          break;
        default:
          message = "未知错误";
      }
    }

    ElMessage.error(message);
    return Promise.reject(error); // 拒绝响应并返回错误
  }
);

export default request;
