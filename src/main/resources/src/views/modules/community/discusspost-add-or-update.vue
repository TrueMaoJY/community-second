<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="userId">
      <el-input v-model="dataForm.userId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="title">
      <el-input v-model="dataForm.title" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="content">
      <el-input v-model="dataForm.content" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="0-普通; 1-置顶;" prop="type">
      <el-input v-model="dataForm.type" placeholder="0-普通; 1-置顶;"></el-input>
    </el-form-item>
    <el-form-item label="0-正常; 1-精华; 2-拉黑;" prop="status">
      <el-input v-model="dataForm.status" placeholder="0-正常; 1-精华; 2-拉黑;"></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="commentCount">
      <el-input v-model="dataForm.commentCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="score">
      <el-input v-model="dataForm.score" placeholder=""></el-input>
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
          userId: '',
          title: '',
          content: '',
          type: '',
          status: '',
          createTime: '',
          commentCount: '',
          score: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '0-普通; 1-置顶;不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '0-正常; 1-精华; 2-拉黑;不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          commentCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          score: [
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
              url: this.$http.adornUrl(`/community/discusspost/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.discussPost.userId
                this.dataForm.title = data.discussPost.title
                this.dataForm.content = data.discussPost.content
                this.dataForm.type = data.discussPost.type
                this.dataForm.status = data.discussPost.status
                this.dataForm.createTime = data.discussPost.createTime
                this.dataForm.commentCount = data.discussPost.commentCount
                this.dataForm.score = data.discussPost.score
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
              url: this.$http.adornUrl(`/community/discusspost/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'title': this.dataForm.title,
                'content': this.dataForm.content,
                'type': this.dataForm.type,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime,
                'commentCount': this.dataForm.commentCount,
                'score': this.dataForm.score
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
