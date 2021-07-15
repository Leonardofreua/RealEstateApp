package com.realestateapp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

class RandomApartmentGeneratorTest {
  private static final double MAX_MULTIPLIER = 4.0;

  @Nested
  class GeneratorDefaultParamsTests {
    private RandomApartmentGenerator generator;

    @BeforeEach
    void setup() {
      this.generator = new RandomApartmentGenerator();
    }

    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    void should_GeberateCorrectApartment_When_DefaultMinAreaMinPrice() {
      // given
      double minArea = 30.0;
      double maxArea = minArea * MAX_MULTIPLIER;
      BigDecimal minPricePerSquareMeter = BigDecimal.valueOf(3000.0);
      BigDecimal maxPricePerSquareMeter = minPricePerSquareMeter.multiply(BigDecimal.valueOf(MAX_MULTIPLIER));

      // when
      var apartment = generator.generate();

      // then
      BigDecimal minApartmentPrice = new BigDecimal(apartment.getArea()).multiply(minPricePerSquareMeter);
      BigDecimal maxApartmentPrice = new BigDecimal(apartment.getArea()).multiply(maxPricePerSquareMeter);
      assertAll(
        () -> assertTrue(apartment.getArea() >= minArea),
        () -> assertTrue(apartment.getArea() <= maxArea),
        () -> assertTrue(apartment.getPrice().compareTo(minApartmentPrice) >= 0),
        () -> assertTrue(apartment.getPrice().compareTo(maxApartmentPrice) <= 0)
      );
    }
  }

  @Nested
  class GeneratorCustomParamsTests {
    private RandomApartmentGenerator generator;
    private double minArea = 5.0;
    private BigDecimal minPricePerSquareMeter = BigDecimal.valueOf(5000.0);

    @BeforeEach
    void setup() {
      this.generator = new RandomApartmentGenerator(minArea, minPricePerSquareMeter);
    }

    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    void should_GeberateCorrectApartment_When_CustomMinAreaMinPrice() {
      // given
      double maxArea = minArea * MAX_MULTIPLIER;
      BigDecimal maxPricePerSquareMeter = minPricePerSquareMeter.multiply(BigDecimal.valueOf(MAX_MULTIPLIER));
  
      // when
      var apartment = generator.generate();
  
      // then
      BigDecimal minApartmentPrice = new BigDecimal(apartment.getArea()).multiply(this.minPricePerSquareMeter);
      BigDecimal maxApartmentPrice = new BigDecimal(apartment.getArea()).multiply(maxPricePerSquareMeter);
      assertAll(
        () -> assertTrue(apartment.getArea() >= this.minArea),
        () -> assertTrue(apartment.getArea() <= maxArea),
        () -> assertTrue(apartment.getPrice().compareTo(minApartmentPrice) >= 0),
        () -> assertTrue(apartment.getPrice().compareTo(maxApartmentPrice) <= 0)
      );
    }
  }
}
