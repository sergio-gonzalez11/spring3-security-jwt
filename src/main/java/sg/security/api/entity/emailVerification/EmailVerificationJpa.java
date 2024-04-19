package sg.security.api.entity.emailVerification;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.NumericBooleanConverter;
import sg.security.api.entity.user.UserJpa;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "EMAIL_VERIFICATION")
public class EmailVerificationJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMAIL_VERIFICATION")
    private Integer id;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "EXPIRATION_TIME", nullable = false)
    private Date expirationTime;

    @Column(name = "EXPIRED", nullable = false, columnDefinition = "DECIMAL(1,0)")
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean expired = false;

    @OneToOne
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", nullable = false)
    private UserJpa user;

}