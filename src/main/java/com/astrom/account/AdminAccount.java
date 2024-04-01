package com.astrom.account;

import com.astrom.entity.Konsert;
import com.astrom.entity.Wc;
import com.astrom.entity.Adress;
import com.astrom.entity.Arena;
import com.astrom.entity.Users;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.Scanner;

public class AdminAccount {

    private Session session;
    private final Scanner input;
    private int controll;
    public AdminAccount(Session session){
        this.input = new Scanner(System.in);
        this.session = session;
    }

    public void adminMenu(){
        while(true) {
            switch(adminMenuOptions()) {
                case -1:
                    session.close();
                    System.exit(0);
                    break;
                case 1:
                        // Check bookings
                        tesstForAddressAndArenaJoin();
                    break;
                case 2:
                        // Add information
                    addInformation();
                    break;
                case 3:
                        // Check general information
                    readTable();
                    break;
                case 4:
                        updateDataInTable();
                    break;
                case 5:
                        deleteInformation();
                    break;

            }
        }
    }

    private void tesstForAddressAndArenaJoin() {
        TypedQuery<Arena> arenaQuery = session.createNamedQuery("Arena.byAddress2", Arena.class);

        arenaQuery.setParameter("namn","Tele2 Arena");
        for(Arena arena : arenaQuery.getResultList()){
            System.out.println(arena);
        }
    }

    private void updateDataInTable() {
        this.controll = 3;
        tableMenu();
    }

    private void readTable() {
        this.controll = 2;
        tableMenu();
    }

    private void deleteInformation() {
        this.controll = 4;
        tableMenu();
    }

    private void addInformation() {
        this.controll = 1;
        tableMenu();
    }
    private void tableMenu(){
        boolean temp = true;
        while(temp) {
            switch(selectTable()) {
                case -1:
                    temp = false;
                    break;
                case 1:
                    selectFunctionAddress();
                    temp = false;
                    break;
                case 2:
                    selectFunctionArena();
                    temp = false;
                    break;
                case 3:
                    selectFunctionConcert();
                    temp = false;
                    break;
                case 4:
                    selectFunctionUser();
                    temp = false;
                    break;
                case 5:
                    selectFunctionWC();
                    temp = false;
                    break;

            }
        }
    }// End table menu method

    private void selectFunctionWC() {
        if(this.controll == 1){
            System.out.println("add info");
            addToWC();
        } else if (this.controll == 2) {
            System.out.println("check info");
            readFromWC();
        } else if (this.controll == 3) {
            System.out.println("update info");
            updateInWC();
        } else if (this.controll == 4) {
            System.out.println("delete info");
            removeFromWC();
        }
    }

    private void updateInWC() {
        System.out.println("Enter id to update desired information in WC: ");
        int updateWc = input.nextInt();
        input.nextLine();
        Wc wcUpdate = session.get(Wc.class, updateWc);

        if(wcUpdate != null){

            System.out.println("Enter Users FK ");
            int usersFkId = input.nextInt();
            input.nextLine();;
            System.out.println("Enter Concert FK: ");
            int concertFkId = input.nextInt();
            input.nextLine();

            wcUpdate.setFkUserId(usersFkId);
            wcUpdate.setFkConcertId(concertFkId);

            commitWcTransactionIfTrue(wcUpdate);
        }
    }

    private void readFromWC() {
        boolean temp = true;
        while(temp){
            switch (checkTableOrRowMenu()){
                case -1:
                    temp = false;
                    break;

                case 1:
                    TypedQuery<Wc> arenaQuery = session.createNamedQuery("WC.table", Wc.class);
                    for(Wc wc : arenaQuery.getResultList()){
                        System.out.println(wc);
                    }
                    break;
                case 2:
                    Wc wc1 = new Wc();
                    System.out.println("Enter WC id to check desired data");
                    int id = input.nextInt();
                    input.nextLine();

                    Wc wc = session.get(Wc.class, id);
                    TypedQuery<Wc> wcQuery = null;
                    if(wc != null) {
                        wcQuery = session.createNamedQuery("Wc.byUserId", Wc.class);
                        wcQuery.setParameter("wc_id", id);

                        for(Wc row : wcQuery.getResultList()){
                            System.out.println(row);
                        }
                    }else{
                        System.out.println("No id with id-number " + id + " exists");
                    }

            }
        }

    }

