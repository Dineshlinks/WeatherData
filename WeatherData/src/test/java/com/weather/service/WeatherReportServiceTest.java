package com.weather.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.weather.constants.ApplicationConstants;
import com.weather.dto.WeatherRepository;
import com.weather.dto.model.WeatherDetails;
import com.weather.service.exception.WeatherReportServiceException;

/**
 * The Class WeatherReportServiceTest is to write service layer test cases by mocking repository
 *
 */
@SpringBootTest
public class WeatherReportServiceTest {

	/** The weather repository. */
	@Mock
	private WeatherRepository weatherRepository;
	
	/** The weather report service. */
	@InjectMocks
	private WeatherReportService weatherReportService;
	
	/** The formatter. */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	/**
	 * Gets the weather report test.
	 *
	 * @return the weather report test
	 * @throws WeatherReportServiceException the weather report service exception
	 */
	@Test
	public void getWeatherReportTest() throws WeatherReportServiceException {
		
		when(weatherRepository.findByZipCodeAndDateBetween("543652", parseDate("2020-02-02"), parseDate("2020-02-28"))).thenReturn(getMockWeatherDetails());
		List<WeatherDetails> listData = weatherReportService.getWeatherReport("543652", "2020-02-02", "2020-02-28");
		assertEquals(1, listData.size());
	}
	
	
	/**
	 * Gets the weather report exception test.
	 *
	 * @return the weather report exception test
	 * @throws WeatherReportServiceException the weather report service exception
	 */
	@Test
	public void getWeatherReportExceptionTest() throws WeatherReportServiceException {
	
		WeatherReportServiceException ex = Assertions.assertThrows(WeatherReportServiceException.class, () -> {
			 weatherReportService.getWeatherReport("543652", "2020-02-02", "2020-02-28");
		});
		assertEquals(ApplicationConstants.DATA_NOT_FOUND, ex.getMessage());
	}
	
	/**
	 * Parses the date.
	 *
	 * @param date the date
	 * @return the local date
	 * @throws WeatherReportServiceException the weather report service exception
	 */
	private LocalDate parseDate(String date) throws WeatherReportServiceException {
		try {
			
			return LocalDate.parse(date, formatter);
		}catch(DateTimeParseException ex) {
			
			throw new WeatherReportServiceException(1,ApplicationConstants.INVALID_DATE); 
		}
	}
	
	/**
	 * Gets the mock weather details.
	 *
	 * @return the mock weather details
	 */
	private List<WeatherDetails> getMockWeatherDetails(){
		List<WeatherDetails> list = new ArrayList<>();
		list.add(new WeatherDetails());
		return list;
	}
}
