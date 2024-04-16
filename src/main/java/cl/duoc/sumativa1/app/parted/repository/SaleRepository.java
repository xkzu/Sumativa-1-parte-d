package cl.duoc.sumativa1.app.parted.repository;

import cl.duoc.sumativa1.app.parted.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT s FROM Sale s WHERE EXTRACT(YEAR FROM s.saleTime) = :year", nativeQuery = false)
    List<Sale> findSalesByYear(int year);
}