    private void removeFromWC() {
        System.out.println("Enter wc_id to remove desired information: ");
        int removeWc = input.nextInt();
        input.nextLine();
        Wc wcRemove = session.get(Wc.class, removeWc);
        checkForIdAndRemove(removeWc, wcRemove, "WC");
        session.getTransaction().commit();
    }

    private void addToWC() {

        System.out.println("Enter Users FK ");
        int usersFkId = input.nextInt();
        input.nextLine();
        System.out.println("Enter Concert FK: ");
        int concertFkId = input.nextInt();
        input.nextLine();

        Users wcUser = session.get(Users.class, usersFkId);
        Konsert wcConcert = session.get(Konsert.class, concertFkId);

        if(wcUser != null && wcConcert != null){
            System.out.println("CHÈCK");
            Wc wc = new Wc();
            wc.setKonsert(wcConcert);
            wc.setUsers(wcUser);

            commitWcTransactionIfTrue(wc);
        }
    }

    private void selectFunctionUser() {
        if(this.controll == 1){
            System.out.println("add info");
            addToUser();
        } else if (this.controll == 2) {
            System.out.println("check info");
            readFromUser();
        } else if (this.controll == 3) {
            System.out.println("update info");
            updateInUser();
        } else if (this.controll == 4) {
            System.out.println("delete info");
            removeFromUser();
        }
    }

    private void updateInUser() {
        System.out.println("Enter id to update desired user: ");
        int updateUser = input.nextInt();
        input.nextLine();
        Users userUpdate = session.get(Users.class, updateUser);

        if(userUpdate != null) {
            System.out.println("Enter Username: ");
            String newUsername = input.nextLine();
            System.out.println("Enter Password: ");
            String newPassword = input.nextLine();
            System.out.println("Enter 1 for employee or 0 for user: ");
            int newEmployeeOrUser = input.nextInt();
            input.nextLine();
            System.out.println("Enter First Name: ");
            String newFirstName = input.nextLine();
            System.out.println("Enter Last Name: ");
            String newLastName = input.nextLine();
            System.out.println("Enter Date of Birth: ");
            String newDateOfBirth = input.nextLine();
            System.out.println("Enter Phone Number: ");
            String newPhoneNumber = input.nextLine();
            System.out.println("Enter FK: ");
            int newFK = input.nextInt();
            input.nextLine();

            userUpdate.setUsername(newUsername);
            userUpdate.setPassword(newPassword);
            userUpdate.setIsEmployee((byte) newEmployeeOrUser);
            userUpdate.setFirstname(newFirstName);
            userUpdate.setLastname(newLastName);
            userUpdate.setDateofbirth(Date.valueOf(newDateOfBirth));
            userUpdate.setPhonenumber(newPhoneNumber);
            userUpdate.setFkAddressId(newFK);

            commitUsersTransactionIfTrue(userUpdate);
        }else{
            System.out.println("User with ID " + updateUser + " not found.");
        }

    }

    private void readFromUser() {

        boolean temp = true;
        while(temp){
            switch (checkTableOrRowMenu()){
                case -1:
                    temp = false;
                    break;

                case 1:
                    TypedQuery<Users> arenaQuery = session.createNamedQuery("Users.table", Users.class);
                    for(Users users : arenaQuery.getResultList()){
                        System.out.println(users);
                    }
                    break;
                case 2:
                    System.out.println("Enter Users id to check desired data");
                    int id = input.nextInt();
                    input.nextLine();

                    Users users = session.get(Users.class, id);
                    TypedQuery<Users> query = null;
                    if(users != null) {
                        query = session.createNamedQuery("Users.byUserId", Users.class);
                        query.setParameter("user_id", id);

                        for(Users row : query.getResultList()){
                            System.out.println(row);
                        }
                    }else{
                        System.out.println("No id with id-number " + id + " exists");
                    }

            }
        }

    }

