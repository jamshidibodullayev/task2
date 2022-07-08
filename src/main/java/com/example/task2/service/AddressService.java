package com.example.task2.service;


import com.example.task2.entity.Address;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.AddressDto;
import com.example.task2.payload.ApiResponse;
import com.example.task2.repository.AddressRepository;
import com.example.task2.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    private final DistrictRepository districtRepository;

    public ApiResponse addAddress(AddressDto addressDto){
        Address address=new Address(addressDto.getStreet(), addressDto.getHomeNumber(),
                addressDto.getFlatRoom(), addressDto.getDiscription(),
                districtRepository.findById(addressDto.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("District topilmadi")));
    addressRepository.save(address);
    return new ApiResponse(true, "Address", address.getId());
    }


    public ApiResponse editAddress(Integer id, AddressDto addressDto){
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address topilmadi"));
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setFlatRoom(addressDto.getFlatRoom());
        address.setDiscription(addressDto.getDiscription());
        address.setDistrict(districtRepository.findById(addressDto.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("District topilmadi")));
        addressRepository.save(address);
        return new ApiResponse(true, "Tahrirlandi");
    }

    public ApiResponse deleteAddress(Integer id){
        boolean existsById = addressRepository.existsById(id);
        if (existsById){
            try {
                addressRepository.deleteById(id);
                return new ApiResponse(true, "Address o`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "Addressni o`chirish mumkin emas");
            }
        }return new ApiResponse(false, "Siz kiritgan address mavjud emas");
    }

    public ApiResponse getByIdAddress(Integer id){
        Optional<Address> byId = addressRepository.findById(id);
        return byId.map(address -> new ApiResponse(true, "Address: ", generateAddressDto(address))).orElseGet(() -> new ApiResponse(false, "Addres topilmadi"));
    }

    public ApiResponse getAllAddressByDistrict(Integer districtId, int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Address> allByDistrict_id = addressRepository.findAllByDistrict_Id(districtId, pageable);
        List<AddressDto> addressDtoList=allByDistrict_id.stream().map(this::generateAddressDto).collect(Collectors.toList());
        return new ApiResponse(true, "All District: ", addressDtoList);
    }

    public AddressDto generateAddressDto(Address address){
        return new AddressDto(address.getId(), address.getStreet(), address.getHomeNumber(),
                address.getFlatRoom(), address.getDiscription(), address.getDistrict().getId());
    }



}
