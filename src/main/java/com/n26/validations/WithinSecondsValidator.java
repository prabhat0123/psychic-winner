package com.n26.validations;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.lang.Nullable;

public class WithinSecondsValidator implements ConstraintValidator<WithinSeconds, ZonedDateTime> {

	private long duration;

	@Override
	public void initialize(final WithinSeconds annotation) {
		duration = annotation.duration();
	}

	@Override
	public boolean isValid(@Nullable final ZonedDateTime value, final ConstraintValidatorContext context) {

		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		return value == null || value.isAfter(now.minusSeconds(duration));

	}
}
