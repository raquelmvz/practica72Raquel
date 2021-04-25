/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p72Raquel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author raquel
 */
public class GestionHorarios {

    public static void main(String[] args) {

        String idFichero = "hor_curso_1920_final.csv";
        ArrayList<RegistroHorario> listaRegistros; //aqui se van guardando los registros del csv
        /* en este arraylist guardamos todos los registros leidos 
        (ya estan ordenados) */
        ArrayList<RegistroHorario> registros = leeFichero(idFichero); //aqui se ejecuta el metodo que lee el fichero

        //set ordenado -- treeset
        SortedSet<String> conjuntoGrupos = new TreeSet<>();
        SortedSet<String> conjuntoInicialesProfesores = new TreeSet<>();

        //recorro los registros y guardo cada
        //dato en el set que corresponda
        for (RegistroHorario registro : registros) {
            conjuntoGrupos.add(registro.getCurso());
            conjuntoInicialesProfesores.add(registro.getInicialesProfesor());
        }

        //impresion por pantalla para comprobar
//        System.out.println("----------------------------------\n"
//                + "--- GRUPOS--- ");
//        conjuntoGrupos.forEach(System.out::println);
//        
//        System.out.println("----------------------------------\n"
//                + "--- PROFESORES --- ");
//        conjuntoInicialesProfesores.forEach(System.out::println);
        //dos opciones: consultar horario por profesor o por grupo
        Scanner entradaTeclado = new Scanner(System.in);

        int opcion;

        do {

            System.out.println("OPCIONES:\n"
                    + "1. CONSULTAR HORARIOS POR PROFESOR\n"
                    + "2. CONSULTAR HORARIOS POR GRUPO");
            opcion = entradaTeclado.nextInt();

        } while (opcion != 1 && opcion != 2);

        //si se elige la primera opcion (profesores)
        if (opcion == 1) {

            //lista con las iniciales del profesorado
            System.out.println("LISTA DE PROFESORES");
            //para mostrar un numero en la lista junto a las iniciales
            //y seleccionar por el numero
            int contadorIniciales = 0;
            for (String iniciales : conjuntoInicialesProfesores) {
                System.out.println(contadorIniciales + ". - " + iniciales);
                contadorIniciales++;
            }

            int eligeProfesor;

            do {

                System.out.println("SELECCIONA UN NUMERO DE LA LISTA:");
                eligeProfesor = entradaTeclado.nextInt();

            } while (eligeProfesor < 0 || eligeProfesor > conjuntoInicialesProfesores.size() - 1);

            //conversion del set a arraylist para poder extraer un elemento 
            //dado su indice
            List<String> lista = new ArrayList<String>(conjuntoInicialesProfesores);

            //queremos guardar las iniciales del profesor seleccionado
            String inicialesSeleccionadas = lista.get(eligeProfesor);
            System.out.println("Has elegido el profesor " + inicialesSeleccionadas);

            //se genera un fichero con el profesor seleccionado
            generaFicheroProfesor(registros, inicialesSeleccionadas);

        } else { //si elegimos por grupos

            //lista con las iniciales de grupos
            System.out.println("LISTA DE GRUPOS");
            //para mostrar un numero en la lista junto a las iniciales
            //y seleccionar por el numero
            int contadorGrupos = 0;
            for (String grupo : conjuntoGrupos) {
                System.out.println(contadorGrupos + ". - " + grupo);
                contadorGrupos++;
            }

            int eligeGrupo;

            do {

                System.out.println("SELECCIONA UN NUMERO DE LA LISTA:");
                eligeGrupo = entradaTeclado.nextInt();

            } while (eligeGrupo < 0 || eligeGrupo > conjuntoGrupos.size() - 1);

            //conversion del set a arraylist para poder extraer un elemento 
            //dado su indice
            List<String> lista = new ArrayList<String>(conjuntoGrupos);

            //queremos guardar el grupo seleccionado
            String grupoSeleccionado = lista.get(eligeGrupo);
            System.out.println("Has elegido el grupo " + grupoSeleccionado);

            //se genera un fichero con el grupo seleccionado
            generaFicheroGrupo(registros, grupoSeleccionado);

        }

    }


