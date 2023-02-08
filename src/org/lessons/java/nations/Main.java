package org.lessons.java.nations;

public class Main {
	public static void main(String[] args) throws SQLException {
		
		
		String url = "jdbc:mysql://localhost:3306/Db_Nations";
		String user = "root";
		String password = "root";
		
		
		Scanner s = new Scanner(System.in);
		
		
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			System.out.print("Cosa vuoi cercare?");
			String cercaNazione = s.nextLine();
			String filtroAvanzato = "%" + cercaNazione + "%";
			
			String sql = "SELECT countries.country_id  as id_paese ,countries.name as nome_paese, "
					+ "regions.name as nome_regione, " + "continents.name as nome_continente\n" 
					+ "FROM countries \n"
					+ "Inner join regions \n" + "on countries.region_id  = regions.region_id  \n"
					+ "Inner join continents \n" + "on regions.continent_id = continents.continent_id \n"
					+"WHERE countries.name LIKE ?"
					+ "Order by countries.name ";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, cercaNazione);

				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {
						int id = rs.getInt(1);
						String nomeNazione = rs.getString(2);
						String nomeRegione = rs.getString(3);
						String nomeContinente = rs.getString(4);
						System.out.println(id + " - " + nomeNazione + " - " + nomeRegione + " - " + nomeContinente);
					}
				}
			}
		} catch (SQLException ex) {

			ex.printStackTrace();
		}
		s.close();

	}
}

