package com.ieum.be.domain.filteredStore.entity;

import com.ieum.be.domain.store.entity.Store;
import jakarta.persistence.*;

@Entity
@Table(name = "filteredStore")
public class FilteredStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fstore_id")
    private Integer fstoreId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "reason", nullable = false)
    private String reason;

    public FilteredStore(Integer fstoreId, Store store, String reason) {
        this.fstoreId = fstoreId;
        this.store = store;
        this.reason = reason;
    }

    public FilteredStore() {}
}