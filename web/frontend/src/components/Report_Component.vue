<template>
  <div class="report-container">
    <div class="report-header">
      <h2>System Reports</h2>
      <p>Wählen Sie einen Report aus, um Anomalien oder Statistiken zu sehen.</p>
    </div>

    <div class="controls-card">
      <div class="control-group">
        <label>Report auswählen:</label>
        <select v-model="selectedReport" @change="onReportChange">
          <option value="" disabled>-- Bitte wählen --</option>
          <option v-for="rep in reports" :key="rep.id" :value="rep.id">
            {{ rep.label }}
          </option>
        </select>
      </div>

      <div v-if="selectedReport === 'daily-report' || selectedReport === 'analysis/without-boxpos' || selectedReport === 'analysis/with-zero-values' || selectedReport === 'analysis/without-time' 
                  || selectedReport === 'samples/multiple-analyses' || selectedReport === 'samples/suspicious/by-date'" class="time-inputs">
        <div class="control-group">
          <label>Von:</label>
          <input type="date" v-model="params.start" step="1" />
        </div>
        <div class="control-group">
          <label>Bis:</label>
          <input type="date" v-model="params.end" step="1" />
        </div>
      </div>

      <div class="control-group">
        <label>Limit:</label>
        <select v-model.number="displayLimit" style="width: 80px; min-width: auto;">
          <option :value="10">10</option>
          <option :value="25">25</option>
          <option :value="50">50</option>
          <option :value="100">100</option>
        </select>
      </div>

      <div class="control-group button-wrapper">
        <button @click="fetchReport" class="btn btn-load" :disabled="loading || !selectedReport">
          <span v-if="loading">Lade...</span>
          <span v-else>Report laden</span>
        </button>
      </div>
    </div>

    <div v-if="error" class="error-box">
      {{ error }}
    </div>

    <div v-if="reportData.length > 0" class="table-card">
      <div class="table-scroll">
        <table>
          <thead>
            <tr>
              <th v-for="col in columns" :key="col">{{ formatHeader(col) }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(row, idx) in reportData" :key="idx">
              <td v-for="col in columns" :key="col">
                {{ formatCell(col, row[col]) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="table-footer" v-if="!selectedReport === 'daily-report'">
        <p class="info-text">
            Zeige {{ reportData.length }} von {{ totalItems }} Einträgen
        </p>
        
        <div class="pagination-controls" v-if="totalPages > 1">
            <button @click="changePage(0)" :disabled="currentPage === 0" class="btn btn-page">«</button>
            <button @click="changePage(currentPage - 1)" :disabled="currentPage === 0" class="btn btn-page">‹</button>
            <span class="page-info">Seite {{ currentPage + 1 }} von {{ totalPages }}</span>
            <button @click="changePage(currentPage + 1)" :disabled="currentPage >= totalPages - 1" class="btn btn-page">›</button>
            <button @click="changePage(totalPages - 1)" :disabled="currentPage >= totalPages - 1" class="btn btn-page">»</button>
        </div>
      </div>
    </div>

    <div v-else-if="!loading && hasSearched" class="empty-state">
      <p>Keine Ergebnisse für diesen Report gefunden.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReportComponent',
  props: {
    globalDateStart: String,
    globalDateEnd: String
  },

  watch: {
    globalDateStart(newVal) {
      this.globalDateInFrom = newVal; 
      if (this.selectedReport) {
          this.currentPage = 0;
          this.fetchReport();
      }
    },
    globalDateEnd(newVal) {
      this.globalDateInTo = newVal;
      if (this.selectedReport) {
          this.currentPage = 0;
          this.fetchReport();
      }
    }
  },

  data() {
    return {
      selectedReport: '',
      loading: false,
      error: null,
      hasSearched: false,
      reportData: [],

      globalDateInFrom: '',
      globalDateInTo: '',
      
      // Pagination State (Server-Side)
      currentPage: 0,
      totalPages: 0,
      totalItems: 0,
      displayLimit: 25,

      params: {
        start: '',
        end: ''
      },

      reports: [
        { id: 'daily-report', label: 'Daily Report' },
        { id: 'boxpos/with-sample-no-analysis', label: 'BoxPos mit Sample ohne Analysis' }, 
        { id: 'boxpos/without-sample', label: 'Box-Positionen ohne Sample' },
        { id: 'analysis/without-boxpos', label: 'Analysis ohne BoxPos' },
        { id: 'analysis/with-zero-values', label: 'Analysis mit Null-Werten' },
        { id: 'analysis/without-time', label: 'Analysis mit fehlendem Timestamp' },
        { id: 'samples/multiple-analyses', label: 'Samples mit mehrfachen Analysen' },
        { id: 'samples/suspicious', label: 'Verdächtige Samples (Allgemein)' },
        { id: 'samples/suspicious/by-date', label: 'Verdächtige Samples (in Zeitraum)' },
        { id: 'samples/invalid-ean13', label: 'Samples mit ungültigem EAN-Code' },
      ]
    };
  },
  computed: {
    columns() {
      if (this.reportData.length === 0) return [];
      return Object.keys(this.reportData[0]);
    }
  },
  methods: {
    onReportChange() {
      this.reportData = [];
      this.hasSearched = false;
      this.error = null;
      this.currentPage = 0; 
      this.totalPages = 0;
      this.totalItems = 0;
    },

    changePage(newPage) {
      if (newPage >= 0 && newPage < this.totalPages) {
        this.currentPage = newPage;
        this.fetchReport();
      }
    },

    formatHeader(key) {
      if (!key) return '';
      return key.charAt(0).toUpperCase() + key.slice(1).replace(/_/g, ' ');
    },

    formatCell(key, value) {
      if (value === null || value === undefined) return '-';
      if (key.toLowerCase().includes('date') || key.toLowerCase().includes('stamp') || key.toLowerCase().includes('at')) {
        try {
          return new Date(value).toLocaleString('de-DE');
        } catch (e) { return value; }
      }
      if (typeof value === 'object' && !Array.isArray(value)) {
        const idKey = Object.keys(value).find(k => k.toLowerCase().endsWith('id'));
        if (idKey) return value[idKey];
        if (value.name) return value.name;
        return Object.values(value)[0];
      }

      return value;
    },

    async fetchReport() {
      this.loading = true;
      this.error = null;
      this.hasSearched = true;

      try {
        let url = `/api/reports/${this.selectedReport}`;
        const queryParams = new URLSearchParams();

        // queryParams.append('page', this.currentPage);
        // queryParams.append('size', this.displayLimit);
        
        // Globale Filter immer mitsenden (Backend entscheidet ob genutzt)
        // if(this.globalDateInFrom) queryParams.append('globalDateInFrom', this.globalDateInFrom);
        // if(this.globalDateInTo) queryParams.append('globalDateInTo', this.globalDateInTo);
        

        if (this.selectedReport === 'daily-report' ||  this.selectedReport === 'analysis/without-boxpos' || this.selectedReport === 'analysis/with-zero-values' 
            || this.selectedReport === 'analysis/without-time' || this.selectedReport === 'samples/multiple-analyses' || this.selectedReport === 'samples/suspicious/by-date') {
          if (!this.params.start || !this.params.end) {
            throw new Error("Bitte Start- und Endzeitpunkt wählen.");
          }
          const startFmt = this.params.start
          const endFmt = this.params.end
          
          queryParams.append('startDate', startFmt);
          queryParams.append('endDate', endFmt);
        }

        const response = await fetch(`${url}?${queryParams.toString()}`, {
          method: 'GET',
          credentials: 'include' 
        });

        if (response.status === 401 || response.status === 403) {
           throw new Error("Sitzung abgelaufen. Bitte neu anmelden.");
        }

        if (!response.ok) {
          const text = await response.text();
          throw new Error(text || 'Fehler beim Laden des Reports');
        }

        const data = await response.json();

        this.reportData = data

        // if(this.selectedReport === 'daily-report'){
        //   this.reportData = data
        // } else {
        //   this.reportData = data.content;
        //   this.totalPages = data.totalPages;
        //   this.totalItems = data.totalElements;
        //   this.currentPage = data.number;
        // }
        

      } catch (err) {
        console.error(err);
        this.error = err.message;
        this.reportData = [];
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.report-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px 20px;
  font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
}

.report-header {
  margin-bottom: 30px;
}
.report-header h2 {
  color: #0f172a;
  margin-bottom: 5px;
}
.report-header p {
  color: #64748b;
}

.controls-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: flex-end;
  border: 1px solid #e2e8f0;
}

.control-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.control-group label {
  font-size: 0.9rem;
  font-weight: 600;
  color: #475569;
}

select, input {
  padding: 10px 14px;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
  font-size: 1rem;
  background-color: #fff;
  min-width: 250px;
}
select:focus, input:focus {
  outline: none;
  border-color: #198754;
  box-shadow: 0 0 0 2px rgba(25, 135, 84, 0.2);
}

.time-inputs {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.button-wrapper {
  margin-left: auto; 
}

.btn-load {
  background-color: #0f172a;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  min-width: 120px;
}
.btn-load:hover:not(:disabled) {
  background-color: #198754;
  transform: translateY(-1px);
}
.btn-load:disabled {
  background-color: #94a3b8;
  cursor: not-allowed;
}

.error-box {
  background-color: #fef2f2;
  border: 1px solid #fecaca;
  color: #dc2626;
  padding: 15px;
  border-radius: 8px;
  margin-top: 20px;
}

.table-card {
  margin-top: 30px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border: 1px solid #e2e8f0;
}

.table-scroll {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.95rem;
}

th, td {
  padding: 14px 20px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
  white-space: nowrap;
}

th {
  background-color: #f1f5f9;
  font-weight: 600;
  color: #334155;
  text-transform: capitalize;
}

tr:hover {
  background-color: #f8fafc;
}

.table-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 15px;
    background-color: #fff;
    border-top: 1px solid #e2e8f0;
}

.info-text {
  color: #64748b;
  font-size: 0.9rem;
}

.pagination-controls {
    display: flex;
    align-items: center;
    gap: 5px;
}

.btn-page {
    padding: 5px 10px;
    background: #f8f9fa;
    border: 1px solid #dee2e6;
    color: #333;
    font-size: 0.9rem;
    border-radius: 4px;
    cursor: pointer;
}

.btn-page:disabled {
    opacity: 0.5;
    cursor: default;
}

.btn-page:hover:not(:disabled) {
    background: #e2e6ea;
}

.page-info {
    font-size: 0.9rem;
    margin: 0 10px;
    color: #64748b;
}

.empty-state {
  margin-top: 40px;
  text-align: center;
  color: #94a3b8;
  font-style: italic;
}

/* Dark Mode Support */
:global(body.dark-theme .report-header h2) { color: #fff; }
:global(body.dark-theme .report-header p) { color: #94a3b8; }
:global(body.dark-theme .controls-card) { background-color: #1e293b; border-color: #334155; }
:global(body.dark-theme .control-group label) { color: #cbd5e1; }
:global(body.dark-theme select), :global(body.dark-theme input) {
  background-color: #0f172a;
  border-color: #334155;
  color: white;
}
:global(body.dark-theme .table-card) { background-color: #1e293b; border-color: #334155; }
:global(body.dark-theme .table-footer) { background-color: #1e293b; border-color: #334155; }
:global(body.dark-theme .info-text) { color: #94a3b8; }
:global(body.dark-theme .page-info) { color: #94a3b8; }
:global(body.dark-theme .btn-page) { background-color: #334155; border-color: #475569; color: #fff; }
:global(body.dark-theme .btn-page:hover:not(:disabled)) { background-color: #475569; }

:global(body.dark-theme th) { background-color: #0f172a; color: #cbd5e1; border-color: #334155; }
:global(body.dark-theme td) { color: #e2e8f0; border-color: #334155; }
:global(body.dark-theme tr:hover) { background-color: #334155; }
</style>