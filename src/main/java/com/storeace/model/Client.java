package com.storeace.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fio;
    private String tel;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Ordering> orderings;

    public Client(String fio, String tel) {
        this.fio = fio;
        this.tel = tel;
        orderings = new ArrayList<>();
    }

    public void addOrdering(Ordering ordering) {
        orderings.add(ordering);
        ordering.setClient(this);
    }

    public void removeOrdering(Ordering ordering) {
        orderings.remove(ordering);
        ordering.setClient(null);
    }
}
