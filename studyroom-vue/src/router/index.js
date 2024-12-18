import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/components/layout/Layout.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'LoginIndex',
    component: () => import('@/views/login/Login.vue')
  },
  {
    path: '/',
    component: Layout,
    redirect: { name: 'DashboardIndex' },
    children: [
      {
        path: 'dashboard',
        name: 'DashboardIndex',
        component: () => import('@/views/dashboard/Dashboard.vue'),
        meta: { title: '控制台', icon: 'el-icon-s-home' }
      },
      {
        path: 'floor',
        name: 'FloorManagement',
        component: () => import('@/views/floor-management/index.vue'),
        redirect: { name: 'FloorList' },
        meta: { title: '自习室管理', icon: 'el-icon-office-building' },
        children: [
          {
            path: 'list',
            name: 'FloorList',
            component: () => import('@/views/floor-management/FloorList.vue'),
            meta: { title: '楼层管理' }
          }
        ]
      },
      {
        path: 'order',
        name: 'OrderManagement',
        component: () => import('@/views/order-management/OrderList.vue'),
        meta: { title: '预约管理', icon: 'el-icon-s-order' }
      },
      {
        path: 'user',
        name: 'UserManagement',
        component: () => import('@/views/user-management/UserList.vue'),
        meta: { title: '用户管理', icon: 'el-icon-user' }
      },
      {
        path: 'Log',
        name: 'LogManagement',
        component: () => import('@/views/user-management/UserList.vue'),
        meta: { title: '日志管理', icon: 'el-icon-user' }
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')

  if (to.path === '/login') {
    if (token) {
      next({ name: 'DashboardIndex' })
    } else {
      next()
    }
  } else {
    if (token) {
      next()
    } else {
      next({ name: 'LoginIndex' })
    }
  }
})

export default router 