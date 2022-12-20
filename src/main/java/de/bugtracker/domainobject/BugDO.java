package de.bugtracker.domainobject;


import de.bugtracker.domainvalue.BugStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Bug")
public class BugDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bugId;

    private String assignedUser;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BugStatus status;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime created = ZonedDateTime.now();

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private boolean deleted = false;

}
