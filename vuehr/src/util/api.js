import axios from "axios"
import {Message} from "element-ui"

let base = '';

axios.interceptors.response.use(res => {
  // res.status：服务器自带的信息
  // res.data.status：后端自定义的信息
  // 这里含义为：如果请求响应成功但业务方面错误（如：请求成功但是账号密码不正确导致的业务错误）
  if (res.status && res.status == 200 && res.data.status == 500) {
    Message.error(res.data.msg);
    return;
  }
  if (res.data.msg){
    Message.success(res.data.msg)
  }
  // 返回正确信息
  return res.data
}, error => {
  if (error.response.status == 504 || error.response.status == 404) {
    Message.error("服务器被吃了")
  } else if (error.response.status == 403) {
    Message.error("权限不足，请联系管理员")
  }
  else if (error.response.status == 401) {
    Message.error("尚未登陆，请登录")
  } else {
    if (error.response.data.msg) {
      Message.error(error.response.data.msg)
    } else {
      Message.error("未知错误！")
    }
  }
})

export const postKeyValueRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = '';
      for (let i in data) {
        ret += encodeURIComponent(i) + "=" + encodeURIComponent(data[i]) + '&'
      }
      return ret;
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params
  })
}
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    data: params
  })
}
export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    data: params
  })
}
