package com.wisdomhaven.library.dto.misc;

import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

public record Sortable(boolean isValid, @Nullable Sort sort) {}
