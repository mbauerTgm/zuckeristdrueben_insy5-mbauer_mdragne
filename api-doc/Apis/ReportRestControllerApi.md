# ReportRestControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAnalysisNoBoxPos**](ReportRestControllerApi.md#getAnalysisNoBoxPos) | **GET** /api/reports/analysis-no-boxpos |  |
| [**getAnalysisZeroValues**](ReportRestControllerApi.md#getAnalysisZeroValues) | **GET** /api/reports/analysis-zero-values |  |
| [**getBoxPosEmpty**](ReportRestControllerApi.md#getBoxPosEmpty) | **GET** /api/reports/boxpos-empty |  |
| [**getBoxPosNoAnalysis**](ReportRestControllerApi.md#getBoxPosNoAnalysis) | **GET** /api/reports/boxpos-no-analysis |  |
| [**getSamplesBadEan**](ReportRestControllerApi.md#getSamplesBadEan) | **GET** /api/reports/samples-bad-ean |  |
| [**getSamplesMultiAnalysis**](ReportRestControllerApi.md#getSamplesMultiAnalysis) | **GET** /api/reports/samples-multi-analysis |  |
| [**getSuspiciousSamples**](ReportRestControllerApi.md#getSuspiciousSamples) | **GET** /api/reports/samples-suspicious |  |
| [**getSuspiciousSamplesInRange**](ReportRestControllerApi.md#getSuspiciousSamplesInRange) | **GET** /api/reports/samples-suspicious-timerange |  |


<a name="getAnalysisNoBoxPos"></a>
# **getAnalysisNoBoxPos**
> PageAnalysis getAnalysisNoBoxPos(filter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageAnalysis**](../Models/PageAnalysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAnalysisZeroValues"></a>
# **getAnalysisZeroValues**
> PageAnalysis getAnalysisZeroValues(filter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageAnalysis**](../Models/PageAnalysis.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getBoxPosEmpty"></a>
# **getBoxPosEmpty**
> PageBoxPos getBoxPosEmpty(filter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageBoxPos**](../Models/PageBoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getBoxPosNoAnalysis"></a>
# **getBoxPosNoAnalysis**
> PageBoxPos getBoxPosNoAnalysis(filter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageBoxPos**](../Models/PageBoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getSamplesBadEan"></a>
# **getSamplesBadEan**
> PageSample getSamplesBadEan(filter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageSample**](../Models/PageSample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getSamplesMultiAnalysis"></a>
# **getSamplesMultiAnalysis**
> PageSampleAnalysisCount getSamplesMultiAnalysis(pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageSampleAnalysisCount**](../Models/PageSampleAnalysisCount.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getSuspiciousSamples"></a>
# **getSuspiciousSamples**
> PageSample getSuspiciousSamples(filter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **filter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageSample**](../Models/PageSample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getSuspiciousSamplesInRange"></a>
# **getSuspiciousSamplesInRange**
> PageSample getSuspiciousSamplesInRange(start, end, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **start** | **String**|  | [default to null] |
| **end** | **String**|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageSample**](../Models/PageSample.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

