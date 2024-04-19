package sg.security.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntityJpa {

    @CreatedDate
    @Column(name = "CREATION_DATE", updatable = false)
    private Date createdDate;

    @CreatedBy
    @Column(name = "CREATION_USER", updatable = false)
    private String createdUser;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    private Date updatedDate;

    @LastModifiedBy
    @Column(name = "UPDATE_USER")
    private String updatedUser;

    @Column(name = "DELETE_DATE")
    private Date deactivatedDate;

    @Column(name = "DELETE_USER")
    private String deactivatedUser;

/*    @Transient
    private String otherCreatedUser;

    @PrePersist
    void prePre() {
        if (this.otherCreatedUser != null) {
            this.setCreatedUser(this.otherCreatedUser);
        }

    }

    @PreUpdate
    void preUpdate() {
        if (this.otherCreatedUser != null) {
            this.setUpdatedUser(this.otherCreatedUser);
        }
    }*/
}
