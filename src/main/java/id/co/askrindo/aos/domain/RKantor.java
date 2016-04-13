package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RKantor.
 */
@Entity
@Table(name = "r_kantor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rkantor")
public class RKantor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = "id_kantor", length = 2, nullable = false)
    private String id_kantor;
    
    @Column(name = "kantor")
    private String kantor;
    
    @Column(name = "alamat")
    private String alamat;
    
    @Column(name = "telepon")
    private String telepon;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_kantor() {
        return id_kantor;
    }
    
    public void setId_kantor(String id_kantor) {
        this.id_kantor = id_kantor;
    }

    public String getKantor() {
        return kantor;
    }
    
    public void setKantor(String kantor) {
        this.kantor = kantor;
    }

    public String getAlamat() {
        return alamat;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }
    
    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RKantor rKantor = (RKantor) o;
        if(rKantor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rKantor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RKantor{" +
            "id=" + id +
            ", id_kantor='" + id_kantor + "'" +
            ", kantor='" + kantor + "'" +
            ", alamat='" + alamat + "'" +
            ", telepon='" + telepon + "'" +
            '}';
    }
}
