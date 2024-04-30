import java.util.ArrayList;

public class player {


String name;
int win_count;
int in_game_point;
boolean is_hakem;
ArrayList<card> hand = new ArrayList<>();

public ArrayList<card> getHand() {
	return hand;
}

public void setHand(ArrayList<card> hand) {
	this.hand = hand;
}

public boolean getIs_hakem() {
	return is_hakem;
}

public void setIs_hakem(boolean is_hakem) {
	this.is_hakem = is_hakem;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getWin_count() {
	return win_count;
}

public void setWin_count(int win_count) {
	this.win_count = win_count;
}

public int getIn_game_point() {
	return in_game_point;
}

public void setIn_game_point(int in_game_point) {
	this.in_game_point = in_game_point;
}

public player() {

}
}
