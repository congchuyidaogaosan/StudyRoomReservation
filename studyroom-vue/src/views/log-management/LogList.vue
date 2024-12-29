<template>
  <div class="log-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="用户名">
          <el-input v-model="queryParams.userName" placeholder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="时间范围">
          <el-date-picker v-model="queryParams.dateRange" type="daterange" range-separator="至" start-placeholder="开始日期"
            end-placeholder="结束日期" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 日志列表 -->
    <el-table v-loading="loading" :data="logList" border>
      <el-table-column prop="kehuname" label="用户名"></el-table-column>
      <el-table-column prop="nick" label="房间号"></el-table-column>
      <el-table-column label="座位位置">
        <template slot-scope="scope">
          第{{ scope.row.seatsrow
 }}行 第{{ scope.row.seatcolunm }}列
        </template>
      </el-table-column>
      <el-table-column prop="amount" label="消费金额">
        <template slot-scope="scope">
          ¥{{ scope.row.amount }}
        </template>
      </el-table-column>
      <el-table-column prop="infotime" label="日期"></el-table-column>
      <el-table-column prop="timeSlot" label="时间段">
        <template slot-scope="scope">
          {{ scope.row.startTime }}-{{ scope.row.endTime }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
      </el-table-column>
    </el-table>



  </div>
</template>

<script>
import { getLogList } from '@/api'

export default {
  name: 'LogList',
  data() {
    return {
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: '',
        dateRange: []
      },
      logList: [],
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
        const [startDate, endDate] = this.queryParams.dateRange || []
        const params = {
          ...this.queryParams,
          startDate,
          endDate
        }
        const res = await getLogList(params)
        this.logList = res.data
        console.log(res)
      } catch (error) {
        console.error('获取日志列表失败:', error)
        this.$message.error('获取日志列表失败')
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
        userName: '',
        dateRange: []
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
    }
  }
}
</script>

<style scoped>
.log-container {
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