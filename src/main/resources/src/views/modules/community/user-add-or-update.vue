<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="username">
      <el-input v-model="dataForm.username" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="password">
      <el-input v-model="dataForm.password" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="salt">
      <el-input v-model="dataForm.salt" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="email">
      <el-input v-model="dataForm.email" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="0-普通用户; 1-超级管理员; 2-版主;" prop="type">
      <el-input v-model="dataForm.type" placeholder="0-普通用户; 1-超级管理员; 2-版主;"></el-input>
    </el-form-item>
    <el-form-item label="0-未激活; 1-已激活;" prop="status">
      <el-input v-model="dataForm.status" placeholder="0-未激活; 1-已激活;"></el-input>
    </el-form-item>
    <el-form-item label="" prop="activationCode">
      <el-input v-model="dataForm.activationCode" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="headerUrl">
      <el-input v-model="dataForm.headerUrl" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          username: '',
          password: '',
          salt: '',
          email: '',
          type: '',
          status: '',
          activationCode: '',
          headerUrl: '',
          createTime: ''
        },
        dataRule: {
          username: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          salt: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '0-普通用户; 1-超级管理员; 2-版主;不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '0-未激活; 1-已激活;不能为空', trigger: 'blur' }
          ],
          activationCode: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          headerUrl: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/community/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.username = data.user.username
                this.dataForm.password = data.user.password
                this.dataForm.salt = data.user.salt
                this.dataForm.email = data.user.email
                this.dataForm.type = data.user.type
                this.dataForm.status = data.user.status
                this.dataForm.activationCode = data.user.activationCode
                this.dataForm.headerUrl = data.user.headerUrl
                this.dataForm.createTime = data.user.createTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/community/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'username': this.dataForm.username,
                'password': this.dataForm.password,
                'salt': this.dataForm.salt,
                'email': this.dataForm.email,
                'type': this.dataForm.type,
                'status': this.dataForm.status,
                'activationCode': this.dataForm.activationCode,
                'headerUrl': this.dataForm.headerUrl,
                'createTime': this.dataForm.createTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
