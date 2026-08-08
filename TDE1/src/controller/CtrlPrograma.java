package src.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import src.Empresa;

// Aluno: Erick Ribeiro Graciano
public class CtrlPrograma {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        List<Empresa> empresas = new ArrayList<>();
        
        //Instanciar quatro objetos de empresa 
        /* Empresa empresa1 = new Empresa(
            "11.111.111/0001-11",
            "Google",
            1000
        );

        Empresa empresa2 = new Empresa(
            "22.222.222/0001-22",
            "Microsoft",
            800
        );

        Empresa empresa3 = new Empresa(
            "33.333.333/0001-33",
            "Apple",
            1200
        );

        Empresa empresa4 = new Empresa(
            "44.444.444/0001-44",
            "Amazon",
            1500
        ); */

        //Colocar em um objeto list
        /* empresas.add(empresa1);
        empresas.add(empresa2);
        empresas.add(empresa3);
        empresas.add(empresa4); */

        //Instanciar empresas dinamicamente 
        for (int i = 0; i < 4; i++) {
            System.out.println("Empresa " + (i + 1));

            System.out.print("CNPJ: ");
            String cnpj = scanner.nextLine();

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Número de empregados: ");
            int empregados = scanner.nextInt();
            scanner.nextLine();

            //Colocando em um objeto list
            empresas.add(new Empresa(cnpj, nome, empregados));
        }

        //Ordenar por nome e exibir
        empresas.sort(
            Comparator.comparing(Empresa::getNome)
        );

        System.out.println(
            "\n========== EMPRESAS ORDENADAS PELO NOME =========="
        );

        for (Empresa empresa : empresas) {
            System.out.println(empresa);
        }

        //Ordenar por número de empregados e exibir
        empresas.sort(
            Comparator.comparingInt(Empresa::getNumEmpregados)
        );

        System.out.println(
            "\n====== EMPRESAS ORDENADAS POR NÚMERO DE EMPREGADOS ======"
        );

        for (Empresa empresa : empresas) {
            System.out.println(empresa);
        }

        //Obter objetos da classe empresa
        Class<?> classeEmpresa = Empresa.class;

        //Listar atributos da classe
        System.out.println(
            "\n========== ATRIBUTOS DA CLASSE EMPRESA =========="
        );

        Field[] atributos = classeEmpresa.getDeclaredFields();

        for (Field atributo : atributos) {
            System.out.println(
                "Atributo: " + atributo.getName()
                + " | Tipo: " + atributo.getType().getSimpleName()
            );
        }

        //Listar métodos da classe
        System.out.println(
            "\n========== MÉTODOS DA CLASSE EMPRESA =========="
        );

        Method[] metodos = classeEmpresa.getDeclaredMethods();

        for (Method metodo : metodos) {
            System.out.println(
                "Método: " + metodo.getName()
            );
        }

        scanner.close();
    }
}
