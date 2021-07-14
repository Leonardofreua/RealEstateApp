package com.realestateapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ApartmentRaterTest {
  @Nested
  class RateApartmentTests {
    @Test
    void should_ReturnErrorValue_When_IncorrectApartment() {
      // given
      Apartment apartment = new Apartment(0.0, BigDecimal.valueOf(20000.0));
      int expected = -1;

      // when
      int actualRateApartment = ApartmentRater.rateApartment(apartment);

      // then
      assertEquals(expected, actualRateApartment);
    }

    @ParameterizedTest
    @CsvSource(value = {"72.0, 250000.0, 0", "48.0, 350000.0, 1", "30.0, 600000.0, 2"})
    void should_ReturnCorrectRating_When_CorrectApartment(double area, double price, int rating) {
      // given
      Apartment apartment = new Apartment(area, BigDecimal.valueOf(price));
      int expected = rating;

      // when
      int actualRateApartment = ApartmentRater.rateApartment(apartment);

      // then
      assertEquals(expected, actualRateApartment);
    }
  }

  @Nested
  class CalculateAveraageRatingTests {
    @Test
    void should_ThrowRuntimeException_When_EmptyApartmentList() {
      // given
      List<Apartment> apartments = new ArrayList<>();

      // when
      Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);

      // then
      assertThrows(RuntimeException.class, executable);
    }

    @Test
    void should_CalculateAverageRating_When_CorrectApartmentList() {
      // given
      List<Apartment> apartments = new ArrayList<>();
      apartments.add(new Apartment(72.0, BigDecimal.valueOf(250000.0)));
      apartments.add(new Apartment(48.0, BigDecimal.valueOf(350000.0)));
      apartments.add(new Apartment(30.0, BigDecimal.valueOf(600000.0)));
      double expected = 1.0;

      // when
      double actual = ApartmentRater.calculateAverageRating(apartments);

      // then
      assertEquals(expected, actual);
    }
  }
}