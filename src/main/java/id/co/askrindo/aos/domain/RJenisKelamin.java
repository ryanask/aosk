package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RJenisKelamin.
 */
@Entity
@Table(name = "r_jenis_kelamin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rjeniskelamin")
public class RJenisKelamin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "id_r_jenis_kelamin", length = 1, nullable = false)
    private String id_r_jenis_kelamin;
    
    @Column(name = "keterangan")
    private String keterangan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_jenis_kelamin() {
        return id_r_jenis_kelamin;
    }
    
    public void setId_r_jenis_kelamin(String id_r_jenis_kelamin) {
        this.id_r_jenis_kelamin = id_r_jenis_kelamin;
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
        RJenisKelamin rJenisKelamin = (RJenisKelamin) o;
        if(rJenisKelamin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rJenisKelamin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RJenisKelamin{" +
            "id=" + id +
            ", id_r_jenis_kelamin='" + id_r_jenis_kelamin + "'" +
            ", keterangan='" + keterangan + "'" +
            '}';
    }
}
