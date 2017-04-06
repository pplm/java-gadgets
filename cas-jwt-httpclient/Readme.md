# CAS JWT Httpclient
����JWT(Json Web Token)��ʹ��java http client���ʱ�[CAS](https://apereo.github.io/cas/5.0.x/index.html)������Դ

## ǰ������
- CAS ֧��JWT��Ȩ
- ׼����JWT token

## ʹ�÷���
ʹ�õ�½����httpclient���ʱ�cas��������Դ����
```java
		String casLoginUrl = "https://${CAS_HOST}:${CAS_PORT}/cas/login";
		String token = "your jwt token";
		CasJwtHttpClient httpclient = new CasJwtHttpClient(casLoginUrl, token);
		Assert.assertEquals(true, httpclient.login());
		httpclient.close();
```
����
```java
		String casLoginUrl = "https://${CAS_HOST}:${CAS_PORT}/cas/login";
		String token = "your jwt token";
		CasJwtHttpClient httpclient = new CasJwtHttpClient();
		Assert.assertEquals(true, httpclient.login(casLoginUrl, token));
		httpclient.close();
```

# Reference
- [CAS](https://apereo.github.io/cas/5.0.x/index.html)��ҵ�������¼
- [Apache Http Client](http://hc.apache.org/)