    private void removeFromUser() {
        System.out.println("Enter id to remove desired user: ");
        int removeUser = input.nextInt();
        input.nextLine();
        Users userRemove = session.get(Users.class, removeUser);
        checkForIdAndRemove(removeUser, userRemove, "User");
        session.getTransaction().commit();
    }

    private void addToUser() {
        System.out.println("Enter Username: ");
        String newUsername = input.nextLine();
        System.out.println("Enter Password: ");
        String newPassword = input.nextLine();
        System.out.println("Enter 1 for employee or 0 for user: ");
        int newEmployeeOrUser= input.nextInt();
        input.nextLine();
        System.out.println("Enter First Name: ");
        String newFirstName = input.nextLine();
        System.out.println("Enter Last Name: ");
        String newLastName = input.nextLine();
        System.out.println("Enter Date of Birth: ");
        String newDateOfBirth = input.nextLine();
        System.out.println("Enter Phone Number: ");
        String newPhoneNumber = input.nextLine();
        System.out.println("Enter FK: ");
        int newFK = input.nextInt();
        input.nextLine();

        Users users = new Users();
        users.setUsername(newUsername);
        users.setPassword(newPassword);
        users.setIsEmployee((byte) newEmployeeOrUser);
        users.setFirstname(newFirstName);
        users.setLastname(newLastName);
        users.setDateofbirth(Date.valueOf(newDateOfBirth));
        users.setPhonenumber(newPhoneNumber);
        users.setFkAddressId(newFK);

        commitUsersTransactionIfTrue(users);

    }

    private void selectFunctionConcert() {
        if(this.controll == 1){
            System.out.println("add info");
            addToConcert();
        } else if (this.controll == 2) {
            System.out.println("check info");
            readFromConcert();
        } else if (this.controll == 3) {
            System.out.println("update info");
            updateInConcert();
        } else if (this.controll == 4) {
            System.out.println("delete info");
            removeFromConcert();
        }
    }

    private void updateInConcert() {
        System.out.println("Enter id to update desired address: ");
        int updateConcert = input.nextInt();
        input.nextLine();
        Konsert concertUpdate = session.get(Konsert.class, updateConcert);

        if(concertUpdate != null){
            System.out.println("Enter Artist Name: ");
            String newArtist = input.nextLine();
            System.out.println("Enter Date: ");
            String newDate = input.nextLine();
            System.out.println("Enter Ticket Price: ");
            double newTicketPrice= input.nextDouble();
            System.out.println("Enter Age Limit: ");
            int newAgeLimit = input.nextInt();
            input.nextLine();
            System.out.println("Enter FK: ");
            int newFK = input.nextInt();
            input.nextLine();

            concertUpdate.setArtistnamn(newArtist);
            concertUpdate.setDatum(Date.valueOf(newDate));
            concertUpdate.setBiljettpris(newTicketPrice);
            concertUpdate.setAgeLimit(newAgeLimit);
            concertUpdate.setFkArenaId(newFK);

            commitConcertTransactionIfTrue(concertUpdate);
        }else{
            System.out.println("Concert with ID " + updateConcert + " not found.");
        }
    }

    private void readFromConcert() {
        boolean temp = true;
        while(temp){
            switch (checkTableOrRowMenu()){
                case -1:
                    temp = false;
                    break;

                case 1:
                    TypedQuery<Konsert> arenaQuery = session.createNamedQuery("Konsert.table", Konsert.class);
                    for(Konsert concert : arenaQuery.getResultList()){
                        System.out.println(concert);
                    }
                    break;
                case 2:
                    System.out.println("Enter Users id to check desired data");
                    int id = input.nextInt();
                    input.nextLine();

                    Konsert konsert = session.get(Konsert.class, id);
                    TypedQuery<Konsert> query = null;
                    if(konsert != null) {
                        query = session.createNamedQuery("Konsert.byUserId", Konsert.class);
                        query.setParameter("konsert_id", id);

                        for(Konsert row : query.getResultList()){
                            System.out.println(row);
                        }
                    }else{
                        System.out.println("No id with id-number " + id + " exists");
                    }

            }
        }
    }

