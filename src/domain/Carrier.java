package domain;

import dtos.CarrierDto;
import interfaces.CarrierInterface;
import interfaces.TrackAndTraceInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NamedQueries(
        @NamedQuery(name = "Carrier.findByName",
                query = "select c from Carrier c where c.name = :name")
)
@NoArgsConstructor
public class Carrier implements Serializable, CarrierInterface {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    private String name;
    private String phone;
    private String email;
    private boolean active;
    //    @Embedded
    private String image;
    private int amountOfCharacters;
    private boolean numOnly;
    private String prefix;

    @OneToMany(mappedBy = "carrier")
    private Set<TrackAndTrace> trackAndTraces;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Carrier(String email, String name, String phone, String image, boolean active, int amountOfCharacters, boolean numOnly, String prefix, Set<TrackAndTrace> trackAndTraces, Company company) {
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.active = active;
        this.amountOfCharacters = amountOfCharacters;
        this.numOnly = numOnly;
        this.prefix = prefix;
        this.trackAndTraces = trackAndTraces;
        this.company = company;
    }

    public Carrier(CarrierDto carrierDto) {
        this.image = carrierDto.image();
        this.name = carrierDto.name();
        this.phone = carrierDto.phone();
        this.email = carrierDto.email();
        this.active = carrierDto.active();
        this.amountOfCharacters = carrierDto.amountOfCharacters();
        this.numOnly = carrierDto.numOnly();
        this.prefix = carrierDto.prefix();
        this.trackAndTraces = new HashSet<>();
        this.company = null;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", amountOfCharacters=" + amountOfCharacters +
                ", numOnly=" + numOnly +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    public Set<TrackAndTraceInterface> getTrackAndTraceInterfaces() {
        return new HashSet<>(trackAndTraces);
    }

}