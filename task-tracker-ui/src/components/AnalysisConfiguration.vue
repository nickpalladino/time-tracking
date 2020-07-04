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


  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import {Calendar, DatePicker} from 'v-calendar';

@Component({
  components: {
    DatePicker
  },
})
export default class AnalysisConfiguration extends Vue {
  dates: Array<Date> = [];
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
