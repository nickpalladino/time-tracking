<template>
  <div class="analysis">
    <p>
      Task time estimations can be made from ActivityWatch data by looking for trello card IDs and keeping track of the time between them.
    </p>
    <h3>Select days to include in analysis:</h3>
    <p/>
    <center>
    <v-date-picker
      v-model="dates"
      mode="multiple"
      is-inline
    />
    </center>
    <h3>Completed task list:</h3>
    <p/>
    <button v-on:click="submitAnalysis">Submit Analysis</button>
    <template v-if="errors && errors.length">
      <p style="color:red;" v-for="error of errors" v-bind:key="error">
        {{error.response.data.message}}
      </p>
    </template>


  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { Calendar, DatePicker } from 'v-calendar';
import axios from 'axios';

@Component({
  components: {
    DatePicker
  },
})
export default class AnalysisConfiguration extends Vue {
  dates: Array<Date> = [];
  taskNames: Array<string> = ["INF-163", "INF-164"];

  errors: any[] = [];
  analysisResult: any;
  

  submitAnalysis() {
    this.errors = [];
    const body = {'taskNames': this.taskNames, 'days': this.dates };
    axios.post(`http://localhost:8085/v1/analysis`, body)
    .then(response => {
      this.analysisResult = response.data
    })
    .catch(e => {
      this.errors.push(e)
    })
  }

}
</script>