package cl.duoc.sumativa1.app.parted.repository;

import cl.duoc.sumativa1.app.parted.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT s FROM Sale s WHERE EXTRACT(YEAR FROM s.saleTime) = :year")
    List<Sale> findSalesByYear(int year);

    @Query(value = "SELECT s FROM Sale s WHERE EXTRACT(MONTH FROM s.saleTime) = :month")
    List<Sale> findSalesByMonth(int month);

    @Query("SELECT s FROM Sale s WHERE s.saleTime BETWEEN :startOfDay AND :endOfDay")
    List<Sale> findSalesByDate(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
