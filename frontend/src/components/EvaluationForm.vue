<script setup lang="ts">
import InputText from 'primevue/inputtext';
import { InputNumber } from 'primevue'
import { ref } from 'vue'
import StockEvaluationOutput from '../components/StockEvaluationOutput.vue'
import type { StockEvaluationApiResponse } from './common/stock-evaluation-api-response'
import axios from 'axios'

const symbolText = ref<string>();
const targetPriceNumber = ref<number>();
const loadingEvaluation = ref<boolean>(false);
const viewingEvaluation = ref<boolean>(false);
const evaluationOutput = ref<StockEvaluationApiResponse | null>(null);

const submitEvaluation = async () => {
  if (symbolText.value === undefined ||
    symbolText.value === '' ||
    targetPriceNumber.value === undefined ||
    targetPriceNumber.value <= 0) {
    return
  }
  loadingEvaluation.value = true;
  viewingEvaluation.value = true;
  const response = await axios.get('http://localhost:8080/evaluate?symbol='
    + symbolText.value + '&target=' + targetPriceNumber.value);
  if (response.data === null) {
    return
  }
  evaluationOutput.value = response.data
  loadingEvaluation.value = false
}

const startNew = () => {
  viewingEvaluation.value = false
  evaluationOutput.value = null
}
</script>
<template>
  <div class="evaluation-form-wrapper" v-if="!loadingEvaluation && !viewingEvaluation">
    <InputText v-model="symbolText" placeholder="Stock Symbol" required/>
    <InputNumber v-model="targetPriceNumber" placeholder="Target Price" mode="currency" currency="USD" required/>
    <div class="button-wrapper">
      <button class="button" @click="submitEvaluation">
        <span class="label">Evaluate</span>
        <span class="gradient-container">
          <span class="gradient"></span>
        </span>
      </button>
    </div>
  </div>
  <div class="stock-evaluation-wrapper" v-else>
    <StockEvaluationOutput :evaluation="evaluationOutput" @startNew="startNew" />
  </div>
</template>
<style scoped>
.evaluation-form-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  width: 40%;
  height: auto;
  min-height: 30vh;
  margin: 0.5rem auto auto;
  border-radius: 1rem;
}

.stock-evaluation-wrapper {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
  text-align: center;
  align-items: center;
  margin: 1rem auto auto;
}

.button-wrapper {
  padding: 2rem;
}

.button {
  border: none;
  outline: none;
  background-color: #3a3a3a;
  width: 180px;
  height: 60px;
  font-size: 18px;
  color: #fff;
  font-weight: 600;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  position: relative;
  transition: all 0.3s;
}

.button::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(255, 255, 255, 0.2);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  width: 106%;
  height: 120%;
  z-index: -1;
  border-radius: inherit;
  transition: all 0.3s;
}

.gradient-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 106%;
  height: 115%;
  overflow: hidden;
  border-radius: inherit;
  z-index: -2;
  filter: blur(10px);
  transition: all 0.3s;
}

.gradient {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 110%;
  aspect-ratio: 1;
  border-radius: 100%;
  transition: all 0.3s;
  background-image: linear-gradient(
    90deg,
    hsl(226, 81%, 64%),
    hsl(271, 81%, 64%),
    hsl(316, 81%, 64%),
    hsl(1, 81%, 64%),
    hsl(46, 81%, 64%),
    hsl(91, 81%, 64%),
    hsl(136, 81%, 64%),
    hsl(181, 81%, 64%)
  );
  animation: rotate 2s linear infinite;
  filter: blur(10px);
}

.label {
  width: 156px;
  height: 45px;
  text-align: center;
  line-height: 45px;
  border-radius: 22px;
  background-color: rgba(43, 43, 43, 1);
  background-image: linear-gradient(
    180deg,
    rgb(43, 43, 43) 0%,
    rgb(68, 68, 68) 100%
  );
}

.button:hover .gradient-container {
  transform: translate(-50%, -50%) scale(0.98);
  filter: blur(5px);
}

.button:hover .gradient {
  filter: blur(5px);
}

@keyframes rotate {
  0% {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  100% {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

</style>