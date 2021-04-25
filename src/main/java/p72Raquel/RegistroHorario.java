/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p72Raquel;

import java.util.Objects;

/**
 *
 * @author raquel
 */
public class RegistroHorario {

    private String curso;
    private String inicialesProfesor;
    private String asignatura;
    private String aula;
    private int diaSemana;
    private int horaDia;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getInicialesProfesor() {
        return inicialesProfesor;
    }

    public void setInicialesProfesor(String inicialesProfesor) {
        this.inicialesProfesor = inicialesProfesor;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getHoraDia() {
        return horaDia;
    }

    public void setHoraDia(int horaDia) {
        this.horaDia = horaDia;
    }

    @Override
    public String toString() {
        return curso + ";" + inicialesProfesor + ";" + asignatura + ";" + aula + ";" + diaSemana + ";" + horaDia;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.curso);
        hash = 47 * hash + Objects.hashCode(this.inicialesProfesor);
        hash = 47 * hash + Objects.hashCode(this.asignatura);
        hash = 47 * hash + Objects.hashCode(this.aula);
        hash = 47 * hash + this.diaSemana;
        hash = 47 * hash + this.horaDia;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegistroHorario other = (RegistroHorario) obj;
        if (this.diaSemana != other.diaSemana) {
            return false;
        }
        if (this.horaDia != other.horaDia) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        if (!Objects.equals(this.inicialesProfesor, other.inicialesProfesor)) {
            return false;
        }
        if (!Objects.equals(this.asignatura, other.asignatura)) {
            return false;
        }
        if (!Objects.equals(this.aula, other.aula)) {
            return false;
        }
        return true;
    }

}
