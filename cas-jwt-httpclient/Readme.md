# CAS JWT Httpclient
ʹ��java http client���ʱ�CAS������Դ

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
```
