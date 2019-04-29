package com.journaldev.spring.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.journaldev.spring.model.Person;

// 我们基于URI和HTTP方法调用RestTemplate methods，并在需要时传递适当的请求对象。

@Service
public class PersonClientImpl implements PersonClient {

	// 最后一步是创建将使用RestTemplate bean的客户端类。
	@Autowired
	RestTemplate restTemplate;

	final String ROOT_URI = "http://localhost:8080/springData/person";

	public List<Person> getAllPerson() {
		ResponseEntity<Person[]> response = restTemplate.getForEntity(ROOT_URI, Person[].class);
		return Arrays.asList(response.getBody());
	}

	public Person getById(Long id) {
		ResponseEntity<Person> response = restTemplate.getForEntity(ROOT_URI + "/"+id, Person.class);
		return response.getBody();
	}

	public HttpStatus addPerson(Person person) {
		ResponseEntity<HttpStatus> response = restTemplate.postForEntity(ROOT_URI, person, HttpStatus.class);
		return response.getBody();
	}

	public void updatePerson(Person person) {
		restTemplate.put(ROOT_URI, person);
	}

	public void deletePerson(Long id) {
		restTemplate.delete(ROOT_URI + id);
	}
}