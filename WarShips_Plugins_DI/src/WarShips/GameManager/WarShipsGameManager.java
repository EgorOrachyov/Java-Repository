package WarShips.GameManager;

import WarShips.*;
import WarShips.Behavior.HumanDecisionMaker;
import WarShips.Behavior.DecisionMaker;
import WarShips.InputManager.BasicInputManager;
import WarShips.InputManager.ConsoleInputManager;
import java.util.ArrayList;
import Plugins.PluginsLoader;

import static WarShips.Common.*;
import static WarShips.Behavior.ShipPositionGenerator.*;


/**
 *  WarShipsGameManager implements common war ships' game logic,
 *  provides menu by using inputManager and runs
 *  main loop of game
 */
public class WarShipsGameManager implements BasicGameManager {

	public WarShipsGameManager() {

		final String AIPluginsFile = "./Plugins/AI/WarShips.Behavior.DecisionMaker";

		isDone = false;
		players = new ArrayList<>();

		PluginsLoader<DecisionMaker> pluginsLoader = new PluginsLoader<>();
		pluginsLoader.load(AIPluginsFile);

		while (pluginsLoader.hasNext()) {
			players.add(new Player(pluginsLoader.getInstance()));
		}

		players.add(new Player(new HumanDecisionMaker()));

		playersCount = players.size();
		inputManager = new ConsoleInputManager();
	}

	public void Run() {
		inputManager.outString("\n");
		inputManager.outString(getDescription());
		inputManager.outString("\n\n");
		inputManager.outString("Legend:\no - empty place\n■ - ship\n* - fire\nX - destroyed slot\n");
		inputManager.outString("\n");

		MainMenu();
	}

	private void MainMenu() {
		final int MAIN_RUN_GAME = 1;
		final int MAIN_SHOW_PLAYERS = 2;
		final int MAIN_EXIT = 3;

		while (!isDone) {

			boolean isRead = false;
			int action = -1;

			while (!isRead) {
				inputManager.outString("1. Run game\n" + "2. Show players' list\n" + "3. Exit\n");
				action = inputManager.getParam();

				if (action == MAIN_RUN_GAME || action == MAIN_SHOW_PLAYERS || action == MAIN_EXIT) {
					isRead = true;
				}
			}

			switch (action) {

				case MAIN_RUN_GAME:
					PreGameMenu();
					break;

				case MAIN_SHOW_PLAYERS:
					for (int i = 0; i < playersCount; i++) {
						inputManager.outString((i + 1) + ". Player: \n");
						inputManager.outPlayerInfo(players.get(i));
						inputManager.outString("\n");
					}
					break;

				case MAIN_EXIT:
					isDone = true;
					break;
			}

		}
	}

	private void PreGameMenu() {
		final int PRE_MAIN_CHOOSE_PLAYER = 1;
		final int PRE_MAIN_NEW_PLAYER = 2;
		final int PRE_MAIN_MAIN_MENU = 3;

		int action = -1;
		boolean isRead = false;

		while (!isRead) {
			inputManager.outString("1. Choose player\n" + "2. New player\n" + "3. Main menu\n");
			action = inputManager.getParam();

			if (action == PRE_MAIN_CHOOSE_PLAYER || action == PRE_MAIN_NEW_PLAYER || action == PRE_MAIN_MAIN_MENU) {
				isRead = true;
			}
		}

		switch (action) {
			case PRE_MAIN_CHOOSE_PLAYER:
				ChoosePlayer();
				break;


			case PRE_MAIN_NEW_PLAYER:
				CreatePlayer();
				break;

			case PRE_MAIN_MAIN_MENU:
				return;
		}

		ChooseEnemy();
		MainLoop(human, enemy);
	}

	private void ChoosePlayer() {
		int action = -1;
		boolean isRead = false;

		while (!isRead) {
			inputManager.outString("Choose player\n");
			for (int i = 0; i < playersCount; i++) {
				if (!players.get(i).isArtificial()) {
					inputManager.outString((i + 1) + ". Player: \n");
					inputManager.outPlayerInfo(players.get(i));
					inputManager.outString("\n");
				}
			}

			action = inputManager.getParam();
			if (action >= 1 && action <= playersCount && !players.get(action - 1).isArtificial()) {
				isRead = true;
			}
		}

		human = players.get(action - 1);
	}

	private void CreatePlayer() {
		inputManager.outString("Enter name\n");
		human = new Player(inputManager.getString(), new HumanDecisionMaker());
		players.add(human);
		playersCount += 1;
	}

	private void ChooseEnemy() {
		int action = -1;
		boolean isRead = false;

		while (!isRead) {
			inputManager.outString("Choose desired enemy\n");
			for (int i = 0; i < playersCount; i++) {
				if (players.get(i).isArtificial()) {
					inputManager.outString((i + 1) + ". Player: \n");
					inputManager.outPlayerInfo(players.get(i));
					inputManager.outString("\n");
				}
			}

			action = inputManager.getParam();
			if (action >= 1 && action <= playersCount && players.get(action - 1).isArtificial()) {
				isRead = true;
			}
		}

		enemy = players.get(action - 1);
	}

