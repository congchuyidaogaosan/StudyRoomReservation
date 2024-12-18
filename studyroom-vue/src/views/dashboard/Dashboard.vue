<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-title">总自习室数</div>
            <div class="card-number">{{ statistics.totalRooms || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-title">使用中自习室</div>
            <div class="card-number">{{ statistics.inUseRooms || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-title">今日预约数</div>
            <div class="card-number">{{ statistics.todayOrders || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-title">总用户数</div>
            <div class="card-number">{{ statistics.totalUsers || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'DashboardIndex',
  data() {
    return {
      statistics: {
        totalRooms: 0,
        inUseRooms: 0,
        todayOrders: 0,
        totalUsers: 0
      }
    }
  },
  created() {
    // TODO: 获取统计数据
    this.statistics = {
      totalRooms: 10,
      inUseRooms: 5,
      todayOrders: 20,
      totalUsers: 100
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.card-item {
  text-align: center;
  padding: 20px 0;
}

.card-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.card-number {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}
</style> 