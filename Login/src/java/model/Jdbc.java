/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author me-aydin
 */
public class Jdbc {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    //String query = null;

    public Jdbc(String query) {
        //this.query = query;
    }

    public Jdbc() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void connect(Connection con) {
        connection = con;
    }

    private ArrayList rsToList() throws SQLException {
        ArrayList aList = new ArrayList();

        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String[] s = new String[cols];
            for (int i = 1; i <= cols; i++) {
                s[i - 1] = rs.getString(i);
            }
            aList.add(s);
        } // while    
        return aList;
    } //rsToList

    private String makeTable(ArrayList list, List<String> columnNames) {
        StringBuilder b = new StringBuilder();
        String[] row;
        b.append("<table border=\"3\">");
        b.append("<tr  style=\"background-color:#dfedf9\">");
        for(String columnName : columnNames){
            b.append("<td>"+columnName+"</td>");
        }
        b.append("</tr>");
        for (Object s : list) {
            b.append("<tr>");
            row = (String[]) s;
            for (String row1 : row) {
                b.append("<td>");
                if(row1 == null)
                    b.append("");
                else
                    b.append(row1);
                b.append("</td>");
            }
            b.append("</tr>\n");
        } // for
        b.append("</table>");
        return b.toString();
    }//makeHtmlTable

    private void select(String query) {
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("way way" + e);
        }
    }

    public String retrieve(String query, List<String> columnNames) throws SQLException {
        select(query);
        String res = makeTable(rsToList(),columnNames);
        statement.close();
        rs.close();
        return res;
    }

  
    public ArrayList getRawDataList(String query) throws SQLException {
        select(query);
        ArrayList lst = rsToList();
        statement.close();
        rs.close();
        return lst;
    }

    public ResultSet getUser(String user) {
        select("select * from Taxi.Customer where NAME='" + user + "'");
        return rs;
    }

    public boolean checkCustomerUsernamePassword(String user, String password) {
        boolean bool = false;
        try {
            select("select * from Taxi.Customer where NAME='" + user + "' and PASSWORD='" + password + "'");
            if (rs.next()) {
                bool = true;
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bool;
    }

    public boolean isDriverExists(String driver, String registration) {
        boolean bool = false;
        try {
            select("select * from Taxi.Drivers where NAME='" + driver + "' AND REGISTRATION='" + registration + "'");
            if (rs.next()) {
                System.out.println("TRUE");
                bool = true;
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }

    public boolean checkDriverUsernamePassword(String driver, String password) {
        boolean bool = false;
        try {
            select("select * from Taxi.Drivers where REGISTRATION='" + driver + "' AND PASSWORD='" + password + "'");
            if (rs.next()) {
                System.out.println("TRUE");
                bool = true;
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }

    public boolean userAddressExists(String user, String address) {
        boolean bool = false;
        try {
            select("select * from Taxi.Customer where NAME='" + user + "' AND ADDRESS='" + address + "'");
            if (rs.next()) {
                System.out.println("TRUE");
                bool = true;
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }

    public String leftPad(String originalString, int length,
            char padCharacter) {
        String paddedString = originalString;
        while (paddedString.length() < length) {
            paddedString = padCharacter + paddedString;
        }
        return paddedString;
    }

    public ArrayList getAnyAvailableDriver(String date, int hour, String minute) {
        ArrayList lst = new ArrayList();

        try {
            //System.out.println("--" + hour + "--");
            //System.out.println("==" + minute + "==");
            statement = connection.createStatement();
            int prevHour = hour - 1;
            if (prevHour < 0) {
                prevHour = 0;
            }
            int nextHour = hour + 1;
            if (nextHour > 23) {
                nextHour = 23;
            }
            String s = "select d2.* from taxi.drivers d2 where d2.REGISTRATION not in ( "
                    + "    select d.REGISTRATION from taxi.drivers d, taxi.journey  j "
                    + "    where d.REGISTRATION=j.REGISTRATION and date='" + date + "' and "
                    + "time>'" + leftPad((prevHour) + "", 2, '0') + ":" + minute + ":00' and "
                    + " time<'" + leftPad((nextHour) + "", 2, '0') + ":" + minute + ":00' )";
            //System.out.println("\n" + s + "\n");

            rs = statement.executeQuery(s);

            lst = rsToList();
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    public boolean isCustomerExists(String user) {
        boolean bool = false;
        try {
            select("select * from Taxi.Customer where NAME='" + user + "'");
            if (rs.next()) {
                System.out.println("TRUE");
                bool = true;
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }

    public void insertCustomer(String[] str) {
        try {
            PreparedStatement pst1 = connection.prepareStatement("SELECT max(id)+1 from Taxi.Customer");
            ResultSet rs = pst1.executeQuery();
            String customer_id = "";
            while (rs.next()) {
                customer_id = rs.getString(1);
            }
            rs.close();
            pst1.close();

            PreparedStatement ps = null;

            ps = connection.prepareStatement("INSERT INTO Taxi.Customer VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, str[0].trim());
            ps.setString(2, str[1]);
            ps.setString(3, customer_id.toString());
            //ps.setInt(3, 
            ps.setString(4, str[3]);
            ps.setString(5, str[5]);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 row added.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int insertInvoice(int journeyId, Double fare) {
        int invoiceId = -1;
        try {
            PreparedStatement pst1 = connection.prepareStatement("SELECT max(id)+1 from Taxi.Invoice");
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                invoiceId = rs.getInt(1);
            }
            rs.close();
            pst1.close();

            PreparedStatement ps = null;

            ps = connection.prepareStatement("INSERT INTO Taxi.Invoice VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, invoiceId);
            ps.setInt(2, journeyId);
            ps.setDouble(3, fare);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 row added.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invoiceId;

    }

    public void updateJourneyAsCompleted(int journeyId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE Taxi.Journey Set status=? WHERE jid=?", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, "COMPLETED");
            ps.setInt(2, journeyId);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 rows updated.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateInvoice(int invoiceId, double fare) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE Taxi.Invoice Set fare=? WHERE id=?", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, fare);
            ps.setInt(2, invoiceId);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 rows updated.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertDriver(String[] str) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO Taxi.Drivers VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, str[1].trim());
            ps.setString(2, str[0]);
            ps.setString(3, str[2]);

            ps.executeUpdate();

            ps.close();
            System.out.println("1 row added.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList getDemand(int demandId) throws SQLException {
        select("select * from Taxi.Demands where id=" + demandId);
        ArrayList demandList = rsToList();
        statement.close();
        rs.close();
        return demandList;

    }
    public ArrayList getDriver(String deriverId) throws SQLException {
        select("select * from Taxi.Drivers where registration='" + deriverId+"'");
        ArrayList demandList = rsToList();
        statement.close();
        rs.close();
        return demandList;

    }
    public ArrayList getInvoiceByJourneyId(int journeyId) throws SQLException {
        statement = connection.createStatement();
        rs = statement.executeQuery("select * from Taxi.Invoice where journey_id=" + journeyId);

        ArrayList invoiceList = rsToList();
        statement.close();
        rs.close();
        return invoiceList;

    }

    public ArrayList getInvoice(int invoiceId) throws SQLException {
        select("select * from Taxi.Invoice where id=" + invoiceId);
        ArrayList invoiceList = rsToList();
        statement.close();
        rs.close();
        return invoiceList;

    }

    public void insertDemands(String[] str) {

        try {
            PreparedStatement pst1 = connection.prepareStatement("SELECT max(id)+1 from Taxi.Demands");
            ResultSet rs1 = pst1.executeQuery();
            String demand_id = "";
            while (rs1.next()) {
                demand_id = rs1.getString(1);
            }
            pst1.close();
            rs1.close();

            PreparedStatement ps = null;

            ps = connection.prepareStatement("INSERT INTO Taxi.Demands VALUES (?,?,?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            //Random rand = new Random();
            //String rands = rand.toString();
            ps.setString(1, demand_id); //id
            ps.setString(2, str[0]); //name
            ps.setString(3, str[1]); //address 
            ps.setString(4, str[4]); //destination
            ps.setString(5, str[5]); // date
            ps.setString(6, leftPad(str[6], 2, '0') + ":" + leftPad(str[7], 2, '0') + ":00"); // time
            ps.setString(7, str[2]); //email
            ps.setString(8, str[3]); //phone
            ps.setString(9, str[8]);//distance in meters
            ps.setString(10, "INITIAL"); // default starting status
            ps.executeUpdate();

            ps.close();
            System.out.println("1 row added.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateCustomerPassword(String[] str) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE Taxi.Customer Set PASSWORD=? WHERE NAME=?", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, str[3]);
            ps.setString(2, str[0]);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 rows updated.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteDriver(String registration) {

        String query = "DELETE FROM Taxi.Drivers WHERE REGISTRATION='" + registration.trim() + "' ";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("way way" + e);
            //results = e.toString();
        }
    }

    public void deleteCustomer(String user) {

        String query = "DELETE FROM Taxi.Customer WHERE NAME = '" + user.trim() + "'";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("way way" + e);
            //results = e.toString();
        }
    }

    public void closeAll() {
        try {
            rs.close();
            statement.close();
            //connection.close();                                         
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertJourney(String[] str) {

        try {
            PreparedStatement pst1 = connection.prepareStatement("SELECT max(jid)+1 from Taxi.Journey");
            ResultSet rs1 = pst1.executeQuery();
            String journey_id = "";
            while (rs1.next()) {
                journey_id = rs1.getString(1);
            }
            pst1.close();
            rs1.close();

            PreparedStatement ps = null;

            ps = connection.prepareStatement("INSERT INTO Taxi.Journey VALUES (?,?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            /*
                inputs[0] = demandFields[3];//dest
                inputs[1] = demandFields[8];//distance
                inputs[2] = driverRegistrationNo;//registration
                inputs[3] = demandFields[2];//pickup
                inputs[4] = demandFields[4];//date
                inputs[5] = demandFields[5];//time
                inputs[6] = demandId;//demandid
             */
            ps.setString(1, journey_id); //id
            ps.setString(2, str[0]); //destination
            ps.setString(3, str[1]); //distance 
            ps.setString(4, str[2]); //registration
            ps.setString(5, str[3]); // pickup
            ps.setString(6, str[4]); // date
            ps.setString(7, str[5]); // time
            ps.setString(8, str[6]); // demandid
            ps.setString(9, "INITIAL"); //status
            ps.executeUpdate();

            ps.close();
            System.out.println("1 row added.");
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
