package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RStatusRekening.
 */
@Entity
@Table(name = "r_status_rekening")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rstatusrekening")
public class RStatusRekening implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_status_rekening", length = 1, nullable = false)
    private String id_r_status_rekening;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_status_rekening() {
        return id_r_status_rekening;
    }
    
    public void setId_r_status_rekening(String id_r_status_rekening) {
        this.id_r_status_rekening = id_r_status_rekening;
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
        RStatusRekening rStatusRekening = (RStatusRekening) o;
        if(rStatusRekening.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rStatusRekening.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RStatusRekening{" +
            "id=" + id +
            ", id_r_status_rekening='" + id_r_status_rekening + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
