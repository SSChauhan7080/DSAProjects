package Views;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import db.DatabaseManager;
import model.data;

public class UserView {
    private String email;
    UserView(String email){
        this.email=email;
    }
    public void home(){
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        do{
            System.out.println("WELCOME: "+this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new files");
            System.out.println("Press 3 to unhide a files");
            System.out.println("Press 0 to exists");
            Scanner sc=new Scanner(System.in);
            int ch=Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1:
                    try {
                        List<data> files=DatabaseManager.getAllfiles(this.email);
                        System.out.println("ID-File Name");
                        for(data file: files){
                            System.out.println(file.getId()+" - "+file.getName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                System.out.println("Enter the file path ");
                String path=sc.nextLine();
                File f=new File(path);
                data filee=new data(0, f.getName(), path, this.email);
                    try {
                        DatabaseManager.hideFiles( filee);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                try {
                List<data> files=DatabaseManager.getAllfiles(this.email);
                System.out.println("ID-File Name");
                for(data fil: files){
                    System.out.println(fil.getId()+" - "+fil.getName());
                }
                System.out.println("Enter the ID of file to unhided: ");
                int id=Integer.parseInt(sc.nextLine());
                boolean isValidID=false;
                for(data file: files){
                    if(file.getId()==id){
                        isValidID=true;
                        break;
                    }
                }
                if(isValidID){
                    DatabaseManager.unhide(id);
                }else{
                    System.out.println("wrong ID ");
                } } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
                case 0:
                System.exit(0);
            
                default:
                    break;
            }

        }while(true);
    }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    
}
