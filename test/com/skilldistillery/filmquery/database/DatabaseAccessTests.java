package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
  private DatabaseAccessor db;

  @BeforeEach
  void setUp() throws Exception {
    db = new DatabaseAccessorObject();
  }

  @AfterEach
  void tearDown() throws Exception {
    db = null;
  }

  @Test
  void test_data_acessor_methods_with_invalid_id_returns_null() {
    Film filmTest = db.findFilmById(-42);
    assertNull(filmTest);
    Actor actorTest = db.findActorById(-42);
    assertNull(actorTest);
    List<Actor> listTest = db.findActorsByFilmId(-42);
    assertNull(listTest);
    String languageTest = db.getLanguageOfFilm(-42);
    assertNull(languageTest);
    String categoryTest = db.getCategoryOfFilm(-42);
    assertNull(categoryTest);
    List<String> inventoryTest = db.getInventoryStatusOfFilm(-42);
    assertNull(inventoryTest);
  }
  
  @Test
  void test_find_film_by_id_returns_correct_film_object() {
	  Film film = db.findFilmById(2);
	  assertEquals("ACE GOLDFINGER", film.getTitle());
	  film = db.findFilmById(530);
	  assertEquals("LORD ARIZONA", film.getTitle());
  }

  @Test
  void test_find_actor_by_id_returns_correct_actor_object() {
	  Actor actor = db.findActorById(2);
	  assertEquals("Nick", actor.getFirstName());
	  assertEquals("Wahlberg", actor.getLastName());
	  actor = db.findActorById(30);
	  assertEquals("Sandra", actor.getFirstName());
	  assertEquals("Peck", actor.getLastName());
  }
  
  @Test
  void test_find_actors_by_film_id_returns_correct_size_list() {
	  List<Actor> listOfCast = db.findActorsByFilmId(20);
	  assertEquals(6, listOfCast.size());
  }
  
  @Test 
  void test_find_by_film_keyword_iki_returns_correct_size() {
	  List<Film> listOfFilms = db.findFilmByKeyword("iki");
	  assertEquals(4, listOfFilms.size());
  }

  @Test
  void test_get_language_of_film_returns_correct_language() {
	  String lang = db.getLanguageOfFilm(784);
	  assertEquals("Japanese", lang);
	  lang = db.getLanguageOfFilm(700);
	  assertEquals("French", lang);
  }

  @Test
  void test_get_category_of_film_returns_correct_category() {
	  String cat = db.getCategoryOfFilm(19);
	  assertEquals("Action", cat);
	  cat = db.getCategoryOfFilm(27);
	  assertEquals("Sports", cat);
  }

  
}
