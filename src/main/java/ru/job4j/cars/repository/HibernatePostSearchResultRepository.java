package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PostSearchParams;
import ru.job4j.cars.model.PostSearchResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class HibernatePostSearchResultRepository implements PostSearchResultRepository {

    private final CrudRepository crudRepository;

    private static final String FIND_ALL_QUERY = "SELECT psr From PostSearchResult psr";

    @Override
    public List<PostSearchResult> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, PostSearchResult.class);
    }

    @Override
    public List<PostSearchResult> search(PostSearchParams postSearchParams) {
        return crudRepository.tx(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PostSearchResult> cq = cb.createQuery(PostSearchResult.class);
            Root<PostSearchResult> root = cq.from(PostSearchResult.class);
            cq.select(root);
            List<Predicate> predicates = this.makePredicates(postSearchParams, cb, root);
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
            Query<PostSearchResult> query = session.createQuery(cq);
            return query.getResultList();
        });
    }

    private List<Predicate> makePredicates(
            PostSearchParams postSearchParams,
            CriteriaBuilder cb,
            Root<PostSearchResult> root
    ) {
        List<Predicate> predicates = new ArrayList<>();
        if (postSearchParams.getCarBodyStyleId() != null) {
            predicates.add(cb.equal(root.get("bodyStyleId"), postSearchParams.getCarBodyStyleId()));
        }
        if (postSearchParams.getCarExteriorColorId() != null) {
            predicates.add(cb.equal(root.get("exteriorColorId"), postSearchParams.getCarExteriorColorId()));
        }
        if (postSearchParams.getCarTransmissionTypeId() != null) {
            predicates.add(cb.equal(root.get("transmissionTypeId"), postSearchParams.getCarTransmissionTypeId()));
        }
        if (postSearchParams.getCarDrivetrainId() != null) {
            predicates.add(cb.equal(root.get("drivetrainId"), postSearchParams.getCarDrivetrainId()));
        }
        if (postSearchParams.getCarSteeringWheelSide() != null) {
            predicates.add(cb.equal(root.get("steeringWheelSide"), postSearchParams.getCarSteeringWheelSide()));
        }
        if (postSearchParams.getCarModel() != null) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("modelName")),
                            "%" + postSearchParams.getCarModel().toLowerCase() + "%"
                    )
            );
        }
        if (postSearchParams.getCarEngineTypeId() != null) {
            predicates.add(cb.equal(root.get("engineTypeId"), postSearchParams.getCarEngineTypeId()));
        }
        if (postSearchParams.getCarBrandId() != null) {
            predicates.add(cb.equal(root.get("brandId"), postSearchParams.getCarBrandId()));
        }
        if (postSearchParams.getCarHorsepowerMin() != null) {
            predicates.add(cb.ge(root.get("horsepower"), postSearchParams.getCarHorsepowerMin()));
        }
        if (postSearchParams.getCarHorsepowerMax() != null) {
            predicates.add(cb.le(root.get("horsepower"), postSearchParams.getCarHorsepowerMax()));
        }
        if (postSearchParams.getCarProductionYearMin() != null) {
            predicates.add(cb.ge(root.get("productionYear"), postSearchParams.getCarProductionYearMin()));
        }
        if (postSearchParams.getCarProductionYearMax() != null) {
            predicates.add(cb.le(root.get("productionYear"), postSearchParams.getCarProductionYearMax()));
        }
        if (postSearchParams.getCarEngineVolumeMin() != null) {
            predicates.add(cb.ge(root.get("engineVolume"), postSearchParams.getCarEngineVolumeMin()));
        }
        if (postSearchParams.getCarEngineVolumeMax() != null) {
            predicates.add(cb.le(root.get("engineVolume"), postSearchParams.getCarEngineVolumeMax()));
        }
        return predicates;
    }
}