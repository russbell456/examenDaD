package com.example.dmbmatriculaservice.entity;

import com.example.dmbmatriculaservice.dto.CursoDto;
import com.example.dmbmatriculaservice.dto.CursoDto;
import jakarta.persistence.*;


@Entity
public class MatriculaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cursoId;

    @Transient
    private CursoDto cursoDto;

    public MatriculaDetalle() {

    }

    public MatriculaDetalle(Integer id, Integer cursoId,CursoDto cursoDto ) {
        this.id = id;
        this.cursoId = cursoId;
        this.cursoDto = cursoDto;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public CursoDto getCursoDto() {
        return cursoDto;
    }

    public void setCursoDto(CursoDto cursoDto) {
        this.cursoDto = cursoDto;
    }


}