    private void removeFromConcert() {
        System.out.println("Enter id to remove desired Concert: ");
        int removeConcert = input.nextInt();
        input.nextLine();
        Konsert concertRemove = session.get(Konsert.class, removeConcert);
        if(concertRemove != null){
            if(!concertRemove.getWcByconcertId().isEmpty()){
                if(!concertRemove.getWcByconcertId().isEmpty()){
                    for(Wc wc : concertRemove.getWcByconcertId()){
                        session.remove(wc);
                    }
                }
            }
            checkForIdAndRemove(removeConcert, concertRemove, "Concert");
            session.getTransaction().commit();
        }
    }

    private void addToConcert() {
        System.out.println("Enter Artist Name: ");
        String newArtist = input.nextLine();
        System.out.println("Enter Date: ");
        String newDate = input.nextLine();
        System.out.println("Enter Ticket Price: ");
        double newTicketPrice= input.nextInt();
        input.nextLine();
        System.out.println("Enter Age Limit: ");
        int newAgeLimit = input.nextInt();
        input.nextLine();
        System.out.println("Enter FK: ");
        int newFK = input.nextInt();
        input.nextLine();

        Arena arena = session.get(Arena.class, newFK);

        if(arena != null){
            Konsert concert = new Konsert();
            concert.setArtistnamn(newArtist);
            concert.setDatum(Date.valueOf(newDate));
            concert.setBiljettpris(newTicketPrice);
            concert.setAgeLimit(newAgeLimit);
            concert.setArena(arena);

            commitConcertTransactionIfTrue(concert);
        }
    }

    private void selectFunctionAddress() {
        if(this.controll == 1){
            System.out.println("add info");
            addToAddress();
        } else if (this.controll == 2) {
            System.out.println("check info");
            readFromAddress();
        } else if (this.controll == 3) {
            System.out.println("update info");
            updateInAddress();
        } else if (this.controll == 4) {
            System.out.println("delete info");
            removeFromAddress();
        }
    }

    private void updateInAddress() {
        System.out.println("Enter id to update desired address: ");
        int updateAddress = input.nextInt();
        input.nextLine();
        Adress addressUpdate = session.get(Adress.class, updateAddress);

        if(addressUpdate != null){
            System.out.println("Enter Street Name: ");
            String newStreet = input.nextLine();
            System.out.println("Enter house number: ");
            String newHouseNumber = input.nextLine();
            System.out.println("Enter post number: ");
            int newPostNumber= input.nextInt();
            input.nextLine();
            System.out.println("Enter Area: ");
            String newArea = input.nextLine();

            addressUpdate.setGata(newStreet);
            addressUpdate.setHusnummer(newHouseNumber);
            addressUpdate.setPostnummer(newPostNumber);
            addressUpdate.setOrt(newArea);

            commitAddressTransactionIfTrue(addressUpdate);
        }else{
            System.out.println("Concert with ID " + updateAddress + " not found.");
        }

    }


    private void readFromAddress() {
        boolean temp = true;
        while(temp){
            switch (checkTableOrRowMenu()){
                case -1:
                    temp = false;
                    break;

                case 1:

                    TypedQuery<Adress> arenaQuery = session.createNamedQuery("Address.table", Adress.class);
                    for(Adress address : arenaQuery.getResultList()){
                        System.out.println(address);
                    }
                    break;
                case 2:
                    System.out.println("Enter Users id to check desired data");
                    int id = input.nextInt();
                    input.nextLine();

                    Adress adress = session.get(Adress.class, id);
                    TypedQuery<Adress> query = null;
                    if(adress != null) {
                        query = session.createNamedQuery("Address.byUserId", Adress.class);
                        query.setParameter("adress_id", id);

                        for(Adress row : query.getResultList()){
                            System.out.println(row);
                        }
                    }else{
                        System.out.println("No id with id-number " + id + " exists");
                    }

            }
        }
    }

