package com.tzavellas.validation.property;

import static com.tzavellas.validation.TestHelper.assertValid;
import static com.tzavellas.validation.TestHelper.assertInvalid;
import static com.tzavellas.validation.property.Validators.calendar;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for CalendarValidatorBuilder
 * 
 * @author Spiros Tzavellas
 */
public class CalendarValidatorBuilderTest extends LinkedValidatorBuilderTest {

	private Calendar FUTURE_DATE;	
	private Calendar PAST_DATE;
	
	@Before
	public void setTimeFixture() {
		// now + 1h
		FUTURE_DATE = Calendar.getInstance();
		FUTURE_DATE.setTimeInMillis(FUTURE_DATE.getTimeInMillis() + 216000000L);
		
		// 1000 sec after 1970
		PAST_DATE = Calendar.getInstance();
		PAST_DATE.setTimeInMillis(1000);
	}
	
	@Override
	protected CalendarValidatorBuilder validator() {
		return calendar("");
	}
		
	@Test
	public void futureAndPast() {
		assertValid(validator().future(), FUTURE_DATE);
		assertInvalid(validator().future(), PAST_DATE);
		
		assertValid(validator().past(), PAST_DATE);
		assertInvalid(validator().past(), FUTURE_DATE);
	}
	
	@Test
	public void beforeAndAfter() {
		assertValid(validator().before(FUTURE_DATE), PAST_DATE);
		assertInvalid(validator().before(PAST_DATE), FUTURE_DATE);
		assertInvalid(validator().before(FUTURE_DATE), FUTURE_DATE);
		
		assertValid(validator().after(PAST_DATE), FUTURE_DATE);
		assertInvalid(validator().after(FUTURE_DATE), PAST_DATE);
		assertInvalid(validator().after(FUTURE_DATE), FUTURE_DATE);
		
		// multiple validators
		assertValid(validator().future().after(PAST_DATE), FUTURE_DATE);
	}
}
