package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RKolektibilitas.
 */
@Entity
@Table(name = "r_kolektibilitas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rkolektibilitas")
public class RKolektibilitas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_kolektibilitas", length = 1, nullable = false)
    private String id_r_kolektibilitas;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_kolektibilitas() {
        return id_r_kolektibilitas;
    }
    
    public void setId_r_kolektibilitas(String id_r_kolektibilitas) {
        this.id_r_kolektibilitas = id_r_kolektibilitas;
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
        RKolektibilitas rKolektibilitas = (RKolektibilitas) o;
        if(rKolektibilitas.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rKolektibilitas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RKolektibilitas{" +
            "id=" + id +
            ", id_r_kolektibilitas='" + id_r_kolektibilitas + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
