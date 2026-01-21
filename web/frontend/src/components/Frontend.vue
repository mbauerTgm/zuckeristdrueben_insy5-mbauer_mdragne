<template>
  <div class="container">
    <header>
      <h1 class="main-title">INSY Frontend</h1>
    </header>

    <main>
      <section>
        <div class="table-selector">
          <div class="control-group">
            <label>Tabelle:</label>
            <select v-model="selectedTable" @change="onTableChange">
              <option value="analysis">Analysis</option>
              <option value="sample">Sample</option>
              <option value="box">Box</option>
              <option value="boxpos">Box Pos</option>
              <option value="log">Log (Read-Only)</option>
              <option value="threshold">Thresholds</option>
            </select>
          </div>

          <div class="control-group search-wrapper">
            <label>Suche:</label>
            <div class="search-input-group">
              <input 
                type="text" 
                v-model="searchQuery" 
                @keyup.enter="applySearch"
                placeholder="ID, Name..." 
                class="search-input"
              />
              <button @click="applySearch" class="btn btn-search" title="Suchen">
                <svg viewBox="0 0 24 24" class="mdi-icon">
                  <path fill="currentColor" d="M9.5,3A6.5,6.5 0 0,1 16,9.5C16,11.11 15.41,12.59 14.44,13.73L14.71,14H15.5L20.5,19L19,20.5L14,15.5V14.71L13.73,14.44C12.59,15.41 11.11,16 9.5,16A6.5,6.5 0 0,1 3,9.5A6.5,6.5 0 0,1 9.5,3M9.5,5C7,5 5,7 5,9.5C5,12 7,14 9.5,14C12,14 14,12 14,9.5C14,7 12,5 9.5,5Z" />
                </svg>
              </button>
            </div>
          </div>

          <div class="control-group">
            <label>Limit:</label>
            <select v-model.number="displayLimit" @change="fetchTableData" style="width: auto;">
              <option :value="10">10</option>
              <option :value="25">25</option>
              <option :value="50">50</option>
              <option :value="100">100</option>
            </select>
          </div>

          <div class="control-group">
            <label>Ansicht: </label>
            <div class="column-selector">
              <button 
                type="button" 
                class="btn btn-columns" 
                @click="toggleColumnSelector"
              >
                <svg viewBox="0 0 24 24" class="mdi-icon">
                  <path fill="currentColor" d="M3,3H11V11H3V3M3,13H11V21H3V13M13,3H21V11H13V3M13,13H21V21H13V13M5,5V9H9V5H5M5,15V19H9V15H5M15,5V9H19V5H15M15,15V19H19V15H15Z" />
                </svg>
                Spalten
              </button>
              
              <div v-if="showColumnSelector" class="column-dropdown">
                <div class="column-dropdown-header">
                  <span>Sichtbare Spalten</span>
                  <button @click="selectAllColumns" class="btn-link">Alle</button>
                  <button @click="deselectAllColumns" class="btn-link">Keine</button>
                </div>
                <div 
                  v-for="col in availableColumns" 
                  :key="col" 
                  class="column-option"
                >
                  <label>
                    <input 
                      type="checkbox" 
                      :value="col" 
                      v-model="selectedColumns"
                    />
                    {{ formatHeader(col) }}
                  </label>
                </div>
              </div>
            </div>
          </div>

          <div class="control-group" v-if="selectedTable === 'analysis'">
            <div style="display: flex; gap: 5px;">
              <div class="column-selector">
                <button 
                  type="button" 
                  class="btn btn-columns" 
                  @click="toggleFilterSelector"
                >
                  <svg viewBox="0 0 24 24" class="mdi-icon">
                    <path fill="currentColor" d="M14,12V19.88C14.04,20.18 13.94,20.5 13.71,20.71C13.32,21.1 12.69,21.1 12.3,20.71L10.29,18.7C10.06,18.47 9.96,18.16 10,17.87V12H9.97L4.21,4.62C3.87,4.19 3.95,3.56 4.38,3.22C4.57,3.08 4.78,3 5,3V3H19V3C19.22,3 19.43,3.08 19.62,3.22C20.05,3.56 20.13,4.19 19.79,4.62L14.03,12H14Z" />
                  </svg>
                  Filter
                </button>
                
                <div v-if="showFilterSelector" class="column-dropdown filter-dropdown">
                  <div class="column-dropdown-header">
                    <span>Bereichs-Filter</span>
                    <button @click="resetFilters" class="btn-link">Reset</button>
                  </div>
                  <div class="filter-content">
                    <template v-if="selectedTable === 'analysis'">
                      <div class="filter-group">
                          <label>ID (a_id)</label>
                          <div class="range-inputs">
                              <input type="number" v-model="searchParams.idFrom" placeholder="Von" />
                              <input type="number" v-model="searchParams.idTo" placeholder="Bis" />
                          </div>
                      </div>
                      <div class="filter-group">
                          <label>Sample ID (s_id)</label>
                          <div class="range-inputs">
                              <input type="text" v-model="searchParams.sIdFrom" placeholder="Von" />
                              <input type="text" v-model="searchParams.sIdTo" placeholder="Bis" />
                          </div>
                      </div>
                      <div class="filter-group">
                          <label>Date In</label>
                          <div class="range-inputs">
                              <input type="datetime-local" step="1" v-model="searchParams.dateInFrom" />
                              <input type="datetime-local" step="1" v-model="searchParams.dateInTo" />
                          </div>
                      </div>
                      <div class="filter-group">
                          <label>Date Out</label>
                          <div class="range-inputs">
                              <input type="datetime-local" step="1" v-model="searchParams.dateOutFrom" />
                              <input type="datetime-local" step="1" v-model="searchParams.dateOutTo" />
                          </div>
                      </div>
                      <div class="filter-group">
                          <label>A Flags</label>
                          <div class="range-inputs">
                              <input type="text" v-model="searchParams.aFlagsFrom" placeholder="Von" />
                              <input type="text" v-model="searchParams.aFlagsTo" placeholder="Bis" />
                          </div>
                      </div>
                      <button class="btn btn-save" style="width: 100%; margin-top: 10px;" @click="applyBackendFilter">Anwenden</button>
                    </template>
                  </div>
                </div>
              </div>

              <button 
                type="button" 
                class="btn btn-columns" 
                @click="exportFilteredCSV"
                :disabled="loadingCSV"
                title="Liste als CSV exportieren"
              >
                <template v-if="!loadingCSV">
                  <svg viewBox="0 0 24 24" class="mdi-icon">
                    <path fill="currentColor" d="M5,20H19V18H5M19,9H15V3H9V9H5L12,16L19,9Z" />
                  </svg>
                  Export CSV
                </template>
                <template v-else>
                  <Create_Loader />
                </template>
              </button>
            </div>
          </div>

          <div class="button-group">
            <button @click="fetchTableData" class="btn btn-load" title="Daten neu laden">
              <svg viewBox="0 0 24 24" class="mdi-icon">
                <path fill="currentColor" d="M17.65,6.35C16.2,4.9 14.21,4 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C15.73,20 18.84,17.45 19.73,14H17.65C16.83,16.33 14.61,18 12,18A6,6 0 0,1 6,12A6,6 0 0,1 12,6C13.66,6 15.14,6.69 16.22,7.78L13,11H20V4L17.65,6.35Z" />
              </svg>
            </button>
            
            <button 
              v-if="canEdit && !currentSchema.readOnly" 
              @click="createNew" 
              class="btn btn-save" 
            >
              <svg viewBox="0 0 24 24" class="mdi-icon">
                <path fill="currentColor" d="M19,13H13V19H11V13H5V11H11V5H13V11H19V13Z" />
              </svg>
              Neu
            </button>
          </div>
        </div>

        <p v-if="loading" class="status-text"> <LoadingBar /> Lade Daten... </p>
        <p v-else-if="error" class="error-text">Fehler: {{ error.message }}</p>
        <p v-else-if="tableData.length === 0" class="status-text">Keine Daten vorhanden.</p>

        <div v-else class="table-wrapper">
          <table>
            <thead>
            <tr>
              <th 
                v-for="col in visibleColumns" 
                :key="col"
                @click="sortBy(col)"
                class="sortable-header"
              >
                <div class="th-content">
                  {{ formatHeader(col) }}
                  <span v-if="sortKey === col">
                    <svg v-if="sortAsc" viewBox="0 0 24 24" class="mdi-icon small-icon">
                      <path fill="currentColor" d="M7.41,15.41L12,10.83L16.59,15.41L18,14L12,8L6,14L7.41,15.41Z" />
                    </svg>
                    <svg v-else viewBox="0 0 24 24" class="mdi-icon small-icon">
                      <path fill="currentColor" d="M7.41,8.59L12,13.17L16.59,8.59L18,10L12,16L6,10L7.41,8.59Z" />
                    </svg>
                  </span>
                </div>
              </th>
              <th class="sticky-col th-sticky">
                Optionen
              </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(row, idx) in sortedData" :key="idx">
              <td v-for="col in visibleColumns" :key="col">
                {{ formatCell(col, row[col]) }}
              </td>
              <td class="sticky-col td-sticky">
                <div class="action-buttons">
                  <button @click="viewItem(row)" class="btn btn-view btn-icon-view" title="Details ansehen">
                    <svg viewBox="0 0 24 24" class="mdi-icon">
                      <path fill="currentColor" d="M12,9A3,3 0 0,0 9,12A3,3 0 0,0 12,15A3,3 0 0,0 15,12A3,3 0 0,0 12,9M12,17A5,5 0 0,1 7,12A5,5 0 0,1 12,7A5,5 0 0,1 17,12A5,5 0 0,1 12,17M12,4.5C7,4.5 2.73,7.61 1,12C2.73,16.39 7,19.5 12,19.5C17,19.5 21.27,16.39 23,12C21.27,7.61 17,4.5 12,4.5Z" />
                    </svg>
                  </button>

                  <button 
                    v-if="canEdit && !currentSchema.readOnly" 
                    @click="editItem(row)" 
                    class="btn btn-edit" 
                    title="Bearbeiten"
                  >
                    <svg viewBox="0 0 24 24" class="mdi-icon">
                      <path fill="currentColor" d="M20.71,7.04C21.1,6.65 21.1,6 20.71,5.63L18.37,3.29C18,2.9 17.35,2.9 16.96,3.29L15.12,5.12L18.87,8.87M3,17.25V21H6.75L17.81,9.93L14.06,6.18L3,17.25Z" />
                    </svg>
                  </button>

                  <button 
                    v-if="canDelete && !currentSchema.readOnly" 
                    @click="deleteItem(row)" 
                    class="btn btn-delete" 
                    title="Löschen"
                  >
                    <svg viewBox="0 0 24 24" class="mdi-icon">
                        <path fill="currentColor" d="M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z" />
                    </svg>
                  </button>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
          
          <div class="table-footer">
            <p class="info-text">
                Zeige {{ sortedData.length }} von {{ totalItems }} Einträgen
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

        <div v-if="showDetailModal" class="modal-overlay" id="detail-modal" @click.self="closeDetailModal">
          <div class="modal-content modal-lg">
            <div class="modal-header">
              <h3>Details: {{ formatHeader(selectedTable) }}</h3>
              <button @click="closeDetailModal" class="btn-close">×</button>
            </div>
            <div class="detail-grid">
              <div v-for="(value, key) in itemToView" :key="key" class="detail-row">
                <div class="detail-label">{{ formatHeader(key) }}</div>
                <div class="detail-value" :id="'detail-' + key">{{ formatCell(key, value) }}</div>
              </div>
            </div>
            <div class="modal-actions">
              <button id="btn-close-detail" @click="closeDetailModal" class="btn btn-cancel">Schließen</button>
              <button 
                v-if="canEdit && !currentSchema.readOnly" 
                id="btn-edit-from-detail"
                @click="editFromDetail(itemToView)" 
                class="btn btn-edit"
              >
                Bearbeiten
              </button>
            </div>
          </div>
        </div>

        <div v-if="itemToEdit" class="form-container" ref="formContainer">
          <h3>{{ isNewItem ? 'Neuer Eintrag' : 'Bearbeiten' }}: {{ formatHeader(selectedTable) }}</h3>
          <div class="form-grid">
            <div v-for="col in formColumns" :key="col" class="form-group">
              <label :for="'field-'+col">
                {{ formatHeader(col) }} <span v-if="isFieldRequired(col)" class="required-mark">*</span>
              </label>
              
              <input 
                v-if="isFieldImmutable(col) && !isNewItem" 
                :id="'field-'+col"
                :value="itemToEdit[col]" 
                disabled 
                class="input-disabled" 
              />
              
              <select 
                v-else-if="selectedTable === 'box' && (col === 'type' || col === 'typ')"
                :id="'field-'+col"
                v-model.number="itemToEdit[col]"
              >
                  <option v-for="n in 9" :key="n" :value="n">Typ {{ n }}</option>
              </select>

              <input 
                v-else-if="getFieldConfig(col).type === 'datetime'" 
                :id="'field-'+col"
                type="datetime-local" 
                v-model="itemToEdit[col]" 
                :step="1" 
              />
              
              <input 
                v-else-if="getFieldConfig(col).type === 'number'" 
                :id="'field-'+col"
                type="number" 
                v-model.number="itemToEdit[col]" 
                :step="getFieldConfig(col).step" 
              />
              
              <input 
                v-else 
                :id="'field-'+col"
                type="text" 
                v-model="itemToEdit[col]" 
                :required="isFieldRequired(col)" 
              />
            </div>
          </div>
          <div>
            <p v-if="error_isRequired" class="error-text">Alle Pflichtfelder müssen ausgefüllt sein <br>{{ error_isRequired_message }}</p>
          </div>
          <div class="form-actions">
            <button @click="cancelEdit" class="btn btn-cancel" :disabled="isSaving">Abbrechen</button>
            <button @click="saveItem" class="btn btn-save" :disabled="isSaving">
              <template v-if="!isSaving">
                Speichern
              </template>
              <template v-else>
                <Create_Loader />
              </template>
            </button>
          </div>
        </div>

        <div v-if="showDeleteModal" class="modal-overlay">
          <div class="modal-content">
             <h3>Eintrag löschen?</h3>
            <p>Sind Sie sicher, dass Sie diesen Eintrag löschen möchten?</p>
            <div class="modal-actions">
              <button @click="closeDeleteModal" class="btn btn-cancel">Abbrechen</button>
              <button @click="confirmDelete" class="btn btn-delete">Löschen</button>
            </div>
          </div>
        </div>

      </section>
    </main>
  </div>
