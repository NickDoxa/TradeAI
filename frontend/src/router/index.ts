import { createRouter, createWebHistory } from 'vue-router'
import StockEvaluationView from '../views/StockEvaluationView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: StockEvaluationView
    }
  ]
})

export default router
