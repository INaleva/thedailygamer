package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class socketThread extends Thread{

	private static Socket socket;
	private DataOutputStream output  = null;
	private DataInputStream input    = null; 
	private Connection sqlconnect;
	IGDBWrapper wrapper = 

	public socketThread(Socket socket) throws SQLException {
		this.socket = socket;
		//sqlconnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,"root","123456");

		IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
		wrapper.setUserkey("YOUR_API_KEY");
	}

	@Override
	public void run() {
		try 
		{
			String message = "";
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());

			while (!message.equals("shutdown")) 
			{  
				if (input.available() > 0) {
					message = input.readUTF(); 
					System.out.println(this.getName() +" accepted this message: " + message);  

					/*if (message.equals("addOrUpdateUser"))
					{
						String email = input.readUTF(); 
						String name  = input.readUTF(); 
						int score    = input.readInt();
						int success = 0; //if we later want to return feedback
						PreparedStatement statement = sqlconnect.prepareStatement("SELECT * FROM highscore WHERE email=?");
						statement.setString(1, email);

						ResultSet result = statement.executeQuery();
						if (!result.next()) {
							PreparedStatement statementB = sqlconnect.prepareStatement("INSERT INTO highscore (email,name,score)\n" +
									" VALUES(?,?,?);");
							statementB.setString(1, email);
							statementB.setString(2, name);
							statementB.setInt(3, score);
							success = statementB.executeUpdate();
						}
						else 
						{
							int oldScore=result.getInt(3); //check if new score is bigger
							if (score > oldScore) 
								updateScore(email, score);
						}
						//output.writeInt(success); //if we want to return feedback
					}

					if (message.equals("showTopTenScores"))
					{

						int i=10;
						PreparedStatement statement = sqlconnect.prepareStatement("SELECT name,score FROM highscore ORDER BY highscore.score desc LIMIT 10");
						ResultSet resultSet = statement.executeQuery();

						//send rows of names and scores
						while (resultSet.next()) { 
							output.writeUTF(resultSet.getString(1));
							output.writeUTF(resultSet.getString(2));
							i--;
						}

						//fill empty rows if size<10
						while (i>=0) { 
							output.writeUTF("null");
							output.writeUTF("null");
							i--;
						}

					}  */
				}
			} 

			System.out.println(this.getName() +" closing connection"); 
			socket.close(); 
			input.close(); 
		} 

		catch (IOException e) {
			e.printStackTrace();
		} 

	}

	/*private int updateScore(String email, int newScore) throws SQLException {
		PreparedStatement statement = sqlconnect.prepareStatement("INSERT INTO highscore (email, score) "
				+ "VALUES(?,?) ON DUPLICATE KEY UPDATE score=?");
		statement.setString(1, email);
		statement.setInt(2, newScore);
		statement.setInt(3, newScore);
		return statement.executeUpdate();
	}*/
}
