package org.pplm.cas.jwt.httpclient;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author OracleGao
 *
 */
public class CasJwtHttpClientTest {
	
	@Test
	public void loginTest() throws IOException {
		String casLoginUrl = "https://192.168.130.115:8443/cas/login";
		String token = "eyJjdHkiOiJKV1QiLCJlbmMiOiJBMTkyQ0JDLUhTMzg0IiwiYWxnIjoiZGlyIn0..oSOKh_a8PRoDOn9YZ4iKFg.7lnPJuJZ0io4g-7U5M2bSkTQb8qh5VVLNGMWsFVkycH-vd6ZnhCR_eu4caLkY1FHYfwVNh8ftcpLgzThEJ3WJLWCHlvgRv196MdsX6KPFy9DYngN65bPrjnOHS0unRVXKG0ipfxdDjfoZnGfzVdpj1tF5nHc96NA0l2kbqrKmBCPJo9JrVImp6czhO_j7k46cz7YO7lQuZqSiLIJ71F_i4wuriDcESifdRxax-56S8cv7i8pIAYkoS9w8-HQeYosxRCQUgrxHz-nDH4aTY10YQ.BVTWjnbKom2XPdAphUVqn47tvRUxEHSU";
		CasJwtHttpClient httpclient = null;
		try {
			httpclient = new CasJwtHttpClient(casLoginUrl, token);
			Assert.assertEquals(true, httpclient.login());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
	}
	
}
