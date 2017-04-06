package org.pplm.cas.jwt.httpclient;

import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * 
 * @author OracleGao
 *
 */
@SuppressWarnings("deprecation")
public class CasJwtHttpClient implements HttpClient, Closeable {
	
	private String casLoginUrl;
	private String token;
	
	private String tokenParamName = "token";
	private String serviceParamName = "service";
	private String urlEncoding = "utf-8";
	
	private TrustStrategy trustStrategy;
	private HostnameVerifier hostnameVerifier;
	
	private CloseableHttpClient httpClient;
	private boolean logined;
	
	public CasJwtHttpClient() {
		super();
	}

	public CasJwtHttpClient(String casLoginUrl, String token) {
		super();
		this.casLoginUrl = casLoginUrl;
		this.token = token;
	}
	
	public boolean login() throws ClientProtocolException, IOException {
		initHttpClient();
		try {
			String loginUrl = casLoginUrl + "?" + serviceParamName + "=" + new URLCodec(urlEncoding).encode(casLoginUrl) + "&" + tokenParamName + "=" + token;
			HttpGet httpGet = new HttpGet(loginUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				logined = true;
			} else {
				logined = false;
			}
		} catch (EncoderException e) {
			throw new RuntimeException("should never throw this.");
		}
		return logined;
	}
	
	private void initHttpClient() {
		if (trustStrategy == null) {
			trustStrategy = (chain, authType) -> true;
		}
		if (hostnameVerifier == null) {
			hostnameVerifier = (hostname, session) -> true;
		}
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStrategy).build();
			LayeredConnectionSocketFactory layeredConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			httpClient = HttpClients.custom().setSSLSocketFactory(layeredConnectionSocketFactory).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void close() throws IOException {
		if (httpClient != null) {
			logined = false;
			httpClient.close();
		}
	}

	@Override
	public HttpParams getParams() {
		return httpClient.getParams();
	}

	@Override
	public ClientConnectionManager getConnectionManager() {
		return httpClient.getConnectionManager();
	}

	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		return httpClient.execute(request);
	}

	@Override
	public HttpResponse execute(HttpUriRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		return httpClient.execute(request, context);
	}

	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException {
		return httpClient.execute(target, request);
	}

	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		return httpClient.execute(target, request, context);
	}

	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException {
		return httpClient.execute(request, responseHandler);
	}

	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
			throws IOException, ClientProtocolException {
		return httpClient.execute(request, responseHandler, context);
	}

	@Override
	public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException {
		return httpClient.execute(target, request, responseHandler);
	}

	@Override
	public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler,
			HttpContext context) throws IOException, ClientProtocolException {
		return httpClient.execute(target, request, responseHandler, context);
	}

	public String getCasLoginUrl() {
		return casLoginUrl;
	}

	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenParamName() {
		return tokenParamName;
	}

	public void setTokenParamName(String tokenParamName) {
		this.tokenParamName = tokenParamName;
	}

	public String getServiceParamName() {
		return serviceParamName;
	}

	public void setServiceParamName(String serviceParamName) {
		this.serviceParamName = serviceParamName;
	}

	public TrustStrategy getTrustStrategy() {
		return trustStrategy;
	}

	public void setTrustStrategy(TrustStrategy trustStrategy) {
		this.trustStrategy = trustStrategy;
	}

	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}

	public boolean isLogined() {
		return logined;
	}

}
