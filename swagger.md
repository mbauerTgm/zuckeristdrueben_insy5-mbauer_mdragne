# OpenAPI definition
## Version: v0

---

### [GET] /legacy/api/samples/{sId}/{sStamp}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| sId | path |  | Yes | string |
| sStamp | path |  | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Sample](#sample)<br> |

### [PUT] /legacy/api/samples/{sId}/{sStamp}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| sId | path |  | Yes | string |
| sStamp | path |  | Yes | string |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Sample](#sample)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Sample](#sample)<br> |

### [DELETE] /legacy/api/samples/{sId}/{sStamp}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| sId | path |  | Yes | string |
| sStamp | path |  | Yes | string |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /legacy/api/boxpos/{bId}/{bposId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |
| bposId | path |  | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [BoxPos](#boxpos)<br> |

### [PUT] /legacy/api/boxpos/{bId}/{bposId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |
| bposId | path |  | Yes | integer |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [BoxPos](#boxpos)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [BoxPos](#boxpos)<br> |

### [DELETE] /legacy/api/boxpos/{bId}/{bposId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |
| bposId | path |  | Yes | integer |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /legacy/api/boxes/{bId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Box](#box)<br> |

### [PUT] /legacy/api/boxes/{bId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Box](#box)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Box](#box)<br> |

### [DELETE] /legacy/api/boxes/{bId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /legacy/api/analysis/{id}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path |  | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Analysis](#analysis)<br> |

### [PUT] /legacy/api/analysis/{id}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path |  | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Analysis](#analysis)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Analysis](#analysis)<br> |

### [DELETE] /legacy/api/analysis/{id}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path |  | Yes | long |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /legacy/api/samples
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Sample](#sample) ]<br> |

### [POST] /legacy/api/samples
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Sample](#sample)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Sample](#sample)<br> |

### [GET] /legacy/api/boxpos
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [BoxPos](#boxpos) ]<br> |

### [POST] /legacy/api/boxpos
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [BoxPos](#boxpos)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [BoxPos](#boxpos)<br> |

### [GET] /legacy/api/boxes
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Box](#box) ]<br> |

### [POST] /legacy/api/boxes
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Box](#box)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Box](#box)<br> |

### [GET] /legacy/api/analysis
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Analysis](#analysis) ]<br> |

### [POST] /legacy/api/analysis
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Analysis](#analysis)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Analysis](#analysis)<br> |

### [GET] /legacy/api/thresholds
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Threshold](#threshold) ]<br> |

### [GET] /legacy/api/thresholds/{thId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| thId | path |  | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Threshold](#threshold)<br> |

### [GET] /legacy/api/thresholds/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageThreshold](#pagethreshold)<br> |

### [GET] /legacy/api/samples/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageSample](#pagesample)<br> |

### [GET] /legacy/api/samples/export
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /legacy/api/logs
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Log](#log) ]<br> |

### [GET] /legacy/api/logs/{logId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| logId | path |  | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Log](#log)<br> |

### [GET] /legacy/api/logs/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageLog](#pagelog)<br> |

### [GET] /legacy/api/boxpos/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageBoxPos](#pageboxpos)<br> |

### [GET] /legacy/api/boxes/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageBox](#pagebox)<br> |

### [GET] /legacy/api/analysis/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filterDto | query |  | Yes | [AnalysisFilterDto](#analysisfilterdto) |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageAnalysis](#pageanalysis)<br> |

### [GET] /legacy/api/analysis/export
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| searchDto | query |  | Yes | [AnalysisFilterDto](#analysisfilterdto) |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

---

### [GET] /api/thresholds/{thId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| thId | path |  | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Threshold](#threshold)<br> |

### [PUT] /api/thresholds/{thId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| thId | path |  | Yes | string |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Threshold](#threshold)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Threshold](#threshold)<br> |

### [GET] /api/thresholds
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Threshold](#threshold) ]<br> |

### [POST] /api/thresholds
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Threshold](#threshold)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Threshold](#threshold)<br> |

### [GET] /api/thresholds/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageThreshold](#pagethreshold)<br> |

---

### [GET] /api/samples/{sId}/{sStamp}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| sId | path |  | Yes | string |
| sStamp | path |  | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Sample](#sample)<br> |

### [PUT] /api/samples/{sId}/{sStamp}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| sId | path |  | Yes | string |
| sStamp | path |  | Yes | string |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Sample](#sample)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Sample](#sample)<br> |

### [DELETE] /api/samples/{sId}/{sStamp}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| sId | path |  | Yes | string |
| sStamp | path |  | Yes | string |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /api/samples
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Sample](#sample) ]<br> |

### [POST] /api/samples
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Sample](#sample)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Sample](#sample)<br> |

### [GET] /api/samples/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageSample](#pagesample)<br> |

### [GET] /api/samples/export
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

---

### [GET] /api/boxpos/{bId}/{bposId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |
| bposId | path |  | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [BoxPos](#boxpos)<br> |

### [PUT] /api/boxpos/{bId}/{bposId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |
| bposId | path |  | Yes | integer |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [BoxPos](#boxpos)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [BoxPos](#boxpos)<br> |

### [DELETE] /api/boxpos/{bId}/{bposId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |
| bposId | path |  | Yes | integer |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /api/boxpos
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [BoxPos](#boxpos) ]<br> |

### [POST] /api/boxpos
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [BoxPos](#boxpos)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [BoxPos](#boxpos)<br> |

### [GET] /api/boxpos/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageBoxPos](#pageboxpos)<br> |

---

### [GET] /api/boxes/{bId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Box](#box)<br> |

### [PUT] /api/boxes/{bId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Box](#box)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Box](#box)<br> |

### [DELETE] /api/boxes/{bId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| bId | path |  | Yes | string |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /api/boxes
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Box](#box) ]<br> |

### [POST] /api/boxes
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Box](#box)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Box](#box)<br> |

### [GET] /api/boxes/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageBox](#pagebox)<br> |

---

### [GET] /api/analysis/{id}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path |  | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Analysis](#analysis)<br> |

### [PUT] /api/analysis/{id}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path |  | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Analysis](#analysis)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Analysis](#analysis)<br> |

### [DELETE] /api/analysis/{id}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path |  | Yes | long |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### [GET] /api/analysis
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Analysis](#analysis) ]<br> |

### [POST] /api/analysis
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [Analysis](#analysis)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Analysis](#analysis)<br> |

### [GET] /api/analysis/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filterDto | query |  | Yes | [AnalysisFilterDto](#analysisfilterdto) |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageAnalysis](#pageanalysis)<br> |

### [GET] /api/analysis/export
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| searchDto | query |  | Yes | [AnalysisFilterDto](#analysisfilterdto) |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

---

### [POST] /api/auth/logout
#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: <br> |

### [POST] /api/auth/login
#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [LoginRequest](#loginrequest)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: <br> |

### [POST] /api/auth/admin/register
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| Authorization | header |  | Yes | string |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [RegisterRequest](#registerrequest)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: <br> |

---

### [GET] /api/reports/samples-suspicious
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageSample](#pagesample)<br> |

### [GET] /api/reports/samples-suspicious-timerange
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| start | query |  | Yes | string |
| end | query |  | Yes | string |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageSample](#pagesample)<br> |

### [GET] /api/reports/samples-multi-analysis
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageSampleAnalysisCount](#pagesampleanalysiscount)<br> |

### [GET] /api/reports/samples-bad-ean
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageSample](#pagesample)<br> |

### [GET] /api/reports/boxpos-no-analysis
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageBoxPos](#pageboxpos)<br> |

### [GET] /api/reports/boxpos-empty
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageBoxPos](#pageboxpos)<br> |

### [GET] /api/reports/analysis-zero-values
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageAnalysis](#pageanalysis)<br> |

### [GET] /api/reports/analysis-no-boxpos
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| filter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageAnalysis](#pageanalysis)<br> |

---

### [GET] /api/logs
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [ [Log](#log) ]<br> |

### [GET] /api/logs/{logId}
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| logId | path |  | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [Log](#log)<br> |

### [GET] /api/logs/filter
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| globalFilter | query |  | Yes | [AnalysisGlobalFilterDto](#analysisglobalfilterdto) |
| pageable | query |  | Yes | [Pageable](#pageable) |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | ***/***: [PageLog](#pagelog)<br> |

---
### Schemas

#### Analysis

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| pol | number |  | No |
| nat | number |  | No |
| kal | number |  | No |
| an | number |  | No |
| glu | number |  | No |
| dry | number |  | No |
| density | number |  | No |
| lane | integer |  | No |
| comment | string |  | No |
| aid | long |  | No |
| sid | string |  | No |
| sstamp | dateTime |  | No |
| aflags | string |  | No |
| a_id | long |  | No |
| s_id | string |  | No |
| s_stamp | dateTime |  | No |
| date_in | dateTime |  | No |
| date_out | dateTime |  | No |
| weight_mea | number |  | No |
| weight_nrm | number |  | No |
| weight_cur | number |  | No |
| weight_dif | number |  | No |
| a_flags | string |  | No |
| date_exported | dateTime |  | No |

#### Sample

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| name | string |  | No |
| quantity | integer |  | No |
| distance | number |  | No |
| lane | integer |  | No |
| comment | string |  | No |
| analyses | [ [Analysis](#analysis) ] |  | No |
| sflags | string |  | No |
| sid | string |  | No |
| sstamp | dateTime |  | No |
| s_id | string |  | No |
| s_stamp | dateTime |  | No |
| weight_net | number |  | No |
| weight_bru | number |  | No |
| weight_tar | number |  | No |
| date_crumbled | dateTime |  | No |
| s_flags | string |  | No |
| date_exported | dateTime |  | No |

#### BoxPos

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| sample | [Sample](#sample) |  | No |
| sid | string |  | No |
| sstamp | dateTime |  | No |
| bid | string |  | No |
| b_id | string |  | No |
| bpos_id | integer |  | No |
| s_id | string |  | No |
| s_stamp | dateTime |  | No |
| date_exported | dateTime |  | No |

#### Box

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| name | string |  | No |
| type | integer |  | No |
| comment | string |  | No |
| boxPositions | [ [BoxPos](#boxpos) ] |  | No |
| bid | string |  | No |
| b_id | string |  | No |
| num_max | integer |  | No |
| date_exported | dateTime |  | No |

#### Threshold

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| th_id | string |  | No |
| value_min | number |  | No |
| value_max | number |  | No |
| date_changed | dateTime |  | No |

#### LoginRequest

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| UsersID | string |  | No |
| password | string |  | No |

#### RegisterRequest

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| UsersID | string |  | No |
| password | string |  | No |
| roleName | string |  | No |

#### AnalysisGlobalFilterDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| globalDateIn | [RangeLocalDateTime](#rangelocaldatetime) |  | No |

#### RangeLocalDateTime

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| from | dateTime |  | No |
| to | dateTime |  | No |

#### Pageable

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer |  | No |
| size | integer |  | No |
| sort | [ string ] |  | No |

#### PageThreshold

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [Threshold](#threshold) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### Pageablenull

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| offset | long |  | No |
| paged | boolean |  | No |
| pageNumber | integer |  | No |
| pageSize | integer |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| unpaged | boolean |  | No |

#### Sortnull

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| empty | boolean |  | No |
| sorted | boolean |  | No |
| unsorted | boolean |  | No |

#### PageSample

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [Sample](#sample) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### Log

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| level | string |  | No |
| info | string |  | No |
| aid | long |  | No |
| sid | string |  | No |
| sstamp | dateTime |  | No |
| log_id | long |  | No |
| date_created | dateTime |  | No |
| s_id | string |  | No |
| s_stamp | dateTime |  | No |
| a_id | long |  | No |
| date_exported | dateTime |  | No |

#### PageLog

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [Log](#log) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### PageBoxPos

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [BoxPos](#boxpos) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### PageBox

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [Box](#box) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### AnalysisFilterDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| dateIn | [RangeLocalDateTime](#rangelocaldatetime) |  | No |
| dateOut | [RangeLocalDateTime](#rangelocaldatetime) |  | No |
| aid | [RangeLong](#rangelong) |  | No |
| sid | [RangeString](#rangestring) |  | No |
| aflags | [RangeString](#rangestring) |  | No |

#### RangeLong

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| from | long |  | No |
| to | long |  | No |

#### RangeString

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| from | string |  | No |
| to | string |  | No |

#### PageAnalysis

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [Analysis](#analysis) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### PageSampleAnalysisCount

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| totalElements | long |  | No |
| totalPages | integer |  | No |
| size | integer |  | No |
| content | [ [SampleAnalysisCount](#sampleanalysiscount) ] |  | No |
| number | integer |  | No |
| pageable | [Pageablenull](#pageablenull) |  | No |
| sort | [Sortnull](#sortnull) |  | No |
| numberOfElements | integer |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| empty | boolean |  | No |

#### SampleAnalysisCount

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| count | long |  | No |
| sid | string |  | No |
