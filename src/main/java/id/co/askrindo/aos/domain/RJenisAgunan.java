package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RJenisAgunan.
 */
@Entity
@Table(name = "r_jenis_agunan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rjenisagunan")
public class RJenisAgunan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = "id_r_jenis_agunan", length = 2, nullable = false)
    private String id_r_jenis_agunan;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_jenis_agunan() {
        return id_r_jenis_agunan;
    }
    
    public void setId_r_jenis_agunan(String id_r_jenis_agunan) {
        this.id_r_jenis_agunan = id_r_jenis_agunan;
    }

    public String getUraian() {
        return uraian;
    }
    
    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RJenisAgunan rJenisAgunan = (RJenisAgunan) o;
        if(rJenisAgunan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rJenisAgunan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RJenisAgunan{" +
            "id=" + id +
            ", id_r_jenis_agunan='" + id_r_jenis_agunan + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
