

import java.sql.SQLException;

import Views.Welcome;
public class Main {  
        public static void main(String[] args) throws SQLException {
            
            Welcome w= new Welcome();
            do{
                w.welcomeScreen();
            }while(true);
        }
    
    }
    

