# CAS JWT Httpclient
使用java http client访问被CAS保护资源

## 前置条件
- CAS 支持JWT鉴权
- 准备好JWT token

## 使用方法
使用登陆过的httpclient访问被cas保护的资源即可
```java
		String casLoginUrl = "https://${CAS_HOST}:${CAS_PORT}/cas/login";
		String token = "your jwt token";
		CasJwtHttpClient httpclient = new CasJwtHttpClient(casLoginUrl, token);
		Assert.assertEquals(true, httpclient.login());
```
