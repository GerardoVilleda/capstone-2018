package capstone.kafka.producer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import capstone.kafka.producer.entity.AssetPrice;

@Repository
public interface AssetPriceRepository extends CrudRepository<AssetPrice, String> {
}
