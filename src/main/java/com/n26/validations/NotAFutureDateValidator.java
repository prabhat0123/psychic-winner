package com.n26.validations;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.lang.Nullable;

public class NotAFutureDateValidator implements ConstraintValidator<NotAFutureDate, ZonedDateTime> {

	public static Instant instant = Instant.now();
	public static ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

	@Override
	public boolean isValid(@Nullable final ZonedDateTime value, final ConstraintValidatorContext context) {
		return value == null || value.isBefore(now);

	}
}
