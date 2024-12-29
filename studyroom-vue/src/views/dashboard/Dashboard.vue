<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-header">
            <i class="el-icon-user"></i>
            <span>用户总数</span>
          </div>
          <div class="card-body">
            {{ userCount }}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-header">
            <i class="el-icon-office-building"></i>
            <span>自习室总数</span>
          </div>
          <div class="card-body">
            {{ roomCount }}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-header">
            <i class="el-icon-s-order"></i>
            <span>今日预约数</span>
          </div>
          <div class="card-body">
            {{ todayOrderCount }}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-header">
            <i class="el-icon-s-platform"></i>
            <span>开放自习室</span>
          </div>
          <div class="card-body">
            {{ openRoomCount }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getUserCount, getRoomCount, getTodayOrderCount, getOpenRoomCount } from '@/api'

export default {
  name: 'DashboardIndex',
  data() {
    return {
      userCount: 0,
      roomCount: 0,
      todayOrderCount: 0,
      openRoomCount: 0
    }
  },
  created() {
    this.getStatistics()
  },
  methods: {
    async getStatistics() {
      try {
        // 获取用户总数
        const userRes = await getUserCount()
        this.userCount = userRes.data

        // 获取自习室总数
        const roomRes = await getRoomCount()
        this.roomCount = roomRes.data

        // 获取今日预约数
        const today = new Date().toISOString().split('T')[0]
        const orderRes = await getTodayOrderCount({ time: today })
        this.todayOrderCount = orderRes.data

        // 获取开放自习室数量
        const openRoomRes = await getOpenRoomCount()
        this.openRoomCount = openRoomRes.data

      } catch (error) {
        console.error('获取统计数据失败:', error)
        this.$message.error('获取统计数据失败')
      }
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.el-row {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.card-header i {
  font-size: 20px;
  margin-right: 8px;
}

.card-body {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  text-align: center;
}
</style> 