    /* Metodo para quitar las comillas a todos los datos que
    se recogen en el fichero */
    private static String formateaTexto(String texto) {
        return texto.substring(1, texto.length() - 1);
    }

    /* Metodo que lee el fichero */
    public static ArrayList<RegistroHorario> leeFichero(String fichero) {

        /* para guardar los datos que se van leyendo */
        String[] tokens;
        String linea;
        ArrayList<RegistroHorario> registros = new ArrayList<>();

        /*Instanciación de BufferedReader a partir de un objeto InputStreamReader
        InputStreamReader permite indicar el tipo de codificación del archivo */
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), "UTF-8"))) {

            //mientras el metodo readline no devuelva null es que existen datos por leer
            while ((linea = br.readLine()) != null) {

                tokens = linea.split(";"); //se guarda cada elemento de la linea en funcion del separador

                RegistroHorario tmp = new RegistroHorario();

                tmp.setCurso(formateaTexto(tokens[1]).trim());
                tmp.setInicialesProfesor(formateaTexto(tokens[2]).trim());
                tmp.setAsignatura(formateaTexto(tokens[3]).trim());
                tmp.setAula(formateaTexto(tokens[4]).trim());
                tmp.setDiaSemana(Integer.parseInt(tokens[5]));
                tmp.setHoraDia(Integer.parseInt(tokens[6]));

                registros.add(tmp);

                //se ordena la lista por dia y luego por hora
                //se usa el thencompairing
                Comparator<RegistroHorario> porDia = (d1, d2) -> Integer.compare(d1.getDiaSemana(), d2.getDiaSemana());
                Comparator<RegistroHorario> porHora = (h1, h2) -> Integer.compare(h1.getHoraDia(), h2.getHoraDia());
                Collections.sort(registros, porDia.thenComparing(porHora));

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("----Lectura del fichero:-----");
        for (RegistroHorario re : registros) {
            System.out.println(re);
        }

        return registros;
    }

    //metodo para dar nombre personalizado a los ficheros
    public static String devuelveNombreFichero(String iniciales) {
        return (iniciales + ".csv");
    }

    //metodo que genera un fichero de profesor
    public static void generaFicheroProfesor(ArrayList<RegistroHorario> lista, String iniciales) {

        String idFichero = devuelveNombreFichero(iniciales);

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(idFichero))) {

            flujo.write("CURSO;ASIGNATURA;AULA;DIA;HORA");
            flujo.newLine();

            for (RegistroHorario regi : lista) {
                if (regi.getInicialesProfesor().equals(iniciales)) {
                    //se escribe el fichero de ese profesor
                    flujo.write(regi.getCurso() + ";" + regi.getAsignatura()
                            + ";" + regi.getAula() + ";" + regi.getDiaSemana() + ";" + regi.getHoraDia());
                    flujo.newLine();

                }
            }

            System.out.println(idFichero + " se ha creado");
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

    //metodo que genera un fichero de grupo
    public static void generaFicheroGrupo(ArrayList<RegistroHorario> lista, String grupo) {

        String idFichero = devuelveNombreFichero(grupo);

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(idFichero))) {

            flujo.write("PROFESOR;ASIGNATURA;AULA;DIA;HORA");
            flujo.newLine();

            for (RegistroHorario regi : lista) {
                if (regi.getCurso().equals(grupo)) {
                    //se escribe el fichero de ese grupo
                    flujo.write(regi.getInicialesProfesor() + ";" + regi.getAsignatura()
                            + ";" + regi.getAula() + ";" + regi.getDiaSemana() + ";" + regi.getHoraDia());
                    flujo.newLine();

                }
            }

            System.out.println(idFichero + " se ha creado");
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

}
