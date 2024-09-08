package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Categoria pescaderia = Categoria.builder()
                    .denominacion("pescaderia")
                    .build();

            Categoria merceria = Categoria.builder()
                    .denominacion("merceria")
                    .build();

            Categoria conVencimiento = Categoria.builder()
                    .denominacion("Con vencimiento")
                    .build();

            Articulo Salmon = Articulo.builder()
                    .cantidad(10)
                    .denominacion("Salmon")
                    .precio(20)
                    .build();

            Salmon.getCategorias().add(pescaderia);
            Salmon.getCategorias().add(conVencimiento);

            Articulo Atun = Articulo.builder()
                    .cantidad(20)
                    .denominacion("Atun")
                    .precio(10)
                    .build();

            Atun.getCategorias().add(pescaderia);
            Atun.getCategorias().add(conVencimiento);

            Articulo HilosMulticolor = Articulo.builder()
                    .cantidad(5)
                    .denominacion("Hilos Multicolor")
                    .precio(100)
                    .build();

            HilosMulticolor.getCategorias().add(merceria);
            HilosMulticolor.getCategorias().add(conVencimiento);

            Articulo agujas = Articulo.builder()
                    .cantidad(10)
                    .denominacion("Agujas")
                    .precio(50)
                    .build();

            agujas.getCategorias().add(merceria);
            agujas.getCategorias().add(conVencimiento);

            Domicilio domicilio = Domicilio.builder()
                    .nombreCalle("Emilio civit")
                    .numero(286)
                    .build();

            Cliente cliente = Cliente.builder()
                    .nombre("Camila")
                    .apellido("Fabian")
                    .dni(40123456)
                    .domicilio(domicilio)
                    .build();

            DetalleFactura detalleFactura1 = DetalleFactura.builder()
                    .cantidad(2)
                    .articulo(Salmon)
                    .subtotal(40)
                    .build();

            DetalleFactura detalleFactura2 = DetalleFactura.builder()
                    .cantidad(1)
                    .articulo(HilosMulticolor)
                    .subtotal(100)
                    .build();

            Set<DetalleFactura> detalleFacturas = new HashSet<>();
            detalleFacturas.add(detalleFactura1);
            detalleFacturas.add(detalleFactura2);

            Factura factura = Factura.builder()
                    .numero(98765)
                    .fecha(Date.valueOf("2024-09-04"))
                    .cliente(cliente)
                    .detalleFacturas(detalleFacturas)
                    .build();

            entityManager.persist(factura);
            entityManager.flush();
            entityManager.getTransaction().commit();

            Factura facturaDB = entityManager.find(Factura.class, factura.getId());
            System.out.println("Se recupera factura n° " + facturaDB.getNumero());

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Factura");
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