</template>

<script>
import LoadingBar from '@/components/Loadingbar.vue'
import Create_Loader from './Create_Loader.vue';
import api from '@/services/api'; // AXIOS API IMPORT

// SCHEMAS object remains unchanged (removed for brevity, keeping structure)
const SCHEMAS = {
  analysis: {
    endpoint: 'analysis',
    pk: 'a_id',
    displayColumns: ['a_id', 's_id', 's_stamp', 'pol', 'nat', 'glu', 'date_in', 'a_flags'],
    formColumns: [
      's_id', 'pol', 'nat', 'kal', 'an', 'glu', 'dry',
      'date_in', 'date_out', 'weight_mea', 'weight_nrm', 'weight_cur',
      'weight_dif', 'density', 'a_flags', 'lane', 'comment'
    ],
    immutable: ['a_id'], 
    fieldConfigs: {
      s_id: { maxLength: 13, required: true },
      s_stamp: { type: 'datetime', required: true },
      pol: { type: 'number', step: '0.01' },
      nat: { type: 'number', step: '0.01' },
      kal: { type: 'number', step: '0.01' },
      an: { type: 'number', step: '0.01' },
      glu: { type: 'number', step: '0.01' },
      dry: { type: 'number', step: '0.01' },
      density: { type: 'number', step: '0.01' },
      date_in: { type: 'datetime' },
      date_out: { type: 'datetime' },
      a_flags: { maxLength: 15 },
      lane: { type: 'number', step: 1 },
      comment: { maxLength: 255 }
    }
  },
  sample: {
    endpoint: 'samples',
    pk: ['s_id', 's_stamp'], 
    displayColumns: ['s_id', 's_stamp', 'name', 'quantity', 'weight_net', 's_flags'],
    formColumns: [
      's_id', 's_stamp', 'name', 'weight_net', 'weight_bru', 'weight_tar',
      'quantity', 'distance', 'date_crumbled', 's_flags', 'lane', 'comment'
    ],
    immutable: ['s_id', 's_stamp'],
    fieldConfigs: {
      s_id: { maxLength: 13, required: true },
      s_stamp: { type: 'datetime', required: true },
      name: { maxLength: 255 },
      weight_net: { type: 'number', step: '0.01' },
      weight_bru: { type: 'number', step: '0.01' },
      weight_tar: { type: 'number', step: '0.01' },
      quantity: { type: 'number', step: 1 },
      distance: { type: 'number', step: '0.01' },
      date_crumbled: { type: 'datetime' },
      s_flags: { maxLength: 10 },
      lane: { type: 'number', step: 1 },
      comment: { maxLength: 255 }
    }
  },
  box: {
    endpoint: 'boxes',
    pk: 'b_id',
    displayColumns: ['b_id', 'name', 'num_max', 'type', 'comment'],
    formColumns: ['b_id', 'name', 'num_max', 'type', 'comment'],
    immutable: ['b_id'], 
    fieldConfigs: {
      b_id: { maxLength: 4, required: true },
      name: { maxLength: 255 },
      num_max: { type: 'number', step: 1, min: 1, max: 999 },
      type: { type: 'number', step: 1, min: 1, max: 9 },
      comment: { maxLength: 255 }
    }
  },
  boxpos: {
    endpoint: 'boxpos',
    pk: ['b_id', 'bpos_id'], 
    displayColumns: ['b_id', 'bpos_id', 's_id', 's_stamp', 'date_exported'],
    formColumns: ['b_id', 'bpos_id', 's_id', 'date_exported'],
    immutable: ['b_id', 'bpos_id'], 
    fieldConfigs: {
      b_id: { maxLength: 4, required: true },
      bpos_id: { type: 'number', step: 1, required: true },
      s_id: { maxLength: 13 },
      s_stamp: { type: 'datetime' },
      date_exported: { type: 'datetime' }
    }
  },
  log: {
    endpoint: 'logs',
    pk: 'log_id',
    displayColumns: ['log_id', 'date_created', 'level', 'info', 's_id', 'a_id'],
    readOnly: true
  },
  threshold: {
    endpoint: 'thresholds',
    pk: 'th_id',
    displayColumns: ['th_id', 'value_min', 'value_max', 'date_changed'],
    formColumns: ['th_id', 'value_min', 'value_max'],
    immutable: ['th_id'],
    fieldConfigs: {
      th_id: { maxLength: 10, required: true },
      value_min: { type: 'number', step: '0.01' },
      value_max: { type: 'number', step: '0.01' }
    }
  }
};

