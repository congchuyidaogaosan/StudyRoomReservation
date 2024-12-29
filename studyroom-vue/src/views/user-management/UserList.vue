<template>
  <div class="user-container">
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="用户名">
          <el-input v-model="queryParams.username" placeholder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="16人格">

          <el-select v-model="queryParams.mbti" placeholder="16人格选择" clearable>
            <el-option v-for="mbti in mbtiList" :key="mbti.englishName" :label="mbti.name"
              :value="mbti.englishName"></el-option>
          </el-select>

        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table v-loading="loading" :data="userList" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="账号"></el-table-column>
      <el-table-column prop="nickname" label="用户名"></el-table-column>
      <el-table-column prop="photo" label="头像">
        <template slot-scope="scope">
          <el-image fit="fill" :src="scope.row.photo"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="mbti" label="MBTI"></el-table-column>
      <el-table-column prop="createTime" label="注册时间" width="160"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
            {{ scope.row.status === '0' ? '正常' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" :type="scope.row.status === '0' ? 'danger' : 'success'"
            @click="handleStatusChange(scope.row)">{{ scope.row.status === '0' ? '禁用' : '启用' }}</el-button>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script>
import { getUserList, getMbtiList, updateUser } from '@/api'

export default {
  name: 'UserList',
  data() {
    return {
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: '',

      },
      userList: [],
      total: 0,
      mbtiList: [],
    }
  },
  created() {
    this.getList()
    this.getMBTI()
  },
  methods: {
    async getMBTI() {

      const res = await getMbtiList(this.queryParams)

      this.mbtiList = res.data

    },
    async getList() {
      this.loading = true
      try {
        const res = await getUserList(this.queryParams)
        this.userList = res.data
      } catch (error) {
        console.error('获取用户列表失败:', error)
      } finally {
        this.loading = false
      }
    },
    handleSearch() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        username: '',
        phone: ''
      }
      this.getList()
    },
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    handleEdit(row) {
      // TODO: 编辑用户信息
      console.log('编辑用户:', row)
    },
    handleStatusChange(row) {
      const statusText = row.status === '0' ? '禁用' : '启用'
      this.$confirm(`确认要${statusText}该用户吗？`, '提示', {
        type: 'warning'
      }).then(() => {
        // TODO: 调用修改状态接口

        row.status = row.status === '0' ? '1' : '0'

        console.log(row)

        updateUser(row).then(() => {
          this.$message.success(`${statusText}成功`)
          this.getList()
        }).catch(() => { })
        })

    }
  }
}
</script>

<style scoped>
.user-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style>