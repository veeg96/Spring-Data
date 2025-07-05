package com.abhishekvermaa10.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerIDDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.repository.OwnerRepository;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.util.OwnerMapper;
import com.abhishekvermaa10.util.OwnerPetInfoMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {
	
	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	private final MessageSource messageSource;
	private final OwnerPetInfoMapper ownerPetInfoMapper;
	@Value("${owner.not.found}")
	private String ownerNotFound;
	private static final String KEY="owner.not.found";

	@Override
	public OwnerIDDTO saveOwner(OwnerDTO ownerDTO) {
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		Integer id = ownerRepository.save(owner).getId();
		return OwnerIDDTO.builder().id(id).build();
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(ownerMapper::ownerToOwnerDTO).map(this::formatDateField)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(KEY), ownerId)));
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(KEY), ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		boolean ownerExists = ownerRepository.existsById(ownerId);
		if (!ownerExists) {
			throw new OwnerNotFoundException(String.format(getMessage(KEY), ownerId));
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll()
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.map(this::formatDateField)
				.toList();
	}
	
	@Override
	public Page<OwnerPetInfoDTO> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(Pageable pageable) {
		Page<Object[]> page = ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
		List<OwnerPetInfoDTO> list = page.stream().map(ls -> ownerPetInfoMapper.convOwnerPetInfoDTO(ls)).toList();
		return new PageImpl<>(list,pageable,page.getTotalElements());
	}
	
	private OwnerDTO formatDateField(OwnerDTO dto){
		PetDTO petDto = dto.getPetDTO();
		if(petDto != null && petDto instanceof DomesticPetDTO domesticPetDTO) {
			domesticPetDTO.setFormattedDate(dateTimeConverter(domesticPetDTO.getBirthDate()));
		}
		return dto;
	}

	private String dateTimeConverter(LocalDate birthDate) {
		return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(birthDate);
	}
	 public String getMessage(String key) {
        return messageSource.getMessage(key,null, LocaleContextHolder.getLocale());
    }
}