export default {
  name: 'VenlabFrontend',
  components: { 
    LoadingBar,
    Create_Loader
   },
  props: {
    globalDateStart: String,
    globalDateEnd: String
  },

  watch: {
    globalDateStart(newVal) {
      this.searchParams.globalDateInFrom = newVal; 
      this.currentPage = 0;
      this.fetchTableData();
    },
    globalDateEnd(newVal) {
      this.searchParams.globalDateInTo = newVal;
      this.currentPage = 0;
      this.fetchTableData();
    }
  },

  data() {
    return {
      selectedTable: 'analysis',
      tableData: [],      
      currentPage: 0,
      totalPages: 0,   
      totalItems: 0,
      displayLimit: 25,
      loading: false,
      error: null,
      isSaving: false,
      
      searchQuery: '',
      activeSearchQuery: '',
      
      searchParams: {
        idFrom: '', idTo: '',
        sIdFrom: '', sIdTo: '',
        dateInFrom: '', dateInTo: '',
        dateOutFrom: '', dateOutTo: '',
        aFlagsFrom: '', aFlagsTo: '',
        globalDateInFrom: '', globalDateInTo: '',
      },

      sortKey: '',
      sortAsc: true,

      itemToEdit: null,
      isNewItem: false,

      showDeleteModal: false,
      itemToDelete: null,

      showDetailModal: false,
      itemToView: null,
      
      currentUserRole: '',

      showColumnSelector: false,
      showFilterSelector: false,
      selectedColumns: [],

      loadingCSV: false,

      error_isRequired: false,
      error_isRequired_message: '',
    };
  },

  computed: {
    currentSchema() {
      return SCHEMAS[this.selectedTable] || SCHEMAS.analysis;
    },
    availableColumns() {
      if (this.currentSchema.displayColumns) {
        return this.currentSchema.displayColumns;
      }
      if (this.tableData.length > 0) {
        return Object.keys(this.tableData[0]);
      }
      return [];
    },

    visibleColumns() {
      if (this.selectedColumns.length === 0) {
        return this.availableColumns;
      }
      return this.availableColumns.filter(col => this.selectedColumns.includes(col));
    },
    formColumns() {
      return this.currentSchema.formColumns || [];
    },
    filteredCount() {
       let data = this.tableData;
       if (this.activeSearchQuery) {
        const query = this.activeSearchQuery.toLowerCase();
        data = data.filter(row => {
          return Object.values(row).some(val => 
            String(val || '').toLowerCase().includes(query)
          );
        });
       }
       return data.length;
    },
    sortedData() {
      let data = this.tableData.slice();
      if (this.activeSearchQuery) {
        const query = this.activeSearchQuery.toLowerCase();
        data = data.filter(row => {
          return Object.values(row).some(val => 
            String(val || '').toLowerCase().includes(query)
          );
        });
      }
      if (this.sortKey) {
        data.sort((a, b) => {
          let aVal = a[this.sortKey];
          let bVal = b[this.sortKey];
          if (aVal === null) aVal = '';
          if (bVal === null) bVal = '';

          if (!isNaN(parseFloat(aVal)) && isFinite(aVal) && !this.sortKey.includes('id')) {
            aVal = parseFloat(aVal);
            bVal = parseFloat(bVal);
          } else {
            aVal = String(aVal).toLowerCase();
            bVal = String(bVal).toLowerCase();
          }
          if (aVal < bVal) return this.sortAsc ? -1 : 1;
          if (aVal > bVal) return this.sortAsc ? 1 : -1;
          return 0;
        });
      }
      return data; 
    },
    
    canEdit() {
      return ['Admin', 'Researcher'].includes(this.currentUserRole);
    },
    canDelete() {
      return this.currentUserRole === 'Admin';
    }
  },

  mounted() {
    this.updateUserRole();
    
    if(this.globalDateStart) this.searchParams.globalDateInFrom = this.globalDateStart;
    if(this.globalDateEnd) this.searchParams.globalDateInTo = this.globalDateEnd;

    this.fetchTableData();
    document.addEventListener('click', this.closeSelectors); 
    this.loadingCSV = false
  },

  beforeUnmount() {
    document.removeEventListener('click', this.closeSelectors);
  },

  methods: {
    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(';').shift();
      return null;
    },

    updateUserRole() {
      this.currentUserRole = this.getCookie('role') || localStorage.getItem('role') || '';
    },

    onTableChange() {
      this.tableData = [];
      this.itemToEdit = null;
      this.error = null;
      this.sortKey = '';
      this.searchQuery = '';
      this.activeSearchQuery = '';
      
      this.currentPage = 0;
      
      this.resetFilters(false); 

      this.selectedColumns = []; 
      
      this.showColumnSelector = false;
      this.showFilterSelector = false;

      this.error_isRequired = false;
      this.error_isRequired_message = '';
      
      this.fetchTableData();
    },

    applySearch() {
        this.activeSearchQuery = this.searchQuery;
    },
    
    applyBackendFilter() {
        this.currentPage = 0;
        this.showFilterSelector = false;
        this.fetchTableData();
    },

    resetFilters(doFetch = true) {
        // Reset Search Params aber behalte Globale Filter
        const globals = {
            globalDateInFrom: this.searchParams.globalDateInFrom,
            globalDateInTo: this.searchParams.globalDateInTo
        };

        this.searchParams = {
            idFrom: '', idTo: '',
            sIdFrom: '', sIdTo: '',
            dateInFrom: '', dateInTo: '',
            dateOutFrom: '', dateOutTo: '',
            aFlagsFrom: '', aFlagsTo: '',
            ...globals
        };
        if(doFetch) this.fetchTableData();
    },
    
    changePage(newPage) {
        if (newPage >= 0 && newPage < this.totalPages) {
            this.currentPage = newPage;
            this.fetchTableData();
        }
    },

    async fetchTableData() {
      this.loading = true;
      this.error = null;
      this.itemToEdit = null;
      this.updateUserRole();

      // AXIOS: Parameter Objekt bauen statt URLSearchParams
      const queryParams = {
        page: this.currentPage,
        size: this.displayLimit
      };
      
      // Filter Params (nur für Analysis)
      if (this.selectedTable === 'analysis') {
          if (this.searchParams.idFrom) queryParams['aId.from'] = this.searchParams.idFrom;
          if (this.searchParams.idTo) queryParams['aId.to'] = this.searchParams.idTo;
          
          if (this.searchParams.sIdFrom) queryParams['sId.from'] = this.searchParams.sIdFrom;
          if (this.searchParams.sIdTo) queryParams['sId.to'] = this.searchParams.sIdTo;
          
          // Lokale Datum Filter
          if (this.searchParams.dateInFrom) queryParams['dateIn.from'] = this.searchParams.dateInFrom;
          if (this.searchParams.dateInTo) queryParams['dateIn.to'] = this.searchParams.dateInTo;
          
          if (this.searchParams.dateOutFrom) queryParams['dateOut.from'] = this.searchParams.dateOutFrom;
          if (this.searchParams.dateOutTo) queryParams['dateOut.to'] = this.searchParams.dateOutTo;
          
          if (this.searchParams.aFlagsFrom) queryParams['aFlags.from'] = this.searchParams.aFlagsFrom;
          if (this.searchParams.aFlagsTo) queryParams['aFlags.to'] = this.searchParams.aFlagsTo;

          if (this.searchParams.globalDateInFrom) queryParams['globalDateIn.from'] = this.searchParams.globalDateInFrom;
          if (this.searchParams.globalDateInTo) queryParams['globalDateIn.to'] = this.searchParams.globalDateInTo;
      }
      
      try {
        // AXIOS Call
        // Pfad: /api + endpoint + /filter
        // api.js hat baseURL /api, also hier nur endpoint...
        const response = await api.get(`/${this.currentSchema.endpoint}/filter`, {
            params: queryParams
        });

        const data = response.data; 
        
        this.tableData = data.content;
        this.totalPages = data.totalPages;
        this.totalItems = data.totalElements;
        this.currentPage = data.number;
        
        if (this.selectedColumns.length === 0) {
            this.selectAllColumns();
        }

      } catch (error) {
        console.error('Fetch error:', error);
        
        // 401 Check ist jetzt im Interceptor, aber UI Feedback hier:
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
            this.$emit('logout');
            return;
        }
        
        this.error = error;
      } finally {
        this.loading = false;
      }
    },

    async exportFilteredCSV() {
      this.loadingCSV = true;
      this.error = null;

      if(this.selectedTable === 'analysis' || this.selectedTable === 'sample') {
        
        this.updateUserRole();

        // AXIOS Params
        const queryParams = {};
        
        // Filter Params (nur für Analysis) - Analog zu fetchTableData
        if (this.selectedTable === 'analysis') {
            if (this.searchParams.idFrom) queryParams['aId.from'] = this.searchParams.idFrom;
            if (this.searchParams.idTo) queryParams['aId.to'] = this.searchParams.idTo;
            
            if (this.searchParams.sIdFrom) queryParams['sId.from'] = this.searchParams.sIdFrom;
            if (this.searchParams.sIdTo) queryParams['sId.to'] = this.searchParams.sIdTo;
            
            if (this.searchParams.dateInFrom) queryParams['dateIn.from'] = this.searchParams.dateInFrom;
            if (this.searchParams.dateInTo) queryParams['dateIn.to'] = this.searchParams.dateInTo;

            if (this.searchParams.globalDateInFrom) queryParams['globalDateIn.from'] = this.searchParams.globalDateInFrom;
            if (this.searchParams.globalDateInTo) queryParams['globalDateIn.to'] = this.searchParams.globalDateInTo;
            
            if (this.searchParams.dateOutFrom) queryParams['dateOut.from'] = this.searchParams.dateOutFrom;
            if (this.searchParams.dateOutTo) queryParams['dateOut.to'] = this.searchParams.dateOutTo;
            
            if (this.searchParams.aFlagsFrom) queryParams['aFlags.from'] = this.searchParams.aFlagsFrom;
            if (this.searchParams.aFlagsTo) queryParams['aFlags.to'] = this.searchParams.aFlagsTo;
        }
        
        try {
          // AXIOS Blob Request
          const response = await api.get(`/${this.currentSchema.endpoint}/export`, {
            params: queryParams,
            responseType: 'blob' // WICHTIG: Blob anfordern
          });

          // Blob verarbeiten
          const blob = new Blob([response.data]);
          
          const downloadUrl = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = downloadUrl;
          
          const timestamp = new Date().toISOString().split('T')[0];
          link.setAttribute('download', `${this.selectedTable}_export_${timestamp}.csv`);
          
          // Link klicken und entfernen
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          
          window.URL.revokeObjectURL(downloadUrl);

        } catch (error) {
          console.error('Export error:', error);
          if (error.response && error.response.status === 401) {
             this.$emit('logout');
             return;
          }
          this.error = error.message;
        } finally {
          this.loadingCSV = false;
        }
      } else {
        this.showColumnSelector = false,
        this.showFilterSelector = false,
        this.loadingCSV = false;
        this.error = "Export nur verfügbar in Analysis & Sample";
      }
    },

    createNew() {
      this.isNewItem = true;
      const emptyItem = {};
      const config = this.currentSchema.fieldConfigs || {};

      this.formColumns.forEach(col => {
        const fieldType = config[col]?.type;
        if (fieldType === 'number' || fieldType === 'datetime') {
           emptyItem[col] = null;
        } else {
           emptyItem[col] = '';
        }
      });
      if (this.selectedTable === 'box') {
        emptyItem['num_max'] = 40;
        emptyItem['type'] = 1; 
      }
      this.itemToEdit = emptyItem;
      this.scrollToForm();
    },

    editItem(item) {
      this.isNewItem = false;
      const copy = JSON.parse(JSON.stringify(item));

      copy.__pk = {};
      if (Array.isArray(this.currentSchema.pk)) {
        this.currentSchema.pk.forEach(key => {
          copy.__pk[key] = item[key];
        });
      } else {
        copy.__pk[this.currentSchema.pk] = item[this.currentSchema.pk];
      }

      const config = this.currentSchema.fieldConfigs || {};
      for (const [key, value] of Object.entries(copy)) {
        if (config[key]?.type === 'datetime' && value) {
          copy[key] = this.toDateTimeLocal(value);
        }
      }
      this.itemToEdit = copy;
      this.scrollToForm();
    },

    cancelEdit() {
      this.itemToEdit = null;
      this.error_isRequired = false;
      this.error_isRequired_message = '';
    },

    async saveItem() {

      const config = this.currentSchema.fieldConfigs || {};
      const missingFields = [];

      for (const col of this.formColumns) {
        if (config[col]?.required) {
          const val = this.itemToEdit[col];
          // Prüfen auf null, undefined oder leerer String
          if (val === null || val === undefined || val === '') {
            missingFields.push(this.formatHeader(col));
          }
        }
      }

      if (missingFields.length > 0) {
        // Abbruch wenn Pflichtfelder fehlen
        this.error_isRequired_message = `Fehlende Felder: - ${missingFields.join(' - ')}`
        console.log(this.error_isRequired_message);
        this.error_isRequired = true;
        return; 
      }

      this.isSaving = true;

      const schema = this.currentSchema;
      const dataToSend = { ...this.itemToEdit };
      // config wurde oben schon definiert

      for (const key in dataToSend) {
        if (config[key]?.type === 'number') {
           if (dataToSend[key] === '' || dataToSend[key] === null) {
              dataToSend[key] = null;
           } else {
              dataToSend[key] = Number(dataToSend[key]);
           }
        }
        if (config[key]?.type === 'datetime' && dataToSend[key]) {
           try {
             dataToSend[key] = new Date(dataToSend[key]).toISOString();
           } catch(e) { console.warn("Date parsing issue", e); }
        }
      }

      let url = `/${schema.endpoint}`; // Leading slash for axios baseUrl
      let method = 'post';

      if (!this.isNewItem) {
        method = 'put';
        url = this.constructUrlWithId(schema, dataToSend); 
      }

      try {
        // AXIOS POST/PUT
        // api[method] ruft dynamisch api.post oder api.put auf
        await api[method](url, dataToSend);

        await this.fetchTableData();
        this.itemToEdit = null;
      } catch (error) {
        console.log(`Fehler beim Speichern:\n${error.message}`);
        if (error.response && error.response.status === 401) {
             this.$emit('logout');
             return;
        }
        this.error = error;
      } finally {
        this.isSaving = false;
      }
    },

    deleteItem(item) {
      this.itemToDelete = item;
      this.showDeleteModal = true;
    },

    closeDeleteModal() {
      this.showDeleteModal = false;
      this.itemToDelete = null;
    },

    async confirmDelete() {
      if (!this.itemToDelete) return;
      
      const schema = this.currentSchema;
      const item = this.itemToDelete;
      this.closeDeleteModal();

      try {
        const url = this.constructUrlWithId(schema, item);
        
        // AXIOS DELETE
        await api.delete(url);

        await this.fetchTableData();
        if (this.itemToEdit && JSON.stringify(this.itemToEdit) === JSON.stringify(item)) {
            this.itemToEdit = null;
        }
      } catch (error) {
        console.log(`Fehler beim Löschen: ${error.message}`);
        if (error.response && error.response.status === 401) {
             this.$emit('logout');
        }
      }
    },

    constructUrlWithId(schema, item) {
      // AXIOS: BaseURL ist /api, also hier relativ starten
      let url = `/${schema.endpoint}`;
      if (Array.isArray(schema.pk)) {
        schema.pk.forEach(key => {
          const val = item.__pk && item.__pk[key] !== undefined ? item.__pk[key] : item[key];
          url += `/${encodeURIComponent(val)}`;
        });
      } else {
        url += `/${item[schema.pk]}`;
      }
      return url;
    },

    formatHeader(col) {
      if(!col) return '';
      return col.charAt(0).toUpperCase() + col.slice(1).replace(/_/g, ' ');
    },

    formatCell(col, value) {
      if (value === null || value === undefined) return '-';
      const config = this.currentSchema.fieldConfigs || {};
      if (config[col]?.type === 'datetime' || col.includes('date') || col.includes('stamp')) {
        try {
           return new Date(value).toLocaleString('de-DE');
        } catch (e) { return value; }
      }
      return value;
    },

    toDateTimeLocal(isoString) {
      if (!isoString) return '';
      return new Date(isoString).toISOString().slice(0, 19); 
    },

    getFieldConfig(col) {
      return (this.currentSchema.fieldConfigs && this.currentSchema.fieldConfigs[col]) || {};
    },
    
    isFieldRequired(col) {
      return this.getFieldConfig(col).required === true;
    },
    
    isFieldImmutable(col) {
      return this.currentSchema.immutable && this.currentSchema.immutable.includes(col);
    },

    scrollToForm() {
      this.$nextTick(() => {
        if (this.$refs.formContainer) {
          this.$refs.formContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
      });
    },
    
    sortBy(col) {
      if (this.sortKey === col) {
        this.sortAsc = !this.sortAsc;
      } else {
        this.sortKey = col;
        this.sortAsc = true;
      }
    },

    viewItem(item) {
      this.itemToView = { ...item }; 
      this.showDetailModal = true;
    },

    closeDetailModal() {
      this.showDetailModal = false;
      this.itemToView = null;
    },
    
    editFromDetail(item) {
      this.closeDetailModal();
      this.editItem(item);
    },
    selectAllColumns() {
      this.selectedColumns = [...this.availableColumns];
    },

    deselectAllColumns() {
      this.selectedColumns = [];
    },

    toggleColumnSelector() {
      this.showColumnSelector = !this.showColumnSelector;
      if (this.showColumnSelector) {
        this.showFilterSelector = false;
      }
    },

    toggleFilterSelector() {
      this.showFilterSelector = !this.showFilterSelector;
      if (this.showFilterSelector) {
        this.showColumnSelector = false;
      }
    },

    closeSelectors(event) {
      // Schließt beide Selectors bei Klick außerhalb
      if (!event.target.closest('.column-selector')) {
        this.showColumnSelector = false;
        this.showFilterSelector = false;
      }
    },
  }
};
</script>

