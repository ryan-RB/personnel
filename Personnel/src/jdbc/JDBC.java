package jdbc;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Date;

import personnel.*;

public class JDBC implements Passerelle 
{
    Connection connection;

    public JDBC()
    {
        try
        {
            Class.forName(Credentials.getDriverClassName());
            connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Pilote JDBC non installÃ©.");
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible
    {
        GestionPersonnel gestionPersonnel = new GestionPersonnel();
        try 
        {
            String requete = "select * from ligue";
            Statement instruction = connection.createStatement();
            ResultSet ligues = instruction.executeQuery(requete);
            
            while (ligues.next()) {
                gestionPersonnel.addLigue(ligues.getInt("id_ligue"), ligues.getString("nom"));
                PreparedStatement req = connection.prepareStatement("SELECT * FROM employé WHERE id_ligue = ?");
                req.setInt(1, ligues.getInt("id_ligue"));
                ResultSet employé = req.executeQuery();
                Ligue ligue = gestionPersonnel.getLigues().last();


                while (employé.next())
                {
                    int		 id = employé.getInt("id_employé");
                    String    nom = employé.getString("nom_employé");
                    String    prenom = employé.getString("prénom_employé"); 
                    String    mail = employé.getString("email");
                    String    psw = employé.getString("password_employé");
                    int 	type = employé.getType();
                    LocalDate    arrive = employé.getDate("date_entrée") != null ? LocalDate.parse(employé.getString("date-entrée")) : null;
                    LocalDate    depart = employé.getDate("date_sortie") != null ? LocalDate.parse(employé.getString("date_sortie")) : null;

                    Employe emp = ligue.addEmploye(nom, prenom, mail, psw, arrive, depart, id);

                    if (employé.getBoolean("admin_ligue"))
                        ligue.setAdministrateur(emp);
        }
            }
        }

        catch (SQLException e)
        {
            System.out.println(e);
            throw new SauvegardeImpossible(e);
        }
        return gestionPersonnel;
    }

    @Override
    public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
    {
        close();
    }

    public void close() throws SauvegardeImpossible
    {
        try
        {
            if (connection != null)
                connection.close();
        }
        catch (SQLException e)
        {
            throw new SauvegardeImpossible(e);
        }
    }

    @Override
    public int insert(Ligue ligue) throws SauvegardeImpossible
{
        try 
        {
            PreparedStatement instruction;
            instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
            instruction.setString(1, ligue.getNom());
            instruction.executeUpdate();
            ResultSet id = instruction.getGeneratedKeys();
            id.next();
            return id.getInt(1);
        } 
        catch (SQLException exception) 
        {
            exception.printStackTrace();
            throw new SauvegardeImpossible(exception);
        }
    }

	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insertRoot(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}
}
