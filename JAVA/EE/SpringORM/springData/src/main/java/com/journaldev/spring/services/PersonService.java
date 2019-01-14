package com.journaldev.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.repository.PersonRepository;

// Spring Service Class

// 创建服务类并定义我们必须使用数据库表的方法。

// @Transactional注释表示该方法将在事务中执行。 Spring将负责事务管理。
//Spring will take care of transaction management.

@Service
public class PersonService {

	@Autowired
	PersonRepository<Person> personRepository;

	@Transactional
	public List<Person> getAllPersons() {
		return (List<Person>) personRepository.findAll();
	}

	@Transactional
	public List<Person> findByName(String name) {
		return personRepository.findByFirstName(name);
	}

	@Transactional
	public Person getById(Long id) {
		return personRepository.findOne(id);
	}

	@Transactional
	public void deletePerson(Long personId) {
		personRepository.delete(personId);
	}

	@Transactional
	public boolean addPerson(Person person) {
		return personRepository.save(person) != null;
	}

	@Transactional
	public boolean updatePerson(Person person) {
		return personRepository.save(person) != null;
	}
}