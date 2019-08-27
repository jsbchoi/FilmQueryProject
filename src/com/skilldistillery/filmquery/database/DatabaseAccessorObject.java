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
		if (filmId <= 0) {
			return null;
		}
		String sql = "SELECT film.* "
						+ "FROM film "
						+ "WHERE film.id = ?";
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
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		if (actorId <= 0) {
			return null;
		}
		String sql = "SELECT actor.* "
						+ "FROM actor "
						+ "WHERE actor.id = ?";
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
		if (filmId <= 0) {
			return null;
		}
		List<Actor> listOfActors = new ArrayList<>();
		String sql = "SELECT actor.* "
						+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
						+ "WHERE film_actor.film_id = ?";
		Connection conn = null;
		Actor actor = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorResult = stmt.executeQuery();
			while (actorResult.next()) {
				actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"), actorResult.getString("last_name"));
				listOfActors.add(actor);
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfActors;
	}
	
	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> listOfFilms = new ArrayList<>();
		String sql = "SELECT film.* "
						+ "FROM film "
						+ "WHERE film.title LIKE ? OR film.description LIKE ?";
		Connection conn = null;
		Film film = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet fr = stmt.executeQuery();
			while (fr.next()) {
				film = new Film(fr.getInt("id"), fr.getString("title"), fr.getString("description"), fr.getInt("release_year"), fr.getInt("language_id"), fr.getInt("rental_duration"), fr.getDouble("rental_rate"), fr.getInt("length"), fr.getDouble("replacement_cost"), fr.getString("rating"), fr.getString("special_features"));
				listOfFilms.add(film);
			}
			fr.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfFilms;
	}

	@Override
	public String getLanguageOfFilm(int filmId) {
		if (filmId <= 0) {
			return null;
		}
		String sql = "SELECT language.name "
						+ "FROM film JOIN language ON film.language_id = language.id "
						+ "WHERE film.id = ?";
		Connection conn = null;
		String language = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();
			if (fr.next()) {
				language =  fr.getString("language.name");
			}
			fr.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return language;
	}

	@Override
	public String getCategoryOfFilm(int filmId) {
		if (filmId <= 0) {
			return null;
		}
		String sql = "SELECT category.name "
				+ "FROM film JOIN film_category ON film.id = film_category.film_id "
						  + "JOIN category ON category.id = film_category.category_id "
				+ "WHERE film.id = ?";
		Connection conn = null;
		String category = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();
			if (fr.next()) {
				category =  fr.getString("category.name");
			}
			fr.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public List<String> getInventoryStatusOfFilm(int filmId) {
		if (filmId <= 0) {
			return null;
		}
		List<String> listOfLocations = new ArrayList<>();
		String sql = "SELECT inventory_item.film_id, inventory_item.store_id, inventory_item.media_condition "
				+ "FROM inventory_item "
				+ "WHERE inventory_item.film_id = ?";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();
			while (fr.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Film id #").append(filmId).append(" available at store #").append(fr.getString("inventory_item.store_id")).append(" Condition: ").append(fr.getString("inventory_item.media_condition"));
				listOfLocations.add(sb.toString());
			}
			fr.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfLocations;
	}
	
}