<style scoped>
/* Styles bleiben exakt wie vorher */
.container { font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; max-width: 1400px; margin: 0 auto; padding: 20px; min-height: 100%; color: #333; transition: background-color 0.3s; }
header h1 { margin-bottom: 20px; color: #2c3e50; }
.table-selector { display: flex; flex-wrap: wrap; align-items: flex-end; gap: 15px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); margin-bottom: 20px; }
.control-group { display: flex; flex-direction: column; gap: 5px; }
.control-group label { font-size: 0.85rem; font-weight: 600; color: #6c757d; text-transform: uppercase; }
.search-wrapper { flex-grow: 1; }
.search-input-group { display: flex; }
input, select, .search-input { padding: 8px 12px; border: 1px solid #ced4da; border-radius: 4px; font-size: 1rem; }
.search-input { border-top-right-radius: 0; border-bottom-right-radius: 0; flex: 1; }
.button-group { margin-left: auto; display: flex; gap: 10px; }
.btn { border: none; border-radius: 4px; padding: 8px 16px; cursor: pointer; font-weight: 600; transition: background 0.2s, color 0.2s; display: inline-flex; align-items: center; justify-content: center; gap: 8px; }
.btn-search { background: #7108c7; color: white; border-radius: 0 4px 4px 0; padding: 0 12px; }
.btn-load { background: #7ca7cd; color: white; }
.btn-save { background: #18ad68; color: white; }
.btn-cancel { background: #f8f9fa; color: #333; border: 1px solid #ddd; }
.action-buttons { display: flex; gap: 5px; }
.action-buttons .btn { padding: 6px; }
.btn-edit { background: #f59e0b; color: white; }
.btn-edit:hover { background: #d97706; }
.btn-delete { background: #ef4444; color: white; }
.btn-delete:hover { background: #dc2626; }
.btn-logout { background: #dc3545; color: white; }
.btn:hover { opacity: 0.9; }
.mdi-icon { width: 20px; height: 20px; }
.action-buttons .mdi-icon { width: 18px; height: 18px; }
.table-wrapper { background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); overflow-x: auto; }
table { width: 100%; border-collapse: collapse; font-size: 0.95rem; }
th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #e9ecef; }
th { background: #f1f3f5; font-weight: 600; color: #495057; white-space: nowrap; }
.sortable-header { cursor: pointer; user-select: none; }
.sortable-header:hover { background: #e2e6ea; }
tr:hover { background: #f8f9fa; }
.sticky-col { position: sticky; right: 0; box-shadow: -2px 0 5px rgba(0,0,0,0.05); }
.th-sticky { background: #f1f3f5; z-index: 2; }
.td-sticky { background: white; }
tr:hover .td-sticky { background: #f8f9fa; }
.form-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); margin-top: 30px; border-top: 4px solid #198754; }
.form-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; margin-bottom: 20px; }
.form-group { display: flex; flex-direction: column; }
.form-group label { font-weight: 600; margin-bottom: 5px; font-size: 0.9rem; }
.form-group input:focus, .form-group select:focus { border-color: #198754; outline: 0; box-shadow: 0 0 0 0.2rem rgba(25, 135, 84, 0.25); }
.input-disabled { background-color: #e9ecef; cursor: not-allowed; color: #6c757d; }
.code-input { font-family: 'Courier New', monospace; letter-spacing: 1px; }
.required-mark { color: red; }
.field-hint { font-size: 0.75rem; color: #6c757d; margin-top: 3px; }
.form-actions { display: flex; justify-content: flex-end; gap: 10px; border-top: 1px solid #e9ecef; padding-top: 20px; }
.status-text { text-align: center; margin-top: 50px; color: #6c757d; }
.error-text { text-align: center; margin-top: 20px; color: #dc3545; background: #f8d7da; padding: 15px; border-radius: 4px; }
.info-text { padding: 10px 15px; color: #6c757d; font-size: 0.85rem; text-align: right; }
.th-content { display: flex; align-items: center; gap: 5px; }
.small-icon { width: 16px; height: 16px; }
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2); width: 100%; max-width: 400px; animation: fadeIn 0.2s; }
.modal-content h3 { margin-top: 0; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
@keyframes fadeIn { from { opacity: 0; transform: translateY(-10px); } to { opacity: 1; transform: translateY(0); } }
.btn-view { background: #0d6efd; color: white; }
.btn-view:hover { background: #0b5ed7; }
.modal-lg { max-width: 600px; max-height: 90vh; display: flex; flex-direction: column; }
.modal-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; border-bottom: 1px solid #dee2e6; padding-bottom: 10px; }
.modal-header h3 { margin: 0; color: #2c3e50; }
.btn-close { background: none; border: none; font-size: 1.5rem; cursor: pointer; color: #6c757d; }
.detail-grid { display: grid; grid-template-columns: 1fr 1.5fr; gap: 10px; overflow-y: auto; padding-right: 5px; margin-bottom: 20px; }
.detail-row { display: contents; }
.detail-label { font-weight: 600; color: #6c757d; padding: 8px 0; border-bottom: 1px solid #f1f3f5; align-self: center; }
.detail-value { color: #212529; padding: 8px 0; border-bottom: 1px solid #f1f3f5; word-break: break-all; }
.table-footer { display: flex; justify-content: space-between; align-items: center; padding: 10px 15px; border-top: 1px solid #e9ecef; }
.pagination-controls { display: flex; align-items: center; gap: 5px; }
.btn-page { padding: 5px 10px; background: #f8f9fa; border: 1px solid #dee2e6; color: #333; font-size: 0.9rem; }
.btn-page:disabled { opacity: 0.5; cursor: default; }
.btn-page:hover:not(:disabled) { background: #e2e6ea; }
.page-info { font-size: 0.9rem; margin: 0 10px; color: #6c757d; }
:global(body.dark-theme) { background-color: #0f172a !important; }
:global(body.dark-theme h1) { color: #ffffff !important; }
:global(body.dark-theme .table-selector) { background-color: #1e293b !important; border-radius: 8px !important; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5) !important; }
:global(body.dark-theme .table-selector .control-group label) { color: #94a3b8 !important; }
:global(body.dark-theme .table-selector input), :global(body.dark-theme .table-selector select) { background-color: #0f172a !important; color: #ffffff !important; border: 1px solid #334155 !important; }
:global(body.dark-theme .table-selector input::placeholder) { color: #64748b !important; }
:global(body.dark-theme .table-selector input:focus), :global(body.dark-theme .table-selector select:focus) { border-color: #910dfd !important; box-shadow: 0 0 0 0.2rem rgba(145, 13, 253, 0.25) !important; }
:global(body.dark-theme .table-wrapper) { background-color: #0f172a !important; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5) !important; }
:global(body.dark-theme th) { background-color: #334155 !important; color: #94a3b8 !important;font-weight: 600; border-bottom: 2px solid #334155 !important; }
:global(body.dark-theme .th-sticky) { background-color: #1e293b !important; }
:global(body.dark-theme td) { background-color: #1e293b !important; letter-spacing: 0.02em; color: #cbd5e1 !important; font-weight: 450; border-bottom: 1px solid #334155 !important; }
:global(body.dark-theme .td-sticky) { background-color: #0f172a !important; }
:global(body.dark-theme tr:hover td), :global(body.dark-theme tr:hover .td-sticky) { background-color: #2d3a4f !important; }
:global(body.dark-theme .sortable-header:hover) { background-color: #334155 !important; }
:global(body.dark-theme .info-text) { color: #94a3b8 !important; }
:global(body.dark-theme .page-info) { color: #94a3b8 !important; }
:global(body.dark-theme .btn-load) { background-color: #475569 !important; color: white !important; }
:global(body.dark-theme .btn-load:hover) { background-color: #64748b !important; }
:global(body.dark-theme .btn-search) { background-color: #910dfd !important; color: white !important; }
:global(body.dark-theme .btn-page) { background-color: #334155 !important; border-color: #475569 !important; color: #fff !important; }
:global(body.dark-theme .btn-page:hover:not(:disabled)) { background-color: #475569 !important; }
:global(body.dark-theme .table-footer) { border-top-color: #334155 !important; }
:global(body.dark-theme .form-container) { background-color: #1e293b !important; border-top: 4px solid #22c55e !important; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5) !important; }
:global(body.dark-theme .form-container h3) { color: #ffffff !important; }
:global(body.dark-theme .form-group label) { color: #cbd5e1 !important; }
:global(body.dark-theme .form-container input), :global(body.dark-theme .form-container select) { background-color: #0f172a !important; color: #ffffff !important; border: 1px solid #334155 !important; }
:global(body.dark-theme .form-container input:focus), :global(body.dark-theme .form-container select:focus) { border-color: #22c55e !important; box-shadow: 0 0 0 0.2rem rgba(34, 197, 94, 0.25) !important; }
:global(body.dark-theme .input-disabled) { background-color: #334155 !important; color: #94a3b8 !important; }
:global(body.dark-theme .form-actions) { border-top: 1px solid #334155 !important; }
:global(body.dark-theme .btn-cancel) { background-color: #475569 !important; color: #ffffff !important; border: 1px solid #64748b !important; }
:global(body.dark-theme .modal-overlay) { background-color: rgba(0, 0, 0, 0.7) !important; }
:global(body.dark-theme .modal-content) { background-color: #1e293b !important; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5) !important; color: #ffffff !important; }
:global(body.dark-theme .modal-header) { border-bottom: 1px solid #334155 !important; }
:global(body.dark-theme .modal-header h3) { color: #ffffff !important; }
:global(body.dark-theme .btn-close) { color: #94a3b8 !important; }
:global(body.dark-theme .btn-close:hover) { color: #ffffff !important; }
:global(body.dark-theme .detail-label) { color: #94a3b8 !important; border-bottom: 1px solid #334155 !important; }
:global(body.dark-theme .detail-value) { color: #ffffff !important; border-bottom: 1px solid #334155 !important; }
:global(body.dark-theme .status-text) { color: #94a3b8 !important; }
:global(body.dark-theme .error-text) { background-color: #7f1d1d !important; color: #fecaca !important; }
:global(body.dark-theme tbody tr:nth-child(even) td) { background-color: #1a2536 !important; }
.column-selector { position: relative; }
.btn-columns { display: flex; align-items: center; gap: 6px; background: #6c757d; color: white; padding: 8px 12px; border: none; border-radius: 6px; cursor: pointer; }
.btn-columns:hover { background: #5a6268; }
.column-dropdown { position: absolute; top: 100%; left: 0; margin-top: 4px; background: white; border: 1px solid #dee2e6; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); min-width: 200px; max-height: 300px; overflow-y: auto; z-index: 1000; }
.filter-dropdown { width: 300px; max-height: 500px; }
.column-dropdown-header { display: flex; align-items: center; gap: 10px; padding: 10px 12px; border-bottom: 1px solid #dee2e6; background: #f8f9fa; border-radius: 8px 8px 0 0; font-weight: 600; font-size: 0.85rem; }
.column-dropdown-header span { flex: 1; }
.btn-link { background: none; border: none; color: #007bff; cursor: pointer; font-size: 0.8rem; padding: 2px 6px; }
.btn-link:hover { text-decoration: underline; }
.column-option { padding: 8px 12px; }
.column-option:hover { background: #f1f3f5; }
.column-option label { display: flex; align-items: center; gap: 8px; cursor: pointer; font-size: 0.9rem; }
.column-option input[type="checkbox"] { width: 16px; height: 16px; cursor: pointer; }
.filter-content { padding: 10px; }
.filter-group { margin-bottom: 10px; }
.filter-group label { font-size: 0.8rem; font-weight: 600; color: #6c757d; display: block; margin-bottom: 4px; }
.range-inputs { display: flex; gap: 5px; }
.range-inputs input { width: 50%; font-size: 0.85rem; padding: 4px 8px; }
:global(body.dark-theme .column-dropdown) { background: #1e293b; border-color: #334155; }
:global(body.dark-theme .column-dropdown-header) { background: #0f172a; border-color: #334155; color: #fff; }
:global(body.dark-theme .column-option:hover) { background: #334155; }
:global(body.dark-theme .column-option label) { color: #e2e8f0; }
:global(body.dark-theme .filter-group label) { color: #cbd5e1; }
</style>