package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RSektorKur.
 */
@Entity
@Table(name = "r_sektor_kur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rsektorkur")
public class RSektorKur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "id_sektor", length = 10, nullable = false)
    private String id_sektor;
    
    @Column(name = "uraian")
    private String uraian;
    
    @Size(max = 1)
    @Column(name = "tanaman_keras", length = 1)
    private String tanaman_keras;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_sektor() {
        return id_sektor;
    }
    
    public void setId_sektor(String id_sektor) {
        this.id_sektor = id_sektor;
    }

    public String getUraian() {
        return uraian;
    }
    
    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public String getTanaman_keras() {
        return tanaman_keras;
    }
    
    public void setTanaman_keras(String tanaman_keras) {
        this.tanaman_keras = tanaman_keras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RSektorKur rSektorKur = (RSektorKur) o;
        if(rSektorKur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rSektorKur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RSektorKur{" +
            "id=" + id +
            ", id_sektor='" + id_sektor + "'" +
            ", uraian='" + uraian + "'" +
            ", tanaman_keras='" + tanaman_keras + "'" +
            '}';
    }
}
