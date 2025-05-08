package com.pawan.MightyBull.utils;

import com.pawan.MightyBull.dto.FilterCondition;
import com.pawan.MightyBull.enums.FilterType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;

import java.util.ArrayList;
import java.util.List;

@Component
public class CriteriaQueryUtils {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> Page<T> getFilteredPage(Class<T> entityClass, List<FilterCondition> filters, String sortBy,
            boolean sortDesc, int pageNumber, int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // === Main Query ===
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        List<Predicate> predicates = buildPredicates(cb, root, filters);

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        if (StringUtils.isNotBlank(sortBy)) {
            cq.orderBy(sortDesc ? cb.desc(root.get(sortBy)) : cb.asc(root.get(sortBy)));
        }

        TypedQuery<T> query = entityManager.createQuery(cq);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<T> results = query.getResultList();

        // === Count Query ===
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, filters);
        countQuery.select(cb.count(countRoot)).where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, PageRequest.of(pageNumber, pageSize), totalCount);
    }

    private <T> List<Predicate> buildPredicates(CriteriaBuilder cb, Root<T> root, List<FilterCondition> filters) {
        List<Predicate> predicates = new ArrayList<>();

        for(FilterCondition filter : filters) {
            String field = filter.getField();
            Object value = filter.getValue();
            Object secondValue = filter.getSecondValue();
            FilterType type = filter.getType();

            switch (type) {
                case EQUAL:
                    predicates.add(cb.equal(root.get(field), value));
                    break;

                case NOT_EQUAL:
                    predicates.add(cb.notEqual(root.get(field), value));
                    break;

                case GREATER_THAN:
                    predicates.add(cb.greaterThan(root.get(field), (Comparable) value));
                    break;

                case GREATER_THAN_OR_EQUAL:
                    predicates.add(cb.greaterThanOrEqualTo(root.get(field), (Comparable) value));
                    break;

                case LESS_THAN:
                    predicates.add(cb.lessThan(root.get(field), (Comparable) value));
                    break;

                case LESS_THAN_OR_EQUAL:
                    predicates.add(cb.lessThanOrEqualTo(root.get(field), (Comparable) value));
                    break;

                case BETWEEN:
                    predicates.add(cb.between(root.get(field), (Comparable) value, (Comparable) secondValue));
                    break;

                case NOT_BETWEEN:
                    predicates.add(cb.not(cb.between(root.get(field), (Comparable) value, (Comparable) secondValue)));
                    break;

                case LIKE:
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
                    break;

                case NOT_LIKE:
                    predicates.add(cb.notLike(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
                    break;

                case ILIKE:
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
                    break;

                case NOT_ILIKE:
                    predicates.add(cb.notLike(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
                    break;

                case IN:
                    predicates.add(root.get(field).in((Collection<?>) value));
                    break;

                case NOT_IN:
                    predicates.add(cb.not(root.get(field).in((Collection<?>) value)));
                    break;

                case IS_NULL:
                    predicates.add(cb.isNull(root.get(field)));
                    break;

                case IS_NOT_NULL:
                    predicates.add(cb.isNotNull(root.get(field)));
                    break;

                default:
                    throw new UnsupportedOperationException("Unsupported filter type: " + type);
            }
        }

        return predicates;
    }
}
