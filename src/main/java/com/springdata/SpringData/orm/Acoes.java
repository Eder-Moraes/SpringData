package com.springdata.SpringData.orm;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Acoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @ManyToMany(mappedBy = "acoes")
    List<Registro> registros;
}