    private void removeFromAddress() {
        System.out.println("Enter id to remove desired Address: ");
        int removeAddress = input.nextInt();
        input.nextLine();

        Adress addressRemove = session.get(Adress.class, removeAddress);

        if(addressRemove != null){

            if(!addressRemove.getArenasByAddressId().isEmpty()){
                for(Arena arena : addressRemove.getArenasByAddressId()){
                    if(!arena.getConcertsByArenaId().isEmpty()){
                        for(Konsert konsert : arena.getConcertsByArenaId()){
                            if(!konsert.getWcByconcertId().isEmpty()){
                                for(Wc wc : konsert.getWcByconcertId()){
                                    session.remove(wc);
                                }
                            }
                            session.remove(konsert);
                        }
                    }
                    session.remove(arena);
                }
                System.out.println("Dependent arena rows have been deleted");
            }

            if(!addressRemove.getUsersByAddressId().isEmpty()){
                for(Users users : addressRemove.getUsersByAddressId()){
                    if(!users.getWcByUserstId().isEmpty()){
                        session.remove(users);
                    }
                    session.remove(users);
                }
                System.out.println("Dependent user rows have been deleted");
            }

            checkForIdAndRemove(removeAddress, addressRemove, "Address");
        }else{
            System.out .println("Id " + removeAddress + " not found");
        }
        session.getTransaction().commit();
    }

    private void addToAddress() {
        System.out.println("Enter Street Name: ");
        String newStreet = input.nextLine();
        System.out.println("Enter house number: ");
        String newHouseNumber = input.nextLine();
        System.out.println("Enter post number: ");
        int newPostNumber= input.nextInt();
        input.nextLine();
        System.out.println("Enter Area: ");
        String newArea = input.nextLine();

        Adress address = new Adress();
        address.setGata(newStreet);
        address.setHusnummer(newHouseNumber);
        address.setPostnummer(newPostNumber);
        address.setOrt(newArea);

        commitAddressTransactionIfTrue(address);
    } // End method addToAddress

    private void selectFunctionArena() {
        if(this.controll == 1){
            System.out.println("add info");
            addToArena();
        } else if (this.controll == 2) {
            System.out.println("check info");
            readFromArena();
        } else if (this.controll == 3) {
            System.out.println("update info");
            updateInArena();
        } else if (this.controll == 4) {
            System.out.println("delete info");
            removeFromArena();
        }

    }

    private void updateInArena() {
        System.out.println("Enter id to update desired arena: ");
        int updateArena = input.nextInt();
        input.nextLine();
        Arena arenaUpdate = session.get(Arena.class, updateArena);

        if(arenaUpdate != null){

            System.out.println("Enter Arena Name: ");
            String newArena = input.nextLine();
            System.out.println("Enter utomhus or inomhus: ");
            String inOrOut = input.nextLine();
            System.out.println("Enter Foreign Key: ");
            int fkId = input.nextInt();
            input.nextLine();
            Adress adress = session.get(Adress.class, fkId);
            if(adress != null){
                arenaUpdate.setNamn(newArena);
                arenaUpdate.setInomhusUtomhus(inOrOut);
                arenaUpdate.setFkAdressId(fkId);

                commitArenaTransactionIfTrue(arenaUpdate);
            }else{
                System.out.println("Address not found");
            }
        }
    }

