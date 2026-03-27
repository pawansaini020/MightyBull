package com.pawan.MightyBull.dao.mongo.support;

import com.pawan.MightyBull.dto.FilterCondition;
import com.pawan.MightyBull.enums.FilterType;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Builds Mongo {@link Criteria} from {@link FilterCondition} lists, mirroring JPA criteria in {@code AbstractDao}.
 */
public final class MongoFilterSupport {

    private MongoFilterSupport() {
    }

    public static Criteria toCriteria(List<FilterCondition> filters) {
        if (filters == null || filters.isEmpty()) {
            return new Criteria();
        }
        List<Criteria> parts = new ArrayList<>();
        for (FilterCondition filter : filters) {
            parts.add(single(filter));
        }
        if (parts.size() == 1) {
            return parts.get(0);
        }
        return new Criteria().andOperator(parts.toArray(new Criteria[0]));
    }

    private static Criteria single(FilterCondition filter) {
        String field = filter.getField();
        Object value = filter.getValue();
        Object secondValue = filter.getSecondValue();
        FilterType type = filter.getType();

        return switch (type) {
            case EQUAL -> Criteria.where(field).is(value);
            case NOT_EQUAL -> Criteria.where(field).ne(value);
            case GREATER_THAN -> Criteria.where(field).gt((Comparable) value);
            case GREATER_THAN_OR_EQUAL -> Criteria.where(field).gte((Comparable) value);
            case LESS_THAN -> Criteria.where(field).lt((Comparable) value);
            case LESS_THAN_OR_EQUAL -> Criteria.where(field).lte((Comparable) value);
            case BETWEEN -> Criteria.where(field).gte((Comparable) value).lte((Comparable) secondValue);
            case NOT_BETWEEN -> new Criteria().orOperator(
                    Criteria.where(field).lt((Comparable) value),
                    Criteria.where(field).gt((Comparable) secondValue));
            case LIKE, ILIKE -> Criteria.where(field)
                    .regex(".*" + Pattern.quote(value.toString()) + ".*", "i");
            case NOT_LIKE, NOT_ILIKE -> Criteria.where(field)
                    .not()
                    .regex(".*" + Pattern.quote(value.toString()) + ".*", "i");
            case IN -> Criteria.where(field).in((Collection<?>) value);
            case NOT_IN -> Criteria.where(field).nin((Collection<?>) value);
            case IS_NULL -> Criteria.where(field).isNull();
            case IS_NOT_NULL -> Criteria.where(field).ne(null);
            default -> throw new UnsupportedOperationException("Unsupported filter type: " + type);
        };
    }
}
