# AuthControllerApi

All URIs are relative to *http://localhost:9090*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**logout**](AuthControllerApi.md#logout) | **POST** /api/auth/logout |  |
| [**register**](AuthControllerApi.md#register) | **POST** /api/auth/admin/register |  |
| [**signIn**](AuthControllerApi.md#signIn) | **POST** /api/auth/login |  |


<a name="logout"></a>
# **logout**
> oas_any_type_not_mapped logout()



### Parameters
This endpoint does not need any parameter.

### Return type

[**oas_any_type_not_mapped**](../Models/AnyType.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="register"></a>
# **register**
> oas_any_type_not_mapped register(Authorization, RegisterRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **Authorization** | **String**|  | [default to null] |
| **RegisterRequest** | [**RegisterRequest**](../Models/RegisterRequest.md)|  | |

### Return type

[**oas_any_type_not_mapped**](../Models/AnyType.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="signIn"></a>
# **signIn**
> oas_any_type_not_mapped signIn(LoginRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **LoginRequest** | [**LoginRequest**](../Models/LoginRequest.md)|  | |

### Return type

[**oas_any_type_not_mapped**](../Models/AnyType.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