    private void readFromArena() {
        boolean temp = true;
        while(temp){
            switch (checkTableOrRowMenu()){
                case -1:
                    temp = false;
                    break;

                case 1:
                    TypedQuery<Arena> arenaQuery = session.createNamedQuery("Arena.table", Arena.class);
                    for(Arena arena : arenaQuery.getResultList()){
                        System.out.println(arena);
                    }
                    break;
                case 2:
                    System.out.println("Enter Users id to check desired data");
                    int id = input.nextInt();
                    input.nextLine();

                   Arena arena = session.get(Arena.class, id);
                    TypedQuery<Arena> query = null;
                    if(arena != null) {
                        query = session.createNamedQuery("Arena.byUserId", Arena.class);
                        query.setParameter("arena_id", id);

                        for(Arena row : query.getResultList()){
                            System.out.println(row);
                        }
                    }else{
                        System.out.println("No id with id-number " + id + " exists");
                    }

            }
        }
    }

    private void removeFromArena() {
        System.out.println("Enter address_id to remove desired arena: ");
        int removeArena = input.nextInt();
        input.nextLine();
        Arena arenaRemove = session.get(Arena.class, removeArena);
        if(arenaRemove != null){

            if(!arenaRemove.getConcertsByArenaId().isEmpty()){
                for(Konsert konsert : arenaRemove.getConcertsByArenaId()){
                    if(!konsert.getWcByconcertId().isEmpty()){
                        for(Wc wc : konsert.getWcByconcertId()){
                            session.remove(wc);
                        }
                    }
                    session.remove(konsert);
                }
            }
            checkForIdAndRemove(removeArena, arenaRemove, "Arena");
            session.getTransaction().commit();
        }
    }

    private void addToArena() {
        System.out.println("Enter Arena Name: ");
        String newArena = input.nextLine();
        System.out.println("Enter utomhus or inomhus: ");
        String inOrOut = input.nextLine();
        System.out.println("Enter Foreign Key: ");
        int fkId = input.nextInt();
        input.nextLine();

        Adress adress = session.get(Adress.class, fkId);

        if(adress != null){
            Arena arena = new Arena();
            arena.setNamn(newArena);
            arena.setInomhusUtomhus(inOrOut);
            arena.setAdress(adress);

            commitArenaTransactionIfTrue(arena);
        }else{
            System.out.println("Address not found");
        }

    }

