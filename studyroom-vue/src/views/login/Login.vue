<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">自习室管理系统</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" placeholder="用户名" prefix-icon="el-icon-user" />
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" placeholder="密码" prefix-icon="el-icon-lock" />
      </el-form-item>
      <el-button :loading="loading" type="primary" style="width: 100%" @click.native.prevent="handleLogin">
        登录
      </el-button>
    </el-form>
  </div>
</template>

<script>
import { login } from '@/api'

export default {
  name: 'LoginIndex',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, message: '输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      loading: false
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(async valid => {
        if (valid) {
          this.loading = true
          try {

            const res = await login(this.loginForm)
            // localStorage.setItem('token', res.data.token)
            localStorage.setItem('token', 'test-token')
            this.$router.push({ name: 'DashboardIndex' })
          } catch (error) {
            console.error('登录失败:', error)
          } finally {
            this.loading = false
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #2d3a4b;
}

.login-form {
  width: 400px;
  padding: 30px;
  background: #fff;
  border-radius: 4px;
}

.title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
</style>