package org.example;

import org.example.entities.ClientEntity;
import org.example.entities.OrderEntity;
import org.example.entities.OrderStatusEntity;
import org.example.entities.ServicesEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        insertData();
        //selectList();
//        var entity = getById(1);
//        System.out.println(entity.getFirstName()+" "+entity.getLastName());
        //insertDataServices();
          insertOrderStatus();

    }





    private static void insertData(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        ClientEntity entity = new ClientEntity();
        entity.setFirstName("Іван");
        entity.setLastName("Барабашка");
        entity.setPhone("+38 068 47 85 458");
        entity.setCar_model("Volkswagen Beetle A5");
        entity.setCar_year(2003);

        session.save(entity);


        transaction.commit();
        session.close();
    }

    private static void selectList(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<ClientEntity> results = session.createQuery("from ClientEntity", ClientEntity.class)
                .getResultList();

        System.out.println("Count = "+ results.size());


        for (ClientEntity client : results) {
            System.out.println(client);
        }
    }

    private static ClientEntity getById(int id) {
        // Obtain a session from the SessionFactory
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        ClientEntity entity = session.get(ClientEntity.class, id);
        transaction.commit();
        session.close();
        return entity;
    }

    private static void insertOrderStatus() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String [] list = {
                "Нове замовлення",
                "В процесі виконання",
                "Виконано",
                "Скасовано клієнтом"
        };
        for (var item : list) {
            OrderStatusEntity entity = new OrderStatusEntity();
            entity.setName(item);
            session.save(entity);
        }
        transaction.commit();
        session.close();
    }

    private static void insertDataServices() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Object[][] services = {
                {"ТО", 3000},
                {"заміна ричага", 500},
                {"заміна рейки", 1000},
                {"баласування коліс", 300},
                {"розвал зходження", 200}
        };

        for (Object[] service : services) {
            ServicesEntity entity = new ServicesEntity();
            entity.setName((String) service[0]);
            entity.setPrice((float)service[1]);
            session.save(entity);
        }

        transaction.commit();
        session.close();
    }
    private static void selectAllOrders() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<OrderEntity> orders = session.createQuery("from OrderEntity", OrderEntity.class).getResultList();

        for (OrderEntity order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Client: " + order.getClient().getFirstName() + " " + order.getClient().getLastName());
            System.out.println("Status: " + order.getStatus().getName());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Discount: " + order.getDiscount());
            System.out.print("Services: ");
            for (ServicesEntity service : order.getServices()) {
                System.out.print(service.getName() + " ");
            }
            System.out.println("\n-----------------------------");
        }

        session.close();
    }

}
