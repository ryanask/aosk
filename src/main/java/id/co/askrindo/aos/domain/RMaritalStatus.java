package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RMaritalStatus.
 */
@Entity
@Table(name = "r_marital_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rmaritalstatus")
public class RMaritalStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_marital_status", length = 1, nullable = false)
    private String id_r_marital_status;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_marital_status() {
        return id_r_marital_status;
    }
    
    public void setId_r_marital_status(String id_r_marital_status) {
        this.id_r_marital_status = id_r_marital_status;
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
        RMaritalStatus rMaritalStatus = (RMaritalStatus) o;
        if(rMaritalStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rMaritalStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RMaritalStatus{" +
            "id=" + id +
            ", id_r_marital_status='" + id_r_marital_status + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
