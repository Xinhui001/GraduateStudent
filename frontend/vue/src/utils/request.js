import { ElMessage } from 'element-plus'
import router from '../router'
import axios from "axios";

const request = axios.create({
    baseURL: 'http://127.0.0.1:8080/api'
});

request.defaults.withCredentials = true;

// request 拦截器
// 可以自请求发送前对请求做一些处理
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // 当权限验证不通过的时候给出提示
        if (res.code === '401') {
            ElMessage.error(res.msg);
            router.push("/login")
        }
        return res;
    },
        error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)


//拦截器
// 添加请求拦截器
// request.interceptors.request.use(function (config) {
//     // 在发送请求之前做些什么
//     console.log("请求发送了",config)
//     return config;
// }, function (error) {
//     // 对请求错误做些什么
//     return Promise.reject(error);
// });
//
// // 添加响应拦截器
// request.interceptors.response.use(function (response) {
//     // 对响应数据做点什么
//     console.log("请求收到了了",response)
//     return response.data;
// }, function (error) {
//     // 对响应错误做点什么
//     return Promise.reject(error);
// });


export default request
