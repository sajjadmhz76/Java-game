import java.util.*;

public class myapp {
String ground_type;
int higher_point = 0;
int team1_point = 0;
int team2_point = 0;
int team1_win = 0;
int team2_win = 0;

String player_name = "";
boolean last_point_team1 = false;
boolean last_point_team2 = false;
ArrayList<ground> myground = new ArrayList<>();
Random random = new Random();
ArrayList<card> card_list = new ArrayList<>();
ArrayList<player> player_list = new ArrayList<>();
Scanner input = new Scanner(System.in);
int hakem_index = -1;
String hokm = " ";
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String TYPE_DEL = ANSI_RED + "del";
public static final String TYPE_KHESHT = ANSI_RED + "khesht";
public static final String TYPE_PIK = ANSI_RESET + "pik";
public static final String TYPE_GISHNIZ = ANSI_RESET + "gishniz";

public myapp() {

	start();

}

public String set_types(int index) {
	String mytype = null;
	if(index == 0)
		mytype = TYPE_DEL;
	else if(index == 1)
		mytype = TYPE_GISHNIZ;
	else if(index == 2)
		mytype = TYPE_KHESHT;
	else if(index == 3)
		mytype = TYPE_PIK;
	return mytype;
}

public void generate_cards() {
	for (int i = 0; i < 52; i++) {//Generate cards !
		card_list.add(new card());
		card_list.get(i).setId(i);
	}
	for (int i = 0; i < 4; i++) {
		String card_name = set_types(i);
		for (int j = 0; j < 13; j++) {
			int index = 13 * i + j;
			card_list.get(index).setType(card_name);
			card_list.get(index).setValue(j + 2);
			card_list.get(index).setName(card_name + " " + (j + 2) + ANSI_RESET);
			if(j == 12)
				card_list.get(index).setName(card_name + " " + "Ace" + ANSI_RESET);
			if(j == 11)
				card_list.get(index).setName(card_name + " " + "King" + ANSI_RESET);
			if(j == 10)
				card_list.get(index).setName(card_name + " " + "Queen" + ANSI_RESET);
			if(j == 9)
				card_list.get(index).setName(card_name + " " + "Soldier" + ANSI_RESET);
			card_list.get(index).setIs_hokm(false);
		}
	}
}

public void generate_players() {
	for (int i = 0; i < 4; i++) {
		String playername = " ";
		if(i == 0)
			playername = player_name;
		else if(i == 1)
			playername = "player1";
		else if(i == 2)
			playername = "player2";
		else
			playername = "player3";
		player_list.add(new player());
		player_list.get(i).setName(playername);
		player_list.get(i).setWin_count(0);
		player_list.get(i).setIn_game_point(0);
		player_list.get(i).setIs_hakem(false);
	}
}


public int set_hakem() {
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
	System.out.println("Lets set Hakem !");
	Collections.shuffle(card_list);
	int j = 0;
	while (true) {
		for (int i = 0; i < player_list.size(); i++) {
			System.out.println(player_list.get(i).getName() + ANSI_RESET + " : " + card_list.get(j).getName() + ANSI_RESET);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			if(card_list.get(j).getValue() == 14) {
				return i;
			}
			j++;
		}
	}
}

public void Distributing_cards() {
	player_list.clear();
	generate_players();
	Collections.shuffle(card_list);
	for (int i = 0; i < player_list.size(); i++) {
		for (int j = 0; j < 13; j++) {
			player_list.get(i).getHand().add(card_list.get(13 * i + j));
		}
	}
}

public String set_hokm(int index) {
	player_list.get(index).setIs_hakem(true);
	String hokm = " ";
	int hokmint = 0;
	ArrayList<card> hakem_five_cards = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
		hakem_five_cards.add(player_list.get(index).getHand().get(i));
	}
	Collections.sort(hakem_five_cards);
	if(index == 0) {
		for (card hakem_five_card : hakem_five_cards) {
			System.out.println(hakem_five_card.getName());
		}
		System.out.println("You are Hakem !");
		System.out.println("What is hokm?   1.del   2.gishniz   3.khesht   4.pik");
		hokmint = getint();
	} else {
		hokmint = random.nextInt(5);
	}
	hokm = set_types(hokmint - 1);
	System.out.println("hokm is " + hokm + " !" + ANSI_RESET);
	return hokm;
}

public void show_cards_to_player() {
	Collections.sort(player_list.get(0).getHand());
	for (int i = 0; i < player_list.get(0).getHand().size(); i++) {
		String url = i + " " + player_list.get(0).getHand().get(i).getName();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		if(player_list.get(0).getHand().get(i).getType().equals(hokm))
			System.out.println(url + " (Hokm !)");
		else System.out.println(url);
	}
}

