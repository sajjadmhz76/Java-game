public class card implements Comparable<card> {


String name;
String type;
int value;
boolean is_hokm;
int id;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public card() {
	
}

public String getType() {
	return type;
}


public void setType(String del) {
	this.type = del;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getValue() {
	return value;
}

public void setValue(int value) {
	this.value = value;
}

public boolean getIs_hokm() {
	return is_hokm;
}

public void setIs_hokm(boolean is_hokm) {
	this.is_hokm = is_hokm;
}


@Override
public int compareTo(card o) {
	return Integer.compare(this.id, o.id);
}


}
