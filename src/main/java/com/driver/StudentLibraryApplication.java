package com.driver;

import com.driver.models.Card;
import com.driver.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class StudentLibraryApplication{

	public static void main(String[] args) {
		SpringApplication.run(StudentLibraryApplication.class, args);
	}

}
