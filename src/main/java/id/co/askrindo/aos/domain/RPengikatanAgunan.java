package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RPengikatanAgunan.
 */
@Entity
@Table(name = "r_pengikatan_agunan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rpengikatanagunan")
public class RPengikatanAgunan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "id_r_pengikatan_agunan", nullable = false)
    private Integer id_r_pengikatan_agunan;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_pengikatan_agunan() {
        return id_r_pengikatan_agunan;
    }
    
    public void setId_r_pengikatan_agunan(Integer id_r_pengikatan_agunan) {
        this.id_r_pengikatan_agunan = id_r_pengikatan_agunan;
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
        RPengikatanAgunan rPengikatanAgunan = (RPengikatanAgunan) o;
        if(rPengikatanAgunan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rPengikatanAgunan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RPengikatanAgunan{" +
            "id=" + id +
            ", id_r_pengikatan_agunan='" + id_r_pengikatan_agunan + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