public void play_hokm() {
	for (int i = 0; i < 7; i++) {
		if(i == 0) {
			hakem_index = set_hakem();
		}
		System.out.println(player_list.get(hakem_index).getName() + " is hakem !");
		Distributing_cards();
		hokm = set_hokm(hakem_index);
		int starter = hakem_index;
		while (higher_point < 7) {
			System.out.println(" team 1: " + team1_point + " team 2:" + team2_point);
			first_round(starter);
			starter = next_turn_fixer(starter);
			first_round(starter);
			starter = next_turn_fixer(starter);
			first_round(starter);
			starter = next_turn_fixer(starter);
			first_round(starter);
			starter = analizor(starter);
			System.out.println("startrt " + starter);
		}
		if(last_point_team1)
			team1_win++;
		if(last_point_team2)
			team2_win++;
		hakem_index = set_next_hakem(hakem_index);
		last_point_team1 = false;
		last_point_team2 = false;
		higher_point = 0;
		team1_point = 0;
		team2_point = 0;
		System.out.println("round " + (i + 1) + " finished !");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Its " + "team 1 :" + team1_win+ "team 2 :" + team2_win);
	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
	System.out.println("Game over !");
	System.out.println("team 1 : " + team1_win+ "\n team 2 :" + team2_win);


	
	
	
}

public int analizor(int starter) {
	//show_ground();
	int player_point_winner = point_calculator();
	if(player_point_winner == 0 || player_point_winner == 2) {
		System.out.println(" team 1 won !");
		team1_point++;
		last_point_team1 = true;
		last_point_team2 = false;
	}
	if(player_point_winner == 1 || player_point_winner == 3) {
		System.out.println(" team 2 won !");
		team2_point++;
		last_point_team2 = true;
		last_point_team1 = false;
	}
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
	higher_point = team1_point;
	if(team2_point > higher_point)
		higher_point = team2_point;
	int first_starter = myground.get(0).getPlayer_index();
	myground.clear();
	return set_next_hakem(first_starter);
}

public int set_next_hakem(int lasthakem) {
	System.out.println("last hakem" + lasthakem);
	if(lasthakem == 0) {
		if(last_point_team1)
			return 0;
		return 1;
	}
	if(lasthakem == 1) {
		if(last_point_team2)
			return 1;
		return 2;
	}
	if(lasthakem == 2) {
		if(last_point_team1)
			return 2;
		return 3;
	}
	if(lasthakem == 3) {
		if(last_point_team2)
			return 3;
		return 0;
	}
	return -1;
}

public int point_calculator() {
	int value = 0;
	int last_index = 0;
	for (ground ground : myground) {
		if(ground_hokm_exist()) {
			if(ground.getCard_droped().getType().equals(hokm)) {
				if(ground.getCard_droped().getValue() > value) {
					value = ground.getCard_droped().getValue();
					last_index = ground.getPlayer_index();
				}
			}
		} else {
			if(ground.getCard_droped().getType().equals(ground_type)) {
				if(ground.getCard_droped().getValue() > value) {
					value = ground.getCard_droped().getValue();
					last_index = ground.getPlayer_index();
				}
			}
		}
	}
	return last_index;
}

public boolean ground_hokm_exist() {
	for (ground ground : myground) {
		if(ground.getCard_droped().getType().equals(hokm))
			return true;
	}
	return false;
}

public int next_turn_fixer(int this_turn) {
	int next = this_turn + 1;
	if(next > 3) {
		next = 0;
	}
	return next;
}

public void first_round(int starter) {
	int chosed;
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
	System.out.println("its " + player_list.get(starter).getName() + " turn ");
	if(starter == 0) {
		show_cards_to_player();
		System.out.println("choose by index !");
	}
	if(myground.size() == 0) {
		if(starter == 0)
			chosed = getint();
		else chosed = random.nextInt(player_list.get(starter).getHand().size());
	} else
		chosed = player_cheat(starter);
	card played_card = player_list.get(starter).getHand().get(chosed);
	myground.add(new ground());
	myground.get(myground.size() - 1).setCard_droped(played_card);
	myground.get(myground.size() - 1).setPlayer_index(starter);
	if(myground.size() == 1)
		ground_type = played_card.getType();/////////////////////////////////////////FIRST GROUNDDDDDDDDDDDDDDDDDDDDDDD
	player_list.get(starter).getHand().remove(chosed);
	System.out.println(player_list.get(starter).getName() + " played " + played_card.getName());
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
}

public boolean player_has_ground(int player_index) {
	for (int i = 0; i < player_list.get(player_index).getHand().size(); i++) {
		if(player_list.get(player_index).getHand().get(i).getType().equals(ground_type))
			return true;
	}
	return false;
}

public void show_ground() {
	for (ground ground : myground) {
		System.out.println(player_list.get(ground.getPlayer_index()).getName() + " played :" + ground.getCard_droped().getName());
	}
}

public int player_cheat(int starter) {
	card tester;
	boolean player_cheat = true;
	int chosed = 0;
	while (player_cheat) {
		if(starter == 0)
			chosed = getint();
		else chosed = random.nextInt(player_list.get(starter).getHand().size());
		tester = player_list.get(starter).getHand().get(chosed);
		if(player_has_ground(starter) && !tester.getType().equals(ground_type)) {
			if(starter == 0)
				System.out.println("You are cheating !");
		} else if(!player_has_ground(starter)) {
			System.out.println(player_list.get(starter).getName() + " has not have " + ground_type);
			player_cheat = false;
		} else {
			player_cheat = false;
		}
	}
	return chosed;
}

public static int getint() {
	Scanner input = new Scanner(System.in);
	while (true) {
		try {
			return input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Enter valid integer :");
			input.next();
		}
	}
}
public void start(){
	System.out.println("Enter your name :");
	player_name = input.next();
	generate_cards();
	Collections.shuffle(card_list);
	generate_players();
	while (true){
		System.out.println("Choose :   1.Play Hokm   2.Exit");
		int ans=getint();
		switch (ans){
			case 1:
				play_hokm();
				break;
//			case 2:
//				caseB();
//				break;
//			case 3:
//				caseC();
//				break;
		
		}
		if(ans==2)
			break;
	}
}
public void caseA(){
	System.out.println("case A done !");
}
public void caseB(){
	System.out.println("case B done !");
}
public void caseC(){
	System.out.println("case C done !");
}
}

