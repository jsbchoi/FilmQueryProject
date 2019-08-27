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
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}
	
	private void launch() {

		startUserInterface();

	}

	private void startUserInterface() {
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
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				userChoice = -1;
				input.next();
			}
			switch (userChoice) {
			case 1:
				displayFilmData(db.findFilmById(getIntInput()));
				break;
			case 2:
				displayListData(db.findFilmByKeyword(getStringInput()));
				break;
			case 3:
				goAgain = false;
				break;
			default:
				break;
			}
		} while (goAgain);
	}

	private int displayFilmData(Film film) {
		if (film == null) {
			System.out.println("Film not found");
			System.out.println();
			return 0;
		} else {
			System.out.println("Title: " + film.getTitle() + "\nRelease year: " + film.getReleaseYear() + "\nRating: "
					+ film.getRating() + "\nDescription: " + film.getDescription() + "\nLanguage: "
					+ db.getLanguageOfFilm(film.getId()));
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
			if (subMenu() == 2) {
				System.out.println(film + "Category: " + db.getCategoryOfFilm(film.getId()));
				for (String s : db.getInventoryStatusOfFilm(film.getId())) {
					System.out.println(s);
				}
				System.out.println();
				return 0;
			} else {
				return 1;
			}
		}
	}

	private void displayListData(List<Film> listOfFilms) {
		if (listOfFilms.size() == 0) {
			System.out.println("Film not found");
			System.out.println();
			return;
		}
		int returnFlag = 0;
		for (Film f : listOfFilms) {
			returnFlag = displayFilmData(f);
			if (returnFlag == 1) {
				break;
			}
		}
		System.out.println();
	}

	private int getIntInput() {
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

	private String getStringInput() {
		System.out.print("Enter a search keyword: ");
		return input.next();
	}

	private int subMenu() {
		int userInput = 0;
		do {
			System.out.println("1. Return to main menu\n2. View all film details.");
			if (userInput <= 0 || userInput > 2) {
				System.out.print("Please enter 1 or 2: ");
			}
			if (input.hasNextInt()) {
				userInput = input.nextInt();
			}
			else {
				userInput = -1;
				input.next();
			}
		} while (userInput <= 0 || userInput > 2);
		return userInput;
	}

}
