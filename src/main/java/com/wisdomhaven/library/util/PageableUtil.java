package com.wisdomhaven.library.util;

import com.wisdomhaven.library.dto.misc.Sortable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public final class PageableUtil {
    private PageableUtil() {}

    public static int getPageNumber(Integer pageNumber) {
        return pageNumber == null ? 0 : pageNumber - 1;
    }

    public static Sortable getSortable(String orderBy,
                                       Map<String, String> validFieldMap,
                                       List<Sort.Order> defaultOrder) {
        if (orderBy == null || validFieldMap.isEmpty()) {
            return new Sortable(true, Sort.by(defaultOrder));
        }

        // For example: orderBy = field1:asc,field2:desc
        String[] fieldOrderByList = orderBy.split(",");

        final Map<String, Sort.Order> orderByMap = new TreeMap<>();

        for (String fieldOrderBy : fieldOrderByList) {
            // For example: fieldOrderBy = field1:asc
            String trimmedFieldOrderBy = fieldOrderBy.trim();
            String[] fieldOrderBySeparatedList = trimmedFieldOrderBy.split(":");
            String field = fieldOrderBySeparatedList[0].toLowerCase();

            if (orderByMap.containsKey(field)) {
                return new Sortable(false, null);
            }

            Optional<String> validField =  validFieldMap.keySet().stream().filter(v -> v.equalsIgnoreCase(field)).findFirst();

            if (validField.isEmpty()) {
                return new Sortable(false, null);
            }

            Sort.Direction direction = Sort.Direction.fromString(fieldOrderBySeparatedList[1]);
            String actualField = validFieldMap.get(validField.get());
            orderByMap.put(field, new Sort.Order(direction, actualField));
        }

        return new Sortable(true, Sort.by(orderByMap.values().stream().toList()));
    }
}
