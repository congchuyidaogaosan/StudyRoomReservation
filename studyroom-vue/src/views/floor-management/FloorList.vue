<template>
  <div class="floor-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="楼层">
          <el-select v-model="queryParams.rootFloor" placeholder="请选择楼层" clearable>
            <el-option v-for="floor in floorOptions" :key="floor" :label="floor+'楼'"
              :value="floor"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 操作按钮 -->
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">新增房间</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="floorList" border style="width: 100%">
      <!-- <el-table-column prop="id" label="ID" width="80"></el-table-column> -->
      <el-table-column prop="rootFloor" label="楼层" width="100">
        <template slot-scope="scope">
          {{ scope.row.rootFloor + '楼' }}
        </template>
      </el-table-column>
      <el-table-column prop="roomLocation" label="房号"></el-table-column>
      <el-table-column prop="roomName" label="房间名称"></el-table-column>
      <el-table-column prop="totalSeats" label="人数"></el-table-column>
      <el-table-column prop="states" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.states
            === '0' ? 'success' : 'danger'">
            {{ scope.row.states
              === '0' ? '开放' : '关闭' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" :type="scope.row.states === '0' ? 'danger' : 'success'"
            @click="handleStatusChange(scope.row)">{{ scope.row.states === '0' ? '关闭' : '开放' }}</el-button>
          <el-button size="mini" type="info" @click="handleManageSeats(scope.row)">管理座位</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <!-- <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div> -->

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
      <el-form :model="floorForm" :rules="rules" ref="floorForm" label-width="100px">
        <el-form-item label="楼层" prop="rootFloor">
          <el-select v-model="floorForm.rootFloor" placeholder="请选择楼层">
            <el-option v-for="floor in floorOptions" :key="floor" :label="floor+'楼'"
              :value="floor"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房号" prop="roomLocation">
          <el-input type="textarea" v-model="floorForm.roomLocation"></el-input>
        </el-form-item>
        <el-form-item label="房间名称" prop="roomName">
          <el-input type="textarea" v-model="floorForm.roomName"></el-input>
        </el-form-item>
        <el-form-item label="人数" prop="totalSeats">
          <el-input type="textarea" v-model="floorForm.totalSeats"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="states">
          <el-select v-model="floorForm.states">
            <el-option label="开放" value="0"></el-option>
            <el-option label="关闭" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加座位管理弹框 -->
    <el-dialog 
      :title="`${currentRoom ? currentRoom.roomLocation : ''} 座位管理`" 
      :visible.sync="seatDialogVisible" 
      width="80%">
      <div class="seat-header">
        <el-form :inline="true">
          <el-form-item label="选择日期">
            <el-date-picker
              v-model="selectedDate"
              type="date"
              placeholder="选择日期"
              @change="loadSeatStatus"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
          <el-form-item label="时间段">
            <el-select
              v-model="selectedTime"
              placeholder="选择时间段"
              @change="loadSeatStatus">
              <el-option label="8:00-10:00" value="1" />
              <el-option label="10:00-12:00" value="2" />
              <el-option label="12:00-14:00" value="3" />
              <el-option label="14:00-16:00" value="4" />
              <el-option label="16:00-18:00" value="5" />
              <el-option label="18:00-20:00" value="6" />
              <el-option label="20:00-22:00" value="7" />
              <el-option label="22:00-24:00" value="8" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <div class="seat-container">
        <div v-for="seat in seatList" :key="seat.seatId" class="seat-item">
          <el-card :body-style="{ padding: '10px' }">
            <div class="seat-number">座位号：{{ seat.number }}</div>
            <div class="seat-position">
              第{{ seat.rrow }}行 第{{ seat.ccolumn }}列
            </div>
            <div class="seat-status" 
              :class="{ 
                'occupied': seat.isAvailable === 'occupied', 
                'available': seat.isAvailable === 'available' 
              }">
              状态：{{ seat.isAvailable === 'occupied' ? '已占用' : '空闲' }}
            </div>
            <div class="seat-price" v-if="seat.price">
              价格：¥{{ seat.price }}
            </div>
            <div class="seat-actions">
              <el-button type="primary" size="mini" @click="handlePrice(seat)">价格管理</el-button>
              <el-button type="info" size="mini" @click="handleRecord(seat)">预约记录</el-button>
            </div>
          </el-card>
        </div>
      </div>

      <!-- 价格管理弹框 -->
      <el-dialog
        title="价格管理"
        :visible.sync="priceDialogVisible"
        width="30%"
        append-to-body>
        <el-form :model="priceForm" label-width="80px">
          <el-form-item label="价格">
            <el-input-number v-model="priceForm.price" :min="1" :step="1"></el-input-number>
          </el-form-item>
        </el-form>
        <span slot="footer">
          <el-button @click="priceDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitPrice">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 预约记录弹框 -->
      <el-dialog
        title="预约记录"
        :visible.sync="recordDialogVisible"
        width="60%"
        append-to-body>
        <div class="record-filter">
          <el-date-picker
            v-model="recordDate"
            type="date"
            placeholder="选择日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            @change="loadRecords"
          />
        </div>
        <el-table :data="formattedRecordList" border>
          <el-table-column prop="timeSlot" label="时间段"></el-table-column>
          <el-table-column prop="kehuName" label="预约人"></el-table-column>
          <el-table-column prop="status" label="状态">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status === '已预约' ? 'danger' : 'success'">
                {{ scope.row.status }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import { floorList, getFloorList, addFloor, updateFloor, getSeatList, getSeatRecords,updateSeatPrice  } from '@/api'

export default {
  name: 'FloorList',
  data() {
    return {
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        floor: ''
      },
      floorOptions: [
        { value: 1, label: '1楼' },
        { value: 2, label: '2楼' },
        { value: 3, label: '3楼' },
        { value: 4, label: '4楼' },
        { value: 5, label: '5楼' }
      ],
      floorList: [],
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      floorForm: {
        id: null,
        floor: '',
        description: '',
        openTime: '',
        closeTime: '',
        status: '1'
      },
      rules: {
        floor: [
          { required: true, message: '请选择楼层', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请输入描述', trigger: 'blur' }
        ],
        openTime: [
          { required: true, message: '请选择开放时间', trigger: 'change' }
        ],
        closeTime: [
          { required: true, message: '请选择关闭时间', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ]
      },
      seatDialogVisible: false,
      priceDialogVisible: false,
      recordDialogVisible: false,
      selectedDate: `${new Date().getFullYear()}-${('0' + (new Date().getMonth() + 1)).slice(-2)}-${('0' + new Date().getDate()).slice(-2)}`,
      selectedTime: '1',
      currentRoom: null,
      seatList: [],
      priceForm: {
        price: 0,
        seatId: null
      },
      recordDate: `${new Date().getFullYear()}-${('0' + (new Date().getMonth() + 1)).slice(-2)}-${('0' + new Date().getDate()).slice(-2)}`,
      recordList: [],
      currentSeat: null,
      timeSlotList: [
        { id: 1, time: '08:00-10:00' },
        { id: 2, time: '10:00-12:00' },
        { id: 3, time: '12:00-14:00' },
        { id: 4, time: '14:00-16:00' },
        { id: 5, time: '16:00-18:00' },
        { id: 6, time: '18:00-20:00' },
        { id: 7, time: '20:00-22:00' },
        { id: 8, time: '22:00-24:00' }
      ]
    }
  },
  computed: {
    formattedRecordList() {
      // 创建所有时间段的记录
      const allTimeSlots = this.timeSlotList.map(slot => ({
        timeSlot: slot.time,
        timeId: slot.id,
        kehuName: '-',
        status: '空闲'
      }))

      // 用实际预约记录更新对应时间段
      this.recordList.forEach(record => {
        const index = allTimeSlots.findIndex(slot => slot.timeId === record.timeId)
        if (index !== -1) {
          allTimeSlots[index] = {
            timeSlot: this.timeSlotList[record.timeId - 1].time,
            kehuName: record.kehuName || '-',
            status: '已预约'
          }
        }
      })

      return allTimeSlots
    }
  },
  created() {
    this.getList()
    this.rootfloor()
  },
  methods: {
    async rootfloor() {
      try {
        const res = await floorList()

        this.floorOptions = res.data
      } catch (error) {
        console.error('获取楼层列表失败:', error)
      } finally {

      }
    },
    async getList() {
      this.loading = true
      try {
        const res = await getFloorList(this.queryParams)
        this.floorList = res.data.sort((a, b) => a.rootFloor - b.rootFloor)
        this.total = res.data.total
      } catch (error) {
        console.error('获取楼层列表失败:', error)
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
        floor: ''
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
    handleAdd() {
      this.dialogTitle = '新增楼层'
      this.floorForm = {
        id: null,
        floor: '',
        description: '',
        openTime: '',
        closeTime: '',
        status: '1'
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑楼层'
      this.floorForm = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleStatusChange(row) {
      const statusText = row.states === '0' ? '关闭' : '开放'
      this.$confirm(`确认要${statusText}该楼层吗？`, '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await updateFloor(row.id, { ...row, states: row.states === '0' ? '1' : '0' })
          this.$message.success(`${statusText}成功`)
          this.getList()
        } catch (error) {
          console.error('操作失败:', error)
        }
      }).catch(() => { })
    },
    handleManageSeats(row) {
      this.currentRoom = row
      this.selectedTime = '1'
      this.loadSeatStatus()
      this.seatDialogVisible = true
    },
    async loadSeatStatus() {
      if (!this.currentRoom) return

      console.log(this.selectedDate, this.selectedTime);
      
      try {
        const res = await getSeatList({
          roomId: this.currentRoom.id,
          date: this.selectedDate,
          timeId: this.selectedTime
        })
        this.seatList = res.data
      } catch (error) {
        console.error('获取座位状态失败:', error)
      }
    },
    handlePrice(seat) {
      this.currentSeat = seat

        console.log(seat)

      this.priceForm.seatId = seat.seatId
      this.priceForm.roomId = seat.roomId
      this.priceForm.price = seat.price || 0
      this.priceDialogVisible = true
    },
    async submitPrice() {
      
      try {
        await updateSeatPrice(this.priceForm)
        this.$message.success('价格更新成功')
        this.priceDialogVisible = false
        this.loadSeatStatus()
      } catch (error) {
        console.error('更新价格失败:', error)
      }
    },
    handleRecord(seat) {
      this.currentSeat = seat
      this.recordDialogVisible = true
      this.loadRecords()
    },
    async loadRecords() {
      try {
        const res = await getSeatRecords({
          seatId: this.currentSeat.seatId,
          number: this.currentSeat.number,
          time: this.recordDate
        })
        this.recordList = res.data
      } catch (error) {
        console.error('获取预约记录失败:', error)
        this.$message.error('获取预约记录失败')
      }
    },
    isHighlight(seat) {
      return seat.status === '空闲' && !this.hasReservation(seat)
    },
    hasReservation(seat) {
      // 检查该座位在当前时间段是否有预约
      return false // 根据实际数据判断
    },
    submitForm() {
      this.$refs.floorForm.validate(async valid => {
        if (valid) {
          try {
            if (this.floorForm.id) {
              await updateFloor(this.floorForm.id, this.floorForm)
              this.$message.success('更新成功')
            } else {
              await addFloor(this.floorForm)
              this.$message.success('添加成功')
            }
            this.dialogVisible = false
            this.getList()
          } catch (error) {
            console.error('操作失败:', error)
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.floor-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.seat-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
  padding: 20px;
}

.seat-item {
  text-align: center;
  transition: all 0.3s;
}

.seat-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.el-card {
  height: 100%;
}

.seat-number {
  font-size: 16px;
  margin-bottom: 10px;
}

.seat-position {
  font-size: 14px;
  color: #606266;
  margin-bottom: 10px;
}

.seat-status {
  margin-bottom: 10px;
}

.seat-status.occupied {
  color: #F56C6C;  /* 红色 */
  font-weight: bold;
}

.seat-status.available {
  color: #67C23A;  /* 绿色 */
  font-weight: bold;
}

.seat-price {
  font-size: 14px;
  color: #E6A23C;
  margin-bottom: 10px;
  font-weight: bold;
}

.seat-actions {
  display: flex;
  justify-content: space-around;
}

.record-filter {
  margin-bottom: 20px;
}

.available {
  color: #67c23a;
}
</style>