# AnalysisControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createAnalysis1**](AnalysisControllerApi.md#createAnalysis1) | **POST** /api/analysis |  |
| [**deleteAnalysis1**](AnalysisControllerApi.md#deleteAnalysis1) | **DELETE** /api/analysis/{id} |  |
| [**exportAnalysisToCsv1**](AnalysisControllerApi.md#exportAnalysisToCsv1) | **GET** /api/analysis/export |  |
| [**filterAnalysis1**](AnalysisControllerApi.md#filterAnalysis1) | **GET** /api/analysis/filter |  |
| [**getAllAnalysis1**](AnalysisControllerApi.md#getAllAnalysis1) | **GET** /api/analysis |  |
| [**getAnalysisById1**](AnalysisControllerApi.md#getAnalysisById1) | **GET** /api/analysis/{id} |  |
| [**updateAnalysis1**](AnalysisControllerApi.md#updateAnalysis1) | **PUT** /api/analysis/{id} |  |


<a name="createAnalysis1"></a>
# **createAnalysis1**
> Analysis createAnalysis1(Analysis)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **Analysis** | [**Analysis**](../Models/Analysis.md)|  | |

### Return type

[**Analysis**](../Models/Analysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="deleteAnalysis1"></a>
# **deleteAnalysis1**
> deleteAnalysis1(id)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Long**|  | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="exportAnalysisToCsv1"></a>
# **exportAnalysisToCsv1**
> exportAnalysisToCsv1(searchDto, globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **searchDto** | [**AnalysisFilterDto**](../Models/.md)|  | [default to null] |
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="filterAnalysis1"></a>
# **filterAnalysis1**
> PageAnalysis filterAnalysis1(filterDto, globalFilter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filterDto** | [**AnalysisFilterDto**](../Models/.md)|  | [default to null] |
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageAnalysis**](../Models/PageAnalysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllAnalysis1"></a>
# **getAllAnalysis1**
> List getAllAnalysis1(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

[**List**](../Models/Analysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAnalysisById1"></a>
# **getAnalysisById1**
> Analysis getAnalysisById1(id)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Long**|  | [default to null] |

### Return type

[**Analysis**](../Models/Analysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="updateAnalysis1"></a>
# **updateAnalysis1**
> Analysis updateAnalysis1(id, Analysis)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Long**|  | [default to null] |
| **Analysis** | [**Analysis**](../Models/Analysis.md)|  | |

### Return type

[**Analysis**](../Models/Analysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

