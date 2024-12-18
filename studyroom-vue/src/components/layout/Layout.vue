<template>
  <el-container class="app-wrapper">
    <el-aside width="200px">
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#fff"
      >
        <el-menu-item index="/dashboard">
          <i class="el-icon-s-home"></i>
          <span>控制台</span>
        </el-menu-item>
        <el-submenu index="/floor">
          <template slot="title">
            <i class="el-icon-office-building"></i>
            <span>自习室管理</span>
          </template>
          <el-menu-item index="/floor/list">
            <span>楼层管理</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item index="/order">
          <i class="el-icon-s-order"></i>
          <span>预约管理</span>
        </el-menu-item>
        <el-menu-item index="/user">
          <i class="el-icon-user"></i>
          <span>用户管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header>
        <div class="header-right">
          <el-dropdown>
            <span class="el-dropdown-link">
              管理员<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Layout',
  computed: {
    activeMenu() {
      const { path } = this.$route
      if (path.startsWith('/floor/')) {
        return path
      }
      return path
    }
  }
}
</script>

<style scoped>
.app-wrapper {
  height: 100vh;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.header-right {
  margin-right: 20px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
</style> 