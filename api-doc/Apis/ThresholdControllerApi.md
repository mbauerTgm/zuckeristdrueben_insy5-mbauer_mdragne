# ThresholdControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createThreshold**](ThresholdControllerApi.md#createThreshold) | **POST** /api/thresholds |  |
| [**getAllThresholds**](ThresholdControllerApi.md#getAllThresholds) | **GET** /api/thresholds |  |
| [**getAllThresholds3**](ThresholdControllerApi.md#getAllThresholds3) | **GET** /api/thresholds/filter |  |
| [**getThresholdById**](ThresholdControllerApi.md#getThresholdById) | **GET** /api/thresholds/{thId} |  |
| [**updateThreshold**](ThresholdControllerApi.md#updateThreshold) | **PUT** /api/thresholds/{thId} |  |


<a name="createThreshold"></a>
# **createThreshold**
> Threshold createThreshold(Threshold)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **Threshold** | [**Threshold**](../Models/Threshold.md)|  | |

### Return type

[**Threshold**](../Models/Threshold.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="getAllThresholds"></a>
# **getAllThresholds**
> List getAllThresholds(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

[**List**](../Models/Threshold.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllThresholds3"></a>
# **getAllThresholds3**
> PageThreshold getAllThresholds3(globalFilter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageThreshold**](../Models/PageThreshold.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getThresholdById"></a>
# **getThresholdById**
> Threshold getThresholdById(thId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **thId** | **String**|  | [default to null] |

### Return type

[**Threshold**](../Models/Threshold.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="updateThreshold"></a>
# **updateThreshold**
> Threshold updateThreshold(thId, Threshold)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **thId** | **String**|  | [default to null] |
| **Threshold** | [**Threshold**](../Models/Threshold.md)|  | |

### Return type

[**Threshold**](../Models/Threshold.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

