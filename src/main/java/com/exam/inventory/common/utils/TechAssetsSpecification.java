package com.exam.inventory.common.utils;

import com.exam.inventory.dtos.ActivoFiltroDto;
import com.exam.inventory.entity.TechAssetsEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TechAssetsSpecification {

    public static Specification<TechAssetsEntity> filtrar(
            ActivoFiltroDto filtro) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // FILTRO POR SERIE
            if (filtro.getSerie() != null &&
                    !filtro.getSerie().isBlank()) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("serialNumber")),
                                "%" + filtro.getSerie()
                                        .trim()
                                        .toLowerCase(Locale.ROOT) + "%"
                        )
                );
            }

            // FILTRO POR MARCA / MODELO
            if (filtro.getMarcaModelo() != null &&
                    !filtro.getMarcaModelo().isBlank()) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("model")),
                                "%" + filtro.getMarcaModelo()
                                        .trim()
                                        .toLowerCase(Locale.ROOT) + "%"
                        )
                );
            }

            // FILTRO POR CATEGORÍA
            if (filtro.getCategoriaId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("category").get("id"),
                                filtro.getCategoriaId()
                        )
                );
            }

            // FILTRO POR ESTADO
            if (filtro.getEstadoId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("status").get("id"),
                                filtro.getEstadoId()
                        )
                );
            }

            // COSTO MÍNIMO
            if (filtro.getCostoMin() != null) {

                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("acquisitionCost"),
                                filtro.getCostoMin()
                        )
                );
            }

            // COSTO MÁXIMO
            if (filtro.getCostoMax() != null) {

                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("acquisitionCost"),
                                filtro.getCostoMax()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}