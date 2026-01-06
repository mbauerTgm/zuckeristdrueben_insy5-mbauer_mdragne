# SampleControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createSample1**](SampleControllerApi.md#createSample1) | **POST** /api/samples |  |
| [**deleteSample1**](SampleControllerApi.md#deleteSample1) | **DELETE** /api/samples/{sId}/{sStamp} |  |
| [**exportSamplesToCsv1**](SampleControllerApi.md#exportSamplesToCsv1) | **GET** /api/samples/export |  |
| [**filterSamples1**](SampleControllerApi.md#filterSamples1) | **GET** /api/samples/filter |  |
| [**getAllSamples1**](SampleControllerApi.md#getAllSamples1) | **GET** /api/samples |  |
| [**getSampleById1**](SampleControllerApi.md#getSampleById1) | **GET** /api/samples/{sId}/{sStamp} |  |
| [**updateSample1**](SampleControllerApi.md#updateSample1) | **PUT** /api/samples/{sId}/{sStamp} |  |


<a name="createSample1"></a>
# **createSample1**
> Sample createSample1(Sample)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **Sample** | [**Sample**](../Models/Sample.md)|  | |

### Return type

[**Sample**](../Models/Sample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="deleteSample1"></a>
# **deleteSample1**
> deleteSample1(sId, sStamp)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sId** | **String**|  | [default to null] |
| **sStamp** | **String**|  | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="exportSamplesToCsv1"></a>
# **exportSamplesToCsv1**
> exportSamplesToCsv1(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="filterSamples1"></a>
# **filterSamples1**
> PageSample filterSamples1(globalFilter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageSample**](../Models/PageSample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllSamples1"></a>
# **getAllSamples1**
> List getAllSamples1(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

[**List**](../Models/Sample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getSampleById1"></a>
# **getSampleById1**
> Sample getSampleById1(sId, sStamp)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sId** | **String**|  | [default to null] |
| **sStamp** | **String**|  | [default to null] |

### Return type

[**Sample**](../Models/Sample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="updateSample1"></a>
# **updateSample1**
> Sample updateSample1(sId, sStamp, Sample)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sId** | **String**|  | [default to null] |
| **sStamp** | **String**|  | [default to null] |
| **Sample** | [**Sample**](../Models/Sample.md)|  | |

### Return type

[**Sample**](../Models/Sample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

