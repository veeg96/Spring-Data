package com.abhishekvermaa10.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.service.PetService;
import com.abhishekvermaa10.util.PetMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Service
@Slf4j
@Profile("dev")
public class PetServiceImpl implements PetService {
	
	private final PetRepository petRepository;
	private final PetMapper petMapper;
	@Value("${pet.not.found}")
	private String petNotFound;

	@Value("${transliteration.api.url}")
	private String url;

	@Autowired
	private RestClient restClient;

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		return petRepository.findById(petId)
				.map(petMapper::petToPetDTO)
				.orElseThrow(() -> new PetNotFoundException(callTranslationApi(String.format(petNotFound, petId))));
	}

	private String callTranslationApi(String message) {
		String code = "hi-t-i0-und";
		try {
			if(restClient != null)
				message = restClient.get()
						.uri(url,code,message)
						.retrieve()
						.body(String.class);
		} catch (Exception e) {
				log.error("Error calling translate API {} ",e.getMessage());
		}
		return message;
	}

	@Override
	public AverageAgeDTO findAverageAgeOfPet() {
		return petRepository.findAverageAgeOfPet().map(petMapper::getAverageAgeDTO).get();
	}

}
