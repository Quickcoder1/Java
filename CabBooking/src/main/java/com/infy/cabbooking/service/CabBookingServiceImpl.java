package com.infy.cabbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.dto.CabDTO;
import com.infy.cabbooking.entity.Booking;
import com.infy.cabbooking.entity.Cab;
import com.infy.cabbooking.exception.CabBookingException;
import com.infy.cabbooking.repository.BookingRepository;
import com.infy.cabbooking.repository.CabRepository;
import com.infy.cabbooking.validator.BookingValidator;

@Service(value="cabBookingService")
@Transactional

public class CabBookingServiceImpl implements CabBookingService {
@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private CabRepository cabRepository;


	@Override
	public List<BookingDTO> getDetailsByBookingType(String bookingType) throws CabBookingException {
		List<BookingDTO> bDTOList= new ArrayList<>();
		List<Booking> bookingList = bookingRepository.findByBookingType(bookingType);
		if(bookingList.isEmpty())
			throw new CabBookingException("Service.NO_DETAILS_FOUND");
		bookingList.forEach(booking->{
			BookingDTO bDTO = new BookingDTO();
			bDTO.setBookingId(booking.getBookingId());
			bDTO.setBookingType(booking.getBookingType());
			bDTO.setCustomerName(booking.getCustomerName());
			bDTO.setPhoneNo(booking.getPhoneNo());
			CabDTO c = new CabDTO();
			c.setCabNo(booking.getCab().getCabNo());
			c.setModelName(booking.getCab().getModelName());
			c.setAvailability(booking.getCab().getAvailability());
			c.setDriverPhoneNo(booking.getCab().getDriverPhoneNo());
			bDTO.setCabDTO(c);
			bDTOList.add(bDTO);
		});
		return bDTOList;
		}

	
	@Override
	public Integer bookCab(BookingDTO bookingDTO) throws CabBookingException {
		BookingValidator bv = new BookingValidator();
		bv.validate(bookingDTO);
		Optional<Cab> optional= cabRepository.findById(bookingDTO.getCabDTO().getCabNo());
		Cab cab = optional.orElseThrow(()-> new CabBookingException("Service.CAB_NOT_FOUND"));
		if(cab.getAvailability().equals("No")) {
			throw new CabBookingException("Service.CAB_NOT_AVAILABLE");
		}
		Booking book = new Booking();
		book.setBookingType(bookingDTO.getBookingType());
		book.setCustomerName(bookingDTO.getCustomerName());
		book.setPhoneNo(bookingDTO.getPhoneNo());
		cab.setAvailability("No");
		book.setCab(cab);
		bookingRepository.save(book);
		
		return book.getBookingId();
		}
	
	

}



