package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;
public class insuranceProduct {
	 public static void main(String[] args) {
		    int TotalGain=0;
		    int Difference=0;
		    int DifferenceThreeMonths=0;
		    int Premiums=0;
		    int ThreePremiums=0;
		    String risk=" ";
		    LocalDate startingDate = LocalDate.now().withMonth(1).with(TemporalAdjusters.firstDayOfMonth());
		    LocalDate startingDate1 = LocalDate.now().withMonth(1).with(TemporalAdjusters.firstDayOfMonth());
		    LocalDate OneMonthsLater = startingDate.plusMonths(1).minusDays(1);
		    LocalDate ThreeMonthsLater = startingDate.plusMonths(3).minusDays(1);
	        String url = "jdbc:mysql://localhost:3306/sys";
	        String username = "root";
	        String password = "Gjy123450!";

	        try {
	            try (Connection connection = DriverManager.getConnection(url, username, password)) {	                
	                try (Statement statement = connection.createStatement()) {	                    
	                    String sql = "SELECT product FROM InsuranceProduct";
	                    try (ResultSet resultSet = statement.executeQuery(sql)) {
	                    	 System.out.println("All of the product");
	                        while (resultSet.next()) {
	                            String column1 = resultSet.getString("product");
	                            System.out.println(column1);
	                        }
	                    }
	                }
	                Scanner sc=new Scanner(System.in);
	     	       System.out.println("which product do you choose:");
	     	       String letter=sc.nextLine();
	     	      System.out.println("what is your SSN(xxx-xx-xxxx)? ");
	     	     String SSN=sc.nextLine();
	     	      try (PreparedStatement preparedStatement = connection.prepareStatement(
	     	    	        "SELECT product, CoverageDetails,Deductibles, CopaymentsAndCoinsurance, OutofPocketMaximums, AdditionalBenefits, ClaimsProcess, NumberOfPeople,AmontOfPay,TotalPremiums,Premiums FROM InsuranceProduct WHERE product = ?")) {
	     	    	    preparedStatement.setString(1, letter);

	     	    	    try (ResultSet resultSet = preparedStatement.executeQuery()) {
	     	    	        while (resultSet.next()) {
	     	    	            // Process the results
	     	    	            String product = resultSet.getString("product");
	     	    	            String coverageDetails = resultSet.getString("CoverageDetails");	     	 
	     	    	            String Deductibles = resultSet.getString("Deductibles");
	     	    	            String CopaymentsAndCoinsurance = resultSet.getString("CopaymentsAndCoinsurance");
	     	    	            String OutofPocketMaximums = resultSet.getString("OutofPocketMaximums");
	     	    	            String AdditionalBenefits = resultSet.getString("AdditionalBenefits");
	     	    	            String ClaimsProcess = resultSet.getString("ClaimsProcess");
	     	    	            int NumberOfPeople=resultSet.getInt("NumberOfPeople");	     	    	            
	     	    	            int AmontOfPay=resultSet.getInt("AmontOfPay");
	     	    	            int TotalPremiums=resultSet.getInt("TotalPremiums");
	     	    	            Premiums=resultSet.getInt("Premiums");
	     	    	            LocalDate today = LocalDate.now();
	     	    	            if (today.equals(OneMonthsLater)&&today.isBefore(ThreeMonthsLater)) {	     	    	            	
	     	    	            	startingDate1=today.with(TemporalAdjusters.firstDayOfNextMonth());	 
	     	    	            	ThreePremiums=TotalPremiums+ThreePremiums;	     	    	        	  	     	    	        	 
	     	    	        	}
	     	    	            if(today.equals(ThreeMonthsLater)) {
	     	    	        		 DifferenceThreeMonths=ThreePremiums-AmontOfPay;
		     	    	        	   if(DifferenceThreeMonths<1000) {
		     	    	        		  Premiums=Premiums+(Math.abs(DifferenceThreeMonths)/NumberOfPeople);
		     	    	     	    	 }
		     	    	        	  try (PreparedStatement updateStatement = connection.prepareStatement(
		 	     	    	        	        "UPDATE customers SET Claim = 0 WHERE ClaimDateReceived IS NOT NULL")) {		 	     	   	        	        		 	     	          	        
		     	    	        			int affectedRows = updateStatement.executeUpdate();
		     	    	                   System.out.println("Updated " + affectedRows + " rows.");
		 	     	    	        	    }
		     	    	        	 try (PreparedStatement updateStatement1 = connection.prepareStatement(
		 	     	    	        	        "UPDATE InsuranceProduct SET AmontOfPay = 0 ")) {
		     	    	        		int rowsAffected = updateStatement1.executeUpdate();
		     	    	        		if (rowsAffected > 0) {
	     	    	        	            System.out.println("AmontOfPay updated to 0 for product: " + letter);
	     	    	        	        } 
		 	     	    	        	    }
		     	    	        	 try (PreparedStatement updateStatement = connection.prepareStatement(
			 	     	    	        	        "UPDATE InsuranceProduct SET TotalPremiums = 0")) {
			 	     	    	        	        int rowsAffected = updateStatement.executeUpdate();
			 	     	    	        	        if (rowsAffected > 0) {
			 	     	    	        	            System.out.println("TotalPremiums updated to 0 for product: "  + letter);
			 	     	    	        	        } 
			 	     	    	        	    }
		     	    	        	 
		     	    	        	  ThreePremiums=0;	
		     	    	        	 startingDate = today.with(TemporalAdjusters.firstDayOfNextMonth());	 
			     	    	         ThreeMonthsLater=startingDate.plusMonths(3);
		     	    	        	  
		     	    	        	 try (PreparedStatement updateStatement = connection.prepareStatement(
		 	     	    	        	        "UPDATE InsuranceProduct SET Premiums = ? WHERE product = ?")) {
		 	     	    	        	        updateStatement.setInt(1,Premiums );
		 	     	    	        	        updateStatement.setString(2, letter);
		 	     	    	        	        
		 	     	    	        	        int rowsAffected = updateStatement.executeUpdate();
		 	     	    	        	        if (rowsAffected > 0) {
		 	     	    	        	            System.out.println("Premiums updated to "+Premiums+" for product: " + letter);
		 	     	    	        	        } 
		 	     	    	        	    }
		     	    	        	 
		     	    	        	  
	     	    	        	}
	     	    	      	
	     	    	            
	     	    	          
	     	    	            System.out.println("Product: " + product);
	     	    	            System.out.println("Coverage Details: " + coverageDetails);
	     	    	            System.out.println("Deductibles: "+Deductibles);
	     	    	            System.out.println("Copayments and Coinsurance: "+CopaymentsAndCoinsurance);
	     	    	            System.out.println("Out of Pocket Maximums: "+OutofPocketMaximums);
	     	    	            System.out.println("Additional Benefits: "+AdditionalBenefits);
	     	    	            System.out.println("Claims Process: "+ClaimsProcess);
	     	    	            System.out.println("Premiums: "+ Premiums);
	     	    	        }
	     	    	    }
	     	    	}
	     	     
	     	     try (PreparedStatement statement3 = connection.prepareStatement(
  	        		    "SELECT Risk FROM RiskOfDisease WHERE SSN = ?")) {
  	        		    statement3.setString(1, SSN);

  	        		    try (ResultSet resultSet1 = statement3.executeQuery()) {
  	        		        if (resultSet1.next()) { 
  	        		              risk = resultSet1.getString("Risk");
  	        		            
  	        		        }
  	        		    }
  	        		}
	     	    if(risk.equals("High Risk")) {
      		    	Premiums=Premiums+50;
      		    	System.out.println("Premiums: "+Premiums);
      		    	try (PreparedStatement updateStatement = connection.prepareStatement(
    	        	        "UPDATE customers SET Premiums = ? WHERE SSN = ?")) {
    	        	        updateStatement.setInt(1, Premiums);
    	        	        updateStatement.setString(2, SSN);
    	        	        int rowsAffected = updateStatement.executeUpdate();

    	        	        if (rowsAffected > 0) {
    	        	            System.out.println("Premiums +50 for product " + letter);
    	        	        } 
    	        	    }
      		    }
	     	    

	            }
	            
	        } catch (SQLException e) {
	            System.out.println("Error connecting to and querying from the database");
	            e.printStackTrace();
	        }
	   
	       
	       
	    }
}

