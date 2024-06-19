<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 260px;margin-right: 10px" v-model="data.studentName" placeholder="请输入学生姓名查询" :prefix-icon="Search"/>
      <el-input style="width: 260px;margin-right: 10px" v-model="data.employmentStatus" placeholder="请输入就业状态查询" :prefix-icon="Search"/>
      <el-button @click="load" type="primary" style="margin-left: 10px">查询</el-button>
      <el-button @click="reset" type="info">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>

      <div>
        <el-table :data="data.tableData" style="width: 100%">
          <el-table-column prop="graduateId" label="序号" width="70" />
          <el-table-column prop="studentName" label="学生姓名"/>
          <el-table-column prop="gender" label="性别"/>
          <el-table-column prop="majorName" label="专业"/>
          <el-table-column prop="className" label="班级"/>
          <el-table-column prop="graduationYear" label="毕业年份"/>
          <el-table-column prop="birthdate" label="出生日期"/>
          <el-table-column prop="employmentStatus" label="就业状态"/>
          <el-table-column prop="isDeleted" label="是否删除"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button type="primary" @click="handleEdit(scope.row)" size="small" plain>编辑</el-button>
              <el-button type="danger" size="small" @click="del(scope.row.graduateId)" plain>删除</el-button>

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

    <el-dialog width="35%" v-model="data.formVisible" title="学生信息">
      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right: 30px">
        <el-form-item label="学生名字">
          <el-input v-model="data.form.studentName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="data.form.gender" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="data.form.majorName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="data.form.className" autocomplete="off" />
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input v-model="data.form.graduationYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="出生日期">
          <el-input v-model="data.form.birthdate" autocomplete="off" />
        </el-form-item>
      <el-form-item label="就业状态">
        <el-input v-model="data.form.employmentStatus" autocomplete="off" />
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

const baseUrl = '/student'
const data = reactive({
  studentName: '',     // 学生的姓名
  birthdate: '',       // 学生的出生日期
  className: '',       // 学生所在的班级名称
  employmentStatus: '', // 学生的就业状态
  gender: '',          // 学生性别
  graduateId: 0,       // 学生ID
  graduationYear: '',  // 学生的毕业年份
  majorName: '',       // 学生所学专业的名称
  tableData:[] ,
  total:0,
  pageNum:1,
  pageSize:8,
  formVisible:false,
  form:{
    studentName: '',     // 学生的姓名
    birthdate: '',       // 学生的出生日期
    className: '',       // 学生所在的班级名称
    employmentStatus: '', // 学生的就业状态
    gender: '',          // 学生性别
    graduateId: 0,       // 学生ID
    graduationYear: '',  // 学生的毕业年份
    majorName: '',       // 学生所学专业的名称
  }
})


const load = () => {
  request.get(baseUrl+'/selectPage', {
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      studentName:data.studentName,
      employmentStatus: data.employmentStatus,
    }
  }).then(res => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
    data.pageNum = res.data?.pageNum;
    data.pageSize = res.data?.pageSize;
    console.log(data.total);
    console.log(data.tableData);
  })
}
load()

const handleCurrentChange = () => {
  load()
}

const reset = () => {
  data.studentName = ''
  load()
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const save = () => {
  request.request({
        url: data.form.graduateId? baseUrl+'/update' : baseUrl+'/add',
        method: data.form.graduateId? 'POST' : 'POST',
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

const del = (graduateId) =>{
  if (!graduateId) {
    ElMessage.error("删除操作需要有效的数据ID");
    console.log(graduateId)
    return;
  }
  console.log(graduateId)
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？','删除确认',{type:'warning'}).then(res => {
    request.post(baseUrl+'/delete',{

      graduateId: graduateId
    }).then(res => {
      if(res.code === 0){
        load()
        ElMessage.success("操作成功")

      }else{
        ElMessage.error(res.msg)
      }
    })
  }).catch(res => {})
}
</script>