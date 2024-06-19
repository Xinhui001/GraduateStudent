import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Manager',
      component: () => import('@/views/Manager.vue'),
      redirect: '/login',
      children: [
        { path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
        { path: 'admin', name: 'Admin', component: () => import('@/views/manager/Admin.vue')},
        { path: 'class', name: 'Class', component: () => import('@/views/manager/Class.vue')},
        { path: 'student', name: 'Student', component: () => import('@/views/manager/Student.vue')},
        { path: 'major', name: 'Major', component: () => import('@/views/manager/Major.vue')},
        {path: '/login', name: 'Login', component: () => import('@/views/Login.vue'),},
      ]
    },
  ]
})

export default router
