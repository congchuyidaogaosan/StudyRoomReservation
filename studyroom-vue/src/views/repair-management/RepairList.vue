<template>
  <div class="repair-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="房间号">
          <el-input v-model="queryParams.roomLocation" placeholder="请输入房间号" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="待处理" value="pending"></el-option>
            <el-option label="处理中" value="processing"></el-option>
            <el-option label="已完成" value="completed"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 报修列表 -->
    <el-table v-loading="loading" :data="repairList" border>
      <el-table-column prop="roomLocation" label="房间号"></el-table-column>
      <el-table-column prop="seatNumber" label="座位号"></el-table-column>
      <el-table-column prop="description" label="问题描述"></el-table-column>
      <el-table-column prop="reportTime" label="报修时间"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button 
            size="mini" 
            type="primary" 
            @click="handleProcess(scope.row)"
            v-if="scope.row.status === 'pending'">
            处理
          </el-button>
          <el-button 
            size="mini" 
            type="success" 
            @click="handleComplete(scope.row)"
            v-if="scope.row.status === 'processing'">
            完成
          </el-button>
          <el-button 
            size="mini" 
            type="info" 
            @click="handleDetail(scope.row)">
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <!-- 详情弹窗 -->
    <el-dialog title="报修详情" :visible.sync="detailDialogVisible" width="500px">
      <div class="repair-detail">
        <p><strong>房间号：</strong>{{ currentRepair.roomLocation }}</p>
        <p><strong>座位号：</strong>{{ currentRepair.seatNumber }}</p>
        <p><strong>问题描述：</strong>{{ currentRepair.description }}</p>
        <p><strong>报修时间：</strong>{{ currentRepair.reportTime }}</p>
        <p><strong>处理记录：</strong></p>
        <div class="process-records">
          <el-timeline>
            <el-timeline-item
              v-for="(record, index) in currentRepair.processRecords"
              :key="index"
              :timestamp="record.time">
              {{ record.content }}
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </el-dialog>

    <!-- 处理弹窗 -->
    <el-dialog title="处理报修" :visible.sync="processDialogVisible" width="500px">
      <el-form :model="processForm" label-width="80px">
        <el-form-item label="处理记录">
          <el-input
            type="textarea"
            v-model="processForm.content"
            :rows="4"
            placeholder="请输入处理记录">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="processDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitProcess">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getRepairList, updateRepairStatus, getRepairDetail, addRepairRecord } from '@/api'

export default {
  name: 'RepairList',
  data() {
    return {
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomLocation: '',
        status: ''
      },
      repairList: [],
      total: 0,
      detailDialogVisible: false,
      currentRepair: {},
      processDialogVisible: false,
      processForm: {
        repairId: null,
        content: '',
        status: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      this.loading = true
      try {
        const res = await getRepairList(this.queryParams)
        this.repairList = res.data
        this.total = res.total
      } catch (error) {
        console.error('获取报修列表失败:', error)
        this.$message.error('获取报修列表失败')
      } finally {
        this.loading = false
      }
    },
    
    async handleProcess(row) {
      this.processForm = {
        repairId: row.id,
        content: '',
        status: 'processing'
      }
      this.processDialogVisible = true
    },
    
    async handleComplete(row) {
      this.$confirm('确认完成此报修任务？', '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await updateRepairStatus({
            id: row.id,
            status: 'completed'
          })
          this.$message.success('操作成功')
          this.getList()
        } catch (error) {
          console.error('操作失败:', error)
          this.$message.error('操作失败')
        }
      }).catch(() => {})
    },
    
    async handleDetail(row) {
      try {
        const res = await getRepairDetail(row.id)
        this.currentRepair = res.data
        this.detailDialogVisible = true
      } catch (error) {
        console.error('获取详情失败:', error)
        this.$message.error('获取详情失败')
      }
    },
    
    async submitProcess() {
      try {
        await addRepairRecord(this.processForm)
        await updateRepairStatus({
          id: this.processForm.repairId,
          status: this.processForm.status
        })
        this.$message.success('处理成功')
        this.processDialogVisible = false
        this.getList()
      } catch (error) {
        console.error('处理失败:', error)
        this.$message.error('处理失败')
      }
    },
    
    getStatusType(status) {
      const statusMap = {
        pending: 'warning',
        processing: 'primary',
        completed: 'success'
      }
      return statusMap[status]
    },
    getStatusText(status) {
      const statusMap = {
        pending: '待处理',
        processing: '处理中',
        completed: '已完成'
      }
      return statusMap[status]
    },
    // ... 其他方法
  }
}
</script>

<style scoped>
.repair-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.repair-detail {
  padding: 20px;
}

.process-records {
  margin-top: 10px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}
</style> 