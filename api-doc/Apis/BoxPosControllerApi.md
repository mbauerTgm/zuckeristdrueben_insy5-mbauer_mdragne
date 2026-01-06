# BoxPosControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createBoxPos1**](BoxPosControllerApi.md#createBoxPos1) | **POST** /api/boxpos |  |
| [**deleteBoxPos1**](BoxPosControllerApi.md#deleteBoxPos1) | **DELETE** /api/boxpos/{bId}/{bposId} |  |
| [**getAllBoxPos1**](BoxPosControllerApi.md#getAllBoxPos1) | **GET** /api/boxpos |  |
| [**getAllBoxPos3**](BoxPosControllerApi.md#getAllBoxPos3) | **GET** /api/boxpos/filter |  |
| [**getBoxPosById1**](BoxPosControllerApi.md#getBoxPosById1) | **GET** /api/boxpos/{bId}/{bposId} |  |
| [**updateBoxPos1**](BoxPosControllerApi.md#updateBoxPos1) | **PUT** /api/boxpos/{bId}/{bposId} |  |


<a name="createBoxPos1"></a>
# **createBoxPos1**
> BoxPos createBoxPos1(BoxPos)



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

<a name="deleteBoxPos1"></a>
# **deleteBoxPos1**
> deleteBoxPos1(bId, bposId)



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

<a name="getAllBoxPos1"></a>
# **getAllBoxPos1**
> List getAllBoxPos1(globalFilter)



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

<a name="getAllBoxPos3"></a>
# **getAllBoxPos3**
> PageBoxPos getAllBoxPos3(globalFilter, pageable)



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

<a name="getBoxPosById1"></a>
# **getBoxPosById1**
> BoxPos getBoxPosById1(bId, bposId)



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

<a name="updateBoxPos1"></a>
# **updateBoxPos1**
> BoxPos updateBoxPos1(bId, bposId, BoxPos)



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

