package controller;

import java.util.Scanner;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CrtlPrograma {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome completo da classe: ");
        String nomeClasse = scanner.nextLine();

        Class<?> classe = Class.forName(nomeClasse);

        Object objeto =
            classe.getDeclaredConstructor().newInstance();

        Field[] atributos =
            classe.getDeclaredFields();

        for (Field atributo : atributos) {

            System.out.println(
                "Atributo: " + atributo.getName()
            );

            String nomeAtributo =
                atributo.getName();

            String nomeSetter =
                "set" +
                Character.toUpperCase(
                    nomeAtributo.charAt(0)
                ) +
                nomeAtributo.substring(1);

            if (atributo.getType() == String.class) {

                System.out.print(
                    "Digite o valor de " +
                    atributo.getName() + ": "
                );

                String valor =
                    scanner.nextLine();

                Method metodo =
                    classe.getMethod(
                        nomeSetter,
                        String.class
                    );

                metodo.invoke(
                    objeto,
                    valor
                );

            } else if (atributo.getType() == int.class) {

                System.out.print(
                    "Digite o valor de " +
                    atributo.getName() + ": "
                );

                int valor =
                    scanner.nextInt();

                scanner.nextLine();

                Method metodo =
                    classe.getMethod(
                        nomeSetter,
                        int.class
                    );

                metodo.invoke(
                    objeto,
                    valor
                );
            }
        }

        Method metodoToString =
            classe.getMethod("toString");

        String resultado =
            (String) metodoToString.invoke(objeto);

        System.out.println("\nObjeto criado:");

        System.out.println(resultado);

        scanner.close();
    }
}