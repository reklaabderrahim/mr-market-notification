package fr.mr_market.mr_notification.repository;

import fr.mr_market.mr_notification.model.Mail;
import fr.mr_market.mr_notification.model.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    @Modifying
    @Query("update Mail m set m.status = :status, m.updateDate = :updateDate where m.id = :id")
    void updateStatus(@Param(value = "id") long id, @Param(value = "status") MailStatus status, @Param(value = "updateDate") LocalDateTime updateDate);
}
