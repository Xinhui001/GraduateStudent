<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 260px;margin-right: 10px" v-model="data.className" placeholder="请输入班级名称查询" :prefix-icon="Search"/>
      <el-input style="width: 260px;margin-right: 10px" v-model="data.majorName" placeholder="请输入专业名称查询" :prefix-icon="Search"/>
      <el-button @click="load" type="primary" style="margin-left: 10px">查询</el-button>
      <el-button @click="reset" type="info">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>

      <div>
        <el-table :data="data.tableData" style="width: 100%">
          <el-table-column prop="classId" label="序号" width="70" />
          <el-table-column prop="className" label="班级名称"/>
          <el-table-column prop="majorName" label="专业名称"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button type="primary" @click="handleEdit(scope.row)" size="small" plain>编辑</el-button>


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

    <el-dialog width="35%" v-model="data.formVisible" title="班级信息">
      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right: 30px">
        <el-form-item label="班级名称">
          <el-input v-model="data.form.className" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业名称">
          <el-input v-model="data.form.majorName" autocomplete="off" />
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
import {reactive} from "vue";
import { Search } from '@element-plus/icons-vue'
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const baseUrl = '/class'
const data = reactive({
  classId: 0,
  className: "",
  majorName: "",
  tableData:[],
  total:0,
  pageNum:1,
  pageSize:10,
  formVisible:false,
  form:{
    classId: 0,
    className: "",
    majorName: "",
  }
})

const load = () => {
  request.get(baseUrl+'/selectPage', {
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      className:data.className,
      majorName:data.majorName,
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
  data.className = ''
  data.majorName=''
  load()
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const save = () => {
  request.request({
        url: data.form.classId? baseUrl+'/update' : baseUrl+'/add',
        method: data.form.classId? 'POST' : 'POST',
        data: data.form
      }
  ).then(res =>{
    if(res.code === 0){
      load()
      data.formVisible = false
      ElMessage.success("操作成功")

    }else{
      ElMessage.error(res.msg)
    }
  })
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

</script>