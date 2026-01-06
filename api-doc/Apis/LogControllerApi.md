# LogControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAllLogs2**](LogControllerApi.md#getAllLogs2) | **GET** /api/logs |  |
| [**getAllLogs3**](LogControllerApi.md#getAllLogs3) | **GET** /api/logs/filter |  |
| [**getLogById1**](LogControllerApi.md#getLogById1) | **GET** /api/logs/{logId} |  |


<a name="getAllLogs2"></a>
# **getAllLogs2**
> List getAllLogs2(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

[**List**](../Models/Log.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllLogs3"></a>
# **getAllLogs3**
> PageLog getAllLogs3(globalFilter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageLog**](../Models/PageLog.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getLogById1"></a>
# **getLogById1**
> Log getLogById1(logId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **logId** | **Long**|  | [default to null] |

### Return type

[**Log**](../Models/Log.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

