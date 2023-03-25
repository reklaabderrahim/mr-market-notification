package fr.mr_market.mr_notification.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mail")
public class Mail extends FullBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_id_seq")
    @SequenceGenerator(name = "mail_id_seq", sequenceName = "mail_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "emitter")
    private String from;
    @Column(name = "recipients")
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private Set<String> recipients = new HashSet<>();
    @Column(name = "subject")
    private String subject;
    @Column(name = "body")
    private String body;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MailStatus status;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MailType type;
    @Column(name = "template_name")
    private String templateName;
    @Column(name = "content_type")
    private String contentType;

    public static Mail create(String from, Set<String> recipients, String subject, String body, String templateName, MailType mailType) {
        Mail mail = new Mail();
        mail.setUuid(UUID.randomUUID());
        mail.setCreateDate(LocalDateTime.now());
        mail.setStatus(MailStatus.PENDING);
        mail.setContentType("text/html");
        mail.setFrom(from);
        mail.setBody(body);
        mail.setSubject(subject);
        mail.setRecipients(recipients);
        mail.setTemplateName(templateName);
        mail.setType(mailType);
        return mail;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Mail.class.getSimpleName() + "[", "]").add("username=" + from).add("subject=" + subject).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mail mail)) return false;
        return Objects.equals(getUuid(), mail.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
