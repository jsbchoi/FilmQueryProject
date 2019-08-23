package com.skilldistillery.filmquery.app;

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

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
		Actor actor = db.findActorById(1);
		System.out.println(actor);
		List<Actor> la = db.findActorsByFilmId(1);
		System.out.println(la);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean goAgain = true;
		int userChoice = 0;
		do {
			System.out.println("Film query app");
			System.out.println("1. Look up film by id");
			System.out.println("2. Look up film by keyword");
			System.out.println("3. Quit");
			System.out.print("Your choice: ");
			try {
				if (input.hasNextInt()) {
					userChoice = input.nextInt();
					goAgain = false;
				} else {
					goAgain = true;
					userChoice = -1;
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number between 1 and 3");
				userChoice = -1;
			}
			switch (userChoice) {
			case 1:
				System.out.println(db.findFilmById(getInput(input)));
				break;
			}
		} while (goAgain);
	}

	private int getInput(Scanner input) {
		int userInput = 0;
		do {
			System.out.print("Please enter a number: ");
			if (input.hasNextInt()) {
				userInput = input.nextInt();
			} else {
				userInput = -1;
			}
		} while (userInput < 0);
		return userInput;
	}

}
