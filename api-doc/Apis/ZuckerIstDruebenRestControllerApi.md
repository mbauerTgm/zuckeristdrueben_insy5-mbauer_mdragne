# ZuckerIstDruebenRestControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createAnalysis**](ZuckerIstDruebenRestControllerApi.md#createAnalysis) | **POST** /legacy/api/analysis |  |
| [**createBox**](ZuckerIstDruebenRestControllerApi.md#createBox) | **POST** /legacy/api/boxes |  |
| [**createBoxPos**](ZuckerIstDruebenRestControllerApi.md#createBoxPos) | **POST** /legacy/api/boxpos |  |
| [**createSample**](ZuckerIstDruebenRestControllerApi.md#createSample) | **POST** /legacy/api/samples |  |
| [**deleteAnalysis**](ZuckerIstDruebenRestControllerApi.md#deleteAnalysis) | **DELETE** /legacy/api/analysis/{id} |  |
| [**deleteBox**](ZuckerIstDruebenRestControllerApi.md#deleteBox) | **DELETE** /legacy/api/boxes/{bId} |  |
| [**deleteBoxPos**](ZuckerIstDruebenRestControllerApi.md#deleteBoxPos) | **DELETE** /legacy/api/boxpos/{bId}/{bposId} |  |
| [**deleteSample**](ZuckerIstDruebenRestControllerApi.md#deleteSample) | **DELETE** /legacy/api/samples/{sId}/{sStamp} |  |
| [**exportAnalysisToCsv**](ZuckerIstDruebenRestControllerApi.md#exportAnalysisToCsv) | **GET** /legacy/api/analysis/export |  |
| [**exportSamplesToCsv**](ZuckerIstDruebenRestControllerApi.md#exportSamplesToCsv) | **GET** /legacy/api/samples/export |  |
| [**filterAnalysis**](ZuckerIstDruebenRestControllerApi.md#filterAnalysis) | **GET** /legacy/api/analysis/filter |  |
| [**filterSamples**](ZuckerIstDruebenRestControllerApi.md#filterSamples) | **GET** /legacy/api/samples/filter |  |
| [**getAllAnalysis**](ZuckerIstDruebenRestControllerApi.md#getAllAnalysis) | **GET** /legacy/api/analysis |  |
| [**getAllBoxPos**](ZuckerIstDruebenRestControllerApi.md#getAllBoxPos) | **GET** /legacy/api/boxpos |  |
| [**getAllBoxPos2**](ZuckerIstDruebenRestControllerApi.md#getAllBoxPos2) | **GET** /legacy/api/boxpos/filter |  |
| [**getAllBoxes**](ZuckerIstDruebenRestControllerApi.md#getAllBoxes) | **GET** /legacy/api/boxes |  |
| [**getAllBoxes2**](ZuckerIstDruebenRestControllerApi.md#getAllBoxes2) | **GET** /legacy/api/boxes/filter |  |
| [**getAllLogs**](ZuckerIstDruebenRestControllerApi.md#getAllLogs) | **GET** /legacy/api/logs |  |
| [**getAllLogs1**](ZuckerIstDruebenRestControllerApi.md#getAllLogs1) | **GET** /legacy/api/logs/filter |  |
| [**getAllSamples**](ZuckerIstDruebenRestControllerApi.md#getAllSamples) | **GET** /legacy/api/samples |  |
| [**getAllThresholds1**](ZuckerIstDruebenRestControllerApi.md#getAllThresholds1) | **GET** /legacy/api/thresholds |  |
| [**getAllThresholds2**](ZuckerIstDruebenRestControllerApi.md#getAllThresholds2) | **GET** /legacy/api/thresholds/filter |  |
| [**getAnalysisById**](ZuckerIstDruebenRestControllerApi.md#getAnalysisById) | **GET** /legacy/api/analysis/{id} |  |
| [**getBoxById**](ZuckerIstDruebenRestControllerApi.md#getBoxById) | **GET** /legacy/api/boxes/{bId} |  |
| [**getBoxPosById**](ZuckerIstDruebenRestControllerApi.md#getBoxPosById) | **GET** /legacy/api/boxpos/{bId}/{bposId} |  |
| [**getLogById**](ZuckerIstDruebenRestControllerApi.md#getLogById) | **GET** /legacy/api/logs/{logId} |  |
| [**getSampleById**](ZuckerIstDruebenRestControllerApi.md#getSampleById) | **GET** /legacy/api/samples/{sId}/{sStamp} |  |
| [**getThresholdById1**](ZuckerIstDruebenRestControllerApi.md#getThresholdById1) | **GET** /legacy/api/thresholds/{thId} |  |
| [**updateAnalysis**](ZuckerIstDruebenRestControllerApi.md#updateAnalysis) | **PUT** /legacy/api/analysis/{id} |  |
| [**updateBox**](ZuckerIstDruebenRestControllerApi.md#updateBox) | **PUT** /legacy/api/boxes/{bId} |  |
| [**updateBoxPos**](ZuckerIstDruebenRestControllerApi.md#updateBoxPos) | **PUT** /legacy/api/boxpos/{bId}/{bposId} |  |
| [**updateSample**](ZuckerIstDruebenRestControllerApi.md#updateSample) | **PUT** /legacy/api/samples/{sId}/{sStamp} |  |


<a name="createAnalysis"></a>
# **createAnalysis**
> Analysis createAnalysis(Analysis)



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

<a name="createBox"></a>
# **createBox**
> Box createBox(Box)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **Box** | [**Box**](../Models/Box.md)|  | |

### Return type

[**Box**](../Models/Box.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="createBoxPos"></a>
# **createBoxPos**
> BoxPos createBoxPos(BoxPos)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **BoxPos** | [**BoxPos**](../Models/BoxPos.md)|  | |

### Return type

[**BoxPos**](../Models/BoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="createSample"></a>
# **createSample**
> Sample createSample(Sample)



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

<a name="deleteAnalysis"></a>
# **deleteAnalysis**
> deleteAnalysis(id)



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

<a name="deleteBox"></a>
# **deleteBox**
> deleteBox(bId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bId** | **String**|  | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="deleteBoxPos"></a>
# **deleteBoxPos**
> deleteBoxPos(bId, bposId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bId** | **String**|  | [default to null] |
| **bposId** | **Integer**|  | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="deleteSample"></a>
# **deleteSample**
> deleteSample(sId, sStamp)



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

<a name="exportAnalysisToCsv"></a>
# **exportAnalysisToCsv**
> exportAnalysisToCsv(searchDto, globalFilter)



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

<a name="exportSamplesToCsv"></a>
# **exportSamplesToCsv**
> exportSamplesToCsv(globalFilter)



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

<a name="filterAnalysis"></a>
# **filterAnalysis**
> PageAnalysis filterAnalysis(filterDto, globalFilter, pageable)



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

<a name="filterSamples"></a>
# **filterSamples**
> PageSample filterSamples(globalFilter, pageable)



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

<a name="getAllAnalysis"></a>
# **getAllAnalysis**
> List getAllAnalysis(globalFilter)



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

<a name="getAllBoxPos"></a>
# **getAllBoxPos**
> List getAllBoxPos(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

[**List**](../Models/BoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllBoxPos2"></a>
# **getAllBoxPos2**
> PageBoxPos getAllBoxPos2(globalFilter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageBoxPos**](../Models/PageBoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllBoxes"></a>
# **getAllBoxes**
> List getAllBoxes(globalFilter)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |

### Return type

[**List**](../Models/Box.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllBoxes2"></a>
# **getAllBoxes2**
> PageBox getAllBoxes2(globalFilter, pageable)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **globalFilter** | [**AnalysisGlobalFilterDto**](../Models/.md)|  | [default to null] |
| **pageable** | [**Pageable**](../Models/.md)|  | [default to null] |

### Return type

[**PageBox**](../Models/PageBox.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getAllLogs"></a>
# **getAllLogs**
> List getAllLogs(globalFilter)



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

<a name="getAllLogs1"></a>
# **getAllLogs1**
> PageLog getAllLogs1(globalFilter, pageable)



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

<a name="getAllSamples"></a>
# **getAllSamples**
> List getAllSamples(globalFilter)



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

<a name="getAllThresholds1"></a>
# **getAllThresholds1**
> List getAllThresholds1(globalFilter)



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

<a name="getAllThresholds2"></a>
# **getAllThresholds2**
> PageThreshold getAllThresholds2(globalFilter, pageable)



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

<a name="getAnalysisById"></a>
# **getAnalysisById**
> Analysis getAnalysisById(id)



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

<a name="getBoxById"></a>
# **getBoxById**
> Box getBoxById(bId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bId** | **String**|  | [default to null] |

### Return type

[**Box**](../Models/Box.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getBoxPosById"></a>
# **getBoxPosById**
> BoxPos getBoxPosById(bId, bposId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bId** | **String**|  | [default to null] |
| **bposId** | **Integer**|  | [default to null] |

### Return type

[**BoxPos**](../Models/BoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getLogById"></a>
# **getLogById**
> Log getLogById(logId)



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

<a name="getSampleById"></a>
# **getSampleById**
> Sample getSampleById(sId, sStamp)



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

<a name="getThresholdById1"></a>
# **getThresholdById1**
> Threshold getThresholdById1(thId)



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

<a name="updateAnalysis"></a>
# **updateAnalysis**
> Analysis updateAnalysis(id, Analysis)



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

<a name="updateBox"></a>
# **updateBox**
> Box updateBox(bId, Box)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bId** | **String**|  | [default to null] |
| **Box** | [**Box**](../Models/Box.md)|  | |

### Return type

[**Box**](../Models/Box.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="updateBoxPos"></a>
# **updateBoxPos**
> BoxPos updateBoxPos(bId, bposId, BoxPos)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bId** | **String**|  | [default to null] |
| **bposId** | **Integer**|  | [default to null] |
| **BoxPos** | [**BoxPos**](../Models/BoxPos.md)|  | |

### Return type

[**BoxPos**](../Models/BoxPos.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="updateSample"></a>
# **updateSample**
> Sample updateSample(sId, sStamp, Sample)



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

