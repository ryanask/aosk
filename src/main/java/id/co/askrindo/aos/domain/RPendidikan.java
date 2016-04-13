package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RPendidikan.
 */
@Entity
@Table(name = "r_pendidikan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rpendidikan")
public class RPendidikan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_pendidikan", length = 1, nullable = false)
    private String id_r_pendidikan;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_pendidikan() {
        return id_r_pendidikan;
    }
    
    public void setId_r_pendidikan(String id_r_pendidikan) {
        this.id_r_pendidikan = id_r_pendidikan;
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
        RPendidikan rPendidikan = (RPendidikan) o;
        if(rPendidikan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rPendidikan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RPendidikan{" +
            "id=" + id +
            ", id_r_pendidikan='" + id_r_pendidikan + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
