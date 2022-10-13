<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="fromId">
      <el-input v-model="dataForm.fromId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="toId">
      <el-input v-model="dataForm.toId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="conversationId">
      <el-input v-model="dataForm.conversationId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="content">
      <el-input v-model="dataForm.content" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="0-未读;1-已读;2-删除;" prop="status">
      <el-input v-model="dataForm.status" placeholder="0-未读;1-已读;2-删除;"></el-input>
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
          fromId: '',
          toId: '',
          conversationId: '',
          content: '',
          status: '',
          createTime: ''
        },
        dataRule: {
          fromId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          toId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          conversationId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '0-未读;1-已读;2-删除;不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/community/message/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.fromId = data.message.fromId
                this.dataForm.toId = data.message.toId
                this.dataForm.conversationId = data.message.conversationId
                this.dataForm.content = data.message.content
                this.dataForm.status = data.message.status
                this.dataForm.createTime = data.message.createTime
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
              url: this.$http.adornUrl(`/community/message/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'fromId': this.dataForm.fromId,
                'toId': this.dataForm.toId,
                'conversationId': this.dataForm.conversationId,
                'content': this.dataForm.content,
                'status': this.dataForm.status,
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
