package com.example.dmbmatriculaservice.dto;

public class CursoDto {
    private Integer  id;
    private String   codigo;
    private String   nombre;
    private Integer  capacidad;

    public CursoDto() {

    }

    public CursoDto(Integer id,String codigo, String nombre, Integer capacidad ) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
