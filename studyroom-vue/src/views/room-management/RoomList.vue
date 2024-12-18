<template>
  <div class="room-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="自习室名称">
          <el-input v-model="queryParams.name" placeholder="请输入自习室名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="楼层">
          <el-select v-model="queryParams.floor" placeholder="请选择楼层" clearable>
            <el-option v-for="floor in floorOptions" :key="floor.value" :label="floor.label"
              :value="floor.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="空闲" value="0"></el-option>
            <el-option label="使用中" value="1"></el-option>
            <el-option label="维护中" value="2"></el-option>
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
      <el-button type="primary" @click="handleAdd">新增自习室</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="roomList" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="自习室名称"></el-table-column>
      <el-table-column prop="floor" label="楼层" width="100">
        <template slot-scope="scope">
          {{ scope.row.floor + '楼' }}
        </template>
      </el-table-column>
      <el-table-column prop="capacity" label="容量" width="100"></el-table-column>
      <el-table-column prop="price" label="价格(元/小时)" width="120"></el-table-column>
      <el-table-column prop="openTime" label="开放时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.openTime }} - {{ scope.row.closeTime }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
      <el-form :model="roomForm" :rules="rules" ref="roomForm" label-width="100px">
        <el-form-item label="自习室名称" prop="name">
          <el-input v-model="roomForm.name"></el-input>
        </el-form-item>
        <el-form-item label="楼层" prop="floor">
          <el-select v-model="roomForm.floor" placeholder="请选择楼层">
            <el-option v-for="floor in floorOptions" :key="floor.value" :label="floor.label"
              :value="floor.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="容量" prop="capacity">
          <el-input-number v-model="roomForm.capacity" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="roomForm.price" :min="0" :precision="2"></el-input-number>
        </el-form-item>
        <el-form-item label="开放时间" prop="openTime">
          <el-time-picker v-model="roomForm.openTime" format="HH:mm" placeholder="开放时间"
            style="width: 180px;"></el-time-picker>
        </el-form-item>
        <el-form-item label="关闭时间" prop="closeTime">
          <el-time-picker v-model="roomForm.closeTime" format="HH:mm" placeholder="关闭时间"
            style="width: 180px;"></el-time-picker>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="roomForm.status">
            <el-option label="空闲" value="0"></el-option>
            <el-option label="使用中" value="1"></el-option>
            <el-option label="维护中" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getRoomList, addRoom, updateRoom, deleteRoom } from '@/api'

export default {
  name: 'RoomList',
  data() {
    return {
      loading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        floor: '',
        status: ''
      },
      // 楼层选项
      floorOptions: [
        { value: 1, label: '1楼' },
        { value: 2, label: '2楼' },
        { value: 3, label: '3楼' },
        { value: 4, label: '4楼' },
        { value: 5, label: '5楼' }
      ],
      // 自习室列表
      roomList: [],
      total: 0,
      // 对话框
      dialogVisible: false,
      dialogTitle: '',
      // 表单对象
      roomForm: {
        id: null,
        name: '',
        floor: 1,
        capacity: 1,
        price: 0,
        openTime: '',
        closeTime: '',
        status: '0'
      },
      // 表单验证规则
      rules: {
        name: [
          { required: true, message: '请输入自习室名称', trigger: 'blur' }
        ],
        floor: [
          { required: true, message: '请选择楼层', trigger: 'change' }
        ],
        capacity: [
          { required: true, message: '请输入容量', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' }
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
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取列表数据
    async getList() {
      this.loading = true
      try {
        const res = await getRoomList(this.queryParams)
        this.roomList = res.data.list
        this.total = res.data.total
      } catch (error) {
        console.error('获取自习室列表失败:', error)
      } finally {
        this.loading = false
      }
    },
    // 状态显示
    getStatusType(status) {
      const types = {
        '0': 'success',
        '1': 'warning',
        '2': 'danger'
      }
      return types[status] || ''
    },
    getStatusText(status) {
      const texts = {
        '0': '空闲',
        '1': '使用中',
        '2': '维护中'
      }
      return texts[status] || '未知'
    },
    // 搜索
    handleSearch() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        floor: '',
        status: ''
      }
      this.getList()
    },
    // 分页
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    // 新增
    handleAdd() {
      this.dialogTitle = '新增自习室'
      this.roomForm = {
        id: null,
        name: '',
        floor: 1,
        capacity: 1,
        price: 0,
        openTime: '',
        closeTime: '',
        status: '0'
      }
      this.dialogVisible = true
    },
    // 编辑
    handleEdit(row) {
      this.dialogTitle = '编辑自习室'
      this.roomForm = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    // 删除
    handleDelete(row) {
      this.$confirm('确认删除该自习室吗？', '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await deleteRoom(row.id)
          this.$message.success('删除成功')
          this.getList()
        } catch (error) {
          console.error('删除失败:', error)
        }
      }).catch(() => { })
    },
    // 提交表单
    submitForm() {
      this.$refs.roomForm.validate(async valid => {
        if (valid) {
          try {
            if (this.roomForm.id) {
              await updateRoom(this.roomForm.id, this.roomForm)
              this.$message.success('更新成功')
            } else {
              await addRoom(this.roomForm)
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
.room-container {
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
</style>