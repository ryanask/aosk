package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RStatusAkad.
 */
@Entity
@Table(name = "r_status_akad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rstatusakad")
public class RStatusAkad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_status_akad", length = 1, nullable = false)
    private String id_r_status_akad;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_status_akad() {
        return id_r_status_akad;
    }
    
    public void setId_r_status_akad(String id_r_status_akad) {
        this.id_r_status_akad = id_r_status_akad;
    }

    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RStatusAkad rStatusAkad = (RStatusAkad) o;
        if(rStatusAkad.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rStatusAkad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RStatusAkad{" +
            "id=" + id +
            ", id_r_status_akad='" + id_r_status_akad + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
