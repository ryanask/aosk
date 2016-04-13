package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RNegaraTujuan.
 */
@Entity
@Table(name = "r_negara_tujuan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rnegaratujuan")
public class RNegaraTujuan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_negara_tujuan", length = 1, nullable = false)
    private String id_r_negara_tujuan;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_negara_tujuan() {
        return id_r_negara_tujuan;
    }
    
    public void setId_r_negara_tujuan(String id_r_negara_tujuan) {
        this.id_r_negara_tujuan = id_r_negara_tujuan;
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
        RNegaraTujuan rNegaraTujuan = (RNegaraTujuan) o;
        if(rNegaraTujuan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rNegaraTujuan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RNegaraTujuan{" +
            "id=" + id +
            ", id_r_negara_tujuan='" + id_r_negara_tujuan + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
