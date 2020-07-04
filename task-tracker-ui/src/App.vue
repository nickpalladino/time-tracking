<template>
  <div id="app">
    <!-- <img alt="Breeding Insight logo" src="./assets/bi-logo.svg" width="300">
    <img alt="ActivityWatch logo" src="./assets/activitywatch-logo.png" width="300"> -->
    <h1>Automated Task Time Calculator</h1>
    <AnalysisConfiguration/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import AnalysisConfiguration from './components/AnalysisConfiguration.vue';

@Component({
  components: {
    AnalysisConfiguration,
  },
})
export default class App extends Vue {}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
