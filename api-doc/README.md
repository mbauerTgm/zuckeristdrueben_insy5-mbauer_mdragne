# Documentation for OpenAPI definition

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://localhost:9090*

| Class | Method | HTTP request | Description |
|------------ | ------------- | ------------- | -------------|
| *AnalysisControllerApi* | [**createAnalysis1**](Apis/AnalysisControllerApi.md#createAnalysis1) | **POST** /api/analysis |  |
*AnalysisControllerApi* | [**deleteAnalysis1**](Apis/AnalysisControllerApi.md#deleteAnalysis1) | **DELETE** /api/analysis/{id} |  |
*AnalysisControllerApi* | [**exportAnalysisToCsv1**](Apis/AnalysisControllerApi.md#exportAnalysisToCsv1) | **GET** /api/analysis/export |  |
*AnalysisControllerApi* | [**filterAnalysis1**](Apis/AnalysisControllerApi.md#filterAnalysis1) | **GET** /api/analysis/filter |  |
*AnalysisControllerApi* | [**getAllAnalysis1**](Apis/AnalysisControllerApi.md#getAllAnalysis1) | **GET** /api/analysis |  |
*AnalysisControllerApi* | [**getAnalysisById1**](Apis/AnalysisControllerApi.md#getAnalysisById1) | **GET** /api/analysis/{id} |  |
*AnalysisControllerApi* | [**updateAnalysis1**](Apis/AnalysisControllerApi.md#updateAnalysis1) | **PUT** /api/analysis/{id} |  |
| *AuthControllerApi* | [**logout**](Apis/AuthControllerApi.md#logout) | **POST** /api/auth/logout |  |
*AuthControllerApi* | [**register**](Apis/AuthControllerApi.md#register) | **POST** /api/auth/admin/register |  |
*AuthControllerApi* | [**signIn**](Apis/AuthControllerApi.md#signIn) | **POST** /api/auth/login |  |
| *BoxControllerApi* | [**createBox1**](Apis/BoxControllerApi.md#createBox1) | **POST** /api/boxes |  |
*BoxControllerApi* | [**deleteBox1**](Apis/BoxControllerApi.md#deleteBox1) | **DELETE** /api/boxes/{bId} |  |
*BoxControllerApi* | [**getAllBoxes1**](Apis/BoxControllerApi.md#getAllBoxes1) | **GET** /api/boxes |  |
*BoxControllerApi* | [**getAllBoxes3**](Apis/BoxControllerApi.md#getAllBoxes3) | **GET** /api/boxes/filter |  |
*BoxControllerApi* | [**getBoxById1**](Apis/BoxControllerApi.md#getBoxById1) | **GET** /api/boxes/{bId} |  |
*BoxControllerApi* | [**updateBox1**](Apis/BoxControllerApi.md#updateBox1) | **PUT** /api/boxes/{bId} |  |
| *BoxPosControllerApi* | [**createBoxPos1**](Apis/BoxPosControllerApi.md#createBoxPos1) | **POST** /api/boxpos |  |
*BoxPosControllerApi* | [**deleteBoxPos1**](Apis/BoxPosControllerApi.md#deleteBoxPos1) | **DELETE** /api/boxpos/{bId}/{bposId} |  |
*BoxPosControllerApi* | [**getAllBoxPos1**](Apis/BoxPosControllerApi.md#getAllBoxPos1) | **GET** /api/boxpos |  |
*BoxPosControllerApi* | [**getAllBoxPos3**](Apis/BoxPosControllerApi.md#getAllBoxPos3) | **GET** /api/boxpos/filter |  |
*BoxPosControllerApi* | [**getBoxPosById1**](Apis/BoxPosControllerApi.md#getBoxPosById1) | **GET** /api/boxpos/{bId}/{bposId} |  |
*BoxPosControllerApi* | [**updateBoxPos1**](Apis/BoxPosControllerApi.md#updateBoxPos1) | **PUT** /api/boxpos/{bId}/{bposId} |  |
| *LogControllerApi* | [**getAllLogs2**](Apis/LogControllerApi.md#getAllLogs2) | **GET** /api/logs |  |
*LogControllerApi* | [**getAllLogs3**](Apis/LogControllerApi.md#getAllLogs3) | **GET** /api/logs/filter |  |
*LogControllerApi* | [**getLogById1**](Apis/LogControllerApi.md#getLogById1) | **GET** /api/logs/{logId} |  |
| *ReportRestControllerApi* | [**getAnalysisNoBoxPos**](Apis/ReportRestControllerApi.md#getAnalysisNoBoxPos) | **GET** /api/reports/analysis-no-boxpos |  |
*ReportRestControllerApi* | [**getAnalysisZeroValues**](Apis/ReportRestControllerApi.md#getAnalysisZeroValues) | **GET** /api/reports/analysis-zero-values |  |
*ReportRestControllerApi* | [**getBoxPosEmpty**](Apis/ReportRestControllerApi.md#getBoxPosEmpty) | **GET** /api/reports/boxpos-empty |  |
*ReportRestControllerApi* | [**getBoxPosNoAnalysis**](Apis/ReportRestControllerApi.md#getBoxPosNoAnalysis) | **GET** /api/reports/boxpos-no-analysis |  |
*ReportRestControllerApi* | [**getSamplesBadEan**](Apis/ReportRestControllerApi.md#getSamplesBadEan) | **GET** /api/reports/samples-bad-ean |  |
*ReportRestControllerApi* | [**getSamplesMultiAnalysis**](Apis/ReportRestControllerApi.md#getSamplesMultiAnalysis) | **GET** /api/reports/samples-multi-analysis |  |
*ReportRestControllerApi* | [**getSuspiciousSamples**](Apis/ReportRestControllerApi.md#getSuspiciousSamples) | **GET** /api/reports/samples-suspicious |  |
*ReportRestControllerApi* | [**getSuspiciousSamplesInRange**](Apis/ReportRestControllerApi.md#getSuspiciousSamplesInRange) | **GET** /api/reports/samples-suspicious-timerange |  |
| *SampleControllerApi* | [**createSample1**](Apis/SampleControllerApi.md#createSample1) | **POST** /api/samples |  |
*SampleControllerApi* | [**deleteSample1**](Apis/SampleControllerApi.md#deleteSample1) | **DELETE** /api/samples/{sId}/{sStamp} |  |
*SampleControllerApi* | [**exportSamplesToCsv1**](Apis/SampleControllerApi.md#exportSamplesToCsv1) | **GET** /api/samples/export |  |
*SampleControllerApi* | [**filterSamples1**](Apis/SampleControllerApi.md#filterSamples1) | **GET** /api/samples/filter |  |
*SampleControllerApi* | [**getAllSamples1**](Apis/SampleControllerApi.md#getAllSamples1) | **GET** /api/samples |  |
*SampleControllerApi* | [**getSampleById1**](Apis/SampleControllerApi.md#getSampleById1) | **GET** /api/samples/{sId}/{sStamp} |  |
*SampleControllerApi* | [**updateSample1**](Apis/SampleControllerApi.md#updateSample1) | **PUT** /api/samples/{sId}/{sStamp} |  |
| *ThresholdControllerApi* | [**createThreshold**](Apis/ThresholdControllerApi.md#createThreshold) | **POST** /api/thresholds |  |
*ThresholdControllerApi* | [**getAllThresholds**](Apis/ThresholdControllerApi.md#getAllThresholds) | **GET** /api/thresholds |  |
*ThresholdControllerApi* | [**getAllThresholds3**](Apis/ThresholdControllerApi.md#getAllThresholds3) | **GET** /api/thresholds/filter |  |
*ThresholdControllerApi* | [**getThresholdById**](Apis/ThresholdControllerApi.md#getThresholdById) | **GET** /api/thresholds/{thId} |  |
*ThresholdControllerApi* | [**updateThreshold**](Apis/ThresholdControllerApi.md#updateThreshold) | **PUT** /api/thresholds/{thId} |  |
| *ZuckerIstDruebenRestControllerApi* | [**createAnalysis**](Apis/ZuckerIstDruebenRestControllerApi.md#createAnalysis) | **POST** /legacy/api/analysis |  |
*ZuckerIstDruebenRestControllerApi* | [**createBox**](Apis/ZuckerIstDruebenRestControllerApi.md#createBox) | **POST** /legacy/api/boxes |  |
*ZuckerIstDruebenRestControllerApi* | [**createBoxPos**](Apis/ZuckerIstDruebenRestControllerApi.md#createBoxPos) | **POST** /legacy/api/boxpos |  |
*ZuckerIstDruebenRestControllerApi* | [**createSample**](Apis/ZuckerIstDruebenRestControllerApi.md#createSample) | **POST** /legacy/api/samples |  |
*ZuckerIstDruebenRestControllerApi* | [**deleteAnalysis**](Apis/ZuckerIstDruebenRestControllerApi.md#deleteAnalysis) | **DELETE** /legacy/api/analysis/{id} |  |
*ZuckerIstDruebenRestControllerApi* | [**deleteBox**](Apis/ZuckerIstDruebenRestControllerApi.md#deleteBox) | **DELETE** /legacy/api/boxes/{bId} |  |
*ZuckerIstDruebenRestControllerApi* | [**deleteBoxPos**](Apis/ZuckerIstDruebenRestControllerApi.md#deleteBoxPos) | **DELETE** /legacy/api/boxpos/{bId}/{bposId} |  |
*ZuckerIstDruebenRestControllerApi* | [**deleteSample**](Apis/ZuckerIstDruebenRestControllerApi.md#deleteSample) | **DELETE** /legacy/api/samples/{sId}/{sStamp} |  |
*ZuckerIstDruebenRestControllerApi* | [**exportAnalysisToCsv**](Apis/ZuckerIstDruebenRestControllerApi.md#exportAnalysisToCsv) | **GET** /legacy/api/analysis/export |  |
*ZuckerIstDruebenRestControllerApi* | [**exportSamplesToCsv**](Apis/ZuckerIstDruebenRestControllerApi.md#exportSamplesToCsv) | **GET** /legacy/api/samples/export |  |
*ZuckerIstDruebenRestControllerApi* | [**filterAnalysis**](Apis/ZuckerIstDruebenRestControllerApi.md#filterAnalysis) | **GET** /legacy/api/analysis/filter |  |
*ZuckerIstDruebenRestControllerApi* | [**filterSamples**](Apis/ZuckerIstDruebenRestControllerApi.md#filterSamples) | **GET** /legacy/api/samples/filter |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllAnalysis**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllAnalysis) | **GET** /legacy/api/analysis |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllBoxPos**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllBoxPos) | **GET** /legacy/api/boxpos |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllBoxPos2**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllBoxPos2) | **GET** /legacy/api/boxpos/filter |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllBoxes**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllBoxes) | **GET** /legacy/api/boxes |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllBoxes2**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllBoxes2) | **GET** /legacy/api/boxes/filter |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllLogs**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllLogs) | **GET** /legacy/api/logs |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllLogs1**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllLogs1) | **GET** /legacy/api/logs/filter |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllSamples**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllSamples) | **GET** /legacy/api/samples |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllThresholds1**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllThresholds1) | **GET** /legacy/api/thresholds |  |
*ZuckerIstDruebenRestControllerApi* | [**getAllThresholds2**](Apis/ZuckerIstDruebenRestControllerApi.md#getAllThresholds2) | **GET** /legacy/api/thresholds/filter |  |
*ZuckerIstDruebenRestControllerApi* | [**getAnalysisById**](Apis/ZuckerIstDruebenRestControllerApi.md#getAnalysisById) | **GET** /legacy/api/analysis/{id} |  |
*ZuckerIstDruebenRestControllerApi* | [**getBoxById**](Apis/ZuckerIstDruebenRestControllerApi.md#getBoxById) | **GET** /legacy/api/boxes/{bId} |  |
*ZuckerIstDruebenRestControllerApi* | [**getBoxPosById**](Apis/ZuckerIstDruebenRestControllerApi.md#getBoxPosById) | **GET** /legacy/api/boxpos/{bId}/{bposId} |  |
*ZuckerIstDruebenRestControllerApi* | [**getLogById**](Apis/ZuckerIstDruebenRestControllerApi.md#getLogById) | **GET** /legacy/api/logs/{logId} |  |
*ZuckerIstDruebenRestControllerApi* | [**getSampleById**](Apis/ZuckerIstDruebenRestControllerApi.md#getSampleById) | **GET** /legacy/api/samples/{sId}/{sStamp} |  |
*ZuckerIstDruebenRestControllerApi* | [**getThresholdById1**](Apis/ZuckerIstDruebenRestControllerApi.md#getThresholdById1) | **GET** /legacy/api/thresholds/{thId} |  |
*ZuckerIstDruebenRestControllerApi* | [**updateAnalysis**](Apis/ZuckerIstDruebenRestControllerApi.md#updateAnalysis) | **PUT** /legacy/api/analysis/{id} |  |
*ZuckerIstDruebenRestControllerApi* | [**updateBox**](Apis/ZuckerIstDruebenRestControllerApi.md#updateBox) | **PUT** /legacy/api/boxes/{bId} |  |
*ZuckerIstDruebenRestControllerApi* | [**updateBoxPos**](Apis/ZuckerIstDruebenRestControllerApi.md#updateBoxPos) | **PUT** /legacy/api/boxpos/{bId}/{bposId} |  |
*ZuckerIstDruebenRestControllerApi* | [**updateSample**](Apis/ZuckerIstDruebenRestControllerApi.md#updateSample) | **PUT** /legacy/api/samples/{sId}/{sStamp} |  |


<a name="documentation-for-models"></a>
## Documentation for Models

 - [Analysis](./Models/Analysis.md)
 - [AnalysisFilterDto](./Models/AnalysisFilterDto.md)
 - [AnalysisGlobalFilterDto](./Models/AnalysisGlobalFilterDto.md)
 - [Box](./Models/Box.md)
 - [BoxPos](./Models/BoxPos.md)
 - [Log](./Models/Log.md)
 - [LoginRequest](./Models/LoginRequest.md)
 - [PageAnalysis](./Models/PageAnalysis.md)
 - [PageBox](./Models/PageBox.md)
 - [PageBoxPos](./Models/PageBoxPos.md)
 - [PageLog](./Models/PageLog.md)
 - [PageSample](./Models/PageSample.md)
 - [PageSampleAnalysisCount](./Models/PageSampleAnalysisCount.md)
 - [PageThreshold](./Models/PageThreshold.md)
 - [Pageable](./Models/Pageable.md)
 - [Pageablenull](./Models/Pageablenull.md)
 - [RangeLocalDateTime](./Models/RangeLocalDateTime.md)
 - [RangeLong](./Models/RangeLong.md)
 - [RangeString](./Models/RangeString.md)
 - [RegisterRequest](./Models/RegisterRequest.md)
 - [Sample](./Models/Sample.md)
 - [SampleAnalysisCount](./Models/SampleAnalysisCount.md)
 - [Sortnull](./Models/Sortnull.md)
 - [Threshold](./Models/Threshold.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
