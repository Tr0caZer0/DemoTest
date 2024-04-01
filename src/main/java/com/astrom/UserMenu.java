package com.astrom;


import com.astrom.account.AdminAccount;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Scanner;

public class UserMenu {
    private final Scanner input;
    private Session session;

    private String  userName;
    private String userPassword;
    public UserMenu(Session session){
        this.input = new Scanner(System.in);
        this.session = session;
    }

    public void menu(){

        while(true) {
            switch(menuOptions()) {
                case -1:
                    System.exit(0);
                    break;
                case 1:

                    if(userDetails()){
                        if(emplyeeOrCustomer()){
                            System.out.println("Admin");
                            AdminAccount adminAccount = new AdminAccount(session);
                            adminAccount.adminMenu();
                        }else{
                            System.out.println("Customer");
                        }
                    }
                    // Login
                    // If admin or user in table
                    break;
                case 2:
                    // register
                    break;

            }
        }
    }

    private boolean userDetails() {
        System.out.println("Enter your user name");
        this.userName = input.nextLine();
        System.out.println("Enter your password");
        this.userPassword = input.nextLine();

        if(checkForUser()){
            System.out.println("Valid");
            return true;

        }else{
            System.out.println("The username or password is not correct");
        }

        return false;
    }

    private boolean emplyeeOrCustomer() {
        Query query  = this.session.createNativeQuery(
                "SELECT COUNT(*) FROM wigells_concert.users " +
                        "WHERE users.username=:loginName AND users.password=:loginPassword AND users.is_employee = true");
        query.setParameter("loginName", this.userName);
        query.setParameter("loginPassword", this.userPassword);

        int check = ((Number) query.getSingleResult()).intValue();

        return check == 1;
    }

    private boolean checkForUser() {
        Query query  = this.session.createNativeQuery(
                "SELECT COUNT(*) FROM wigells_concert.users " +
                        "WHERE users.username=:loginName AND users.password=:loginPassword ");
        query.setParameter("loginName", this.userName);
        query.setParameter("loginPassword", this.userPassword);

        int check = ((Number) query.getSingleResult()).intValue();

        return check == 1;

    }

    private int menuOptions() {
        int menuSelection = 0;
        while(true) {
            System.out.printf("%s%n%s%n%s%n%s%n", "Please log in or register an account", "1. Login", "2. Register", "0. Exit");

            String command = input.nextLine();
            try {
                menuSelection = Integer.parseInt(command);
                if(menuSelection == 0) {
                    return -1;
                }
                break;
            }catch(NumberFormatException e) {
                System.out.println("incorrect input");
            }

        }
        return menuSelection;
    }
}
