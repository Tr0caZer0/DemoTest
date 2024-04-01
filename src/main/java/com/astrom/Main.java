package com.astrom;


import com.astrom.entity.Arena;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();//Öppnar sessionen
        session.beginTransaction(); // Startar Sessionen

        session.getTransaction().commit(); // kör commit mot databasen
        session.close();// stänger sessionen

       UserMenu userMenu = new UserMenu(session);
       userMenu.menu();


        /*Arena arena = new Arena();
        arena.setNamn("TEST2");
        arena.setInomhusUtomhus("utomhus");
        arena.setFkAdressId(3);
        session.persist(arena);

        session.getTransaction().commit();
        session.close();*/

        /*Adress adress = session.get(Adress.class, 3);
//", Address=" + adress
        if(adress != null){
            System.out.println("Adress_id: " + adress.getAdressId());
            System.out.println("Gata: " + adress.getGata());
            System.out.println("Husnummer: " + adress.getHusnummer());
            System.out.println("Postnummer: " + adress.getPostnummer());
            System.out.println("Ort: " + adress.getOrt());
        }else{
            System.out.println("There is no address correlated to id" + 3);
        }*/

        /*Arena arenaRemove = session.get(Arena.class, 7);

        if(arenaRemove != null){
            session.remove(arenaRemove);
            System.out.println("Removed");
        }else{
            System.out.println("Arena with ID " + 7 + " not found");
        }
        session.getTransaction().commit();
        session.close();*/

    }

    private void userMenu(Session session) {

    }
}