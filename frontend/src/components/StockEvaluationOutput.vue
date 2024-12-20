<script setup lang="ts">
import SpinnerAnimation from '../components/SpinnerAnimation.vue'
import MeterGroup from 'primevue/metergroup'
import type { StockEvaluationApiResponse } from './common/stock-evaluation-api-response'
import { computed } from 'vue'
import Button from 'primevue/button'
import { Fieldset } from 'primevue'

const props = defineProps<{
  evaluation?: StockEvaluationApiResponse | null
}>()

const emits = defineEmits<{
  (e: 'startNew'): void
}>()

const meterResult = computed(() => [{ label: 'Probability of reaching target price',
  value: props.evaluation?.evaluationScore * 100, color: 'var(--p-sky-400)' }]);
</script>

<template>
  <SpinnerAnimation v-if="evaluation === null || evaluation === undefined" />
  <div class="evaluation-wrapper" v-else>
    <MeterGroup :value="meterResult" fluid/>
    <Fieldset legend="Evaluation Report" :toggleable="true" :collapsed="true">
      <div class="evaluation-fieldset-wrapper">
        <span>
         {{evaluation.evaluationResponse}}
        </span><br>
        <span style="color: lightgray">
          <i>Evaluated With Data From: {{evaluation.evaluationTime}}</i>
        </span>
      </div>
    </Fieldset>
    <Button severity="secondary" label="Evaluate Another Stock" @click="emits('startNew')"/>
  </div>
</template>

<style scoped>
.evaluation-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  width: 70%;
  height: auto;
  gap: 2rem;
  margin-bottom: 2rem;
}

.evaluation-fieldset-wrapper {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  font-family: BodyFont, sans-serif;
}
</style>