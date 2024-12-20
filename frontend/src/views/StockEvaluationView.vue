<script setup lang="ts">
import EvaluationForm from '../components/EvaluationForm.vue'
import StockAnimation from '../components/StockAnimation.vue'
</script>

<template>
  <div class="logo-wrapper">
    <StockAnimation/>
  </div>
  <div class="heading">
    <span class="title heading-font">TradeAI <i class="pi pi-globe title rotating-anim"/></span>
    <span class="subtitle body-font">Evaluate the probability of any stock hitting a target price
      based on the latest market data and reputable news sources</span>
  </div>
  <EvaluationForm/>
</template>

<style scoped>
.logo-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: auto;
  width: 10rem;
  height: 20rem;
  padding-top: 5rem;
}

.heading {
  text-align: center;
  display: flex;
  flex-direction: column;
  width: auto;
  height: auto;
  padding: 2rem;
}

.title {
  font-size: 4rem;
  padding-top: 2rem;
}

.subtitle {
  font-size: 1.5rem;
  padding: 0.5rem;
  width: 50%;
  margin: auto;
}
</style>