package cl.waypoint.ms.mailer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.waypoint.ms.mailer.dto.Message;
import cl.waypoint.ms.mailer.rest.MailerEndpoint;

public class MailerEndpointTest {

	private static final String URL = "http://localhost:8080/send";

	@Test
	public final void basicSend() {
		MailerEndpoint endpoint = new MailerEndpoint();
		Message msg = getMessage();
		String resp = endpoint.send(msg);
		assertNotNull(resp);
	}

	@Test
	public final void emptySend() {
		MailerEndpoint endpoint = new MailerEndpoint();
		try {
			endpoint.send(null);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Mensaje vacío");
		}
	}

	private Message getMessage() {
		Message msg = new Message();
		msg.setFrom("gvasquez@waypoint.cl");
		String[] to = { "Hello@waypoint.cl", "gvasquez@waypoint.cl" };
		msg.setTo(to);
		msg.setSubject(this.getClass().getName());
		msg.setBody("cuerpo");
		return msg;
	}

	//@Test
	public void testResponse() throws JsonProcessingException {
		TestRestTemplate restTemplate = new TestRestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		Object input = null;
		String body = mapper.writeValueAsString(input);
		System.out.println("Body:" + body);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		ResponseEntity resp = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
		System.out.println("Response:" + resp);
		if (resp.hasBody()) {
			System.out.println(resp.getBody());
		}
	}
}