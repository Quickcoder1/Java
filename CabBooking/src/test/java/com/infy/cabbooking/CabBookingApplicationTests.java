package com.infy.cabbooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.dto.CabDTO;
import com.infy.cabbooking.entity.Booking;
import com.infy.cabbooking.entity.Cab;
import com.infy.cabbooking.exception.CabBookingException;
import com.infy.cabbooking.repository.BookingRepository;
import com.infy.cabbooking.repository.CabRepository;
import com.infy.cabbooking.service.CabBookingService;
import com.infy.cabbooking.service.CabBookingServiceImpl;

@SpringBootTest
public class CabBookingApplicationTests {

	
	@Mock
	private CabRepository cabRepository;
	

	@Mock
	private BookingRepository bookingRepository;
	
	@InjectMocks
	private CabBookingService cabBookingService = new CabBookingServiceImpl();
	
	
	@Test
	public void bookCabInvalidCabNoTest() throws Exception{
		BookingDTO bdto = new BookingDTO();
		bdto.setCustomerName("Robert");
		bdto.setBookingId(1001);
		bdto.setPhoneNo(9867542341L);
		bdto.setBookingType("Personal");
		CabDTO cdto = new CabDTO();
		cdto.setCabNo(451678);
		cdto.setDriverPhoneNo(9947835654L);
		cdto.setModelName("Toyota");
		cdto.setAvailability("Yes");
		bdto.setCabDTO(cdto);
	    
		Integer id = 1001;
		
		Mockito.when(cabRepository.findById(id)).thenReturn(Optional.empty());
		CabBookingException cbe = Assertions.assertThrows(CabBookingException.class,()->cabBookingService.bookCab(bdto));
		Assertions.assertEquals("Service.CAB_NOT_FOUND", cbe.getMessage());
	}
	
	@Test
	public void getDetailsByBookingTypeNoDetailsFound() throws Exception {
	
		List<Booking> bookings = new ArrayList<Booking>();
	    String name = "Share";
		Mockito.when(bookingRepository.findByBookingType(name)).thenReturn(bookings);
		CabBookingException cbe = Assertions.assertThrows(CabBookingException.class,()->cabBookingService.getDetailsByBookingType(name));
		Assertions.assertEquals("Service.NO_DETAILS_FOUND", cbe.getMessage());

	}
	

}
