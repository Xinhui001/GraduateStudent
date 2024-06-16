<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 260px;margin-right: 10px" v-model="data.username" placeholder="请输入用户名查询" :prefix-icon="Search"/>
      <el-input style="width: 260px;margin-right: 10px" v-model="data.name" placeholder="请输入姓名查询" :prefix-icon="Search"/>
      <el-button @click="load" type="primary" style="margin-left: 10px">查询</el-button>
      <el-button @click="reset" type="info">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>

      <div>
        <el-table :data="data.tableData" style="width: 100%">
          <el-table-column prop="id" label="序号" width="70" />
          <el-table-column prop="username" label="用户名"/>
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="phone" label="手机号"/>
          <el-table-column prop="email" label="邮箱"/>
          <el-table-column prop="sex" label="性别"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button type="primary" @click="handleEdit(scope.row)" size="small" plain>编辑</el-button>
              <el-button type="danger" size="small" @click="del(scope.row.id)" plain>删除</el-button>

            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>


    <div>
      <el-pagination v-model:current-page="data.pageNum"  v-model:page-size="data.pageSize"
                     @current-change="handleCurrentChange"
                     background layout="prev, pager, next" :total="data.total" />
    </div>

    <el-dialog width="35%" v-model="data.formVisible" title="管理员信息" destroy-on-close>
      <el-form :model="data.form"  ref="formRef" :rules="rules" label-width="100px" label-position="right" style="padding-right: 30px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input show-password v-model="data.form.password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="data.form.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="data.form.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="data.form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible= false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>>



<script setup>
import {reactive, ref} from "vue";
import { Search } from '@element-plus/icons-vue'
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

  const baseUrl = '/admin'
  const data = reactive({
    username:'',
    name:'',
    tableData:[],
    total:0,
    pageNum:1,
    pageSize:5,
    formVisible:false,
    form:{}
  })



const load = () => {
  request.get(baseUrl+'/selectPage', {
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      username:data.username,
      name:data.name,
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}
load()

const handleCurrentChange = () => {
    load()
}

const reset = () => {
  data.username = ''
  data.name=''
  load()
}

const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
  ],
})
const formRef = ref()

const handleAdd = () => {
    data.form = {}
  data.formVisible = true
}
const save = () => {
  formRef.value.validate((valid) => {
    if(valid){
      request.request({
            url: data.form.id? baseUrl+'/update' : baseUrl+'/add',
            method: data.form.id? 'PUT' : 'POST',
            data: data.form
          }
      ).then(res =>{
        if(res.code === '200'){
          load()
          data.formVisible = false
          ElMessage.success("操作成功")

        }else{
          ElMessage.error(res.msg)
        }
      })

    }
  })
}
const handleEdit = (row) => {
    data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const del = (id) =>{
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？','删除确认',{type:'warning'}).then(res => {
    request.delete(baseUrl+'/delete/' + id).then(res => {
      if(res.code === '200'){
        load()
        ElMessage.success("操作成功")

      }else{
        ElMessage.error(res.msg)
      }
    })
  }).catch(res => {})
}

const handleImgUploadSuccess = (res) => {
  data.form.avatar = res.data
}
</script>