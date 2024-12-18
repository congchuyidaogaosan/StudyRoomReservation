<template>
  <div class="order-container">
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="预约编号">
          <el-input v-model="queryParams.orderNo" placeholder="请输入预约编号" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="未开始" value="未开始"></el-option>
            <el-option label="进行中" value="进行中"></el-option>
            <el-option label="已完成" value="已完成"></el-option>
            <el-option label="已取消" value="已取消"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table v-loading="loading" :data="orderList" border style="width: 100%">
      <el-table-column prop="id" label="预约编号" width="80"></el-table-column>
      <el-table-column prop="nick" label="自习室"></el-table-column>
      <el-table-column prop="kehuname" label="预约人"></el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="160"></el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="160"></el-table-column>
      <el-table-column prop="infotime" label="申请时间" width="160"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleDetail(scope.row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script>
import { getOrderList } from '@/api'

export default {
  name: 'OrderList',
  data() {
    return {
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: '',
        status: ''
      },
      orderList: [],
      total: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      this.loading = true
      try {
        const res = await getOrderList(this.queryParams)
        this.orderList = res.data

      } catch (error) {
        console.error('获取预约列表失败:', error)
      } finally {
        this.loading = false
      }
    },
    getStatusType(status) {
      const types = {
        '待使用': 'info',
        '使用中': 'warning',
        '已完成': 'success',
        '已取消': 'danger'
      }
      return types[status] || ''
    },
    handleSearch() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        orderNo: '',
        status: ''
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
    handleDetail(row) {
      // TODO: 查看预约详情
      console.log('查看详情:', row)
    }
  }
}
</script>

<style scoped>
.order-container {
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