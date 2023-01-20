package com.example.rentalservice.repository;

import com.example.rentalservice.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Page<Reservation> findByUserid(Long userid, Pageable pageable);
    @Query(value = "select r.id, r.from_date,r.price, r.to_date,r.userid,r.vehicle_id from Reservation r join Company_Vehicle cv on(r.vehicle_id = cv.id) where cv.company_id = :companyid", nativeQuery = true)
    Page<Reservation> findReservationsForCompany(Long companyid,Pageable pageable);
}