    private void commitWcTransactionIfTrue(Wc wc) {
        if (!existsWc(wc)) {
            if (!session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            session.getTransaction();
            session.persist(wc);
            session.merge(wc);
            session.getTransaction().commit();
        }
    }

    private void commitUsersTransactionIfTrue(Users users){
        if(!existsUser(users)){
            if (!session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            session.getTransaction();
            session.persist(users);
            session.merge(users);
            session.getTransaction().commit();
        }else{
            System.out.println("The user already exists in the database.");
        }
    }

    private void commitConcertTransactionIfTrue(Konsert concert){
        if(!existsConcert(concert)){
            if (!session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            session.getTransaction();
            session.persist(concert);
            session.getTransaction().commit();
        }else{
            System.out.println("The concert already exists in the database.");
        }
    }

    private void commitArenaTransactionIfTrue(Arena arena){
        if(!existsArena(arena)){
                if (!session.getTransaction().isActive()) {
                    session.beginTransaction();
                }
            session.getTransaction();
            session.merge(arena);
            session.getTransaction().commit();
        }else{
            System.out.println("The address already exists in the database.");
        }
    }

    private void commitAddressTransactionIfTrue(Adress address) {
        if(!existsAddress(address)){
            if (!session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            session.getTransaction();
            session.persist(address);
            session.getTransaction().commit();
        }else{
            System.out.println("The arena already exists in the database.");
        }
    }

    private boolean existsWc(Wc wc) {
        Query query = session.createQuery("SELECT COUNT(*) FROM Wc WHERE fkUserId = :fkUserId AND" +
                " fkConcertId = :fkConcertId  AND wcId != :wcId");
        query.setParameter("fkUserId", wc.getFkUserId());
        query.setParameter("fkConcertId", wc.getFkConcertId());
        query.setParameter("wcId", wc.getWcId());

        long count = (long) query.getSingleResult();
        return count > 0;
    }

    private boolean existsUser(Users user){
        Query query = session.createQuery("SELECT COUNT(*) FROM Users WHERE username = :username AND" +
                " password = :password AND isEmployee = :isEmployee AND firstname = :firstname AND " +
                "lastname = :lastname AND dateofbirth = :dateofbirth  AND phonenumber = :phonenumber " +
                "AND fkAddressId = :fkAddressId AND userId != :userId");
        query.setParameter("username", user.getUsername());
        query.setParameter("password", user.getPassword());
        query.setParameter("isEmployee", user.getIsEmployee());
        query.setParameter("firstname", user.getFirstname());
        query.setParameter("lastname", user.getLastname());
        query.setParameter("dateofbirth", user.getDateofbirth());
        query.setParameter("phonenumber", user.getPhonenumber());
        query.setParameter("fkAddressId", user.getFkAddressId());
        query.setParameter("userId", user.getUserId());

        long count = (long) query.getSingleResult();
        return count > 0;
    }
    private boolean existsConcert(Konsert concert){
        Query query = session.createQuery("SELECT COUNT(*) FROM Konsert WHERE artistnamn = :artistnamn AND" +
                " datum = :datum AND biljettpris = :biljettpris AND ageLimit = :åldersgräns AND " +
                "fkArenaId = :fkArenaId AND konsertId != :konsertId");
        query.setParameter("artistnamn", concert.getArtistnamn());
        query.setParameter("datum", concert.getDatum());
        query.setParameter("biljettpris", concert.getBiljettpris());
        query.setParameter("åldersgräns", concert.getAgeLimit());
        query.setParameter("fkArenaId", concert.getFkArenaId());
        query.setParameter("konsertId", concert.getKonsertId());

        long count = (long) query.getSingleResult();
        return count > 0;
    }
    private boolean existsAddress(Adress address){
        Query query = session.createQuery("SELECT COUNT(*) FROM Adress WHERE gata = :gata AND" +
                " husnummer = :husnummer AND postnummer = :postnummer AND ort = :ort AND adressId != :adressId");
        query.setParameter("gata", address.getGata());
        query.setParameter("husnummer", address.getHusnummer());
        query.setParameter("postnummer", address.getPostnummer());
        query.setParameter("ort", address.getOrt());
        query.setParameter("adressId", address.getAdressId());

        long count = (long) query.getSingleResult();
        return count > 0;
    }
    private boolean existsArena(Arena arena){
        Query query = session.createQuery("SELECT COUNT(*) FROM Arena WHERE namn = :namn AND" +
                " inomhusUtomhus = :inomhusUtomhus AND fkAdressId = :fkAdressId AND arenaId != :arenaId");
        query.setParameter("namn", arena.getNamn());
        query.setParameter("inomhusUtomhus", arena.getInomhusUtomhus());
        query.setParameter("fkAdressId", arena.getFkAdressId());
        query.setParameter("arenaId", arena.getArenaId());

        long count = (long) query.getSingleResult();
        return count > 0;
    }

    private <T> void checkForIdAndRemove(int id, T entityRemove, String table) {
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        session.persist(entityRemove);
        if(entityRemove != null){
            session.getTransaction();
            session.remove(entityRemove);
            System.out.println("Removed");
        }else{
            System.out.println(table + " with ID " + id + " not found");
        }
    }

    private int selectTable(){
        System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n", "1. Address", "2. Arena",
                "3. Concert", "4. User", "5. WC", "0. Go back");
        return command(input);
    }
    private int adminMenuOptions() {
        System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n", "1. Check bookings", "2. Add information",
                "3. Check general information", "4. Update information", "5. Delete information", "0. Log out");
        return command(input);

    }// End menu method.

    private int checkTableOrRowMenu() {
        System.out.printf("%s%n%s%n%s%n%s%n", "Table or Row", "1. Check whole table",
                "2. Check row based on id", "0. Go back");
        return command(input);
    }

    private int command(Scanner input) {
        int menuSelection = 0;
        while (true) {
            String command = input.nextLine();
            try {
                menuSelection = Integer.parseInt(command);
                if (menuSelection == 0) {
                    return -1;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("incorrect input");
            }

        }
        return menuSelection;
    }
}