	private void MainLoop(Player human, Player enemy) {
		Field humanField = new Field();
		Field enemyField = new Field();
		Message event = new Message();

		inputManager.outString("Enter 1 to build map yourself of 0 to generate map in random\n");
		if (inputManager.getParam() == 1) {
			setShip(HEAVY_SHIP_COUNT, HEAVY_SHIP, "\nSet Heavy ship\n", humanField);
			setShip(STANDARD_SHIP_COUNT, STANDARD_SHIP, "\nSet Standard ship\n", humanField);
			setShip(MIDDLE_SHIP_COUNT, MIDDLE_SHIP, "\nSet Middle ship\n", humanField);
			setShipIgnoreOrientation(SMALL_SHIP_COUNT, SMALL_SHIP, "\nSet Small ship\n", humanField);
		}
		else {
			setShipsRandom(humanField);
		}

		inputManager.outString("Your map\n\n");
		inputManager.outFieldHitsInfo(humanField);
		inputManager.outString("\n");

		event.push(humanField, enemyField, ACTION_SET_SHIP, null);
		enemy.handleMessage(event);

		while (!humanField.allShipsDestroyed() && !enemyField.allShipsDestroyed()) {

			do {

				boolean isFireMade = false;
				while (!isFireMade) {
					inputManager.outString("\nEnemy's map (your score = " + human.getScore() + ", fires = " + human.getFires() + ")\n\n");
					inputManager.outFieldFiresInfo(enemyField);
					inputManager.outString("\nMake fire\n");
					inputManager.outString("Enter x pos y pos of fire (or enter -1 -1 to surrender)\n");

					int[] a = inputManager.getParams(2);
					if (a != null) {

						if (a[0] == -1 && a[1] == -1) {
							event.push(enemyField, humanField, ACTION_SURRENDER, null);
						} else {
							event.push(enemyField, humanField, ACTION_FIRE, a);
						}
						human.handleMessage(event);

						if (human.getStatus()) {
							isFireMade = true;
						} else {
							inputManager.outString("Try again\n");
						}
					} else {
						inputManager.outString("Check your input\n");
					}
				}

			} while (enemyField.getLastFireStatus() == SLOT_DESTROYED && !enemyField.allShipsDestroyed());

			if (enemyField.allShipsDestroyed() || humanField.allShipsDestroyed()) {
				break;
			}

			do {
				event.push(humanField, enemyField, ACTION_FIRE, null);
				enemy.handleMessage(event);
				inputManager.outString("\nEnemy are making a step...\n");
				inputManager.outString("Your map (enemy's score = " + enemy.getScore() + ", fires = " + enemy.getFires() + ") \n\n");
				inputManager.outFieldHitsInfo(humanField);
			} while (humanField.getLastFireStatus() == SLOT_DESTROYED && !humanField.allShipsDestroyed());
		}

		if (humanField.allShipsDestroyed()) {
			inputManager.outString("\n-------- You lost --------\n\n");
			enemy.addWins();
		}

		if (enemyField.allShipsDestroyed()) {
			inputManager.outString("\n-------- You win --------\n\n");
			human.addWins();
		}
	}

	private void setShip(int count, int type, String text, Field f) {
		Message event = new Message();

		int setShips = 0;
		while (setShips < count) {
			inputManager.outFieldHitsInfo(f);
			inputManager.outString(text);
			inputManager.outString("Enter x pos, y pos, orientation (1 : Vertical, 2 : Horizontal)\n");

			int[] a = inputManager.getParams(3);
			if (a != null) {
				event.push(null, f, ACTION_SET_SHIP, new int[]{a[0], a[1], type, a[2]});
				human.handleMessage(event);

				if (human.getStatus()) {
					setShips += 1;
				} else {
					inputManager.outString("Try again\n");
				}
			} else {
				inputManager.outString("Check your input\n");
			}
		}
	}

	private void setShipIgnoreOrientation(int count, int type, String text, Field f) {
		Message event = new Message();

		int setShips = 0;
		while (setShips < count) {
			inputManager.outFieldHitsInfo(f);
			inputManager.outString(text);
			inputManager.outString("Enter x pos, y pos\n");

			int[] a = inputManager.getParams(2);
			if (a != null) {
				event.push(null, f, ACTION_SET_SHIP, new int[]{a[0], a[1], type, ORIENTATION_VERTICAL});
				human.handleMessage(event);

				if (human.getStatus()) {
					setShips += 1;
				} else {
					inputManager.outString("Try again\n");
				}
			} else {
				inputManager.outString("Check your input\n");
			}
		}
	}

	public String getDescription() {
		return "War Ships (c) 2018 ";
	}

	private boolean isDone;
	private Player human;
	private Player enemy;
	private int playersCount;
	private ArrayList<Player> players;
	private BasicInputManager inputManager;
}
