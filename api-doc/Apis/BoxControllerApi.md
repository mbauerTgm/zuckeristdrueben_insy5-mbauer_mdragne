# BoxControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createBox1**](BoxControllerApi.md#createBox1) | **POST** /api/boxes |  |
| [**deleteBox1**](BoxControllerApi.md#deleteBox1) | **DELETE** /api/boxes/{bId} |  |
| [**getAllBoxes1**](BoxControllerApi.md#getAllBoxes1) | **GET** /api/boxes |  |
| [**getAllBoxes3**](BoxControllerApi.md#getAllBoxes3) | **GET** /api/boxes/filter |  |
| [**getBoxById1**](BoxControllerApi.md#getBoxById1) | **GET** /api/boxes/{bId} |  |
| [**updateBox1**](BoxControllerApi.md#updateBox1) | **PUT** /api/boxes/{bId} |  |


<a name="createBox1"></a>
# **createBox1**
> Box createBox1(Box)



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

<a name="deleteBox1"></a>
# **deleteBox1**
> deleteBox1(bId)



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

<a name="getAllBoxes1"></a>
# **getAllBoxes1**
> List getAllBoxes1(globalFilter)



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

<a name="getAllBoxes3"></a>
# **getAllBoxes3**
> PageBox getAllBoxes3(globalFilter, pageable)



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

<a name="getBoxById1"></a>
# **getBoxById1**
> Box getBoxById1(bId)



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

<a name="updateBox1"></a>
# **updateBox1**
> Box updateBox1(bId, Box)



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

