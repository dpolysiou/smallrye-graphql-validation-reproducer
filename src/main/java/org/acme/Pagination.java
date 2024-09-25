package org.acme;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Pagination(@Positive int limit, @PositiveOrZero int start) {}
