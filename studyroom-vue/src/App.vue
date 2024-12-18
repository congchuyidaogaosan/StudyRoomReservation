<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}

#app {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',
    'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
}

/* 重置 element-ui 的一些样式 */
.el-menu {
  border-right: none !important;
}

.el-menu-item.is-active {
  background-color: #1f2d3d !important;
}

.el-header {
  height: 50px !important;
  line-height: 50px;
}
</style> 