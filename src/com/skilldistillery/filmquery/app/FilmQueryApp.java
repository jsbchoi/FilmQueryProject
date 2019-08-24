package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//	private void test() {
//		Film film = db.findFilmById(1);
//		System.out.println(film);
//		Actor actor = db.findActorById(1);
//		System.out.println(actor);
//		List<Actor> la = db.findActorsByFilmId(1);
//		System.out.println(la);
//	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean goAgain = true;
		int userChoice = 0;
		do {
			if (userChoice < 0 || userChoice > 3) {
				System.out.println("Please enter a valid number between 1 and 3");
			}
			System.out.println("Film query app");
			System.out.println("1. Look up film by id");
			System.out.println("2. Look up film by keyword");
			System.out.println("3. Quit");
			System.out.print("Your choice: ");
			try {
				if (input.hasNextInt()) {
					userChoice = input.nextInt();
				} else {
					goAgain = true;
					userChoice = -1;
					throw new InputMismatchException(); 
				}
			} catch (InputMismatchException e) {
				userChoice = -1;
				input.next();
			}
			switch (userChoice) {
			case 1:
				displayFilmData(db.findFilmById(getIntInput(input)));
				break;
			case 2:
				displayListData(db.findFilmByKeyword(getStringInput(input)));
				break;
			case 3:
				goAgain = false;
				break;
			default:
				break;
			}
		} while (goAgain);
	}

	private void displayFilmData(Film film) {
		if (film == null) {
			System.out.println("Film not found");
			System.out.println();
		} else {
			System.out.println("Title: " + film.getTitle() + "\nRelease year: " + film.getReleaseYear() + "\nRating: "
					+ film.getRating() + "\nDescription: " + film.getDescription() + "\nLanguage: " + db.getLanguageOfFilm(film.getId()));
			System.out.print("Starring: ");
			List<Actor> listOfActors = db.findActorsByFilmId(film.getId());
			for (int i = 0; i < listOfActors.size(); i++) {
				if (i == listOfActors.size() - 1) {
					System.out.print(listOfActors.get(i).getFirstName() + " " + listOfActors.get(i).getLastName());
					break;
				}
				System.out.print(listOfActors.get(i).getFirstName() + " " + listOfActors.get(i).getLastName() + ", ");
			}
			System.out.println();
			System.out.println();
		}
	}

	private void displayListData(List<Film> listOfFilms) {
		if (listOfFilms.size() == 0) {
			System.out.println("Film not found");
			System.out.println();
			return;
		}
		for (Film f : listOfFilms) {
			displayFilmData(f);
		}
		System.out.println();
	}
	
	private int getIntInput(Scanner input) {
		int userInput = 0;
		do {
			System.out.print("Please enter a number: ");
			if (input.hasNextInt()) {
				userInput = input.nextInt();
			} else {
				userInput = -1;
				input.next();
			}
		} while (userInput < 0);
		return userInput;
	}

	private String getStringInput(Scanner input) {
		System.out.print("Enter a search keyword: ");
		return input.next();
	}

}
