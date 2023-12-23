package database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
public class RiskOfChronicDisease {
	 public static void main(String[] args) {
		 Scanner sc=new Scanner(System.in);
		 System.out.println("SSN: ");
		 String ssn=sc.nextLine();
		 System.out.println("Unstabl Emotion Status(Y/N): ");
		 String emtion=sc.nextLine();
		 
		 System.out.println("UnhealthyEatingHabit(Y/N): ");
		 String UnhealthyEatingHabit=sc.nextLine();
		 System.out.println("InsufficientPhysicalExercise(Y/N): ");
		 String InsufficientPhysicalExercise=sc.nextLine();
		 System.out.println("SmokingAndDrinking(Y/N): ");
	     String SmokingAndDrinking=sc.nextLine();
	     System.out.println("income: ");
		 int income=sc.nextInt();
	       String url = "jdbc:mysql://localhost:3306/sys";
	       String username = "root";
	       String password = "Gjy123450!";
	       int OldEmotion=0;
	       int OldSmokingAndDrinking=0;
	       int OldInsufficientPhysicalExercise=0;
	       int OldUnhealthyEatingHabit=0;
	       int OldIncome=0;
	       int Premiums=0;
	       int rowsAffected=Integer.MIN_VALUE;
	       Date date1=null;

	        try {
	        	
	            try (Connection connection = DriverManager.getConnection(url, username, password)) {	
	            	try (PreparedStatement preparedStatement = connection.prepareStatement(
    	        	        "SELECT Income, UnhealthyEatingHabit, InsufficientPhysicalExercise, SmokingAndDrinking, UnstablEmotionStatus, StartDate FROM RiskOfDisease WHERE SSN = ?")) {
	            		preparedStatement.setString(1, ssn);
	            		 try (ResultSet resultSet1 = preparedStatement.executeQuery()) {
	            			  while (resultSet1.next()) {
	            				  OldIncome=resultSet1.getInt("Income");
	            				  OldUnhealthyEatingHabit=resultSet1.getInt("UnhealthyEatingHabit");
	            				  OldInsufficientPhysicalExercise=resultSet1.getInt("InsufficientPhysicalExercise");
	            				  OldSmokingAndDrinking=resultSet1.getInt("SmokingAndDrinking");
	            			      OldEmotion=resultSet1.getInt("UnstablEmotionStatus");
	            				  date1=resultSet1.getDate("StartDate");
	            			  }
  	        		    }
    	        	        
    	        	    }
	            	if(date1!=null) {
	            		LocalDate localDate1 = date1.toLocalDate();
	            		LocalDate today = LocalDate.now();

	            		// Add one month to localDate1
	            		LocalDate oneMonthLater = localDate1.plusMonths(1);

	            		// Compare if today's date is equal to one month later
	            	     if(today.isEqual(oneMonthLater)) {
	            	    	 System.out.print("Send to customer the premiums will increase");
	            	     }
	            	}
	            	if(emtion.equals("Y")&& OldEmotion==0) {
	            		try (PreparedStatement updateStatement = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET UnstablEmotionStatus = 1 WHERE SSN = ?")) {
	    	        	        updateStatement.setString(1, ssn);	
	    	        	         rowsAffected = updateStatement.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("UnstablEmotionStatus = 1");
 	    	        	        } 
	    	        	    }
	            	}else if(emtion.equals("N")&& OldEmotion==1) {
	            		try (PreparedStatement updateStatement1 = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET UnstablEmotionStatus = 0 WHERE SSN = ?")) {
	    	        	        updateStatement1.setString(1, ssn);	   
	    	        	         rowsAffected = updateStatement1.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("UnstablEmotionStatus = 0");
 	    	        	        } 
	    	        	    }
	            	}
	            	
	            	if(UnhealthyEatingHabit.equals("Y")&& OldUnhealthyEatingHabit==0) {
	            		try (PreparedStatement updateStatement = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET UnhealthyEatingHabit = 1 WHERE SSN = ?")) {
	    	        	        updateStatement.setString(1, ssn);	    
	    	        	         rowsAffected = updateStatement.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("UnhealthyEatingHabit = 1");
 	    	        	        } 
	    	        	    }
	            	}else if(UnhealthyEatingHabit.equals("N")&& OldUnhealthyEatingHabit==1) {
	            		try (PreparedStatement updateStatement1 = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET UnhealthyEatingHabit = 0 WHERE SSN = ?")) {
	    	        	        updateStatement1.setString(1, ssn);	    	   
	    	        	         rowsAffected = updateStatement1.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("UnhealthyEatingHabit = 0");
 	    	        	        } 
	    	        	    }
	            	}
	            	
	            	if(InsufficientPhysicalExercise.equals("Y")&& OldInsufficientPhysicalExercise==0) {
	            		try (PreparedStatement updateStatement = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET InsufficientPhysicalExercise = 1 WHERE SSN = ?")) {
	    	        	        updateStatement.setString(1, ssn);	    
	    	        	         rowsAffected = updateStatement.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("InsufficientPhysicalExercise = 1");
 	    	        	        } 
	    	        	    }
	            	}else if(InsufficientPhysicalExercise.equals("N")&& OldInsufficientPhysicalExercise==1) {
	            		try (PreparedStatement updateStatement1 = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET InsufficientPhysicalExercise = 0 WHERE SSN = ?")) {
	    	        	        updateStatement1.setString(1, ssn);	    
	    	        	         rowsAffected = updateStatement1.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("InsufficientPhysicalExercise = 0");
 	    	        	        } 
	    	        	    }
	            	}
	            	
	            	if(SmokingAndDrinking.equals("Y")&& OldSmokingAndDrinking==0) {
	            		try (PreparedStatement updateStatement = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET SmokingAndDrinking = 1 WHERE SSN = ?")) {
	    	        	        updateStatement.setString(1, ssn);	    
	    	        	         rowsAffected = updateStatement.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("SmokingAndDrinking = 1");
 	    	        	        } 
	    	        	    }
	            	}else if(SmokingAndDrinking.equals("N")&& OldSmokingAndDrinking==1) {
	            		try (PreparedStatement updateStatement1 = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET SmokingAndDrinking = 0 WHERE SSN = ?")) {
	    	        	        updateStatement1.setString(1, ssn);	  
	    	        	         rowsAffected = updateStatement1.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("SmokingAndDrinking = 0");
 	    	        	        } 
	    	        	    }
	            	}
	            	
	            	if((500<=income&&income<2000)&&(OldIncome==0|OldIncome==2) ) {
	            		try (PreparedStatement updateStatement = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET Income = 1 WHERE SSN = ?")) {
	    	        	        updateStatement.setString(1, ssn);	 
	    	        	         rowsAffected = updateStatement.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("Income = 1");
 	    	        	        } 
	    	        	    }
	            	}else if(income<500&& (OldIncome==0|OldIncome==1)) {
	            		try (PreparedStatement updateStatement1 = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET Income = 2 WHERE SSN = ?")) {
	    	        	        updateStatement1.setString(1, ssn);	 
	    	        	         rowsAffected = updateStatement1.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("Income = 2");
 	    	        	        } 
	    	        	    }
	            	}else if(2000<=income&&(OldIncome==1|OldIncome==2)) {
	            		try (PreparedStatement updateStatement1 = connection.prepareStatement(
	    	        	        "UPDATE RiskOfDisease SET Income = 0 WHERE SSN = ?")) {
	    	        	        updateStatement1.setString(1, ssn);	   
	    	        	         rowsAffected = updateStatement1.executeUpdate();

 	    	        	        if (rowsAffected > 0) {
 	    	        	            System.out.println("Income = 0");
 	    	        	        } 
	    	        	    }
	            	}
	            	
	            }
	        }catch (SQLException e) {
	            System.out.println("Error connecting to and querying from the database");
	            e.printStackTrace();
	        }
	        if(rowsAffected>0) {
	        	List<List<Object>> healthRecords = new ArrayList<>();

		        try (Connection con = DriverManager.getConnection(url, username, password);
		             Statement st = con.createStatement();
		             ResultSet rs = st.executeQuery("SELECT * FROM RiskOfDisease")) {

		            while (rs.next()) {
		                List<Object> record = new ArrayList<>();
		                record.add(rs.getString("SSN"));
		                record.add(rs.getInt("Income"));
		                record.add(rs.getInt("UnhealthyEatingHabit"));
		                record.add(rs.getInt("InsufficientPhysicalExercise"));
		                record.add(rs.getInt("SmokingAndDrinking"));
		                record.add(rs.getInt("UnstablEmotionStatus"));
		                record.add(rs.getString("Risk"));
		                record.add(rs.getInt("BurningTheMidnightOil"));
		                record.add(rs.getInt("Age"));
		                record.add(rs.getString("GeneticInformation"));
		                record.add(rs.getInt("Urban"));
		                record.add(rs.getInt("Gender"));
		                record.add(rs.getDate("StartDate"));
		                record.add(rs.getString("PredictRisk"));
		                healthRecords.add(record);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        // Use the healthRecords list as needed
		        // Example: Print out the list
		        
		        
		        ArrayList<Attribute> attributes = new ArrayList<>();
		        attributes.add(new Attribute("SSN"));
		        attributes.add(new Attribute("Income"));
		        attributes.add(new Attribute("UnhealthyEatingHabit"));
		        attributes.add(new Attribute("InsufficientPhysicalExercise"));
		        attributes.add(new Attribute("SmokingAndDrinking"));
		        attributes.add(new Attribute("UnstablEmotionStatus"));
		        attributes.add(new Attribute("BurningTheMidnightOil"));
		        attributes.add(new Attribute("Age"));
		        attributes.add(new Attribute("Urban"));
		        attributes.add(new Attribute("Gender"));
		        List<String> classVal = new ArrayList<>();
		        classVal.add("Low Risk");
		        classVal.add("High Risk");
		        attributes.add(new Attribute("Risk", classVal));

		        // Nominal attribute for "GeneticInformation"
		        List<String> classVal1 = new ArrayList<>();
		        classVal1.add("diabetes");
		        classVal1.add("tumor");
		        classVal1.add("hyperlipemia");
		        classVal1.add("obesity");
		        classVal1.add("chronic respiratory disease");
		        classVal1.add("none");
		        attributes.add(new Attribute("GeneticInformation", classVal1));

		        // Create the Instances object with the defined attributes
		        Instances dataset = new Instances("HealthDataset", attributes, 0);

		        // Set the class index to the attribute "Risk" which is used for the prediction
		        dataset.setClassIndex(dataset.attribute("Risk").index());
		        dataset.setClassIndex(dataset.attribute("GeneticInformation").index());

		        // Assume that healthRecords is already populated with your data
		        // Convert your ArrayList<List<Object>> to Instances
		        for (List<Object> record : healthRecords) {
		            DenseInstance instance = new DenseInstance(attributes.size());

		            for (int i = 0; i < record.size()-2; i++) {
		                if (record.get(i) != null) {
		                    if (record.get(i) instanceof Number) {
		                        // For numeric attributes
		                        instance.setValue(attributes.get(i), ((Number) record.get(i)).doubleValue());
		                    } else if (record.get(i) instanceof String) {
		                        // For nominal attributes
		                        String stringValue = (String) record.get(i);
		                        Attribute att = attributes.get(i);
		                        if (att.isNominal()) {
		                            // Set the value of the nominal attribute
		                            instance.setValue(att, stringValue);
		                        }
		                    } // Add more conditions if there are other data types
		                } else {
		                    // Handle missing value
		                    instance.setMissing(i);
		                }
		            }
		            dataset.add(instance);
		        }

		        
		        RandomForest rf = new RandomForest();
		        try {
		            rf.buildClassifier(dataset); // Train the model
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
               
		       try {
		    	   String updateSQL = "UPDATE RiskOfDisease SET PredictRisk = ? WHERE SSN = ?";
		    	   String updateSQL1 = "UPDATE RiskOfDisease SET StartDate  = ? WHERE SSN = ?";
		    	   String updateSQL2 = "UPDATE RiskOfDisease SET StartDate  = null WHERE SSN = ?";
		    	   String updateSQL3 = "SELECT Premiums FROM customers  WHERE SSN = ?";
		    	   String updateSQL4 = "UPDATE customers SET Premiums = ? WHERE SSN = ?";
		    	   String updateSQL5 = "SELECT StartDate FROM RiskOfDisease  WHERE SSN = ?";
	                LocalDate localDate = LocalDate.now();
	                Date sqlDate = Date.valueOf(localDate);
		    	    Connection con = DriverManager.getConnection(url, username, password);
		    	    PreparedStatement pstmt = con.prepareStatement(updateSQL);
		    	   int i=0;
		    	   Date StartDate=null;
		    	   PreparedStatement pstmt5 = con.prepareStatement(updateSQL5);
		    	   pstmt5.setString(1, ssn);
		    	   try (ResultSet resultSet1 = pstmt5.executeQuery()) {
	        		        if (resultSet1.next()) { // Make sure there's a row in the ResultSet
	        		        	 StartDate = resultSet1.getDate("StartDate");	  	        		            // Process the AmontOfPay as needed
	        		        }
	        		    }
		    	   PreparedStatement pstmt7 = con.prepareStatement(updateSQL3);
		    	    pstmt7.setString(1, ssn);
              	try (ResultSet resultSet1 = pstmt7.executeQuery()) {
	        		        if (resultSet1.next()) { // Make sure there's a row in the ResultSet
	        		        	 Premiums = resultSet1.getInt("Premiums");	  	        		            // Process the AmontOfPay as needed
	        		        }
	        		    }
		    	   
		    	   if (StartDate != null) {
		    		    // Convert Date to LocalDate
		    		    LocalDate localStartDate = StartDate.toLocalDate();
		    		    LocalDate OneMonthsLater = localStartDate.plusMonths(1);		    		    
		    		    LocalDate currentDate = LocalDate.now();
		    		    if (currentDate.isEqual(OneMonthsLater)) {
		    		    	System.out.println("High risk keep from "+StartDate+" to "+currentDate );
		    		        Premiums = Premiums + 50;  
		    		        PreparedStatement pstmt4 = con.prepareStatement(updateSQL4);
		                	pstmt4.setInt(1, Premiums);			                	
		                	pstmt4.setString(2, ssn);	
		                	int rowsAffected9 = pstmt4.executeUpdate();				             				           

    	        	        if (rowsAffected9 > 0) {
    	        	            System.out.println("Sorry customer "+ssn+" Your risk level keep high for one month. Premiums have to plus 50");
    	        	        } 
		    		    }
		    		}
		    	    for (List<Object> record : healthRecords) {
		                double label = rf.classifyInstance(dataset.instance(i));
		                String prediction = (label == 1) ? "high risk" : "low risk";
		                PreparedStatement pstmt3 = con.prepareStatement(updateSQL3);
		                String SSN1=(String)record.get(0);
			    	    pstmt3.setString(1, SSN1);
	                	try (ResultSet resultSet1 = pstmt3.executeQuery()) {
		        		        if (resultSet1.next()) { // Make sure there's a row in the ResultSet
		        		        	 Premiums = resultSet1.getInt("Premiums");	  	        		            // Process the AmontOfPay as needed
		        		        }
		        		    }
		                String recordId = (String)record.get(0);
		                if (prediction.equals("high risk")&&(record.get(13).equals("low risk"))) {
		                	 System.out.println("Send to customer: "+record.get(0)+" Alarm!!! You are in the high risk of chronic disease.");
		                	 PreparedStatement pstmt1 = con.prepareStatement(updateSQL1);
		                	 pstmt1.setDate(1, sqlDate);
				             pstmt1.setString(2, recordId);	
				             int rowsAffected1 = pstmt1.executeUpdate();				             				           

	    	        	        if (rowsAffected1 > 0) {
	    	        	            System.out.println("start sqlDate");
	    	        	        } 
		                }else if(prediction.equals("low risk")&&(record.get(13).equals("high risk"))) {
		                	System.out.println("Send to customer:"+record.get(0)+ " Congratulation! Your risk level reduce to lower. You premiums go down 50 ");
		                	PreparedStatement pstmt2 = con.prepareStatement(updateSQL2);	
		                	pstmt2.setString(1, ssn);
		                	int rowsAffected5 = pstmt2.executeUpdate();
		                	if (rowsAffected5 > 0) {
    	        	            System.out.println("set the startdate to null");
    	        	        } 
		                	Premiums=Premiums-50;
		                	PreparedStatement pstmt4 = con.prepareStatement(updateSQL4);
		                	pstmt4.setInt(1, Premiums);			                	
		                	pstmt4.setString(2, ssn);			            
		                	int rowsAffected4 = pstmt4.executeUpdate();
		                	if (rowsAffected4 > 0) {
    	        	            System.out.println("Premiums is going down");
    	        	        } 
		                	int rowsAffected2 = pstmt4.executeUpdate();
    	        	        if (rowsAffected2 > 0) {
    	        	            System.out.println("start null");
    	        	        } 
		                }
		                // Assuming the ID is stored in a specific attribute
		                pstmt.setString(1, prediction);
		                pstmt.setString(2, recordId);	
		                pstmt.executeUpdate();
		                int rowsAffected1 = pstmt.executeUpdate();

 	        	        i++;
		            }

		            pstmt.close();
		            con.close();
		       }catch (Exception e) {
		            e.printStackTrace();
		        }
		        	        
		        
		        
	        }else {
	        	 System.out.print("no update for table");
	        }
		 
	 }

}
