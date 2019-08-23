package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Film findFilmById(int filmId) {
		String sql = "SELECT film.* FROM film WHERE film.id = ?";
		Connection conn = null;
		Film film = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();
			if (fr.next()) {
				film = new Film(fr.getInt("id"), fr.getString("title"), fr.getString("description"), fr.getInt("release_year"), fr.getInt("language_id"), fr.getInt("rental_duration"), fr.getDouble("rental_rate"), fr.getInt("length"), fr.getDouble("replacement_cost"), fr.getString("rating"), fr.getString("special_features"));
			}
			fr.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		String sql = "SELECT actor.* FROM actor WHERE actor.id = ?";
		Connection conn = null;
		Actor actor = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"), actorResult.getString("last_name"));
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> la = new ArrayList<>();
		String sql = "SELECT actor.* "
						+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
						+ " JOIN film ON film.id = film_actor.film_id "
					 + "WHERE film.id = ?";
		Connection conn = null;
		Actor actor = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorResult = stmt.executeQuery();
			while (actorResult.next()) {
				actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"), actorResult.getString("last_name"));
				la.add(actor);
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return la;
	}

}