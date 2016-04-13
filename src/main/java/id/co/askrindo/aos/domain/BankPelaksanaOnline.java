package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BankPelaksanaOnline.
 */
@Entity
@Table(name = "bank_pelaksana_online")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bankpelaksanaonline")
public class BankPelaksanaOnline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = "kode_bank", length = 2, nullable = false)
    private String kode_bank;
    
    @Column(name = "nama_bank")
    private String nama_bank;
    
    @NotNull
    @Size(max = 2)
    @Column(name = "id_aktifitas_1", length = 2, nullable = false)
    private String id_aktifitas_1;
    
    @NotNull
    @Size(max = 2)
    @Column(name = "id_aktifitas_2", length = 2, nullable = false)
    private String id_aktifitas_2;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode_bank() {
        return kode_bank;
    }
    
    public void setKode_bank(String kode_bank) {
        this.kode_bank = kode_bank;
    }

    public String getNama_bank() {
        return nama_bank;
    }
    
    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getId_aktifitas_1() {
        return id_aktifitas_1;
    }
    
    public void setId_aktifitas_1(String id_aktifitas_1) {
        this.id_aktifitas_1 = id_aktifitas_1;
    }

    public String getId_aktifitas_2() {
        return id_aktifitas_2;
    }
    
    public void setId_aktifitas_2(String id_aktifitas_2) {
        this.id_aktifitas_2 = id_aktifitas_2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankPelaksanaOnline bankPelaksanaOnline = (BankPelaksanaOnline) o;
        if(bankPelaksanaOnline.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bankPelaksanaOnline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BankPelaksanaOnline{" +
            "id=" + id +
            ", kode_bank='" + kode_bank + "'" +
            ", nama_bank='" + nama_bank + "'" +
            ", id_aktifitas_1='" + id_aktifitas_1 + "'" +
            ", id_aktifitas_2='" + id_aktifitas_2 + "'" +
            '}';
    }
}
